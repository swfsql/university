#include<iostream>
using namespace std;

struct No
{
    int info;
    No* next;
};

struct Fila
{
    No  *_inicio,
        *_fim;
    int _tamanho;

    void enfileira(int i)
    {
        _fim = (_fim ?
                    _fim->next :
                    _inicio) =
                    new No;
        _fim->info = i;
        _fim->next = 0;
        ++_tamanho;
    }

    bool desenfileira(int& i)
    {
        if(vazia())
            return false;
        i = _inicio->info;
        _inicio = --_tamanho ?
            _inicio->next :
            (_fim = 0);
        return true;
    }

    bool vazia()
    {
        return !_inicio;
    }

    bool primeiro(int& i)
    {
        if(vazia())
            return false;
        i = _inicio->info;
        return true;
    }

    int tamanho()
    {
        return _tamanho;
    }

    void mostrar()
    {
        No* no = _inicio;
        cout << "fila: ";
        while(no)
        {
            cout << no->info << " ";
            no = no->next;
        }
        cout << "\n";
    }
};

int menu ()
{
    const int tam = 6;
    char ops[tam][40] = {"encerrar", "enfileirar", "desenfileirar", "vazia?", "primeiro?", "quantidade?"};

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
    fila._inicio = fila._fim = 0;
    fila._tamanho = 0;

    int op = menu(),
        i; // temp
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "int a inserir: ";
            cin >> i;
            fila.enfileira(i);
            break;

        case 2:
            if(fila.desenfileira(i))
                cout << "int desenfileirado: " << i;
            else
                cout << "fila vazia.";
            break;

        case 3:
            cout << "fila "
                 << (fila.vazia() ?
                        "" :
                        "nao ")
                 << "esta fazia.";
            break;

        case 4:
            if(fila.primeiro(i))
                cout << "primeiro int: " << i;
            else
                cout << "fila vazia.";
            break;

        case 5:
            fila.mostrar();
            cout << "fila tem " << fila.tamanho() << " elementos";
            break;
        }
        op = menu();
    }
}
