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

    // adiciona um n�
    Tree * add(int x) {
        // obs.: n� pode ser repetido.
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
        show(*search(x)); // mostra n�s relacionados com o n� que tem a chave desejada
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

    // remover elemento
    Tree * rm() {
        rm(&head); // remove o que estiver no topo
        return this;
    }
    // overload function
    Tree * rm(int x) {
        rm(search(x)); // remove o n� que tenha a chave desejada
        return this;
    }
    // overload function
    Tree * rm(No **no) {
        cout << "\n\ncheguei. \nno: " << no;
        cout << "\n*no: " << *no;
        if(*no) cout << "\n(*no)->key: " << (*no)->key;
        cout << "\n\n";
        // TODO: ajustar ponteiros, deletar n�.
        int r = rand() % 2; // 0, 1
        No **links = (*no)->links;
        //if (*links) links = &(*links)[r].links;
        //while(*links && (*links)[1-r] && (*links)[1-r].links[1-r]) links = &(*links)[1-r].links;
        cout << "primeira etapa";

        return this;
    }


    // procura n� que tenha a chave
    No ** search (int x) {
        No **no = &head;
        while(*no && (*no)->key - x) no = &(*no)->links[(*no)->key < x];
        return no;
    }


    // TODO: depois de remover individualmente, remover n�s recursivamentes, tamb�m com 3 possibilidades de fun��o.
    // remover elemento e os abaixo dele
    Tree * rmr() {
        rmr(head); // remove todos
        return this;
    }
    // overload function
    Tree * rmr(No *p) {
        // TODO: remover v�rios elementos
        return this;
    }

};

int main() {

    /*
     * instru��es
     *
     * Para criar uma �rvore, use 'Tree *var', sendo 'var' o nome da vari�vel.
     *  Para instanci�-la, use 'var = new Tree()'.
     *
     * Para adicionar um elemento, use 'var->add(x)', senxo 'x' a chave do elemento.
     * Para mostrar todos os elementos, use 'var->show()'. se quer mostrar elementos abaixo de um n� em espec�fico, use 'var->show(no)'.
     * Para remover um n� que tenha uma chave, use 'var->rm(chave)'. se quer remover um n� em espec�fico, use 'var->rm(no)'. se quer remover s� o n� do topo, use 'var->rm()'.
     *  Para remover tamb�m os n�s abaixo de certo n�, user a fun��o 'rmr' ao inv�s de 'rm'. (rmr = remo��o recursiva). ex.: 'var->rmr()' remove todos os n�s.
     */

    Tree *tree= new Tree();
    tree->add(5)->add(3)->add(2)->add(4)->show();//->rm(6)->rm(4); // por isso que todas as fun��es retornam a &struct. pra voltar ao normal, botar tudo void
    return 0;
}

