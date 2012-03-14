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

    Tree * add(int x) {
        No **no = &head;
        while(*no) no = &(*no)->links[(*no)->key < x];
        No *newNo = new No(x);
        *no = newNo;
        return this;
    }

    // (sem parâmetros) = (mostrar todos)
    Tree * show() {
        show(head);
        return this;
    }
    // overload function
    Tree * show(No *p) {
        if(!p) return this;
        show(p->links[0]); // left
        cout << p->key;
        show(p->links[1]); // right
        return this;
    }

    // (sem parâmetros) = (remover todos)
    Tree * rm() {
        rm(head);
        return this;
    }
    // overload function
    Tree * rm(No *p) {
        // TODO: remover elementos recursivamente

    }

};

int main() {

    Tree *tree= new Tree();
    tree->add(8)->add(6)->add(4)->add(5)->show();

    return 0;
}

