#include <iostream>


void sort (int a[], int l = 0);
void show (int a[], int l = 0);

int main()
{
    int a[3] = {2,3,1},
        l = sizeof(a) / sizeof(a[0]);

    sort(a, l);
    show(a, l);
}

void sort(int a[], int l)
{
    int a2[l],
        i = -1,
        j,
        n1,
        n2;

    while (++i < l)
    {
        n1 = a[i]; // vai pegando elemento de 'a', do início ao fim. supõe-se que 'n1' seja um numero pequeno.
        j = -1;
        while (++j < i)
        {
            n2 = a2[j]; // vai comparando com elementos de 'b', do início ao fim. supõe-se que 'n2' seja grande.
            if (n1 < n2)
            {
                a2[j] = n1; // se realmente (n1 < n2), então coloca n1 no lugar de n2, e n1 vale n2.
                n1 = n2;
            }
        }
        a2[j] = n1; // agora 'n1' é o maior número do arranjo, fica no final
    }

    //grava a2 (ordenado em ordem crescente) em a
    i = -1;
    while(++i < l)
        a[i] = a2[i];
}

void show (int a[], int l)
{
    int i = -1;
    while(++i < l)
        std:: cout << a[i] << "\t";
}
