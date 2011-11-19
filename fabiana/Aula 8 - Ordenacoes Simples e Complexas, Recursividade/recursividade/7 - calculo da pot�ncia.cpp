#include <iostream>
using namespace std;

int potencia(int base = 0, int pow = 0)
{
    return
        pow < 1 ?
        1 :
        base * potencia(base, pow-1);
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "potencia"};

    int op = -1;
    cout << "\n\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}

int main()
{
    int op = menu(),
        base, pow;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira a base e a potencia (inteiros, potencia positiva): ";
            cin >> base >> pow;
            cout << "\nisso vale " << potencia(base, pow);
            break;
        }
        op = menu();
    }
    return 0;
}

