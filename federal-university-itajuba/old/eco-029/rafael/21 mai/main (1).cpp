#include <iostream>
#include <stdlib.h>

using namespace std;

struct geo{
char nome[30];
float lon;
float lat;
geo*prox;
};
struct no{
int code;
no* fil_esq;
no* fil_dir;
geo* lista_hash;};
struct conj{
no* raiz;
int entradas;
};
int hashcode(geo* a){
    int aux1,aux2;
    if(a==NULL)
    {
        return 360;
    }
    aux1=a->lat;
    aux2=a->lon;
    return(aux1+aux2);
}
int compareto(geo* a, no* b){
    int hca=hashcode(a);
    return hca-(b->code);
}
bool equals(geo* a,geo* b){
    if(a==NULL||b==NULL)return false;
    if(a->nome==b->nome&&a->lat==b->lat&&a->lon==b->lon)return true;
    if(equals(a,(b->prox)))return true;
    return false;
}
bool contains(geo* e,no* p){
    int a=hashcode(e);
    if(a<p->code)
    contains(e,p->fil_esq);
    else if(a>p->code)
    contains(e,p->fil_dir);
    else if(equals(e,p->lista_hash))return true;
    return false;
}
bool isEmpty(conj c){
    if(c.entradas==0)
    return true;
    return false;
}
void size(int &ent,conj c){
    ent=c.entradas;
}
void clear(conj* a,no*p){
if (p == NULL) return;
clear(a,p->fil_esq);
delete(p);
a->entradas--;
p=NULL;
clear(a,p->fil_dir);
}
void cria_arvore(conj* tree){
    tree->raiz=NULL;
    tree->entradas=0;
}
void destroi_arvore(conj* tree){
    clear(tree,tree->raiz);
    delete(tree);
    return;
}
void Insere(geo x, no* &p){

  if(p==NULL) {
    no*aux=new no;
    aux->code=hashcode(&x);
    aux->fil_dir=NULL;
    aux->fil_esq=NULL;
    aux->lista_hash=&x;
    p=aux;
    return;
  }
  int a =hashcode(&x);
  if(a<(p->code)){
         Insere(x,p->fil_esq);
        return;
  }
  if(a>p->code)
  {
      Insere(x,p->fil_dir);

      return;
  }
  no*aux=p;
  geo* aux2=aux->lista_hash;
   do
  {
   if(equals(&x,aux2))
    {
        cout<<"No ja existente!!";
        return;
    }
   else
    {
        aux2=aux2->prox;
        return;
    }
  }while(aux2!=NULL);
  aux2=&x;

}
void Remove(geo x, no* &p){
    if(p==NULL)
    {
        cout<<"Lista Vazia!";
        return;
    }
    int a=hashcode(&x);
    no*aux=p;
    geo* aux2=aux->lista_hash;
    do
    {
        if(a<aux->code)
        aux=aux->fil_esq;
        else if(a>aux->code)
        aux=aux->fil_dir;
        else
        {

            do
            {

              if(equals(aux2,&x))
                 {
                     geo*aux3=aux2->prox;
                     aux2=aux2->prox;
                     delete(aux2);
                     aux=NULL;
                 }
              else
              aux2=aux2->prox;
            }while(aux!=NULL);
        }
    }while(aux!=NULL);

}
void menu(conj* &tree){
    int op;
    do{
    do{
        cout<<"MENU:\n\n1-Inserir no\n2-Remover no\n3-Procurar Item";
    cout<<"4-Limpar a Arvore\n5-Numero de Elementos\n6-Verificar se a arvore esta vazia";
    cout<<"7-Apagar Arvore\n8-Sair\n";
    cin>>op;
    system("cls");
    }while(op<1||op>8);
    system("pause");
    if(op==1){
        geo aux;
        cout<<"Insira o Nome do local: ";
        cin>>aux.nome;
        cout<<"Insira a Latitude: ";
        cin>>aux.lat;
        cout<<"Insira a Longitude";
        cin>>aux.lon;
        aux.prox=NULL;
        Insere(aux,tree->raiz);
    }
    else if(op==2){
    geo aux;
     cout<<"Insira o Nome do local: ";
        cin>>aux.nome;
        cout<<"Insira a Latitude: ";
        cin>>aux.lat;
        cout<<"Insira a Longitude";
        cin>>aux.lon;
        aux.prox=NULL;
        Remove(aux,tree->raiz);
    }
    else if(op==3){
    geo aux;
     cout<<"Insira o Nome do local: ";
        cin>>aux.nome;
        cout<<"Insira a Latitude: ";
        cin>>aux.lat;
        cout<<"Insira a Longitude";
        cin>>aux.lon;
        aux.prox=NULL;
        contains(&aux,tree->raiz);}
    else if(op==4){
    clear(tree,tree->raiz);}
    else if(op==5){
    cout<<"Numero de elementos: "<<tree->entradas;
    }
    else if(op==6){
    if(tree->entradas==0)cout<<"Arvore Vazia!";
    else cout<<"Ha elementos na arvore!";}
    else if(op==7){
    destroi_arvore(tree);
    cout<<"Arvore Destruida!";}
    else if(op==8){
    return;}
    }while(true);
}
int main(){
    conj* tree;
    tree->entradas=0;
    tree->raiz=NULL;
    menu(tree);
    return 0;
}
