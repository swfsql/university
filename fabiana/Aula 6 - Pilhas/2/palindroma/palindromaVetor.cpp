#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

/* clipboard para teste
1 4
aba
7
5
7

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

    bool topoNo(char& c)
    {
        if(vazia())
            return false;
        c = nos[topo].info;
        return true;
    }

    bool palindromo()
    {
        if(vazia())
            return false;

        Pilha* pilha = new Pilha;
        pilha->init();
        bool ret = true; // retorno de true
        int l = (topo+1)>>1, // equivale a quebrar o valor em dois, tipo ceil(), sendo ele inteiro e positivo.
            i = -1;
        char str[40];

        // tira metade da pilha, e empilha isso em outra pilha
        while(++i < l)
        {
            desempilha(str);
            pilha->empilha(str);
        }

        // compara as duas metades
        while(i--)
        {
            if(pilha->nos[i].info != nos[i].info)
            {
                ret = false;
                i = -1;
                break;
            }

        }

        // junta de volta as duas metades na pilha original
        while(++i < l)
        {
            pilha->desempilha(str);
            empilha(str);
        }

        delete pilha;

        return ret;
    }

    void mostrar2()
    {
        if(vazia())
            return;
        int i = topo+1;
        cout << "\n";
        while(i--)
            cout << nos[i].info;
        cout << "\n";
        ++topo;
        while(++i < topo)
            cout << nos[i].info;
        --topo;
        cout << "\n";
    }
};

int menu ()
{
    const int tam = 8;
    char ops[tam][40] = {"encerrar", "iniciar pilha", "pilha vazia", "pilha cheia", "empilha", "desempilha", "topo pilha", "verificar palindromo"};

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
            // parte ruim: se estiver cheia, é inútil dar input no 'str'
            cout << "string a inserir: ";
            fflush(stdin);
            gets(str);
            if(!pilha.empilha(str))
                cout << "pilha cheia.";
            break;

        case 5:
            if(!pilha.desempilha(str))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "str desempilhado: " << str;
            break;

        case 6:
            if(!pilha.topoNo(str[0]))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "str no topo: " << str;
            break;

        case 7:
            pilha.mostrar2(); // mostra ida, então a volta
            if(!pilha.palindromo())
            {
                cout << "palavra nao e palindroma, ou a lista esta vazia\n";
                break;
            }
            cout << "palavra eh palindroma";
            break;
        }
        op = menu();
    }
}
