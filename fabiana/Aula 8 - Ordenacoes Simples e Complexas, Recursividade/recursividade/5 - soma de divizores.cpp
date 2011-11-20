#include <iostream>
using namespace std;

int dc (int n1, int n2, int aux, int soma = 0)
{
    if (n1 == n2 || aux)
    {
        if(!aux)
            soma += aux = n1;

        if (n2 < 2)
        {
            cout << soma;
            return 1;
        }
        while(aux%--n2);
        soma += n2;
        return dc (n1, n2, aux, soma);
    }
    return
        n1 < n2 ?
        dc (n2, n1, aux, soma) :
        dc (n1-n2, n2, aux, soma);
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
            cout << "soma dos Divisores Comuns: ";
            dc (n1, n2, 0);

            break;
        }
        op = menu();
    }
    return 0;
}
