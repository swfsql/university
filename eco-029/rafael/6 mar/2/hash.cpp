#include<iostream>
using namespace std;

struct No {
    int key;
    No *next;
};

struct Hash {
    int tam;
    No **v;

    void init(int tamanho) {
        tam = tamanho;
        *v = new No[tam];
        int i = -1;
        while (++i < tam)
            (*v)[i].next = 0; // headers
    }
};

int main()
{
    Hash hash;
    hash.init(8);

    return 0;
}
