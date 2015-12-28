#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

/* clipboard para teste
1 4
a
7
4
(
7
4
)
7
4
+ 3 - (([1+1]) [(0-0)]) {(())}
7
5
7
5 5 5 5 5 7

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

    bool validar()
    {
        if(vazia())
            return false;

        Pilha *operadores = new Pilha; // seriam os parênteses
        operadores->init();
        bool ret = true; // retorno
        char c; // temp
        char str[40] = " ";

        // percorrer a pilha, pegando os parênteses
        int i = topo+1;
        while(i--)
        {
            c = nos[i].info;
            if(c == ')' || c == ']' || c == '}')
            {
               str[0] = c;
               operadores->empilha(str);
            } else switch (c)
            {
                case '(':
                    operadores->topoNo(c);
                    if(')' == c)
                        operadores->desempilha(str);
                    else
                    {
                        ret = false;
                        i = 0;
                    }
                    break;
                case '[':
                    operadores->topoNo(c);
                    if(']' == c)
                        operadores->desempilha(str);
                    else
                    {
                        ret = false;
                        i = 0;
                    }
                    break;
                case '{':
                    operadores->topoNo(c);
                    if('}' == c)
                        operadores->desempilha(str);
                    else
                    {
                        ret = false;
                        i = 0;
                    }
                    break;
            }

        }

        ret = ret && operadores->topo == -1;
        delete operadores;
        return ret;
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
    const int tam = 8;
    char ops[tam][40] = {"encerrar", "iniciar pilha", "pilha vazia", "pilha cheia", "empilha", "desempilha", "topo pilha", "verificar expressao"};

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
            cout << "char desempilhado: " << str[0];
            break;

        case 6:
            if(!pilha.topoNo(str[0]))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "char no topo: " << str[0];
            break;

        case 7:
            pilha.mostrar();
            if(!pilha.validar())
            {
                cout << "expressao invalida, ou a lista esta vazia.\n";
                break;
            }
            cout << "expressao valida.";
            break;
        }
        op = menu();
    }
}
