#include <iostream>
using namespace std;

int mdc (int n1 = 0, int n2 = 0, int n3 = 0)
{
    int i = n1;
    if (i < 2)
    {
        cout << " 1";
        return 1;
    }
    if (i == n2)
    {
        cout << n1 << " ";
        n1 += n3;
        while(n1 % --i > 0);
        return mdc (n1, i, n3);
    }

    return
        n1 < n2 ?
        mdc (n2, n1) :
        mdc (n3=(n1-n2), n2, n3);
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "divisores comuns"};

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
            cout << "insira dois inteiros positivos que deseja saber os Divisores Comuns: ";
            cin >> n1 >> n2;
            cout << "Divisores Comuns: ";
            mdc (n1, n2);
            break;
        }
        op = menu();
    }
    return 0;
}
