// Questão: ordenar alunos de acordo com suas notas.
// Professor: Wandre.
// Alunos: Daniel Nogueira e Thiago Machado.


#include <iostream>
using namespace std;
int main()
{
	const int numAlunos = 3;
	int notas[numAlunos][2] ;
	int niveis = 0;
	for(int i = 0 ; i < numAlunos ; i++)
	{
		for(int j = 0 ; j < 2 ; j++)
		{
			cin>>notas[i][j];
		}
	}
	int dom[numAlunos][numAlunos] = {-1};
	for(int i = 0 ; i < numAlunos ; i++)
	{
		for(int j = 0 ; j < numAlunos ; j++)
		{
			dom[i][j] = -1;
		}
	}
	dom[0][0] = 0;
	// cada aluno será inserido em algum lugar na matriz dominação
	for(int i = 0 ; i < numAlunos ; i++)
	{
		// será percorrido todos os níveis
		for(int j = 0 ; j < numAlunos ; j++)
		{
			bool inc = true;
			bool dsc = false;
			bool melhor;
			// será percorrido cada coluna de cada nível
			for(int k = 0 ; k < numAlunos ; k++)
			{
				// não há mais alunos neste nível
				if (dom[j][k] == -1) {
					// se o aluno foi incomparável com todos, adicionar ele neste nível
					if (inc == true) dom[j][k] = i;
					else {
						// TODO: andar os alunos deste nível pra esquerda (gambiarra).
					}
					break;
				}
				// verifica se notas são comparáveis
				if((notas[i][0] - notas[dom[j][k]][0]) * (notas[i][1] - notas[dom[j][k]][1]) > 0)
				{
					// se são comparáveis, verifica qual aluno que irá descer de nível.
					// mas antes, verificamos se são iguais. se for, só adiciona o novo aluno no mesmo nível.
					if (notas[i][0] == notas[dom[j][k]][0] && notas[i][1] == notas[dom[j][k]][1]) {
						for (int p = 0 ; p < numAlunos ; p++) {
							if (dom[j][p] == -1) {
								dom[j][p] = i;
							}
						}
					}
					// verifica qual aluno é o maior.
					bool n1M = notas[i][0] >= notas[dom[j][k]][0];
					bool n2M = notas[i][1] >= notas[dom[j][k]][1];

					// se o aluno que será inserido é menor daquele que já está neste nível, procurar no nível debaixo.
					if (n1M + n2M == 0) {
						break;
					}
					// senão, o aluno que será inserido é maior de um que já está em um nível, descer então este do nível.

					// flag, quer dizer que eles são comparáveis sim.
					inc = false;

					// se ainda não desceu os alunos abaixo do que vai descer
					if (!dsc) {

						// percorrer do último nível pra cima
						for(int o = numAlunos-1 ; o > j; o--) {

							// percorrer as colunas
							for (int p = 0; p < numAlunos ; p++) {

								// se acima não tem mais alunos, zerar os alunos que estão à direita desta coluna
								if (dom[o-1][p] == -1) {
									for ( ; p < numAlunos ; p++)
										dom[o][p] = -1;
									break;
								}

								// copiar o aluno que está no nível acima, mesma coluna.
								notas[dom[o][k]][0] = notas[dom[o-1][k]][0];
							}
						}
					}

					// elimina o aluno pior deste nível.
					dom[j][k] = -1;

					// achar uma coluna sej alunos para adicionar o novo aluno. TODO: fazer isso fora deste loop fazer quando for andar alunos pra esquerda, no final de cada nível percorrido.
					for (int p = 0 ; p < numAlunos ; p++) {
						if (dom[j][p] == -1) {
							dom[j][p] = i;
						}
					}
				}
			}
		}
	}


	// mostra alunos

	for(int i = 0 ; i < numAlunos ; i++)
	{
		for(int j = 0 ; j < numAlunos ; j++)
		{
			if(dom[i][j] != -1)
			{
				cout<<dom[i][j]<<" \t";
			}
		}
		cout<<"\n";
	}

	
	return 0;


}