#include <iostream>
#include <cstring>
using namespace std;

struct No
{
	No () {
		next = 0;
	}
	No (char nome[40]) {
		next = 0;
		strcpy(n, nome); 
	}
	No (int chave, char nome[40]) {
		next = 0;
		ra = chave;
		strcpy(n, nome); 
	}
	
	No* put (No *no) {
		if (!next) {
			next = no;
			return 0;
		}
		if (no->ra == next->ra) {
			No *antigo = next;
			next = no;
			return antigo;
		}
		return next->put(no);
	}

	int ra; // chave
	char n[40];

	No* next;
};

struct Hashmap {

	int L;
	No **hash;

	int l;

	Hashmap(int len) {
		L = len;
		hash = new No*[len];
		l = 0;
		int i = -1;
		while(++i < len) {
			hash[i] = new No();
		}
	}

	// para implementar
	int size() {return l;}

	bool isEmpty() {return l == 0;}	

	No get(/*K*/) {}

	
	No* put(No *no) {
		return hash[no->ra % L]->put(no);
	}
	No* put(int K, No *no) {
		no->ra = K;
		return put(no);
	}

	No remove(/*K*/) {}

	/*I keys() {}*/

	void clear() {}

	/*I iterator() {}*/


};




int main()
{
	Hashmap *hm = new Hashmap(7);
	char nome[40]; 
	No *aluno;

	//

	strcpy(nome, "aluno1");
	aluno = new No (nome);
	hm->put(2, aluno);
	cout << "\nadicionou: " << hm->hash[2]->next->n << ", de ra = 2.\n";

	strcpy(nome, "aluno2");
	aluno = new No (nome);
	cout << "\nfoi subtituido: " << hm->put(2, aluno)->n << ".";
	cout << "\nno lugar: " << hm->hash[2]->next->n << ", de ra = 2.\n";
	
	return 0;
}














