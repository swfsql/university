#include <iostream>
using namespace std;

int mdc (int n = 0, int m = 0)
{
    return
        m < n && !(n % m) ?
        m :
        mdc (m, n < m ?
             n :
             n % m);
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
        n, m;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira dois inteiros positivos que deseja saber o Maximo Divisor Comum: ";
            cin >> n >> m;
            cout << "mdc vale " << mdc (n, m);
            break;
        }
        op = menu();
    }
    return 0;
}
