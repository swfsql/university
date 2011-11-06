#include<iostream>
using namespace std;

struct No
{
    int info;
    No* next;
};

struct Pilha
{
    // variáveis
    No* topo;

    // funções
    void init()
    {
        topo = 0;
    }

    void esvazia()
    {
        No* del = topo;
        while(topo)
        {
            del = topo;
            topo = del->next;
            delete del;
        }
    }

    bool vazia()
    {
        return topo == 0;
    }

    bool cheia()
    {
        No* no = new No;
        delete no;
        return !no;
    }

    void empilha(int valor)
    {
        No* no = new No;
        no->info = valor;
        no->next = topo;
        topo = no;
        return;
    }

    bool desempilha(int& valor)
    {
        if(vazia())
            return false;
        No* del = topo;
        valor = del->info;
        topo = del->next;
        delete del;
        return true;
    }

    bool topoNo(int& valor)
    {
        if(vazia())
            return false;
        valor = topo->info;
        return true;
    }
};

int menu ()
{
    const int tam = 7;
    char ops[tam][40] = {"encerrar", "limpar pilha", "pilha vazia", "pilha cheia", "empilha", "desempilha", "topo pilha"};

    int op = -1;
    cout << "\n";
    cout << "\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}

int main ()
{
    Pilha pilha;
    pilha.init();
    int op = menu(),
        i,
        valor;
    while(op)
    {
        switch (op)
        {
        case 1:
            pilha.esvazia();
            cout << "pilha esvaziada.";
            break;

        case 2:
            cout << "pilha "
                 << (pilha.vazia() ?
                        "" :
                        "nao ")
                 << "esta fazia.";
            break;

        case 3:
            cout << "pilha "
                 << (pilha.cheia() ?
                        "" :
                        "nao ")
                 << "esta cheia.";
            break;

        case 4:
            // desconsiderei do HD estar cheio
            cout << "valor a inserir: ";
            cin >> valor;
            pilha.empilha(valor);
            break;

        case 5:
            if(!pilha.desempilha(valor))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "valor desempilhado: " << valor;
            break;

        case 6:
            if(!pilha.topoNo(valor))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "valor no topo: " << valor;
            break;
        }
        op = menu();
    }
}
