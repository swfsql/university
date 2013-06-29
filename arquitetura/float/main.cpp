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

    } else {

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
      double quebrado_i = atoi(quebrado_s.c_str());
      double quebrado_i2 = quebrado_i;
      cout << "\ndecimal: " << quebrado_i << "\n";
      double lim = pow(10, k2 - i - 2);
      cout << "k: " << k << ", k2: " << k2 << ", i: " << i << ", lim: " << lim << "\n";
      while((quebrado_i2 *= 2) < lim) {
        --j;
        cout << quebrado_i2 << " < " << lim << "\n";
      }
      --j;
      cout << "j: " << j << "\n";

    }
    a += ((j + 127) & 0b11111111) << 23;

    cout << "\n-----\n";
    cout << bitset <32> (a) << "\n";
  }







  cout << "\n\nfim\n\n";
  return 0;




}
