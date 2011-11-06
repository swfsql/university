#include <iostream>
#include <cstdio>

// cria alunos, suas notas; mostra médias.

struct Aluno
{
    char nome[30];
    int aNota[2];
    int lNota;
};

void load(Aluno [], int);
float mediaAluno(Aluno);
void mediaNotas(Aluno [], int);


int main()
{
    int l = 2, n = 2;
    Aluno a[l];

    // carrega notas alunos
    load(a,l);

    // media alunos individualmente
    int i = -1;
    Aluno t;
    while(++i < l)
        std::cout << "\nmedia de " << a[i].nome << ": " << mediaAluno(a[i]);

    // mostra média de cada nota
    mediaNotas(a,l);
    return 0;
}

void load(Aluno a[], int l)
{
    Aluno t;
    int k,
        lNota = 2;

    t.lNota = lNota;

    int i = -1;
    while(++i < l)
    {
        std::cout << "nome de " << i << ": ";
        fflush(stdin);
        gets(t.nome);

        std::cout << "notas: ";
        k = -1;
        while(++k < lNota)
            std::cin >> t.aNota[k];

        a[i] = t;
    }
}

float mediaAluno(Aluno t)
{
    float media = 0.0;
    int i = -1,
        l = t.lNota;
    while(++i < l)
        media += t.aNota[i];

    return media /= l;
}

void mediaNotas(Aluno a[], int l)
{
    int i;

    int k = -1;
    int l2 = a[0].lNota;

    float media;
    while(++k < l2)
    {
        media = 0.0;
        i = -1;
        while(++i < l)
            media += a[i].aNota[k];
        std::cout << "\nmedia da nota " << k << ": " << (media /= l);
    }
}
