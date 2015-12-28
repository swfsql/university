#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

/* clipboard para teste
1
UNIFEI itabira
3

*/

const unsigned int TAM = 40;

struct No
{
    char info;
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

    bool empilha(char str[40])
    {
        if(cheia())
            return false;
        int i = -1;
        while(str[++i])
            nos[++topo].info = str[i];

        return true;
    }

    bool desempilha(char str[40])
    {
        if(vazia())
            return false;
        str[0] = nos[topo--].info;
        str[1] = '\0';
        return true;
    }

    void mostrar()
    {
        if(vazia())
            return;
        int i = topo+1;
        cout << "\nimpressao como pilha:\n";
        while(i--)
            cout << nos[i].info;
        cout << "\n";
    }
};

int menu ()
{
    const int tam = 4;
    char ops[tam][40] = {"encerrar", "empilha", "desempilha", "mostrar invertido"};

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
        i;
    char str[40];
    while(op)
    {
        switch (op)
        {
        case 1:
            // parte ruim: se estiver cheia, é inútil dar input no 'str'
            cout << "string a inserir: ";
            fflush(stdin);
            gets(str);
            if(!pilha.empilha(str))
                cout << "pilha cheia.";
            break;

        case 2:
            if(!pilha.desempilha(str))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "char desempilhado: " << str[0];
            break;

        case 3:
            pilha.mostrar();
            break;

        }
        op = menu();
    }
}
