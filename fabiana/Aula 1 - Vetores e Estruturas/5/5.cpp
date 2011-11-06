#include <iostream>
#include <cstdio>
using namespace std;

/*clipboard para teste
123
thi
nome do produto
10.00
tipo de pagamento
1 1 1
2 2 2

*/

struct Data
{
    int dia;
    int mes;
    int ano;
};

struct Cliente
{
    int codigo;
    char nome[30];

    char prodComprado[80];
    int valorComprado;

    Data dataEntrega;
    Data dataPagamento;
    char tipoPagamento[10];
};

int main()
{
    Cliente c;


    // input

    cout << "Insira o codigo: ";
    cin >> c.codigo;

    cout << "\nInsira o nome: ";
    fflush(stdin);
    gets(c.nome);

    cout << "\nInsira o nome do produto comprado: ";
    fflush(stdin);
    gets(c.prodComprado);

    cout << "\nInsira o valor do produto: ";
    cin >> c.valorComprado;

    cout << "\nInsira o tipo de pagamento: ";
    fflush(stdin);
    gets(c.tipoPagamento);

    cout << "\nInsira a data de entrega (dd mm aaaa)";
    cout<<"Dia: ";
    cin >> c.dataEntrega.dia;
    cout<<"Mes: ";
    cin >> c.dataEntrega.mes;
    cout<<"Ano: ";
    cin >> c.dataEntrega.ano;

    cout << "\nInsira a data de pagamento (dd mm aaaa)";
    cout<<"Dia: ";
    cin >> c.dataPagamento.dia;
    cout<<"Mes: ";
    cin >> c.dataPagamento.mes;
    cout<<"Ano: ";
    cin >> c.dataPagamento.ano;


    // output

    cout << "\n\n\ncodigo: " << c.codigo;
    cout << "\nnome: " << c.nome;
    cout << "\nnome do produto comprado: " << c.prodComprado;
    cout << "\nvalor do produto: " << c.valorComprado;
    cout << "\ntipo de pagamento: " << c.tipoPagamento;
    cout << "\ndata de entrega (dd/mm/aaaa): " << c.dataEntrega.dia << "/" << c.dataEntrega.mes << "/" << c.dataEntrega.ano;
    cout << "\ndata de pagamento (dd/mm/aaaa): " << c.dataPagamento.dia << "/" << c.dataPagamento.mes << "/" << c.dataPagamento.ano;

    return 0;
}

