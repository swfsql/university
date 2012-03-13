#include <iostream>
using namespace std;

int f (int i)
{
    int soma = i;
    while(--i) soma += i;
    return soma;
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "soma dos numeros anteriores"};

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
            cout << "insira o inteiro positivo que deseja saber a soma dele com todos os numeros anteriores a ele: ";
            cin >> i;
            cout << "a soma vale " << f(i);
            break;
        }
        op = menu();
    }
    return 0;
}

