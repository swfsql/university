#include <iostream>
using namespace std;


void ordena1(int *v, int n)
{
    int aux;
    for (int i=1; i<n; i++)
    {
        for(int j=0; j<n-1; j++)
        {
            if(v[j+1]<v[j])
            {
                aux = v[j + 1];
                v[j + 1] = v[j];
                v[j] = aux;
            }
        }
    }
}

int menu ()
{
    const int tam = 2;
    char ops[tam][40] = {"encerrar", "ordenar {9 3 1 4}"};

    int op = -1;
    cout << "\n";
    cout << "\n";
    while(++op < tam)
        cout << "\t" << op << ". " << ops[op] << "\n";
    cin >> op;
    cout << "\n";
    return op;
}


int main ()
{
    int v[4] = {9, 3, 1, 4},
        op = menu();
    while(op)
    {
        switch (op)
        {
        case 1:
            ordena1(v, 4);
            cout <<  v[0] << v[1] << v[2] << v[3];
            v= {9, 3, 1, 4};
            break;
        }
        op = menu();
    }
}
