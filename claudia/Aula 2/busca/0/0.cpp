#include <iostream>

// busca elemento

int find(int a[], int v = 0, int l = 0)
{
    int i = -1;
    while(++i < l)
        if(a[i] == v)
            return i;
    return -1;
}

int main()
{
    int l = 3;
    int a[3] = {1,2,3};

    std::cout << find(a, 0, l);
    return 0;
}
