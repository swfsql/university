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
		cout << ".";
		if (!*next) return;
		cout << "|||" << (*next)->ra;
		rmr(&(*next)->next);
		delete *next;
		*next = 0;
	}
};

struct Iterator
{
    No **iter;
    Hashmap *hm;
    int l, j;
    int *k;

    // http://paulsolt.com/2009/01/stl-pointers-objects-and-sorting/
    struct Comparador { bool operator() (No* i,No* j) { return i->ra < j->ra;}};

    Iterator(Hashmap* hm) { 
    	refresh(hm);
    }

    void refresh(Hashmap* HM) {
    	hm = HM;
    	refresh();
    }

    void refresh() {
    	j = -1; 
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
        while(++i < l) k[i] = iter[0]->ra;
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

};

// variaveis globais (utilizadas nos testes).
Hashmap *h;
Iterator *I;

// funcoes utilizadas no main().
void vazia();
void tamanho();
void put(int, char[40]);
void get(int);
void rm(int);
void iter();
void keys();

// teste das structs
int main()
{
	int g = 2;
	int *Z;
	Z = new int[g];
	//return 0;

	cout << "Hashmap dinamico.\n Input/output nao acontece em structs.\nFuncoes fora de structs servem para testar as structs apenas.\nHashMap e Iterador sao globais para simplificar os testes.\n\n";

	cout << "tamanho do hash: 7\n";
	h = new Hashmap(7);

	vazia(); tamanho();
	put(2, "aluno 2a");
	vazia(); tamanho();
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
	vazia(); tamanho();
	cout << "\nclear()"; h->clear();
	vazia(); tamanho();
	get(16);
	

	cout << "\n\nagora faremos testes sobre o iterador. adicionaremos 16 alunos";
	put(1, "aluno 1"); 
	put(2, "aluno 2"); put(3, "aluno 3"); put(4, "aluno 4"); put(5, "aluno 5"); put(6, "aluno 6"); put(7, "aluno 7"); put(8, "aluno 8");
	put(9, "aluno 9"); put(10, "aluno 10"); put(11, "aluno 11"); put(12, "aluno 12"); put(13, "aluno 13"); put(14, "aluno 14"); put(15, "aluno 15"); 
	put(16, "aluno 16");
	vazia(); tamanho();

	// pointer error aqui.
	cout << "agora criaremos um iterador para o hash.";
	I = new Iterator(h);
	iter();
	keys();

	rm(0);
	rm(1);
	rm(8);
	rm(4);
	rm(5);
	rm(5);
	vazia(); tamanho();

	cout << "atualizamos o Iterador."; I->refresh();
	iter();
	keys();

	cout << "\nclear()"; h->clear();
	vazia(); tamanho();

	cout << "atualizamos o Iterador."; I->refresh();
	iter();
	keys();
	return 0;
}

// funcoes utilizadas no main().
void vazia() {cout << "\nhash " << (h->isEmpty() ? "" : "nao") << " esta vazio.\n";}
void tamanho() {cout << "hash possui " << h->size() << " alunos cadastrados.\n\n";}
void put(int K, char nome[40]) {
	No* A = new No(K, nome);
	No* B = h->put(A->ra, A);
	if (B) cout << "\nfoi substituido o aluno de ra " << B->ra << " chamado " << B->n << "\n";
	get(K);
}
void get(int K){
	No* A;
	cout << "aluno de ra = " << K << ((A = h->get(K))? "" : " nao") << " existe.";
	if(!A) return;
	cout << " Seu ra e: " << A->ra << ", e seu nome e: " << A->n <<"\n"; 
}
void rm(int K) {
	No* A = h->remove(K);
	cout << "aluno de ra = " << K << (A? "" : " nao") << " existe para remocao.\n";
	if (A) get(K);
}
void iter() {
	cout << "\nagora geraremos um array das chaves do hash. e as mostraremos em ordem crescente.";
	int i = -1, *is = I->iterador();
	while(++i < I->l) cout << "ra: " << is[i];
}
void keys() {
	cout << "\ntambem foi gerado um array de alunos, ordenados de acordo com suas chaves.";
	int i = -1;
	No **as = I->keys();
	while(++i < I->l) cout << "ra: " << as[i]->ra << ", nome: " << as[i]->n;
}