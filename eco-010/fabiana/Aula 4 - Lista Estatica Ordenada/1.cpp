#include <iostream>
using namespace std;

/*clipboard para teste
7
8
1 1 1 2 1 3 1 4 1 5
7
8
6 4 6 1 6 5 6 -1 6 6

*/

struct E
{
    // chave
    int c;
};

//        array      value      lenght
void in(E a[], int v = 0, int l = 0);
// nota: não é alocado valores acima do ultimo valor do arranjo

//       array      value  length
void rem (E a[], int v, int &l); // remove um elemento

//       array      value  length
void rem2 (E a[], int v, int &l); // remove só elemento repetido

//                 array  length
void remRepetidos (E a[], int &l); // remove todos repetidos

//        array      value      length
int find (E a[], int v = 0, int l = 0); // acha a posição na qual a próxima possui um valor maior ou igual
// vai com 'pulos cada vez menores' (cada vez metade do ultimo pulo) em direção ao indice.
//      Quando chega perto, faz uma busca normal, aumentando os indices um a um.
// TODO: Ao invés disso, voltremos em sentido horário. Descartar o uso do i3. (procurar essa frase)

int menu(E a[], int &l, int L);


bool retorna_primeiro(E a[], int l, E &e)
{
    if (!l)
        return false;
    e.c = a[0].c;
    return true;
}

bool retorna_ultimo(E a[], int l, E &e)
{
    if (!l)
        return false;
    e.c = a[l-1].c;
    return true;
}


bool consulta(E a[], int v, int l, int &pos)
{
    int i = find(a, v, l);
    if (a[i].c != v && a[--i].c != v)
        return false;
    pos = i;
}


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
        cout << "6 - consultar elemento\n";
        cout << "7 - retornar primeiro\n";
        cout << "8 - retornar ultimo\n";
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
            // consultar
            cout << "\nvalor a consultar: ";
            {
                int v;
                cin >> v;
                int pos;
                if( consulta(a, v, l, pos) )
                {
                    cout << "indice: " << pos;
                    break;
                }
                cout << "elemento nao encontrado!";
            }
            break;

            case 7:
            // retorna primeiro
            {
                E e;
                if( retorna_primeiro(a, l, e) )
                {
                    cout << "\nprimeiro elemento: " << e.c;
                    break;
                }
                cout << "\nlista vazia";
            }
            break;

            case 8:
            // retorna ultimo
            {
                E e;
                if( retorna_ultimo(a, l, e) )
                {
                    cout << "\nultimo elemento: " << e.c;
                    break;
                }
                cout << "\nlista vazia";
            }

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
        // TODO: Ao invés disso, correremos em sentido contrário (--i).

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
    return --i; // nota: testar mais pra ver se não é só 'i' mesmo, no caso se (obtido >= desejado)

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

    // libera espaço no arranjo
    while (l > i)
        a[l].c = a[--l].c;

    // adiciona o valor na posição
    a[i].c = v;
}
