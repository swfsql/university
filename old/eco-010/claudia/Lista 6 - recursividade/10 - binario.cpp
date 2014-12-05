#include <iostream>
using namespace std;

int b10_b2(int i = 0)
{
    if(i)
        cout << b10_b2(i>>1);
    return i&1;
}

int main()
{
    int i;
    cout << "insira um numero base: ";
    cin >> i;
    cout << b10_b2(i);
    return 0;
}
