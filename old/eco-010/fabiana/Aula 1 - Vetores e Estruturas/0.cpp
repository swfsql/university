#include <iostream>

int main()
{
    int array0[10],
        array1[10],
        soma[10],
        prod[10];

    int i = -1;
    std::cout << "insira 10 elemento do primeiro arranjo: ";
    while(++i <10)
        std::cin >> array0[i];

    i = -1;
    std::cout << "insira 10 elemento do segundo arranjo: ";
    while(++i <10)
        std::cin >> array1[i];

    // operações
    i = -1;
    while(++i <10)
    {
        soma[i] = array0[i] + array1[i];
        prod[i] = array0[i] * array1[i];
    }
    // mostra somas
    std::cout << "\n\nsomas\n";
    i = -1;
    while(++i < 10)
        std::cout << soma[i] << "\t";

    // mostra produtos
    std::cout << "\nprodutos\n";
    i = -1;
    while(++i < 10)
        std::cout << prod[i] << "\t";

    return 0;
}
