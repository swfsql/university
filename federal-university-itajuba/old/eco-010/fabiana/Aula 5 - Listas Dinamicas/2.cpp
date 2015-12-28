#include<iostream>
using namespace std;

struct No
{
    int chave;
    No *next;
    No *prev;
};

void menu (No **inicio, No **fim, int &tamanho);
void inserir(int valor, No **inicio, No **fim);
bool remover(int valor, No **inicio, No **fim);
void mostrar_primeiro(No *inicio);
void mostrar_ultimo(No *fim);
void mostrar(No *inicio, No *fim);
bool pesquisar(No **inicio, int &i);

int main ()
{
    No *inicio = 0;
    No *fim = 0;
    int tamanho = 0;

    menu(&inicio, &fim, tamanho);
}

void menu (No **inicio, No **fim, int &tamanho)
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
            inserir(valor, inicio, fim);
            ++tamanho;
            break;

        case 2:
            cout << "valor a remover: ";
            cin >> valor;
            tamanho -= remover(valor, inicio, fim);
            break;

        case 3:
            mostrar_primeiro(*inicio);
            break;

        case 4:
            mostrar_ultimo(*fim);
            break;

        case 5:
            mostrar(*inicio, *fim);
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

void inserir(int valor, No **inicio, No **fim)
{
    No **next = inicio;
    No *prev = 0;
    while(*next && (*next)->chave < valor)
    {
        prev = *next;
        next = &prev->next;
    }
    // ajusta o novo nó
    No *no = new No;
    no->chave = valor;
    no->next = *next;
    no->prev = prev;

    // ajusta um ponteiro (pode ser o início, pode ser o next do nó anterior)
    *next = no;

    if (!no->next)
        *fim = no; // ajusta o fim
    else
        no->next->prev = no; // ajusta o ponteiro anterior, do próximo nó
}

bool remover(int valor, No **inicio, No **fim)
{
    No **next = inicio;
    while(*next && (*next)->chave != valor)
        next = &(*next)->next;
    if (*next)
    {
        // nó que será deletado
        No *del = *next;

        // ajusta um ponteiro (pode ser o início, pode ser o next do nó anterior)
        *next = (*next)->next;

        if (!del->next)
            *fim = del->prev; // ajusta o fim
        else
            (*next)->prev = del->prev; // ajusta o ponteiro anterior, do próximo nó
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

void mostrar_ultimo(No *fim)
{
    No *no = fim;
    if(!no)
    {
        cout << "nao ha ultimo elemento";
        return;
    }

    cout << "ultimo elemento: " << no->chave;
    return;
}

void mostrar(No *inicio, No *fim)
{
    No *no = inicio;
    while(no)
    {
        cout << no->chave << "\t";
        no = no->next;
    }

    cout << "\nou\n";
    no = fim;
    while(no)
    {
        cout << no->chave << "\t";
        no = no->prev;
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

