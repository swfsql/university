#include <iostream>

int main()
{
    int l = 3,
        a[l];

    // construção do arranjo
    std::cout << "insira " << l << " elementos: ";
    int i = -1;
    while(++i <l)
        std::cin >> a[i];

    std::cout << "\n\n";

    i = l;
    while(i--)
        std::cout << a[i] << "\t";

    return 0;
}
