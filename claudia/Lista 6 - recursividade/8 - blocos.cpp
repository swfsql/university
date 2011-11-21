#include <iostream>
using namespace std;

struct bloco
{
   char nome[50];
   int idade;
   bloco *prox;
};

struct lista

int tabuada(int& base, int i = 10)
{
    if(i > 0)
        cout << tabuada(base, i - 1) << " ";
    return base * i;
}

int main()
{
    int base;
    cout << "insira um numero base: ";
    cin >> base;
    cout << tabuada(base);
    return 0;
}
