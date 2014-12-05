#include <cstring>
#include <cstdio>
#include <iostream>

// implementar cria_vetor_alunos, leia_alunos, imprime_alunos

using namespace std;

struct Aluno
{
    int matricula;
    char nome[40];
};

bool cria_vetor_alunos(Aluno **a, int l)
{
    // tentativa 1 (não da certo, não sei pq)
    {
        //Aluno *b = new Aluno[l];
        //a = &b;
    }

    // tentativa 2 (depois de ler a aula)
    {
        *a = new Aluno[l];
    }

    return true;
}

void leia_alunos(Aluno **a, int l)
{

    int i = -1;
    while(++i < l)
    {
        // a->[i].matricula não fuciona porque depois do -> tem que estar uma propriedade.
        std::cout << "Matricula: "; cin >> (*a)[i].matricula;
        std::cout << "Nome.....: "; fflush(stdin); gets((*a)[i].nome);
    }
}

void imprime_alunos(Aluno *a, int l)
{
    int i = -1;
    while(++i < l)
    {
        std::cout << "Matricula: " << a[i].matricula << "\n";
        std::cout << "Nome.....: " << a[i].nome << "\n";
    }
}

int main()
{
    Aluno *alunos = 0;

    if (cria_vetor_alunos(&alunos, 5))
    // aloca vetor de alunos
    {
        leia_alunos(&alunos, 5);
        imprime_alunos(alunos, 5);
    }

    if (alunos != 0)
        delete [] alunos;
    return 0;
}
