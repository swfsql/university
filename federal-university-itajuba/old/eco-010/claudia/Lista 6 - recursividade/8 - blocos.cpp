#include <iostream>
#include <cstring>
#include <cstdio>
using namespace std;

struct bloco
{
   char nome[50]; int idade; bloco *prox;
};

struct lista
{
    bloco* topo;

    void empilha(char n[50], int i = 0)
    {
        bloco* no = new bloco;
        strcpy(no->nome, n); no->idade = i;
        no->prox = topo; topo = no;
    }

    void mostra(bloco* no)
    {
        if(!no) return;
        cout << no->nome << " - " << no->idade << "\n";
        mostra(no->prox);
    }
};

int menu ()
{
    const int tam = 3;
    char ops[tam][40] = {"encerrar", "add", "mostrar"};

    int op = -1;
    cout << "\n\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}

int main()
{
    int op = menu(),
        i;
    char n[50];

    lista l;
    l.topo = 0;

    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "insira o nome: "; fflush(stdin);
            gets(n);
            cout << "insira a idade: ";
            cin >> i;
            l.empilha(n, i);
            break;

        case 2:
            cout << "cadastros: \n\n"; l.mostra(l.topo);
            break;
        }
        op = menu();
    }
    return 0;
}
