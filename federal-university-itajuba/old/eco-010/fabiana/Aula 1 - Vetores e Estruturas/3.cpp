#include <iostream>

int main()
{
    // cria arranjo
    int l = 20,
        a[l];

    // cria e imprime valores
    {
        int i = -1;
        while(++i < l)
        {
            a[i] = i;
            std::cout << i << "  ";
        }
        std::cout << "\n\n";
    }

    // inverte o arranjo
    {
        int l2 = l-- >> 1,
            i = -1, i2,
            temp;
        while(++i < l2)
        {
            temp = a[i];
            i2 = l-i;
            a[i] = a[i2];
            a[i2] = temp;
        }
        ++l; // l original
    }

    // mostra arranjo
    {
        int i = -1;
        while(++i < l)
            std::cout << a[i] << "  ";
    }

    return 0;
}
