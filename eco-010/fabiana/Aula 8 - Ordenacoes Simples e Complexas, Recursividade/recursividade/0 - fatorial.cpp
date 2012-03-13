#include <iostream>
using namespace std;

int fatorial(int i = 0)
{
    return
        i < 2 ?
        1 :
        i * fatorial(i-1);
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "fatorial"};

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
            cout << "insira o inteiro positivo que deseja saber o fatorial: ";
            cin >> i;
            cout << "fatorial vale " << fatorial(i);
            break;
        }
        op = menu();
    }
    return 0;
}

