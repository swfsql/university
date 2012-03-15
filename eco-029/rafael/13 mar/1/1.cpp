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
};

struct Tree {
    No *head;

    // constructor
    Tree() {
        head = 0;

        // random
        srand(time(0));
    }

    // adiciona um nó
    Tree * add(int x) {
        // obs.: nó pode ser repetido.
        No **no = &head;
        while(*no) no = &(*no)->links[(*no)->key < x];
        *no = new No(x);
        return this;
    }

    // mostrar elementos
    Tree * show() {
        show(head); // mostra todos
        return this;
    }
    // overload function
    Tree * show(int x) {
        show(*search(x)); // mostra nós relacionados com o nó que tem a chave desejada
        return this;
    }
    // overload function
    Tree * show(No *no) {
        if(!no) return this;
        show(no->links[0]); // left
        cout << no->key;
        show(no->links[1]); // right
        return this;
    }

    // remover elemento
    Tree * rm() {
        rm(&head); // remove o que estiver no topo
        return this;
    }
    // overload function
    Tree * rm(int x) {
        rm(search(x)); // remove o nó que tenha a chave desejada
        return this;
    }
    // overload function
    Tree * rm(No **no) {

        // debug
        cout << "\n\ncheguei. \nno: " << no;
        cout << "\n*no: " << *no;
        if(*no) cout << "\n(*no)->key: " << (*no)->key;
        cout << "\n\n";


        int r = rand() % 2; // 0, 1
        if (!*no) return this; // nó não existe
        No **links = (*no)->links;
        if (!*links) {
            // nó é folha
            *no = 0;
            return this;
        }

        // TODO: remover nó que não é folha

        links = (*links)[r].links;
        cout << (links);
        cout << "\n";
        cout << (*links);
        /*while(*links
              &&
              (*links)[1-r]
              &&
              (*links)[1-r].links[1-r])
            links = (*links)[1-r].links;
        cout << "primeira etapa";*/

        return this;
    }

    /*
    Tree * add(int x) {
        No **no = &head;
        while(*no) no = &(*no)->links[(*no)->key < x];
        *no = new No(x);
        return this;
    }
    */

    // procura nó que tenha a chave
    No ** search (int x) {
        No **no = &head;
        while(*no && (*no)->key - x) no = &(*no)->links[(*no)->key < x];
        return no;
    }


    // TODO: depois de remover individualmente, remover nós recursivamentes, também com 3 possibilidades de função.
    // remover elemento e os abaixo dele
    Tree * rmr() {
        rmr(head); // remove todos
        return this;
    }
    // overload function
    Tree * rmr(No *p) {
        // TODO: remover vários elementos
        return this;
    }

};

int main() {

    /*
     * instruções
     *
     * Para criar uma árvore, use 'Tree *var', sendo 'var' o nome da variável.
     *  Para instanciá-la, use 'var = new Tree()'.
     *
     * Para adicionar um elemento, use 'var->add(x)', senxo 'x' a chave do elemento.
     * Para mostrar todos os elementos, use 'var->show()'. se quer mostrar elementos abaixo de um nó em específico, use 'var->show(no)'.
     * Para remover um nó que tenha uma chave, use 'var->rm(chave)'. se quer remover um nó em específico, use 'var->rm(no)'. se quer remover só o nó do topo, use 'var->rm()'.
     *  Para remover também os nós abaixo de certo nó, user a função 'rmr' ao invés de 'rm'. (rmr = remoção recursiva). ex.: 'var->rmr()' remove todos os nós.
     */

    Tree *tree= new Tree();
    tree->add(5)->add(3)->add(2)->add(4)->show();
    cout << "\n\n";
    tree->rm(2);//->rm(4);
    tree->show();
    return 0;
}

