// input / output
#include <iostream>

// random
#include <cstdio>
#include <cstdlib>
#include <ctime>

using namespace std;

struct No {
    int key;
    No **links;

    // constructor
    No (int x){
        key = x;
        links = new No*[2]; // left, right
        links[0] = links[1] =  0;
    }
    // overflow
    No (No **l)
    {
        links = new No*[2];
        links[0] = l[0];
        links[1] = l[1];
    }
};

struct Tree {
    No *head;

    // constructor
    Tree() {
        head = 0; // tree vazia
        srand(time(0)); // random seed
    }

    // adiciona um nó
    void add(int x) {
        // obs.: nó pode ser repetido.
        No **no = &head;
        while(*no) no = &(*no)->links[(*no)->key < x];
        *no = new No(x);
    }

    // mostrar elementos
    void show() {
        show(head); // mostra todos
    }
    // overload function
    void show(int x) {
        show(*search(x)); // mostra nós relacionados com o nó que tem a chave desejada
    }
    // overload function
    void show(No *no) {
        if(!no) return;
        show(no->links[0]); // left
        cout << " " << no->key;
        show(no->links[1]); // right
    }

    // remover elemento
    void rm() {
        rm(&head); // remove o que estiver no topo
    }
    // overload function
    void rm(int x) {
        rm(search(x)); // remove o nó que tenha a chave desejada
    }
    // overload function
    void rm(No **no) {
        if(!*no) return;
        No ***links = &(*no)->links; // links do nó a ser removido
        if (!(*links)[0] && !(*links)[1]) {
            delete *no;
            *no = 0;
            return; // nó é folha
        }
        int r = rand() % 2; // 0, 1
        if ((*links)[1-r] == 0) r = 1-r; // muda o r se precisar
        No ***links2 =  links;
        if ((*links)[1-r] && (*links)[1-r]->links[r]) { // percorrerei a árvore pra pegar algum nó pra levar no lugar do removido
            links2 = &(*links)[1-r]->links;
            while(*links2 && (*links2)[r] && (*links2)[r]->links[r]) links2 = &(*links2)[r]->links;
            r = 1-r;
        }
        No *nLinks3 = new No((*links2)[1-r]->links); // na verdade nao precisava duplicar os dois links, só o da direcao certa
        (*links2)[1-r]->links = *links;
        *no = (*links2)[1-r];
        if ((*links2)[1-r]->links[1-r] == (*links2)[1-r]) (*links2)[1-r]->links[1-r] = nLinks3->links[0]; // bugfix
        *links2 = nLinks3->links;
    }


    // procura nó que tenha a chave
    No ** search (int x) {
        No **no = &head;
        while(*no && (*no)->key - x) no = &(*no)->links[(*no)->key < x];
        return no;
    }


    // TODO: remover nós recursivamentes, também com 3 possibilidades de função.
    // remover elemento e os abaixo dele
    void rmr() {
        rmr(head); // remove todos
    }
    // overload function
    void rmr(No *p) {
        // TODO: remover vários elementos
    }

};

int main() {

    Tree tree = Tree();

    tree.add(6);
    tree.add(4);
    tree.add(5);
    tree.show();

    cout << "\n";

    tree.rm(4);
    tree.show();

    cout << "\n";
    
    tree.rm();
    tree.show();

    tree.rm();
    tree.rm();
    tree.show();

    return 0;
}

