#include <cstring>
#include <cstdio>
#include <iostream>

// implementar insere_aluno_na_posicao_vetor

using namespace std;
const int MAX = 5;

struct Aluno
{
    int matricula;
    char nome[40];
};

void insere_aluno_na_posicao_vetor(Aluno *a, int matricula, char nome[40])
{
    a->matricula = matricula;
    strcpy(a->nome, nome);
}

int main()
{
    Aluno *alunos = new Aluno[MAX];
    char nome[40];
    int matricula, i;

    for (i = 0; i < MAX; i++)
    {
        cout << "Matricula: "; cin >> matricula;
        cout << "Nome.....: "; fflush(stdin); gets(nome);
        insere_aluno_na_posicao_vetor (&alunos[i], matricula, nome);
    }

    if (alunos != 0)
    {
        for (i = 0; i < MAX; i++)
            if (alunos[i].nome != 0)
                delete [] alunos[i].nome;
        delete [] alunos;
    }

    return 0;
}
