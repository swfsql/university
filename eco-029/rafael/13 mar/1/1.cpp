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
        cout << "/";
        show(no->links[0]); // left
        cout << "\n" << no->key << "( " << no << " ) [ " << &no->links << " ] ";
        cout << "\\";
        show(no->links[1]); // right
        cout << "|";
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
        cout << "\nh0: " << &(head->links[0]);
        if(*no) cout << "\n(*no)->key: " << (*no)->key;
        cout << "\n\n";



        if (!*no) return this; // ponteiro para nó não existe
        No **links = (*no)->links; // links do nó a ser removido
        if (!*links) {
            // nó é folha
            cout << "\nno folha\n";
            delete *no;
            *no = 0;
            return this;
        }

        // TODO: remover nó que não é folha
        int r = rand() % 2; // 0, 1
        r = 0;
        if ((links)[r] == 0)
        {
            r = 1-r;
            cout << "\nr mudou para " << r << "\n";
        }

        //No **links2 =  (links)[r]->links;// links lá de baixo
        No **links2 =  links; //[1-r]->links;// links lá de baixo
        if ((links)[r]->links[1-r]) {
            links2 = (links)[r]->links[1-r]->links;

            while(*links2 && (links2)[1-r]->links)
            {
                cout << ",";
            }
            cout << ".";

        }

        cout << "r: " << r << "\n";

        cout << "links: " << (links);
        cout << "\n";
        cout << "*links: " << (*links);
        cout << "\n\n";

        cout << "links2: " << (links2);
        cout << "\n";
        cout << "*links2: " << (*links2);
        cout << "\n\n";
        //cout << "key: " << (*links2)[r-1].key;

        // na verdade nao precisava duplicar os dois links, só o da direcao certa
        No *nLinks3 = new No(links2[1-r]->links); // b = f..a

        cout << "\n\n";
        cout << "n: " << nLinks3->key << ", " << nLinks3->links[0] << ", " << nLinks3->links[1];
        cout << "\n\n";

        links2[1-r]->links = links; // a = c
        *no = links2[1-r]; // d = f..e
        //cout << "\n" << (*no)->links[1-r]->links[1]->key << "\n";
        if (links2[1-r]->links[1-r] == links2[1-r]) links2[1-r]->links[1-r] = 0; // bugfix



        cout << "\n\ntestes:\n";

        cout << "links2: " << links2 << "\n";
        cout << "*links2: " << *links2 << "\n";
        cout << "\nlinks2[0]: " << links2[0];
        cout << "\nlinks2[1]: " << links2[1];
        //*links2 = nLinks3; // f = b // TODO: descomentar isso e fazer funcionar. parece que link2 tá apontando pro nó errado.
        cout << "\nlinks2[0]: " << links2[0];
        cout << "\nlinks2[1]: " << links2[1];

        cout << "\n\nfim";




        /*while(*links
              &&
              (*links)[1-r]
              &&
              (*links)[1-r].links[1-r])
            links = (*links)[1-r].links;
        cout << "primeira etapa";*/

        cout << "\n\n.\n\n";
        return this;
    }


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
    tree->rm(3);//->rm(4);
    tree->show();
    return 0;
}

