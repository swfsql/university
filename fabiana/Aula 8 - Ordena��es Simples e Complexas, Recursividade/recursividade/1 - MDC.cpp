#include <iostream>
using namespace std;

int mdc (int n1 = 0, int n2 = 0)
{
    return
        n1 < n2 ?
        mdc (n2, n1) :
        n1 == n2 ?
        n1 :
        mdc (n1-n2, n2);
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "mdc"};

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
        n1, n2;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira dois inteiros positivos que deseja saber o Maximo Divisor Comum: ";
            cin >> n1 >> n2;
            cout << "mdc vale " << mdc (n1, n2);
            break;
        }
        op = menu();
    }
    return 0;
}

