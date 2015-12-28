#include <iostream>

// insere elemento no final

int main()
{

    int a[3];

    // zerar
    int i = -1;
    while(++i<3)
        a[i] = 0;

    // inserir no final
    i = -1;
    while(++i<3)
        a[i] = i;

    // mostrar
    i = -1;
    while(++i<3)
        std::cout << a[i] << "\t";
}
