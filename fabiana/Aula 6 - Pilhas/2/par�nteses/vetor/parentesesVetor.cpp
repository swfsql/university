#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

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

    bool empilhaValido(char str[40])
    {
        if(cheia())
            return false;
        int i = -1;
        char c; // cada char do vetor
        char cTopoNo; // para verificar o topoNó

        while(c = str[++i])
        {
            // se for adicionar elemento que 'abre', adiciona normalmente
            if(c == '(' || c == '[' || c == '{')
            {
                nos[++topo].info = c;
                continue;
            }

            // se for adicionar elemento que 'fecha'
            if(c == ')' || c == ']' || c == '}')
            {
                if (!topoNo(cTopoNo))
                    return false; // erro: fechou demais. exemplo: '())'

                if(cTopoNo == c)
                {
                    // adiciona normalmente
                    nos[++topo].info = c;
                    continue;
                }
                return false; // erro: fechou com character inválido. exemplo: '[)'
            }
            continue; // desconsidera characters que não são ([{}])
        }
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

        Pilha* operadores = new Pilha; // na verdade não é operadores, mas só ()[]{}
         operadores->init();
        bool ret = true; // retorno de true
        int l,
            i = -1,
            topo2;
        char str[40], c;
        str[1] = '\0';

        // copia só os simbolos da pilha original
        i = topo+1; // tamanho
        topo2 = -1;
        while(i--)
        {
            c = nos[i].info;
            if(c == '(' || c == '[' || c == '{' || c == ')' || c == ']' || c == '}')
           {
               str[0] = c;
               pilha2->empilha(str);// guarda em ordem invertida em relação à uma pilha
               ++topo2;
               cout << ".";
           }
        }
        // TODO: descobrir se é +- 1
        l = (topo2)>>1; // equivale a quebrar o valor em dois com ceil(), sendo ele inteiro positivo.

        // debug
        {
            i = topo2+1;
            i = l+1;
            cout << "\ndebug 1:\n\n";
            cout << "topo = " << i-1 << "\n";
            while(i--)
                cout << pilha2->nos[i].info;
            cout << "\n\n";
        }

        // tira metade da pilha, e empilha isso em outra pilha
        i = -1;
        while(++i < l+1)
        {
            pilha2->desempilha(str);

            // gambiarra
            {
                c = str[0];
                c = (
                     c == '(' ? ')' : c == ')' ? '(' :
                     c == '[' ? ']' : c == ']' ? '[' :
                     c == '{' ? '}' : c == '}' ? '{' : c
                    );
                str[0] = c;
            }

            pilha->empilha(str);
            cout << ",";
        }

        // compara as duas metades
        i = l+1;
        while(i--)
        {

            // debug
            {
                cout << "\ncomprar: " << pilha->nos[i].info << " e " << pilha2->nos[i].info << "\n";
            }

            if(pilha->nos[i].info != pilha2->nos[i].info)
            {
                ret = false;
                i = -1;
                break;
            }

        }

        // junta de volta as duas metades
        // não precisa mais, pois não mudei a pilha original
        delete pilha;
        delete pilha2;

        return ret;
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
    const int tam = 9;
    char ops[tam][40] = {"encerrar", "iniciar pilha", "pilha vazia", "pilha cheia", "empilha", "desempilha", "topo pilha", "verificar expressao", "inserir com validade"};

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
            pilha.mostrar(); // mostra ida, então a volta
            if(!pilha.validar())
            {
                cout << "expressao invalida, ou a lista esta vazia.\n";
                break;
            }
            cout << "expressao valida.";
            break;

            case 8:
            // parte ruim: se estiver cheia, é inútil dar input no 'str'
            cout << "string a inserir: ";
            fflush(stdin);
            gets(str);
            if(!pilha.empilhaValido(str))
                cout << "insercao invalida, ou pilha cheia.";
            break;
        }
        op = menu();
    }
}
