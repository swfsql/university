#include<iostream>
using namespace std;

const unsigned int TAM = 40;

struct No
{
    int info;
};

struct Fila
{

};

struct Fila
{
    int inicio,
        fim,
        tamanho;

    bool enfileira(char str[40])
    {
        if(cheia())
            return false;

        // TODO

        return true;
    }

    bool desenfileira(char str[40])
    {
        if(vazia())
            return false;

        // TODO

        return true;
    }

    bool vazia()
    {
        return inicio == -1;
    }

    bool cheia()
    {
        return (topo+1)%tamanho == inicio;
    }

    bool primeiro(char& c)
    {
        if(vazia())
            return false;

        // TODO

        return true;
    }

    int contador()
    {
        // TODO

        return 0;
    }
};

int menu ()
{
    const int tam = 7;
    char ops[tam][40] = {"encerrar", "enfileirar", "desenfileirar", "vazia?", "cheia?", "primeiro?", "quantidade?"};

    int op = -1;
    cout << "\n\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}

int main ()
{
    Fila fila;
    fila.inicio = -1;

    int op = menu(),
        i; // temp
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "int a inserir: ";
            cin >> i;
            if(!pilha.empilhaValido(str))
                cout << "insercao invalida, ou pilha cheia.";
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
            pilha.mostrar(); // mostra ida, então a volta
            if(!pilha.validar())
            {
                cout << "expressao invalida, ou a lista esta vazia.\n";
                break;
            }
            cout << "expressao valida.";
            break;

            case 8:

            break;
        }
        op = menu();
    }
}
