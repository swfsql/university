#include <iostream>
using namespace std;


int buscabinaria (int v[], int inicio, int fim, int chave){
    if (fim < inicio) return -1;
    int i = (inicio + fim) / 2;
    return (v[i] == chave ? i : chave > v[i] ? buscabinaria(v, i+1, fim, chave) : buscabinaria(v, inicio, i-1, chave));
}

int main() {

    int x, vetor[10] = {0, 1, 2, 3, 4, 5};

    cout<<"Entre com o elemento que deseja encontrar: ";
    cin>>x;

    cout << buscabinaria(vetor, 0, 5, x);




    return 0;
}
