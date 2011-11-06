#include <iostream>

// cria matrizes 3x3, e pode somá-las.

void zerar(int [3][3][3]);
void input(int [3][3][3]);
void somar(int [3][3], int [3][3], int [3][3]);
void mostrar(int [3][3]);

int main()
{
    int a[3][3][3];
    zerar(a);

    int op = -1;
    while(op)
    {
        if(op == 1)
            input(a);
        else if (op == 2)
            somar(a[0], a[1], a[2]);
        else if (op == 3)
        {
            mostrar(a[0]);
            mostrar(a[1]);
            mostrar(a[2]);
        }

        std::cout << "\n\n0 - SAIR\n";
        std::cout << "1 - CARREGAR MATRIZES\n";
        std::cout << "2 - SOMAR MATRIZES\n";
        std::cout << "3 - MOSTRAR MATRIZES\n";
        std::cin >> op;
    }

    return 0;
}

void zerar(int a[3][3][3])
{
    int i = -1, // matriz
        j, // linha
        k, // coluna
        l = 3, // matriz
        m = 3,// linha
        n = 3;// coluna

    while(++i < l)
    {
        j = -1;
        while(++j < m)
        {
            k = -1;
            while(++k < n)
                a[i][j][k] = 0;
        }
    }
}

void input(int a[3][3][3])
{
    int i = -1, // matriz
        j, // linha
        k, // coluna
        l = 2, // matriz
        m = 3,// linha
        n = 3;// coluna

    while(++i < l)
    {
        std::cout << "\n\n\nConstrucao da matriz " << i << ":\n\n";
        j = -1;
        while(++j < m)
        {
            k = -1;
            while(++k < n)
            {
                std::cout << "\nInsira o valor da matriz " << i << ", na linha " << j << ", na coluna " << k << ": ";
                std::cin >> a[i][j][k];
            }
        }
    }
}

void somar(int a[3][3], int b[3][3], int c[3][3])
{
    int l = 3,
        k = 3,
        i = -1,
        j;
    while(++i < l)
    {
        j = -1;
        while(++j < k)
            c[i][j] = a[i][j] + b[i][j];
    }
}

void mostrar(int a[3][3])
{
    std::cout << "\n\n\n(matriz)";
    int i = -1,
        j,
        k = 3,
        l = 3;

    while(++i < l)
    {
        std::cout << "\n";
        j = -1;
        while(++j < k)
            std::cout << a[i][j] << "\t";
    }
}
