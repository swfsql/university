#include <iostream>
#include <cstdio>
using namespace std;

/* clipboard para teste
1 1 3 o1 a1 e1
1 2 1 o2 a2 e2
1 3 2 o3 a1 e3

2 1 1 1 1 1
2 1 2 2 2 2
2 2 3 3 3 3
2 2 4 4 4 4
2 3 5 5 5 5
2 3 6 6 6 6

*/

struct Obra
{
    int numTombo;
    int numExemplar;
    struct data
    {
        int dia;
        int mes;
        int ano;
    } dataCompra;
};

struct Tombo
{
    int numTombo;
    int codArea;
    struct nome
    {
        char obra[30];
        char autor[30];
        char editora[30];
    } nome;

    Obra a[3];
    int lObra;
};

int numPraIndex(int n, Tombo a[], int l)
{
    int i = -1;
    while(++i < l)
        if (a[i].numTombo == n)
            return i;
    return -1;
}

bool stringMenor(char a1[30], char a2[30])
{
    int l = sizeof(a1);
    int l2 = sizeof(a2);
    if (l2 < l)
        l = l2;

    l >>= 1;

    int i = -1;
    char c1, c2;
    while(++i < l)
    {
        c1 = a1[i];
        c2 = a2[i];
        if (c1 < c2)
            return true;
        else if (c2 < c1)
            return false;
    }
    return l<<1 != l2;
}



void cadastrarTombo(Tombo a[], int &l)
{
    if (l == 20)
    {
        cout << "voce ja preencheu todos os espacos para Tombo!";
        return;
    }

    Tombo t;
    cout << "insira o Numero do tombo: ";
    cin >> t.numTombo;
    cout << "insira o Codigo de area: ";
    cin >> t.codArea;
    cout << "insira o nome da Obra: "; // fflush(stdin);
    cin >> t.nome.obra;
    cout << "insira o nome do Autor: "; // fflush(stdin);
    cin >> t.nome.autor;
    cout << "insira o nome da Editora: "; // fflush(stdin);
    cin >> t.nome.editora;
    t.lObra = 0;
    a[l++] = t;
    cout << "Tombo cadastrado com sucesso.";
}

void cadastrarObra(Tombo a[], int &l)
{
    if (!l)
    {
        cout << "primeiro crie Tombos para entao cadastrar as Obras.";
        return;
    }

    int num;
    cout << "insira o Numero do Tombo: ";
    cin >> num;
    int i = numPraIndex(num, a, l);
    if (i == -1)
    {
        cout << "Tombo nao encontrado!";
        return;
    }
    if (a[i].lObra == 3)
    {
        cout << "esse Tombo ja possui 3 Obras";
        return;
    }

    Obra o;
    o.numTombo = a[i].numTombo;
    cout << "insira o Numero do exemplar: ";
    cin >> o.numExemplar;
    cout << "insira o Dia da compra: "; // fflush(stdin);
    cin >> o.dataCompra.dia;
    cout << "insira o Mes da compra: "; // fflush(stdin);
    cin >> o.dataCompra.mes;
    cout << "insira o Ano da compra: "; // fflush(stdin);
    cin >> o.dataCompra.ano;
    a[i].a[a[i].lObra++] = o;

    cout << "Obra cadastrada ao Tombo com sucesso.";
}

void mostrarObras_area(Tombo a[], int l)
{
    // funcionamento - ordenamos o arranjo, toda vez que executamos a função.
    Tombo a2[20];
    int i = -1,
        j;
    Tombo t1,
          t2;
    while(++i < l)
    {
        t1 = a[i];
        j = -1;
        while(++j < i)
        {
            t2 = a2[j];
            if (t1.codArea < t2.codArea)
            {
                a2[j] = t1;
                t1 = t2;
            }
        }
        a2[j] = t1;
    }
    a = a2;
    //

    Obra o;
    i = -1;
    while(++i < l)
    {
        cout << "\n\n";
        cout << a[i].codArea << ": " << a[i].nome.autor << " - " << a[i].nome.obra << " - " << a[i].nome.editora;
        j = -1;
        while(++j < a[i].lObra)
        {
            cout << "\n";
            o = a[i].a[j];
            cout << "numero exemplar: " << o.numExemplar << "\t";
            cout << "data de compra: " << o.dataCompra.dia << "/" << o.dataCompra.mes << "/" << o.dataCompra.ano;
        }

    }

}

