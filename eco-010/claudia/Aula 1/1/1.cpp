#include <iostream>

// insere elemento no começo

//        array      value      lenght     index
void in(int a[], int v = 0, int l = 0, int i = 0);

int main()
{
    int L = 5, // tamanho max
        l = 0, // tamanho usado
        a[L];

    // zerar
    int i = -1;
    while(++i<L)
        a[i] = 0;

    // inserir no começo
    i = -1;
    while(++i < L)
        in (a, i, ++l);

    // debug
    //in (a, -1, l);

    // mostrar
    i = -1;
    while(++i<L)
        std::cout << a[i] << "\t";
}

void in(int a[], int v, int l, int i)
{
    // libera espaço no arranjo
    while (l > i)
        a[l] = a[--l];

    // adiciona o valor no começo
    a[i] = v;
}
