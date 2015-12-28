#include <iostream>
#include <bitset>
#include <string>
using namespace std;

// vars globais pra nao precisar enviar pra funcao debug por param
const bool debug = false;
const int n = 64; // no meu computador uma int utiliza 64 bits, e nao 32.


void pr(bitset <3 * n + 3> bs, string s) {
  if (!debug) return;
  cout << "\nS C M";
  int i = -1;  while(++i < n) cout << " ";
  cout << "A";
  i = -1;  while(++i < n) cout << " ";
  cout << "Q";
  i = -1;  while(++i < n) cout << " ";
  cout << "Q-1";
  cout << "\n" << bs[3 * n + 2] << " " << bs[3 * n + 1] << " ";
  i  = -1;
  while(++i < n) cout << bs[3 * n - i];
  cout << " ";
  --i;
  while(++i < 2 * n) cout << bs[3 * n - i];
  cout << " ";
  --i;
  while(++i < 3 * n) cout << bs[3 * n - i];
  cout << " " << bs[0] << " " << s << "\n";
}


int main() {
  cout << "Algoritmo de multiplicacao de Booth\n"
       << "Fabricio Pirini e Thiago Machado\n"
       << "Se quer ver melhor como funciona, ative a bool debug.\n\n"
       << "Entrada: M e Q, ambos numeros inteiros, positivo ou negativo.\n"
       << "Saida: M * Q, em binario e decimal.\n\n";

  bitset <3 * n + 3> bs (0);

  // bs (bitset, exemplo de n = 4).
  // S C M    A    Q    Q-1
  // 0 0 0000 0000 0000 0
  //
  // S = bit temporario para somador completo
  // C = bit de Carrier do somador completo
  // M = multiplicando
  // A = bits utilizados no booth e no somador completo
  // Q = multiplicador
  // Q-1 = bit utilizado no booth
  //

  {   
    // S C M    A    Q    Q-1
    // 0 0 0000 0000 0000 0
    bs.reset();

    pr(bs, "reset");

    // recebe M e Q.
    {
      int M, Q;
      cin >> M >> Q;
     
      // pega o valor de M.
      // S C M    A    Q    Q-1
      // 0 0 MMMM 0000 0000 0 
      bs |= M; 
      bs <<= (2 * n);
      // bs |= (M & ((1 << n) - 1)) << (2 * n + 1); // para n < 32

      // pega o valor de Q.
      // S C M    A    Q    Q-1
      // 0 0 MMMM 0000 QQQQ 0
      bs |= Q;
      bs <<= 1;
      // bs |= (Q & ((1 << n) - 1)) << 1; // para n < 32
    } // fim do input

    pr(bs, "M e Q");

    // resolvi fazer por esta iteracao constante.
    int i = -1; 
    while (++i < n) {  
      
      // se for necessario somar ou subtrair entre A e M.
      if (bs[0] ^ bs[1]) { // se Q0 é diferente de Q-1.
        // o ultimo bit no bs serve pra guardar o carrier.
        // Q0 Q-1 A_M
        // 0  0   x
        // 0  1   +
        // 1  0   -
        // 1  1   x
        bs.set(3 * n + 1, ~bs[0]); // o Carrier recebe o valor de -Q-1 (A_M).

        
        pr(bs, "arrumou carrier");


        int j = -1; 
        while(++j < n) { // soma.
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
          bs.set(3 * n + 2, bs[j + n + 1] ^ bs[j + 2 * n + 1] ^ ~bs[0] ^ bs[3 * n + 1]);

          // c = A(M ^ A_M) | CA | C(M ^ A_M). depois C recebe esse c.
          bs.set(3 * n + 1, (bs[j + n + 1] & ( bs[j + 2 * n + 1] ^ ~bs[0])) | 
              (bs[3 * n + 1] & bs[j + n + 1]) 
              | (bs[3 * n + 1] & (bs[j + 2 * n + 1] ^ ~bs[0])));
          
          // agora o A recebe de verdade o s
          bs.set(j + n + 1, bs[3 * n + 2]);

          pr(bs, " fim da soma");
        } // fim da soma
      } // fim da soma

      // shift.
      //
      //
      pr(bs, "antes do shift");
      bs >>= 1;
      pr(bs, "shift");

      // como so' agora que percebi que o proprio M leva shift, tenho que
      // fazer com que ele retorne ao seu estado original.

      // retorna M ao seu estado anterior, puta gambiarra.
      bs ^= bs ^ ((bs << (n + 2)) >> (n + 2)) ^ ((bs >> (2 * n)) << (2 * n + 1));
      //((bs & 3840) << 1);
      bs.set(2 * n, bs[2 * n - 1]); // A4 depois do shift sera igual ao A4 de antes.
      pr (bs, "M arrumado");
    } // fim do while
  } // fim do booth

  pr(bs, " fim");


  cout << "\nresultado (binario):\n";
  if (debug) {
    int i = -1;
    while(++i < 2 * n) cout << bs[2 * n - i];
  } else {
    
    if (bs[2 * n]) cout << 1;
    else cout << 0;
    int i = -1;
    bool fl = false;
    while(++i < 2 * n){
      if (fl) cout << bs[2 * n - i];
      else if (bs[2 * n - i] != bs[2 * n]) {
        cout << bs[2 * n - i];
        fl = true;
      }
    }
  }

  cout << "\n\nresultado (decimal):\n";
  
  if (bs[2 * n]) { // negativo
    cout << "-";
    bs.flip();

    // soma com 1
    //
    // A Te  S Ts
    // 0 0 | 0 0
    // 0 1 | 1 0
    // 1 0 | 1 0
    // 1 1 | 0 1
    //
    // S = A ^ Te
    // Ts = ATe

    bs.set(3 * n + 1);
    int i = -1;
    while(++i < 2 * n) {
      // s = A ^ Te. depois A recebe esse s.
      bs.set(3 * n + 2, bs[i + 1] ^ bs[3 * n + 1]);

      // Ts = ATe.
      bs.set(3 * n + 1, bs[i + 1] & bs[3 * n + 1] );
          
      // agora o A recebe de verdade o s
      bs.set(i + 1, bs[3 * n + 2]);

      if (~bs[3 * n + 1]) break;
    }

  }

  bs <<= n + 2;
  bs >>= n + 3;
  cout << bs.to_ulong() << "\n";

  return 0;
}

