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

struct No
{
    char info;
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

    bool vazia()
    {
        return topo == 0;
    }

    void empilha(char str[40])
    {
        No* no;
        int i = -1;
        while(str[++i])
        {
            no = new No;
            no->info = str[i];
            no->next = topo;
            topo = no;
        }
    }

    bool desempilha(char str[40])
    {
        if(vazia())
            return false;

        str[0] = topo->info;
        str[1] = '\0';
        No* del = topo;
        topo = topo->next;
        delete del;
        return true;
    }

    void mostrar()
    {
        if(vazia())
            return;
        No* no = topo;
        cout << "\nimpressao como pilha:\n";
        while(no)
        {
            cout << no->info;;
            no = no->next;
        }
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
            pilha.empilha(str);
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
