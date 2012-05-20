#include <iostream>
#include <cstring>
#include <stdio.h>
#include <stdlib.h>
#include <algorithm>
using namespace std;

struct No
{
	No () { next = 0;}
	No (int chave, char nome[40]) {
		next = 0;
		ra = chave;
		strcpy(n, nome);
	}

	int ra; // chave
	char n[40];
	No* next;
};

struct Hashmap {
	int L, l;
	No **hash;

	Hashmap(int len) {
		L = len;
		hash = new No*[len];
		l = 0;
		int i = -1;
		while(++i < len) hash[i] = new No();
	}

	// para implementar
	int size() {return l;} // length

	bool isEmpty() {return l == 0;}	// 0: nao-vazia; 1: vazia

	No* get(int K) { return *get2(K);} // retorna o endereco de um No.

	No** get2(int K) {
		// retorna um atributo de No, o next. O endereco dele.
		No** next = &hash[K % L]->next;
		while(*next && (*next)->ra - K) next = &(*next)->next;
		return next;
	}

	No* put(int K, No *no) {
		// Adiciona um No. retorna o No substituido, se houver. Senao retorna null.
		No **next = get2(K);
		No *ret = *next;
		*next = no;
		if (ret) (*next)->next = ret->next; else ++l;
		return ret;
	}

	No* remove(int K) {
		// remove e retorna um No do hash. Se nao existe, retorna null.
		No** next = get2(K);
		No* ret = *next;
		if(*next) *next = (*next)->next; else (*next) = 0;
		l -= !!ret;
		return ret;
	}

	/*I keys() {}*/

	void clear() {
		// remove todos os No do hash.
		int i = -1;
		while(++i < l) rmr(hash[i]->next);
		l = 0;
	}

	void rmr(No* no) {
		// remove deste No pra frente (remove recursivo).
		if (!no) return;
		rmr(no->next);
		delete no;
	}

	/*I iterator() {}*/
};



struct Iterator
{
    No **iter;
    int j;

    No **getNos(Hashmap *hm)
    {
        int i = -1, l = hm->size();
        iter = new No*[l];
        j = -1;
        while(++i<hm->L) getr(hm->hash[i]->next);
        std::sort(iter,iter+l, Comparador());
        return iter;
    }
    
    void getr(No *no)
    {
        if (!no) return;
		getr(no->next);
		iter[++j] = no;
    }

    void show(int l) {
    	int i = -1;
    	while(++i < l) cout << iter[i]->ra << " ";
    }
    
    // http://paulsolt.com/2009/01/stl-pointers-objects-and-sorting/
    struct Comparador { bool operator() (No* i,No* j) { return i->ra < j->ra; }};
};






int main()
{
	Hashmap *hm = new Hashmap(7);
	char nome[40];
	No *aluno;

	//

	cout << "lista esta vazia? " << hm->isEmpty() << "\n";
	cout << "qual o tamanho dela? " << hm->size() << "\n";

	strcpy(nome, "aluno1");
	aluno = new No (2, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " << hm->get(2)->n << ", de ra = " << hm->get(2)->ra << ".\n";

	cout << "lista esta vazia? " << hm->isEmpty() << "\n";
	cout << "qual o tamanho dela? " << hm->size() << "\n";

	strcpy(nome, "aluno2");
	aluno = new No (2, nome);
	cout << "\nfoi subtituido: " << hm->put(aluno->ra, aluno)->n << ".";
	cout << "\nno lugar: " << hm->get(2)->n << ", de ra = " <<  hm->get(2)->ra << ".\n";

	cout << "qual o tamanho dela? " << hm->size() << "\n";

	strcpy(nome, "aluno9");
	aluno = new No (9, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " <<  hm->get(9)->n << ", de ra = " <<  hm->get(9)->ra << ".\n";

	cout << "qual o tamanho dela? " << hm->size() << "\n";

	strcpy(nome, "aluno2");
	aluno = new No (2, nome);
	cout << "\nfoi subtituido: " << hm->put(aluno->ra, aluno)->n << ".";
	cout << "\nno lugar: " <<  hm->get(2)->n << ", de ra = " <<  hm->get(2)->ra << ".\n";

	cout << "qual o tamanho dela? " << hm->size() << "\n";

	strcpy(nome, "aluno16");
	aluno = new No (16, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " <<  hm->get(16)->n << ", de ra = " <<  hm->get(16)->ra << ".\n";

	cout << "\ntem aluno 15? " << (hm->get(15) ? "sim" : "nao");

	cout << "\nremovendo o aluno: " << hm->remove(2)->n <<"\n";

	cout << "qual o tamanho dela? " << hm->size() << "\n";

	cout << "\nconseguiu remover aluno 3? " << (hm->remove(3) ? "sim" : "nao") << "\n";

	cout << "mostre o nome do aluno 16: " << hm->get(16)->n << "\n";
	cout << "mostre o nome do aluno 2: " << (hm->get(2)? hm->get(2)->n : "nao") << "\n";

	cout << "lista esta vazia? " << hm->isEmpty() << "\n";
	cout << "qual o tamanho dela? " << hm->size() << "\n";

	//cout << "\nclear()"; hm->clear();

	cout << "\nlista esta vazia? " << hm->isEmpty() << "\n";
	cout << "qual o tamanho dela? " << hm->size() << "\n";

	cout << "mostre o nome do aluno 16: " << (hm->get(16)? hm->get(16)->n : "nao") << "\n";

	hm->put(2, new No(2, "aluno 2"));
	hm->put(3, new No(3, "aluno 3"));
	hm->put(4, new No(4, "aluno 4"));
	hm->put(5, new No(5, "aluno 5"));
	hm->put(6, new No(6, "aluno 6"));
	hm->put(7, new No(7, "aluno 7"));
	hm->put(8, new No(8, "aluno 8"));
	hm->put(9, new No(9, "aluno 9"));
	
	cout << "adicionando alunos com ra: 2,3,4,5,6,7,8,9\n\n";

	cout << "num. de elementos: " << hm->size() << "\n";

	Iterator* I = new Iterator();
	I->getNos(hm);
	cout << "\no iterador vale: \n";
	I->show(hm->size());


	return 0;
}





