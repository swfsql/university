#include<iostream>
using namespace std;

int menu ();

struct No
{
    int info;
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

    void esvazia()
    {
        No* del = topo;
        while(topo)
        {
            del = topo;
            topo = del->next;
            delete del;
        }
    }

    bool vazia()
    {
        return topo == 0;
    }

    bool cheia()
    {
        No* no = new No;
        delete no;
        return !no;
    }

    void empilha(int valor)
    {
        No* no = new No;
        no->info = valor;
        no->next = topo;
        topo = no;
        return;
    }

    bool desempilha(int& valor)
    {
        if(vazia())
            return false;
        No* del = topo;
        valor = del->info;
        topo = del->next;
        delete del;
        return true;
    }

    bool topoNo(int& valor)
    {
        if(vazia())
            return false;
        valor = topo->info;
        return true;
    }

    void mostrar()
    {
        if(vazia())
            return;
        No* no = topo;
        cout << "\n";
        while(no)
        {
            cout << no->info << " ";
            no = no->next;
        }
        cout << "\n";
    }
};

void b10_b2 (int input)
{
    Pilha pilha;
    pilha.init();

    if(!input)
        pilha.empilha(0);

    while (input)
    {
        pilha.empilha(input & 1 ? 1 : 0 );
        input >>= 1;
    }

    pilha.mostrar();
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
            cout << "insira um inteiro positivo na base 10 que queira saber seu valor na base 2: ";
            cin >> i;
            b10_b2(i);
            break;
        }
        op = menu();
    }
}


int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "int para binario"};

    int op = -1;
    cout << "\n";
    cout << "\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}
