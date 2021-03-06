#include <iostream>
using namespace std;

struct E
{
    // chave
    int c;
};

//        array      value      lenght
void in(E a[], int v = 0, int l = 0);
// nota: n�o � alocado valores acima do ultimo valor do arranjo

//       array      value  length
void rem (E a[], int v, int &l); // remove um elemento

//       array      value  length
void rem2 (E a[], int v, int &l); // remove s� elemento repetido

//                 array  length
void remRepetidos (E a[], int &l); // remove todos repetidos

//        array      value      length
int find (E a[], int v = 0, int l = 0); // acha a posi��o na qual a pr�xima possui um valor maior ou igual
// vai com 'pulos cada vez menores' (cada vez metade do ultimo pulo) em dire��o ao indice.
//      Quando chega perto, faz uma busca normal, aumentando os indices um a um.
// TODO: Ao inv�s disso, voltremos em sentido hor�rio. Descartar o uso do i3. (procurar essa frase)

int menu(E a[], int &l, int L);


int main()
{
    int L = 5, // tamanho max
        l = 0; // tamanho usado
    E a[L];

    menu(a, l, L);
}

int menu(E a[], int &l, int L)
{
    int op = 1;
    while(op)
    {
        cout << "0 - encerrar programa\n";
        cout << "1 - inserir elemento\n";
        cout << "2 - remover elemento\n";
        cout << "3 - remover elementos (todos encontrados)\n";
        cout << "4 - remover elementos (repetidos)\n";
        cout << "5 - mostrar elementos\n";
        cout << "6 - buscar elemento\n";
        cout << "7 - verificar se lista cheia\n";
        cout << "8 - verificar se lista vazia\n";
        cout << "escolha uma opcao: ";
        cin >> op;

        switch(op)
        {
            case 1:
            // inserir
            cout << "\nvalor a inserir: ";
            {
                int i;
                cin >> i;
                in (a, i, ++l);
            }
            break;
            case 2:
            // remover
            cout << "\nvalor a remover: ";
            {
                int i;
                cin >> i;
                rem (a, i, l);
            }
            break;
            case 3:
            // remover todos encontrados
            cout << "\nvalor a remover: ";
            {
                int i;
                cin >> i;
                rem2 (a, i, l);
            }
            break;

            case 4:
            // remover todos repetidos
            remRepetidos(a, l);
            break;

            case 5:
            // mostrar
            {
                int i = -1;
                while(++i < l)
                    cout << a[i].c << "\t";
            }
            break;
            case 6:
            // buscar
            cout << "\nvalor a buscar: ";
            {
                int v;
                cin >> v;
                int i = find(a, v, l);
                if (a[i].c != v && a[--i].c != v)
                {
                    cout << "elemento nao encontrado!";
                    break;
                }
                cout << "indice: " << i;
            }
            break;
            case 7:
            // cheia
            cout << "\na lista " << (l == L? "" : "nao ") << "esta cheia.\n";
            break;
            case 8:
            // vazia
            cout << "\na lista " << (l == 0? "" : "nao ") << "esta vazia.\n";
            break;
        }
        cout << "\n\n\n";
    }
}

int find (E a[], int v, int l)
{
    int l2 = l>>1,
        i = l2,
        i3 = 2,
        v2 = a[0].c;

    while(a[i].c != v2 && (v2 = a[i].c) != v)
    {
        // loop para se obter duas vezes no msm valor ou se obter o valor desejado

        ++i3; // mais tarde: se o valor (obtido >= desejado), temos de subtrair um pouco do obtido.
        // TODO: Ao inv�s disso, correremos em sentido contr�rio (--i).

        v2 > v &&
            (i -= l2 >>= 1) ||
            (i += l2 >>= 1);
    }
    v2 = a[i].c; // atualiza o valor obtido

    // obtido >= desejado
    v2 >= v &&
        (i -= i3);

    i < -1 &&
        (i = -1);

    while(++i < l)
        if (a[i].c > v)
            return i;
    return --i; // nota: testar mais pra ver se n�o � s� 'i' mesmo, no caso se (obtido >= desejado)

}

void rem (E a[], int v, int &l)
{
    // index
    int i = find(a, v, l);

    if (a[i].c != v && a[--i].c != v)
        return;

    // remove o elemento
    --l;
    while (i < l)
        a[i].c = a[++i].c;
}

void rem2 (E a[], int v, int &l)
{
    // index
    int i = find(a, v, l);

    if (a[i].c != v && a[--i].c != v)
        return;

    int k = 1;
    while(--i > 0 && a[i].c == v)
        ++k;
    ++i;

    // remove o elemento
    l -= k;
    while (i < l)
    {
        a[i].c = a[i+k].c;
        ++i;
    }
}

void remRepetidos(E a[], int &l)
{
    int i = 0;
    int i2 = 0;

    int l2 = 0;
    int v = a[0].c;

    while(++i < l)
    {
        if (a[i].c != v)
        {
            v = a[i].c;
            a[++i2].c = v;
        }
        else
        {
            ++l2;
        }
    }
    l -= l2;
}

void in(E a[], int v, int l)
{
    // index
    int i = find(a, v, l);

    // libera espa�o no arranjo
    while (l > i)
        a[l].c = a[--l].c;

    // adiciona o valor na posi��o
    a[i].c = v;
}
