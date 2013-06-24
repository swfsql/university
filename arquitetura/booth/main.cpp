#include <iostream>
#include <bitset>
using namespace std;

int main() {

  cout << "Entrada: M e Q, de -8 ate 7, ate EOF. Saida: M * Q.\n\n"; 

  bitset <14> bs (0);

  // bs (bitset).
  // C M    A    Q    Q-1
  // 0 0000 0000 0000 0
  //

  {   
    // C M    A    Q    Q-1
    // 0 0000 0000 0000 0
    bs.reset();

    cout << bs << " reset" << "\n";

    // recebe M e Q.
    {
      char M, Q;
      cin >> M >> Q;
     
      // pega o valor de M.
      // C M    A    Q    Q-1
      // 0 MMMM 0000 0000 0 
      bs |= (M & 15) << 9;

      // pega o valor de Q.
      // C M    A    Q    Q-1
      // 0 MMMM 0000 QQQQ 0
      bs |= (Q & 15) << 5;
    }

    cout << bs << " M e Q" << "\n";

    // resolvi fazer por esta iteracao constante.
    int i = -1; 
    while (++i < 4) {  
      
      // se for necessario somar ou subtrair entre A e M.
      if (!bs[0] ^ bs[1]) { // se Q0 é diferente de Q-1.
        // o ultimo bit no bs serve pra guardar o carrier.
        // Q0 Q-1 A_M
        // 0  0   x
        // 0  1   +
        // 1  0   -
        // 1  1   x
        bs.set(13, bs[0]); // o Carrier recebe o valor de Q-1 (A_M).
        int j = -1; while(++j < 4) { // soma.
          // Somador-completo.
          // C carry-entrada, s saida c carry-saida.
          //
          // C A M | s c
          // 0 0 0 | 0 0
          // 0 0 1 | 1 0
          // 0 1 0 | 1 0
          // 0 1 1 | 0 1
          // 1 0 0 | 1 0
          // 1 0 1 | 0 1
          // 1 1 0 | 0 1
          // 1 1 1 | 1 1
          
    cout << bs << " dentro da soma" << "\n";
          // s = A ^ (M ^ A_M ) ^ C. depois A recebe esse s.
          bs.set(j + 5, bs[j + 5] ^ bs[j + 9] ^ bs[0] ^ bs[13]);

          // c = A(M ^ A_M) | CA | C(M ^ A_M). depois C recebe esse c.
          bs.set(13, (bs[j + 5] & ( bs[j + 9] ^ bs[0])) | 
              (bs[13] & bs[j + 5]) 
              | (bs[13] & (bs[j + 9] ^ bs[0]))); 
        }
      }

    cout << bs << " shift" << "\n";
      // shift.
      bs.set(13, bs[12]); // A4 depois do shift sera igual ao A4 de antes.
      bs >>= 1;
    }
  }

  cout << bs << " fim" << "\n";
  return 0;
}

