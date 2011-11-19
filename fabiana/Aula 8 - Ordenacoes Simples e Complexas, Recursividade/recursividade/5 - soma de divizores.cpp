#include <iostream>
using namespace std;

// quanto maior, mais próximo do dobro.

int mdc (int n1 = 0, int n2 = 0, int n3 = 0, int soma = 0)
{
    int i = n1;
    if (i < 2)
    {
        ++soma;
        cout << "1\n";
        return soma;
    }
    if (i == n2)
    {
        cout << n1 << " ";
        soma += n1;
        n1 += n3;
        while(n1 % --i > 0);
        return mdc (n1, i, n3, soma);
    }

    return
        n1 < n2 ?
        mdc (n2, n1, n3, soma) :
        mdc (n3=(n1-n2), n2, n3, soma);
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
        n1, n2;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira um inteiro positivo que deseja saber a quantidade de Divisores Comuns: ";
            cin >> n1;
            cout << mdc (n1, n1) << " Divisores Comuns";
            break;
        }
        op = menu();
    }
    return 0;
}
