#include <iostream>
using namespace std;

int imprimir(int i = 0)
{
    if(i > 0)
        cout << imprimir(i - 1);
    return i;
}

int main()
{
    cout << imprimir(5);
    return 0;
}

