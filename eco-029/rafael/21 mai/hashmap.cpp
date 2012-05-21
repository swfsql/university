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

	bool isEmpty() {return l == 0;}	// 0: nao-vazio_e_tamanho; 1: vazio_e_tamanho

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
		if(*next) *next = (*next)->next;
		l -= !!ret;
		return ret;
	}

	void clear() {
		// remove todos os No do hash.
		int i = -1;
		while(++i < L) rmr(&hash[i]->next);
		l = 0;
	}

	void rmr(No** next) {
		// remove deste No pra frente (remove recursivo).
		if (!*next) return;
		rmr(&(*next)->next);
		delete *next;
		*next = 0;
		--l;
	}
};

struct Iterator
{
    No **iter;
    Hashmap *hm;
    int l, j, pos;
    int *k;

    // http://paulsolt.com/2009/01/stl-pointers-objects-and-sorting/
    struct Comparador { bool operator() (No* i,No* j) { return i->ra < j->ra;}};

    Iterator(Hashmap* hm) { refresh(hm);}

    void refresh(Hashmap* HM) {
    	hm = HM;
    	refresh();
    }

    void refresh() {
    	j = -1;
    	pos = 0; 
        if(iter) {
        	delete[] iter;
        	delete[] k;
        }
        l = hm->size();
        iter = new No*[l];

        // keys
        int i = -1;
        while(++i<hm->L) getr(hm->hash[i]->next);
        std::sort(iter,iter+l, Comparador());

        // iterator
        k = new int[l];
        i = -1;
        while(++i < l) k[i] = iter[i]->ra;
    }
    
    void getr(No *no)
    {
        if (!no) return;
		getr(no->next);
		iter[++j] = no;
    }

    // para implementar
    int* iterador() { return k;}

    No** keys() { return iter;}

    bool hasNext() { return pos < l;}

    No* nextObject () { return (hasNext() ? iter[pos++] : 0 );}

};

// variaveis globais (utilizadas nos testes).
Hashmap *h;
Iterator *I;

// funcoes utilizadas no main().
void vazio_e_tamanho();
void tamanho();
void put(int, char[40]);
void get(int);
void rm(int);
void iter();
void keys();
void keys2();

// teste das structs
int main() {
	cout << "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
	cout << "Hashmap dinamico. Input/output nao acontece em structs.\nCodigo fora de structs serve para implementa-las.\nHashMap e Iterador sao globais para simplificar os testes.\n\n";
	cout << "Para rever os testes rodei o programa e deixei lado-a-lado com codigo, \nia veriicando o output de acordo";
	cout << "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
	cout << "\n\ntamanho do hash: 7.\n";
	h = new Hashmap(7);

	vazio_e_tamanho();
	put(2, "aluno 2a");
	vazio_e_tamanho();
	put(2, "aluno 2b");
	tamanho();
	put(9, "aluno 9");
	tamanho();
	put(2, "aluno 2c");
	tamanho();
	put(16, "aluno 16");
	tamanho();
	get(15);
	rm(2);
	tamanho();
	rm(3);
	get(16);
	get(2);
	vazio_e_tamanho();

	cout << "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";

	cout << "\nlimpado todos os cadastros."; h->clear();
	vazio_e_tamanho();
	get(16);

	cout << "prerarar um hash para testar com um iterador:\n";
	put(1, "aluno 1"); 
	put(2, "aluno 2"); put(3, "aluno 3"); put(4, "aluno 4"); put(5, "aluno 5"); put(6, "aluno 6"); put(7, "aluno 7"); put(8, "aluno 8");
	put(9, "aluno 9"); put(10, "aluno 10"); put(11, "aluno 11"); put(12, "aluno 12"); put(13, "aluno 13"); put(14, "aluno 14"); put(15, "aluno 15"); 
	put(16, "aluno 16");
	vazio_e_tamanho();

	// pointer error aqui.
	cout << "testes do iterador:\n";
	I = new Iterator(h);
	iter();
	keys();

	cout << "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";

	rm(0);
	rm(1);
	rm(8);
	rm(4);
	rm(5);
	rm(5);
	vazio_e_tamanho();

	cout << "atualizamos o Iterador."; I->refresh();
	iter();
	keys2();

	cout << "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";

	cout << "\nlimpado todos os cadastros."; h->clear();
	vazio_e_tamanho();

	cout << "atualizamos o Iterador."; I->refresh();
	iter();
	keys();
	return 0;
}

// funcoes utilizadas no main().
void vazio_e_tamanho() {cout << "\nhash " << (h->isEmpty() ? "" : "nao " ) << "esta vazio, com " << h->size() << " alunos cadastrados.\n";}
void tamanho() {cout << "hash possui " << h->size() << " alunos cadastrados.\n\n";}
void put(int K, char nome[40]) {
	No* A = new No(K, nome);
	No* B = h->put(A->ra, A);
	if (B) cout << "\nfoi substituido o aluno de ra " << B->ra << " chamado " << B->n << ".\n";
	get(K);
}
void get(int K){
	No* A;
	cout << "aluno de ra: " << K << ((A = h->get(K))? "" : " nao") << " existe.";
	if (A) cout << " Seu nome: " << A->n <<".\n"; else cout << "\n\n";
}
void rm(int K) {
	No* A = h->remove(K);
	cout << "aluno de ra = " << K << (A? "" : " nao") << " existe para remocao.\n";
	if (A) get(K);
}
void iter() {
	cout << "\nmostrar RAs em  ordem crescente:";
	int i = -1, *is = I->iterador();
	while(++i < I->l) cout << " " << is[i];
	cout << "\n";
}
void keys() {
	cout << "\n\nmostrar alunos ordenados de acordo com seus RAs:";
	I->pos = 0;
	No* no;
	while(no = I->nextObject()) cout << "\nRA: " << no->ra << ", nome: " << no->n;
	cout << "\n\n";
}
// código alternativo que chama realmente as keys.
void keys2() {
	cout << "\n\nmostrar alunos ordenados de acordo com seus RAs:";
	int i = -1;
	No **as = I->keys();
	while(++i < I->l) cout << "\nRA: " << as[i]->ra << ", nome: " << as[i]->n;
	cout << "\n\n";
}