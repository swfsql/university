#include<iostream>
using namespace std;

/* clipboard para teste
1 0 1 0 1 1 1 -1 1 -2 1 2 1 -2 1 -1 1 2 5
3 4 7 6 0 6 4 5
2 -2 2 -1 2 0 2 0 2 2 7 5
2 -2 2 -1 2 1 2 2 7 5
2 1 3 4 6 0

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
        }
        cout << "\n\n";
    }
}

/* inserir (um pouco mais antigo). tirei o **fim, e passei a usar ponteiro duplo
void inserir(int valor, No **inicio, No **fim)
{
    No *no = new No;
    no->chave = valor;
    if(!*inicio)
    {
        no->next = 0;
        *fim = no;
        *inicio = no;
        return;
    }
    No *next = *inicio; // nó após
    No *prev = *inicio; // nó antes
    while(next && next->chave < valor)
    {
        prev = next;
        next = next->next;
    }
    no->next = next;
    if (**inicio == *prev)
    {
        *inicio = no;
        return;
    }
    prev->next = no;
    if (!next)
        *fim = no;
}*/

void inserir(int valor, No **inicio)
{
    No **next = inicio;
    while(*next && (*next)->chave < valor)
        next = &(*next)->next;
    No *no = new No;
    no->chave = valor;
    no->next = *next;
    *next = no;
}

bool remover(No **inicio, int valor)
{
    No **next = inicio;
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

