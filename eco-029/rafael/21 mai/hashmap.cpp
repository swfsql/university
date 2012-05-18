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

	No* put(int K, No *no) {
		// substitui no, retorna substituido, null se nao substituiu.
		No **next = &hash[no->ra % L]->next;
		while(*next && (*next)->ra - K) next = &(*next)->next;
		No *ret = *next;
		*next = no;
		if (ret) (*next)->next = ret->next; else ++l;
		return ret;
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
	aluno = new No (2, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " << hm->hash[2]->next->n << ", de ra = " << hm->hash[2]->next->ra << ".\n";

	strcpy(nome, "aluno2");
	aluno = new No (2, nome);
	cout << "\nfoi subtituido: " << hm->put(aluno->ra, aluno)->n << ".";
	cout << "\nno lugar: " << hm->hash[2]->next->n << ", de ra = " << hm->hash[2]->next->ra << ".\n";

	strcpy(nome, "aluno9");
	aluno = new No (9, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " << hm->hash[2]->next->next->n << ", de ra = " << hm->hash[2]->next->next->ra << ".\n";

	strcpy(nome, "aluno2");
	aluno = new No (2, nome);
	cout << "\nfoi subtituido: " << hm->put(aluno->ra, aluno)->n << ".";
	cout << "\nno lugar: " << hm->hash[2]->next->n << ", de ra = " << hm->hash[2]->next->ra << ".\n";

	strcpy(nome, "aluno16");
	aluno = new No (16, nome);
	hm->put(aluno->ra, aluno);
	cout << "\nadicionou: " << hm->hash[2]->next->next->next->n << ", de ra = " << hm->hash[2]->next->next->next->ra << ".\n";
	
	
	return 0;
}














