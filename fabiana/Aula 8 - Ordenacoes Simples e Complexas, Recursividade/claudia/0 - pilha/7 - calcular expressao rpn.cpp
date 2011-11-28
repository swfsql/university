#include<cstdlib>
#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;

// draft

/* clipboard para teste
1 1 1 2 2 + 1 4 2 - 1 4 1 5 2 * 2 / 3 5

*/


struct No
{
    float num;
    char op;
    int tipo; // 0 = num, 1 = operador;
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
    }

    void empilha_num(float num = 0)
    {
        No* no;
        no = new No;
        no->tipo = 0;
        no->num = num;
        no->next = topo;
        topo = no;
    }

    void empilha_op(char po = '+')
    {
        No* no;
        no = new No;
        no->tipo = 1;
        no->op = po;
        no->next = topo;
        topo = no;
    }


    bool desempilha(char& c, float& num)
    {
        if(vazia())
            return false;
        int tipo = topo->tipo;
        if(tipo == 0) c = NULL;
        else c = topo->op;
        num = topo->num;
        No* del = topo;
        topo = topo->next;
        delete del;
        return true;
    }

    bool topoNo(char& c, float& num, int j = 0)
    {
        if(vazia())
            return false;
        int i = -1;
        No* no = topo;
        int tipo = 0;
        while(no)
        {
            tipo = no->tipo;
            if(tipo == 0)
                num = no->num;
            else if (tipo == 1)
                c = no->op;
            if (++i == j)
                break;
            if (!no->next)
                return false;
            no = no->next;
        }

        if(tipo == 0)
            c = NULL;
        return true;
    }

    bool rpn(float& resultado)
    {
        if(vazia())
            return false;

        int i = 0, tipo;
        No* no = topo;
        char c, c2;
        float num, a, b, r;
        bool flag = true, existe, flag2 = true;;

        Pilha* temp = new Pilha;
        temp->init();

        while(no && flag)
        {
            if(flag2)
            {
                tipo = no->tipo;
                if(tipo == 0) c = NULL;
                else c = no->op;
                num = no->num;
            } else
            {
                tipo = 0;
                c = NULL;
                num = r;
            }

            if(tipo == 0)
            {
                // irá empilhar um número. se o próximo for um num: e próximo for um operador, fazer a conta., senão, expressão inválida.
                if (flag2) temp->empilha_num(num);
                flag2 = true;
                existe = temp->topoNo(c, num, 1);
                if(!c && existe)
                {
                    existe = temp->topoNo(c, num, 2);
                    if(c && existe)
                    {
                        temp->desempilha(c2, a);
                        temp->desempilha(c2, b);
                        temp->desempilha(c, r);
                        switch(c)
                        {
                            case '+':
                                r = a + b;
                                break;
                            case '-':
                                r = a - b;
                                break;
                            case '*':
                                r = a * b;
                                break;
                            case '/':
                                r = a / b;
                                break;
                        }
                        temp->empilha_num(r);
                        flag2 = false;
                        continue;
                    } else
                    {
                        flag = false;
                    }
                }

            } else if (tipo == 1)
            {
                temp->empilha_op(c);
            }
            no = no->next;
        }

        temp->topoNo(c, resultado);
        temp->esvaziar();
        if(flag)
        {
            if(c) return false;
            return resultado;
        }
        return false;
    }

    void mostrar()
    {
        if(vazia())
            return;
        int tipo;
        char c;
        float num;
        No* no = topo;
        cout << "\n";
        while(no)
        {
            tipo = no->tipo;
            if(tipo == 0) c = NULL;
            else c = no->op;
            num = no->num;
            if(c) cout << c;
            else cout << num;
            no = no->next;
        }
        cout << "\n";
    }
};

int menu ()
{
    const int tam = 6;
    char ops[tam][40] = {"encerrar", "inserir numero", "inserir operador", "mostrar", "remover ultimo", "calcular por rpn"};

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
    Pilha pilha;
    pilha.init();
    int op = menu();
    float num;
    char str[40];
    char c;
    while(op)
    {
        switch (op)
        {
        case 1:
            cout << "numero a inserir: ";
            cin >> num;
            pilha.empilha_num(num);
            break;

        case 2:
            cout << "operador a inserir: ";
            cin >> c;
            pilha.empilha_op(c);
            break;

        case 3:
            // parte ruim: se estiver cheia, é inútil dar input no 'str'
            cout << "visualizacao como pilha: ";
            pilha.mostrar();
            break;

        case 4:
            if(!pilha.topoNo(c, num))
            {
                cout << "pilha vazia.";
                break;
            }
            cout << "remover ";
            if(c) cout << "o operador " << c;
            else cout << "o numero " << num;
            cout << "? (s/n): ";
            cin >> c;
            if(c == 's' || c == 'S')
                pilha.desempilha(c, num);
            break;

        case 5:
            if(!pilha.rpn(num))
            {
                cout << "expressao invalida.";
                break;
            }
            cout << "= " << num;
            break;
        }
        op = menu();
    }
}
