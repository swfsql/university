#include <iostream>


//           array      value      length
int find (int a[], int v = 0, int l = 0); // acha a posi��o na qual a pr�xima possui um valor maior ou igual
// vai com 'pulos cada vez menores' (cada vez metade do ultimo pulo) em dire��o ao indice.
//      Quando chega perto, faz uma busca normal, aumentando os indices um a um.
// TODO: Ao inv�s disso, voltremos em sentido hor�rio. Descartar o uso do i3.

int find (int a[], int v, int l)
{
    int l2 = l>>1,
        i = l2,
        i3 = 2,
        v2 = a[0];

    while(a[i] != v2 && (v2 = a[i]) != v)
    {
        // loop para se obter duas vezes no msm valor ou se obter o valor desejado

        ++i3; // mais tarde: se o valor (obtido >= desejado), temos de subtrair um pouco do obtido.
        // TODO: Ao inv�s disso, correremos em sentido contr�rio (--i).

        v2 > v &&
            (i -= l2 >>= 1) ||
            (i += l2 >>= 1);
    }
    v2 = a[i]; // atualiza o valor obtido

    // obtido >= desejado
    v2 >= v &&
        (i -= i3);

    i < -1 &&
        (i = -1);

    while(++i < l)
        if (a[i] > v)
            return i;
    return --i; // nota: testar mais pra ver se n�o � s� 'i' mesmo, no caso se (obtido >= desejado)

}

int main()
{
    int a[3] = {1,2,3},
        l = sizeof(a) / sizeof(a[0]);

    std::cout << find(a, 0, l); // 0
}
