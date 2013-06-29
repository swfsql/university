#include <iostream>
#include <string>
#include <cstdlib>
#include <cmath>
#include <bitset>
using namespace std;

int main() {

  string input;
  {
    cin >> input;

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
      cout << "\ninteiro: " << inteiro_i << "\n";

      int inteiro_i2 = inteiro_i;
      while(inteiro_i2 >>= 1) ++j;
      // j = expoente nao polarizado
      
      cout << "j: " << j << "\n";


      cout << "partiu significando\n";
      a += (inteiro_i << (23 - j)) & ((1 << 23)-1);


    cout << "\n-----\n";
    cout << "se     1.s                     .\n";
    cout << bitset <32> (a) << "\n";
    }


    { // agora cuidar dos digitos quebrados

      // considerando que nao ha inteiro; ex. "-.5", e a entrada eh valida..
      cout << "\ndecimal\n"; 
      string quebrado_s = "";
      int k = i;
      while(input[++k] != '0' && input[k]){
        quebrado_s += input[k];
        cout << "add " << input[k] << "\n";

      }
      quebrado_s += input[k];
      int k2 = k;
      while(input[++k2]) {
        quebrado_s += input[k2];
        cout << "add2 " << input[k2] << "\n";
      }
      long int quebrado_i = atoi(quebrado_s.c_str());
      long int quebrado_i2 = quebrado_i;
      cout << "\ndecimal: " << quebrado_i << "\n";
      long int lim = pow(10, k2 - i - 2);
      cout << "k: " << k << ", k2: " << k2 << ", i: " << i << ", lim: " << lim << "\n";

      int l = 0;
      bool fl_primeiro = j != 0;
      int offset = j;
      while(quebrado_i2 *= 2) {
        cout << "\nquebrado agora: " << quebrado_i2 << "\n";
        cout << "++l: " << l+1 << ", j: " << j << "\n";
        if(++l >= 22 || l + j >= 22) {
          cout << "son the bit, l: " << l << ", j: " << j << "\n";
          break;
        }
        if (quebrado_i2 >= lim) {
          if (!fl_primeiro && !offset)  {
            offset = -l + j; // j < 0
            cout << "j: " << j << "\n";
          }
          fl_primeiro = true;
          quebrado_i2 %= lim;
          cout << "vai um, agora quebrado: " << quebrado_i2 << "\n";
          a += (1 << (23 - l - offset + j)) & ((1 << 23) - 1);
          cout << "o shift disso: " << "1 << " << 23 - l - offset + j << "\n";
          cout << "offset: " << offset << "\n";
        } else {
          if (j <= 0 && !fl_primeiro) --j;
        }
        cout << quebrado_i2 << " < " << lim << "\n";
      }
      if (j <= 0) --j;
    cout << "j: " << j << "\n";

    }

    // calcula o expoente
    cout << "expoente: " << j << "\n";
    a += ((j + 127) & 0b11111111) << 23;
    //a += ((j) & 0b11111111) << 23;



     
    
    cout << "\n-----\n";
    cout << "se     1.s                     .\n";
    cout << bitset <32> (a) << "\n";
  }



  cout << "\n\nfim\n\n";
  return 0;




}
