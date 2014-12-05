#include <iostream>

// exclui elemento de uma lisa ordenada

//           array      value      length
int find (int a[], int v = 0, int l = 0); // acha a posição na qual a próxima possui um valor maior, e a atual é igual
// vai com 'pulos cada vez menores' (cada vez metade do ultimo pulo) em direção ao indice.
//      Quando chega perto, faz uma busca normal, aumentando os indices um a um.
// TODO: Ao invés disso, voltremos em sentido horário. Descartar o uso do i3. luk4
// TODO: otimizar na parte de mudar de binário para uma busca com sentinela. Não ter que procurar completamente por sentinela. luk4

int main()
{
    int l = 3;
    int a[3] = {1,2,3};

    // remover do índice (i)
    int i = find(a, /*valor*/ 1, l); // i = índice.
    if(i != -1)
    {
        --l;
        while(i<l)
            a[i] = a[++i];
    }

    // mostrar
    i = -1;
    while(++i<l)
        std::cout << a[i] << "\t";

    return 0;
}


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
        // TODO: Ao invés disso, correremos em sentido contrário (--i).

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

    // faz uma busca por sentinela, mas pode não começar de i = -1, quando o uso do sistema realmente trás alguma vantagem.
    // TODO: otimizar na parte de mudar de binário para uma busca com sentinela. Não ter que procurar completamente por sentinela.
    v2 = a[--l]; // v2 será o valor onde o sentinela substituirá
    a[l] = v;

    i = -1;
    while( a[++i] != v){};

    a[l] = v2;
    return i != l || v == v2 ? i : -1;
}
