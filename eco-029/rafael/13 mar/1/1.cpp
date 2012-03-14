#include <iostream>
using namespace std;

struct No {
    int key;
    No *links[2];

    // constructor
    No (int x){
        key = x;
        links = {0,0}; // left, right
    }
};

struct Tree {
    No *head;

    // constructor
    Tree() {
        head = 0;
    }

    // adiciona um elemento. (TODO? não deixar ele ser repetido)
    Tree * add(int x) {
        No **no = &head;
        while(*no) no = &(*no)->links[(*no)->key < x];
        *no = new No(x);
        return this;
    }

    // mostrar elementos
    Tree * show() {
        show(head);
        return this;
    }
    // overload function (mostrar o elemento e os abaixo)
    Tree * show(No *p) {
        if(!p) return this;
        show(p->links[0]); // left
        cout << p->key;
        show(p->links[1]); // right
        return this;
    }

    // remover elementos
    Tree * rm() {
        rm(head);
        return this;
    }
    // overload function (remover elemento e os abaixo)
    Tree * rm(No *p) {
        // TODO: remover elementos recursivamente
    }

};

int main() {

    Tree *tree= new Tree();
    tree->add(8)->add(6)->add(4)->add(5)->show(); // por isso que todas as funções retornam a &struct. pra voltar ao normal, botar tudo void.

    return 0;
}

