#include<iostream>
using namespace std;

const unsigned int TAM = 3;

struct No
{
    int info;
};


struct Pilha
{
    // variáveis
    int _topo;
    No _nos[TAM];

    // funções
    void init()
    {
        _topo = -1;
    }

    bool vazia()
    {
        return _topo == -1;
    }

    bool cheia()
    {
        return _topo+1 == TAM;
    }

    bool empilha(int _input)
    {
        if(cheia())
            return false;
        int _i = -1;
        _nos[++_topo].info = _input;
        return true;
    }

    bool desempilha(int& _output)
    {
        if(vazia())
            return false;
        _output = _nos[_topo--].info;
        return true;
    }

    void mostrar()
    {
        if(vazia())
            return;
        int _i = _topo+1;
        cout << "\nimpressao como pilha:\n";
        while(_i--)
            cout << _nos[_i].info;
        cout << "\n";
    }
};


struct Fila
{
    int _inicio,
        _fim,
        _tamanho,
        _nos[TAM];

    bool enfileira(int _input)
    {
        if(cheia())
            return false;

        _nos[++_fim % TAM] = _input;
        ++_tamanho;
        if(_inicio == -1)
            _inicio = _fim;
        return true;
    }

    bool desenfileira(int& _output)
    {
        if(vazia())
            return false;
        _output = _nos[_inicio];
        --_tamanho;
        _inicio = _tamanho ?
            (_inicio+1) % TAM :
            (_fim = -1);
        return true;
    }

    bool vazia()
    {
        return _fim == -1;
    }

    bool cheia()
    {
        return (_fim + 1) % TAM == _inicio;
    }

    bool primeiro(int& _output)
    {
        if(vazia())
            return false;
        _output = _nos[_inicio];
        return true;
    }

    int tamanho()
    {
        return _tamanho;
    }

    void mostrar()
    {
        int i = -1, l = _tamanho;
        cout << "fila: ";
        while(++i < l)
        {
            cout << _nos[(i+_inicio)%TAM] << " ";
        }
        cout << "\n";
    }

    void inverter() // fiz na prova da cláudia
    {
        Pilha* _pilha = new Pilha;
        _pilha->init();
        char str[40] = " ";
        int _i;

        while(_inicio + 1)
        {
            desenfileira(_i);
            _pilha->empilha(_i);
        }
        while(_pilha->_topo + 1)
        {
            _pilha->desempilha(_i);
            enfileira(_i);
        }

    }

};


int menu ()
{
    const int tam = 8;
    char ops[tam][40] = {"encerrar", "enfileirar", "desenfileirar", "vazia?", "cheia?", "primeiro?", "quantidade?", "inverter"};

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
    fila._inicio = -1;
    fila._fim = -1;
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
            if(!fila.enfileira(i))
                cout << "fila cheia ou valor invalido. insira um valor impar.";
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
            cout << "fila "
                 << (fila.cheia() ?
                        "" :
                        "nao ")
                 << "esta cheia.";
            break;

        case 5:
            if(fila.primeiro(i))
                cout << "primeiro int: " << i;
            else
                cout << "fila vazia.";
            break;

        case 6:
            fila.mostrar();
            cout << "fila tem " << fila.tamanho() << " elementos";
            break;

        case 7:
            fila.mostrar();
            fila.inverter();
            fila.mostrar();
            break;
        }
        op = menu();
    }
}