void mostrarObras_autor(Tombo a[], int l)
{
    // funcionamento - ordenamos o arranjo, toda vez que executamos a função.
    Tombo a2[20];
    int i = -1,
        j;
    Tombo t1,
          t2;
    while(++i < l)
    {
        t1 = a[i];
        j = -1;
        while(++j < i)
        {
            t2 = a2[j];
            if (stringMenor(t1.nome.autor, t2.nome.autor))
            {
                a2[j] = t1;
                t1 = t2;
            }
        }
        a2[j] = t1;
    }
    a = a2;
    //

    Obra o;
    i = -1;
    while(++i < l)
    {
        cout << "\n\n";
        cout << a[i].nome.autor << " - " << a[i].nome.obra << " - " << a[i].nome.editora << " - area: " << a[i].codArea;
        j = -1;
        while(++j < a[i].lObra)
        {
            cout << "\n";
            o = a[i].a[j];
            cout << "numero exemplar: " << o.numExemplar << "\t";
            cout << "data de compra: " << o.dataCompra.dia << "/" << o.dataCompra.mes << "/" << o.dataCompra.ano;
        }
    }
}

void mostrarObras_editor(Tombo a[], int l)
{
    // funcionamento - ordenamos o arranjo, toda vez que executamos a função.
    Tombo a2[20];
    int i = -1,
        j;
    Tombo t1,
          t2;
    while(++i < l)
    {
        t1 = a[i];
        j = -1;
        while(++j < i)
        {
            t2 = a2[j];
            if (stringMenor(t1.nome.editora, t2.nome.editora))
            {
                a2[j] = t1;
                t1 = t2;
            }
        }
        a2[j] = t1;
    }
    a = a2;
    //

    Obra o;
    i = -1;
    while(++i < l)
    {
        cout << "\n\n";
        cout << a[i].nome.editora << " - " << a[i].nome.autor << " - " << a[i].nome.obra << " - area: " << a[i].codArea;
        j = -1;
        while(++j < a[i].lObra)
        {
            cout << "\n";
            o = a[i].a[j];
            cout << "numero exemplar: " << o.numExemplar << "\t";
            cout << "data de compra: " << o.dataCompra.dia << "/" << o.dataCompra.mes << "/" << o.dataCompra.ano;
        }
    }
}


int menu(Tombo a[], int &l)
{
    int op = 1;
    while(op)
    {
        cout << "0 - Encerrar programa\n";
        cout << "1 - Cadastrar tombo\n";
        cout << "2 - Cadastrar obras\n";
        cout << "3 - Mostrar obras (por area)\n";
        cout << "4 - Mostrar obras (por autor)\n";
        cout << "5 - Mostrar obras (por editora)\n";
        cout << "Escolha uma opcao\n";
        cin >> op;

        switch(op)
        {
            case 1:
            // cadastrar tombo
            cadastrarTombo(a, l);
            break;
            case 2:
            // cadastrar obras
            cadastrarObra(a, l);
            break;
             case 3:
            // mostrar obras (por area)
            mostrarObras_area(a, l);

            break;
             case 4:
            // mostrar obrar (por autor)
            mostrarObras_autor(a, l);

            break;
             case 5:
            // mostrar obras (por editora)
            mostrarObras_editor(a, l);
            break;
        }
        cout << "\n\n\n";
    }
}

//


int main()
{
    Tombo a[20];
    int lTombo = 0;

    menu(a, lTombo);

    return 0;

/*
    cout << "Insira o codigo: ";
    cin >> c.codigo;

    cout << "\nInsira o nome: ";
    // fflush(stdin);
    gets(c.nome);

*/

}
