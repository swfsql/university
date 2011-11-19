#include <iostream>
using namespace std;

int f (int n = 0)
{
    if(n <= 3)
        return 0;
    cout << " - " << (n /= 2);
    return f(n);
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "divisores comuns de um numero"};

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
        n;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira um inteiro positivo para desencadear a sequencia: ";
            cin >> n;
            cout << n;
            f(n);
            break;
        }
        op = menu();
    }
    return 0;
}
