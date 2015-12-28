#include <iostream>

// busca elemento com sentinela

int find(int a[], int v = 0, int l = 0)
{
    int v2 = a[--l];
    a[l] = v;

    int i = -1;
    while( a[++i] != v);

    a[l] = v2;
    return i == l ? -1 : i;
}

int main()
{
    int l = 3;
    int a[3] = {1,2,3};

    std::cout << find(a, 0, l);
    return 0;
}
