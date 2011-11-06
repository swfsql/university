#include <iostream>

int main()
{
    // cria arranjos
    int la = 8,
        a[la],
        p[la],
        n[la];

    // cria valores
    {
        int i = -1;
        std::cout << "insira " << la << " valores inteiros no arranjo\n\n";
        while(++i < la)
            std::cin >> a[i];
    }

    // copia valores positivos em 'p', negativos em 'n'
    int lp, ln;
    {
        int i = -1;
        lp = ln = 0;
        while(++i < la)
            if (a[i] < 0)
                n[ln++] = a[i]; // negativo
            else
                p[lp++] = a[i]; // positivo
    }

    // mostra valores
    {
        std::cout << "num positivos: ";
        int i = -1;
        while(++i < lp)
            std:: cout << p[i] << " ";

        std::cout << "\nnum negativos: ";
        i = -1;
        while(++i < ln)
            std:: cout << n[i] << " ";
    }

    return 0;
}
