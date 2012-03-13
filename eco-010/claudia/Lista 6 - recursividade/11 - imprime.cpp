#include <iostream>
using namespace std;

int imprime(int i = 0)
{
    if(i > 0)
        cout << imprime(i - 1) << " ";
    return i;
}

int main()
{
    cout << imprime(5);
    return 0;
}

