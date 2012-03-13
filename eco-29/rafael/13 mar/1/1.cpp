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
    No *no; // temp

    // constructor
    Tree() {
        head = 0;
    }

    // TODO: add func
    Tree * add(int x) {
        no = head;
        int key;
        bool

        while(no && (no->key < valor)

        if (!head) {
            head = new No(x);
            return this;
        }

        return this;
    }

    /*
    Tree * add(int x)
    {
        while(head && head->chave < valor)
            *head = &(head)->next;
        No *no = new No;
        no->chave = valor;
        no->next = *next;
        head = no;
    }

    void inserir(int valor, No **next)
    {
        while(*next && (*next)->chave < valor)
            next = &(*next)->next;
        No *no = new No;
        no->chave = valor;
        no->next = *next;
        *next = no;
    }
    /*

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

    }

};






int main() {

    Tree *tree= new Tree();
    tree->add(20)->show();

    return 0;
}












