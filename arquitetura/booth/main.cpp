#include <iostream>
#include <bitset>
using namespace std;

#define DEBUG 


void pr(bitset <15> bs) {
  cout << "\nS C M    A    Q    Q-1";
  cout << "\n" << bs[14] << " " << bs[13] << " ";
  int i  = -1;
  while(++i < 4) cout << bs[12 - i];
  cout << " ";
  --i;
  while(++i < 8) cout << bs[12 - i];
  cout << " ";
  --i;
  while(++i < 12) cout << bs[12 - i];
  cout << " " << bs[0] << " ";
}


int main() {

  cout << "Entrada: M e Q, de -8 ate 7, ate EOF. Saida: M * Q.\n\n"; 

  bitset <15> bs (0);

  // bs (bitset).
  // S C M    A    Q    Q-1
  // 0 0 0000 0000 0000 0
  //

  {   
    // S C M    A    Q    Q-1
    // 0 0 0000 0000 0000 0
    bs.reset();

    DEBUG pr(bs); cout << "reset\n";

    // recebe M e Q.
    {
      char M, Q;
      cin >> M >> Q;
     
      // pega o valor de M.
      // S C M    A    Q    Q-1
      // 0 0 MMMM 0000 0000 0 
      bs |= (M & 15) << 9;

      // pega o valor de Q.
      // S C M    A    Q    Q-1
      // 0 0 MMMM 0000 QQQQ 0
      bs |= (Q & 15) << 1;
    }

    DEBUG pr(bs); cout << " M e Q\n";

    // resolvi fazer por esta iteracao constante.
    int i = -1; 
    while (++i < 4) {  
      
      // se for necessario somar ou subtrair entre A e M.
      if (bs[0] ^ bs[1]) { // se Q0 é diferente de Q-1.
        // o ultimo bit no bs serve pra guardar o carrier.
        // Q0 Q-1 A_M
        // 0  0   x
        // 0  1   +
        // 1  0   -
        // 1  1   x
        bs.set(13, ~bs[0]); // o Carrier recebe o valor de -Q-1 (A_M).

        
        DEBUG pr(bs); cout << " arrumou carrier\n";


        int j = -1; 
        while(++j < 4) { // soma.
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
          
          // s = A ^ (M ^ A_M ) ^ C. depois A recebe esse s.
          bs.set(14, bs[j + 5] ^ bs[j + 9] ^ ~bs[0] ^ bs[13]);

          // c = A(M ^ A_M) | CA | C(M ^ A_M). depois C recebe esse c.
          bs.set(13, (bs[j + 5] & ( bs[j + 9] ^ ~bs[0])) | 
              (bs[13] & bs[j + 5]) 
              | (bs[13] & (bs[j + 9] ^ ~bs[0])));
          
          // agora o A recebe de verdade o s
          bs.set(j + 5, bs[14]);

          DEBUG pr(bs); cout << " fim da soma\n";
        }
      }

      // shift.
      //
      //
      DEBUG pr(bs); cout << "antes do shift\n";
      bs >>= 1;
      DEBUG pr(bs); cout << "shift\n";

      // como so' agora que percebi que o proprio M leva shift, tenho que
      // fazer com que ele retorne ao seu estado original.

      // retorna M ao seu estado anterior, puta gambiarra.
      bs ^= bs ^ ((bs << 6) >> 6) ^ ((bs >> 8) << 9);
      //((bs & 3840) << 1);
      bs.set(8, bs[7]); // A4 depois do shift sera igual ao A4 de antes.
      DEBUG pr (bs); cout << "M arrumado\n";
    }
  }

  DEBUG pr(bs); cout << " fim\n";
  cout << "\nresultado (binario):\n";
  int i = -1;
  while(++i < 8) cout << bs[8 - i];
  cout << "\nresultado (decimal):\n";
  cout << "todo";
  return 0;
}

