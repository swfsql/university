
#include<iostream>
using namespace std;

const unsigned int TAM = 10;

struct No
{
    int info;
};

struct Pilha
{
    // variáveis
    int topo;
    No nos[TAM];


    // funções
    void init()
    {
        topo = -1;
    }

    bool vazia()
    {
        return topo == -1;
    }

    bool cheia()
    {
        return topo+1 == TAM;
    }

    bool empilha(int valor)
    {
        if(cheia())
            return false;
        nos[++topo].info = valor;
        return true;
    }

    bool desempilha(int& valor)
    {
        if(vazia())
            return false;
        valor = nos[topo--].info;
        return true;
    }

    bool topoNo(int& valor)
    {
        if(vazia())
            return false;
        valor = nos[topo].info;
        return true;
    }
};

int menu ()
{
    const int tam = 7;
    char ops[tam][40] = {"encerrar", "iniciar pilha", "pilha vazia", "pilha cheia", "empilha", "desempilha", "topo pilha"};

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
            pilha.init();
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
            // parte ruim: se estiver cheia, é inútil dar input no 'valor'
            cout << "valor a inserir: ";
            cin >> valor;
            if(!pilha.empilha(valor))
                cout << "pilha cheia.";
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
