#include <iostream>

// exclui primeiro elemento

int main()
{
    int l = 3;
    int a[3] = {1,2,3};

    // remover do começo
    --l;
    int i = 0;
    while(i<l)
        a[i] = a[++i];

    // mostrar
    i = -1;
    while(++i<l)
        std::cout << a[i] << "\t";

    return 0;
}
