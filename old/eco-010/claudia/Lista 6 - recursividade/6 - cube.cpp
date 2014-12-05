#include <iostream>
using namespace std;

int cubes(int i = 0)
{
    if(i > 1)
        cout << cubes(i - 1) << " ";
    return i * i * i;
}

int main()
{
    cout << cubes(4);
    return 0;
}
