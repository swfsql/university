#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

/* clipboard para teste
1
abcabcabc
3
a
3
c

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
            if(str[i] != ' ') // desconsidera os espaços
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

    bool desempilhaEmpilha(char c, Pilha& pilha2)
    {
        if(vazia())
            return false;
        int i = -1, j = 0;
        char str[40] = " "; ++topo;
        while(++i < topo)
        {
            if(c == nos[i].info)
            {
                ++j;
                str[0] = nos[i].info;
                pilha2.empilha(str);
                continue;
            }
            if(!j)
                continue;

            nos[i-j] = nos[i];
        }
        topo -= ++j;
        return true;
    }

    void mostrar()
    {
        if(vazia())
            return;
        int i = -1;
        cout << "\nimpressao como expressao:\n";
        while(++i < topo+1)
            cout << nos[i].info;
        cout << "\n";
    }
};

int menu ()
{
    const int tam = 4;
    char ops[tam][40] = {"encerrar", "empilha", "desempilha", "desempilha, entao empilha"};

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
            cout << "char a ser desempilhado e epilhado em uma nova pilha: ";
            fflush(stdin);
            gets(str);
            {
                Pilha pilha2;
                pilha2.init();
                if(!pilha.desempilhaEmpilha(str[0], pilha2))
                {
                    cout << "pilha vazia.";
                    break;
                }
                pilha.mostrar();
                pilha2.mostrar();
            }
            break;

        }
        op = menu();
    }
}
