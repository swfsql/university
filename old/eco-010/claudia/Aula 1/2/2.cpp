#include <iostream>

// insere elemento numa lista ordenada

//        array      value      lenght
void in(int a[], int v = 0, int l = 0);
// nota: não é alocado valores acima do ultimo valor do arranjo

//           array      value      length
int find (int a[], int v = 0, int l = 0); // acha a posição na qual a próxima possui um valor maior ou igual
// vai com 'pulos cada vez menores' (cada vez metade do ultimo pulo) em direção ao indice.
//      Quando chega perto, faz uma busca normal, aumentando os indices um a um.
// TODO: Ao invés disso, voltremos em sentido horário. Descartar o uso do i3. (procurar essa frase)


int main()
{
    int L = 5, // tamanho max
        l = 0, // tamanho usado
        a[L];

    // zerar
    int i = -1;
    while(++i<L)
        a[i] = 0;

    // inserir na posição correta
    i = -1;
    while(++i < L)
        in (a, i, ++l);

    // debug
    in (a, -1, l);

    // mostrar
    i = -1;
    while(++i<L)
        std::cout << a[i] << "\t";
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

    while(++i < l)
        if (a[i] > v)
            return i;
    return --i; // nota: testar mais pra ver se não é só 'i' mesmo, no caso se (obtido >= desejado)

}

void in(int a[], int v, int l)
{
    // index
    int i = find(a, v, l);

    // libera espaço no arranjo
    while (l > i)
        a[l] = a[--l];

    // adiciona o valor na posição
    a[i] = v;
}
