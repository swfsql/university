#include <iostream>
using namespace std;

int fibonacci (int i = 0)
{
    return
        i > 2 ?
        fibonacci (i-1) + fibonacci (i-2) :
        1;
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "fibonacci"};

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
        i;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira o temo (n) (inteiro positivo) que deseja saber da sequencia fibonacci: ";
            cin >> i;
            cout << "fibonacci vale " << fibonacci(i);
            break;
        }
        op = menu();
    }
    return 0;
}

