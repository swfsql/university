#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

/* clipboard para teste
1 3
aba
6
4
6

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
    int tam;

    // funções
    void init()
    {
        topo = 0;
        tam = 0;
    }

    void esvaziar()
    {
        No* del;
        while(!vazia())
        {
            del = topo;
            topo = topo->next;
            delete del;
        }
    }

    bool vazia()
    {
        return topo == 0;
        tam = 0;
    }

    void empilha(char str[40])
    {
        No* no;
        int i = -1;
        while(str[++i])
            if(str[i] != ' ') // desconsidera os espaços
            {
                no = new No;
                no->info = str[i];
                no->next = topo;
                topo = no;
                ++tam;
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
        --tam;
        return true;
    }

    bool topoNo(char& c)
    {
        if(vazia())
            return false;

        c = topo->info;
        return true;
    }

    bool validar()
    {
        if(vazia())
            return false;

        Pilha* metade = new Pilha; // seriam metade da lista
        metade->init();
        int l = (tam >> 1) + 1;
        bool ret = true; // retorno
        char c; // temp
        char str[40] = " ";

        // percorrer a pilha, empilhando na metade
        No* no = topo;
        while(--l)
        {
            str[0] = no->info;
            metade->empilha(str);
            no = no->next;
        }
        if(tam & 1)
            no = no->next;

        // compara a pilha, do centro pras pontas
        while(no)
        {
            if(no->info != metade->topo->info)
            {
                ret = false;
                no = 0;
                continue;
            }
            no = no->next;
            metade->desempilha(str);
        }

        ret = ret && metade->topo == 0;
        metade->esvaziar();
        delete metade;
        return ret;
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
    const int tam = 7;
    char ops[tam][40] = {"encerrar", "iniciar pilha", "pilha vazia", "empilha", "desempilha", "topo pilha", "verificar expressao"};

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
            cout << "pilha iniciada.";
            break;

        case 2:
            cout << "pilha "
                 << (pilha.vazia() ?
                        "" :
                        "nao ")
                 << "esta fazia.";
            break;

        case 3:
            // parte ruim: se estiver cheia, é inútil dar input no 'str'
            cout << "string a inserir: ";
            fflush(stdin);
            gets(str);
            pilha.empilha(str);
            break;

        case 4:
            if(!pilha.desempilha(str))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "char desempilhado: " << str[0];
            break;

        case 5:
            if(!pilha.topoNo(str[0]))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "char no topo: " << str[0];
            break;

        case 6:
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
