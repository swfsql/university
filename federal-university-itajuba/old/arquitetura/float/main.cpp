#include <iostream>
#include <string>
#include <cstdlib>
#include <cmath>
#include <bitset>
using namespace std;


const bool debug = false;

float converteNum(string input) {
    unsigned int a = 0;

    // 0 00000000 00000000000000000000000
    a += input[0] == '-' ? 1 << 31 : 0;

    // tenho o bit de sinal

    string inteiro_s = "";
    int i = -1;
    while(input[++i] && input[i] != '.') 
      if(input[i] != '+' && input[i] != '-') inteiro_s += input[i];
    
    int inteiro_i = atoi(inteiro_s.c_str());
    int j = 0;

    if (inteiro_i != 0) {
     if (debug)  cout << "\ninteiro: " << inteiro_i << "\n";

      long int inteiro_i2 = inteiro_i;
      while(inteiro_i2 >>= 1) ++j;
      // j = expoente nao polarizado
      
      if (debug)  cout << "j: " << j << "\n";


      if (debug)  cout << "partiu significando\n";
      if (debug)  cout << "j: " << j << "\n";
      int shift = 23 - j;
      if (shift < 0) a += (inteiro_i >>  (j - 23)) & ((1 << 23)-1);
      else a += (inteiro_i << (23 - j)) & ((1 << 23)-1);

    if (debug)  cout << "\n-----\n";
    if (debug)  cout << "se     1.s                     .\n";
    if (debug)  cout << bitset <32> (a) << "\n";
    }


    { // agora cuidar dos digitos quebrados

      // considerando que nao ha inteiro; ex. "-.5", e a entrada eh valida..
      if (debug)  cout << "\ndecimal\n"; 
      string quebrado_s = "";
      int k = i;
      if (debug)  cout << "vai verificar char: " << input[k+1] << "\n";
      while(input[++k] != '0' && input[k]){
        quebrado_s += input[k];
        if (debug)  cout << "add " << input[k] << "\n";

      }
      quebrado_s += input[k];
      int k2 = k;
      if (debug)  cout << "agora vai verificar o char: " << input[k2+1] << "\n";
      while(input[++k2]) {
        quebrado_s += input[k2];
        if (debug)  cout << "add2 " << input[k2] << "\n";
      }
      long int quebrado_i = atoi(quebrado_s.c_str());
      long int quebrado_i2 = quebrado_i;
      if (debug)  cout << "\ndecimal: " << quebrado_i << "\n";
      long int lim = pow(10, i? i : k2 - k);
      if (debug)  cout << "k: " << k << ", k2: " << k2 << ", i: " << i << ", lim: " << lim << "\n";

      int l = 0;
      bool fl_primeiro = j != 0;
      int offset = j;
      while(quebrado_i2 *= 2) {
        if (debug)  cout << "\nquebrado agora: " << quebrado_i2 << "\n";
        if (debug)  cout << "++l: " << l+1 << ", j: " << j << "\n";
        ++l;
        if (debug)  cout << "l: " << l << ", offset: " << offset << ", j: " << j << "\n" ;
          if (debug)  cout << "vamo ve se vai sair: " << l + (j > 0 ? j : j)  << "\n";
        if (l + (j > 0 ? j : j) > 23) {
        //if(++l >= 22 || l + j >= 22) {
          if (debug)  cout << "son the bit, l: " << l << ", j: " << j << "\n";
          break;
        }
        if (quebrado_i2 >= lim) {
          if (debug)  cout << "one\n";
          if (!fl_primeiro && !offset)  {
          if (debug)  cout << "two\n";
            offset = -l + j; // j < 0
            if (debug)  cout << "j: " << j << "\n";
          }
          if (debug)  cout << "tre\n";
          fl_primeiro = true;
          if (debug)  cout << "for\n";
          if (debug)  cout << "quebrado_i2: " << quebrado_i2 << ", lim: " << lim << "\n";
          quebrado_i2 %= lim;
          if (debug)  cout << "fiv\n";
          if (debug)  cout << "vai um, agora quebrado: " << quebrado_i2 << "\n";
          a += (1 << (23 - l - offset + (j > 0 ? 0 : j))) & ((1 << 23) - 1);
          if (debug)  cout << "o shift disso: " << "1 << " << (23 - l - offset + (j > 0 ? 0 : j))<< "\n";
          if (debug)  cout << "offset: " << offset << "\n";
        } else {
          if (j <= 0 && !fl_primeiro) --j;
        }
        if (debug)  cout << quebrado_i2 << " < " << lim << "\n";
      }
      if (j < 0) --j;
    if (debug)  cout << "j: " << j << "\n";

    }

    // calcula o expoente
    if (debug)  cout << "expoente: " << j << "\n";

    if (a != 0 && j != 0) a += ((j + 127 ) & 0b11111111) << 23;
    //a += ((j) & 0b11111111) << 23;



     
    
    if (debug)  cout << "\n-----\n";
    if (debug)  cout << "se     1.s                     .\n";
    cout << "\n\n" << "(input): " << input << "\n"; 
    cout << "(binar): " << bitset <32> (a) << "\n";
    //if (debug)  cout << (float) a << "\n";
    if (debug) cout << "\n\n-------------------------------\n\n";
    return (float) a;
}



int main() {

  cout << "(float): "<< converteNum("123456789");
  cout << "(float): "<<converteNum("123456.123456");
  cout << "(float): "<<converteNum(".00000001");
  cout << "(float): "<<converteNum("00.00");
  
  return 0;
}



