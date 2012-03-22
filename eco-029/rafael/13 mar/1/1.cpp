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
        rm(search(x)); // remove o n� que tenha a chave desejada
        return this;
    }
    // overload function
    Tree * rm(No **no) {

        // debug
        cout << "\n\ncheguei. \nno: " << no;
        cout << "\n*no: " << *no;
        if (!*no) return this; // ponteiro para n� n�o existe
        cout << "\nh0: " << &(head->links[0]);
        if(*no) cout << "\n(*no)->key: " << (*no)->key;
        cout << "\n\n";



        cout << "VAI";
        No ***links = &(*no)->links; // links do n� a ser removido
        if (!(*links)[0] && !(*links)[1]) {
            // n� � folha
            cout << "\nno folha\n";
            delete *no;
            *no = 0;
            return this;
        }
        cout << "FOI";

        // TODO: remover n� que n�o � folha
        int r = rand() % 2; // 0, 1
        r = 1;
        if ((links)[r] == 0)
        {
            r = 1-r;
            cout << "\nr mudou para " << r << "\n";
        }

        No ***links2 =  links; //[1-r]->links;// links l� de baixo

        cout << "\npreh\n";
        // percorrerei a �rvore pra pegar algum n� pra levar no lugar do removido
        if ((*links)[1-r] && (*links)[1-r]->links[r]) {
            cout << "FUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU";
            links2 = &(*links)[1-r]->links;

            cout << "\nperai; *l2 [0]: " << (*links2)[0] << "\n";

            while(*links2 && (*links2)[r] && (*links2)[r]->links[r])
            {

                cout << ",";
                links2 = &(*links2)[r]->links;
            }
            cout << ".";
            r = 1-r;

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
        cout << "ruim";
        // na verdade nao precisava duplicar os dois links, s� o da direcao certa
        No *nLinks3 = new No((*links2)[1-r]->links); // b = f..a
        cout << "bom";
        cout << "\n\n";
        cout << "n: " << nLinks3->key << ", " << nLinks3->links[0] << ", " << nLinks3->links[1];
        cout << "\n\n";
        cout << "--- " << (*links2)[1-r]->key << " -- "  << (*links2)[1-r]->links[0] << " -- ";// <<  (*links2)[1-r]->links[0]->key;

        (*links2)[1-r]->links = *links; // a = c
        *no = (*links2)[1-r]; // d = f..e
        //cout << "\n" << (*no)->links[1-r]->links[1]->key << "\n";
        if ((*links2)[1-r]->links[1-r] == (*links2)[1-r]) (*links2)[1-r]->links[1-r] = nLinks3->links[0]; // bugfix


        cout << "\n\ntestes:\n";

        cout << "links2: " << links2 << "\n";
        cout << "*links2: " << *links2 << "\n";
        cout << "\nlinks2[0]: " << links2[0];
        cout << "\nlinks2[1]: " << links2[1];
        *links2 = nLinks3->links; // f = b // TODO: descomentar isso e fazer funcionar. parece que link2 t� apontando pro n� errado.
        cout << "\nlinks2[0]: " << links2[0];
        cout << "\nlinks2[1]: " << links2[1];

        cout << "\n\nfim";

        cout << "\n\n.\n\n";
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
    
    /*tree->add(9)->add(5)->add(3)->add(2)->add(4)->add(1)->show();
    cout << "\n\n";
    tree->rm(3);//->rm(4);
    tree->show();
    */




cout << "\n\n______a953421 rm1__a1 rm2__a2 rm3_____a3 rm4_____a4 rm5_____a5 rm9_______ rm53421__\n\n";

tree->add(9)->add(5)->add(3)->add(4)->add(1)->add(2);
cout << "_______________________________________________";
tree->show()->rm(3)->show();//->add(3)->rm(4)->show()->add(4)->rm(5)->show()->add(5)->rm(9)->show(5)->rm(4)->rm(3)->rm(2)->rm(1)->show();

    return 0;
}

