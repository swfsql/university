#include<iostream>
using namespace std;

/* clipboard para teste
8 -1 9 5
8 -1 5
1 -1 9 5
1 -1 1 0 9 5
1 0 9 5
1 0 1 1 9 5
7

*/

struct No
{
    int chave;
    No *next;
};

void menu (No **inicio, int &tamanho);
void inserir(int valor, No **inicio);
bool remover(No **inicio, int valor);
void mostrar_primeiro(No *inicio);
void mostrar_ultimo(No *inicio);
void mostrar(No *inicio);
bool pesquisar(No **inicio, int &i);

bool inserir2(int valor, No **inicio);
void remover2(No **inicio, int &tamanho);

int main ()
{
    No *inicio = 0;
    int tamanho = 0;

    menu(&inicio, tamanho);
}

void menu (No **inicio,int &tamanho)
{
    int op = 1,
        i,
        valor;
    while (op)
    {
        cout<<"0 - encerrar\n";
        cout<<"1 - inserir elemento\n";
        cout<<"2 - remover elemento\n";
        cout<<"3 - mostrar primeiro elemento\n";
        cout<<"4 - mostrar ultimo elemento\n";
        cout<<"5 - mostrar elementos\n";
        cout<<"6 - pesquisar elemento\n";
        cout<<"7 - tamanho da lista\n";
        cout<<"8 - inserir elementos nao repetidos\n";
        cout<<"9 - remover elementos repetidos\n";
        cin>>op;

        cout << "\n";
        switch (op)
        {
        case 1:
            cout << "valor a inserir: ";
            cin >> valor;
            inserir(valor, inicio);
            ++tamanho;
            break;

        case 2:
            cout << "valor a remover: ";
            cin >> valor;
            tamanho -= remover(inicio, valor);
            break;

        case 3:
            mostrar_primeiro(*inicio);
            break;

        case 4:
            mostrar_ultimo(*inicio);
            break;

        case 5:
            mostrar(*inicio);
            break;

        case 6:
            cout << "indice a buscar: ";
            cin >> i;
            if ( pesquisar(inicio, i) )
                cout << "valor da chave: " << i;
            else
                cout << "nao encontrado";
            break;

        case 7:
            cout << "tamanho da lista: " << tamanho;
            break;

        case 8:
            cout << "valor a inserir: ";
            cin >> valor;
            if ( inserir2(valor, inicio) )
                ++tamanho;
            else
                cout << "esse valor ja existe na lista";
            break;

        case 9:
            remover2(inicio, tamanho);
            break;
        }
        cout << "\n\n";
    }
}


void inserir(int valor, No **next)
{
    while(*next && (*next)->chave < valor)
        next = &(*next)->next;
    No *no = new No;
    no->chave = valor;
    no->next = *next;
    *next = no;
}

bool remover(No **next, int valor)
{
    while(*next && (*next)->chave != valor)
        next = &(*next)->next;
    if (*next)
    {
        No *del = *next;
        *next = (*next)->next;
        delete del;
        return true;
    }
    return false;
}

void mostrar_primeiro(No *inicio)
{
    if(!inicio)
    {
        cout << "nao ha primeiro elemento";
        return;
    }
    cout << "primeiro elemento: " << inicio->chave;
    return;
}

void mostrar_ultimo(No *inicio)
{
    No *no = inicio;
    if(!no)
    {
        cout << "nao ha ultimo elemento";
        return;
    }

    while(no->next)
        no = no->next;
    cout << "ultimo elemento: " << no->chave;
    return;
}

void mostrar(No *inicio)
{
    No *no = inicio;
    while(no)
    {
        cout << no->chave << "\t";
        no = no->next;
    }
}

bool pesquisar(No **inicio, int &i)
{
    No *no = *inicio;
    while(i-- && no)
        no = no->next;
    return
        no &&
        (i = no->chave) ||
        !i;
}



bool inserir2(int valor, No **inicio)
{
    No **next = inicio;
    while(*next && (*next)->chave < valor)
        next = &(*next)->next;
    if (*next && (*next)->chave == valor)
        return false;
    No *no = new No;
    no->chave = valor;
    no->next = *next;
    *next = no;
    return true;
}

// vou removendo 1 por 1 mesmo.. remover v�rios de uma vez seria melhor.
void remover2(No **inicio, int &tamanho)
{
    No **next = inicio;
    No *del;
    if (!*next)
        return;
    int valor = (*next)->chave;
    next = &(*next)->next;

    while(*next)
    {
        if ((*next)->chave != valor)
        {
            valor = (*next)->chave;
            next = &(*next)->next;
            continue;
        }

        del = *next;
        *next = (*next)->next; // removendo um por um, tenho de acertar esse ponteiro um por um.
        --tamanho; // aqui tamb�m, tenho de diminuir um por um.
        delete del;
    }
}

