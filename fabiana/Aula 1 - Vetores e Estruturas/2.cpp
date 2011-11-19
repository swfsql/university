#include <iostream>
using namespace std;

/* clipboard pra teste
5
4
3
2
1 0
4
2 -1 2 1 2 0 -1
4
3 1 3 -1
1 1 1 2 1 3 1 4 1 5
4
0

*/

// botei um menu. acho que o erro estava na linha 105

void inserir(int a[], int &l, int L);
void alterar(int a[], int l);
int localizar(int a[], int l);
void mostrar(int a[], int l);

int menu(int a[], int &l, int L);

int main()
{
    // criação do arranjo
    int L,      // tamanho máximo
        l = 0;  // tamanho usado
    cout << "insira o tamanho do arranjo: ";
    cin >> L;
    int a[L];

    menu(a, l, L);

    return 0;
}

int menu(int a[], int &l, int L)
{
    int op = 1;
    while(op)
    {
        cout << "0 - encerrar programa\n";
        cout << "1 - inserir elemento\n";
        cout << "2 - alterar elemento\n";
        cout << "3 - localizar elemento\n";
        cout << "4 - mostrar elementos\n";
        cout << "escolha uma opcao: ";
        cin >> op;

        switch(op)
        {
            case 1:
                inserir(a, l, L); break;
            case 2:
                alterar(a, l); break;

            case 3:
            // localizar
            {
                int i = localizar(a, l);
                if (i == -1)
                {
                    cout << "indice nao encontrado.";
                    break;
                }
                cout << "indice: " << i;
            }
            break;

            case 4:
                mostrar(a,l); break;
        }
        cout << "\n\n\n";
    }
}

void inserir(int a[], int &l, int L)
{
    if (l == L)
    {
        cout << "\narranjo cheio!";
        return;;
    }
    cout << "\nvalor a inserir: ";
    cin >> a[l++];
}

void alterar(int a[], int l)
{
    if (!l)
    {
        cout << "\narranjo vazio!";
        return;
    }

    cout << "\nindice a alterar: ";
    int i;
    cin >> i;
    if (i < 0 || i+1 > l) // esse i+1 antes era só i, acho que esse era o erro..
    {
        cout << "\nindice nao existe!";
        return;
    }

    cout << "\nnovo valor: ";
    cin >> a[i];
}

int localizar(int a[], int l)
{
    if (!l)
    {
        cout << "\narranjo vazio!\n";
        return -1;
    }

    cout << "\nvalor a localizar: ";
    int v;
    cin >> v;

    int i = -1;
    while(++i < l)
        if (a[i] == v)
            return i;
    return -1;
}

void mostrar(int a[], int l)
{
    int i = -1;
    while(++i < l)
        cout << a[i] << "\t";
}
