// uso do FileSystem eh demonstrado na funcao main(), sem interface.

#include <fstream>
#include <iostream>
#include <cstring>
#include <stdio.h> // remover arquivos
#include <stdlib.h> // exit

using namespace std;

// todo: 
// 1) retirar estas variáveis locais, elas deviam estar dentro de estruturas,
// 2) usar prototipos.
static const int NUM_BLOCKS = 1000; // no DISK
static const int BLOCK_SIZE = 512; // no DISK
static const int POINTERS_PER_BLOCK = (BLOCK_SIZE / 4); // no DISK
//
static const int SIZE = 64; // no Inode


// primeiro bloco
struct SuperBlock {
	int size, // num blocos 
		iSize, // num blocos de indice
		freeList, // primeiro bloco da lista livre
    inum; // nome do proximo arquivo a ser criado

	void print(){
		cout << "SUPERBLOCK:\n"
			<< "Tamanho: " << size
			<< ", Tamanho dos indices: " << iSize
			<< ", Primeiro bloco livre: " << freeList
      << ", Nome do arquivo mais recente: " << inum
      << "\n";
	}
};


struct Inode { 
	int 
    fileSize,
		flags, // serve pra nada
		owner, // sera o inum
		*pointer;

	Inode() {
		flags = owner = fileSize = 0;
		pointer = new int[13];
    int i = -1; while(++i < 13) pointer[i] = 0;
	}

	void print() {
		cout << "[fileSize: " << fileSize << ", flags: " << flags 
      << ", owner: " << owner <<  ", tamanho: " << fileSize << " ";
		int i = -1; while(++i < 13) cout << ";" << pointer[i];
    cout << "]\n";
	}
};


struct InodeBlock {
	Inode **nodes;

	InodeBlock() {
		nodes = new Inode* [BLOCK_SIZE / SIZE];
		int i = -1; while(++i < BLOCK_SIZE / SIZE) nodes[i] = new Inode();
	}

	void print() {
		cout << "InodeBlock:\n";
		int i = -1; while(++i < BLOCK_SIZE / SIZE) nodes[i]->print(); 
	}

};

struct IndirectBlock {
	int *pointer;

	void clear() {
		int i = -1; while(++i < BLOCK_SIZE / 4) pointer[i] = 0;
	}

	IndirectBlock() {
		pointer = new int[BLOCK_SIZE / 4];
		clear();
	}

	void print() {
		cout << "IndirectBlock:\n";
		int i  = -1; while(++i < BLOCK_SIZE / 4) cout << pointer[i] << " | ";
	}
};

struct DescritorArquivo {
    int inum;
    Inode *in;

    DescritorArquivo() {
      inum = 0; 
      in = 0;
    }
};

struct DISK {
	fstream dsk;

	// num leitura / escrita
	int nread, nwrite;

	DISK() {
		nread = nwrite = 0;
		string arq_nome = "DISK";

		// carrega/cria DISK
		if( dsk.is_open() ) dsk.close();
		dsk.open( arq_nome.c_str() ); // reabrindo
	    if( !dsk.is_open() ) {
	    	cout << "DISK criado\n";
	    	dsk.open(arq_nome.c_str(), 
            ios_base::in | ios_base::out | ios_base::binary | ios::trunc);
	    
        // inicializa o superbloco
        SuperBlock sb;
        sb.size = 999;
        sb.iSize = 10;
        sb.freeList = sb.iSize + 1;
        sb.inum = 0;
        formatDisk(sb.size, sb.iSize); 

        write(0, &sb); // grava superblock no DISK

        // inicializa os blocos de inodes
        int i = 0; while(++i < sb.iSize + 1) InodeBlock ib;
        
      } else cout << "DISK carregado\n";
	}

/*
{ // teste que funciona de escrita/leitura sobre o disco.
  		long size = dsk.tellg();
 		cout << "tam: " << size << "\n";

	    char buff[4] = "oi";
	    int oi = 3000;
 		dsk.seekg (0,ios::beg);
	    // dsk.write((char*) (&oi), sizeof(oi));

	    cout << "o conteúdo foi: \n";
	    // dsk.seekg (0,ios::beg);
	    int a;
	    dsk.read((char*)(&a), sizeof(a));
	    int b;
	    dsk.read((char*)(&b), sizeof(b));
	    int c;
	    dsk.read((char*)(&c), sizeof(c));
	    cout << a << "\n";
	    cout << b << "\n";
	    cout << c << "\n";
}
*/

	void seek(int nblock) {
		if (nblock < 0 || nblock >= NUM_BLOCKS) {
			cout << "bloco invalido\n";
			exit (1);
		} 
		dsk.seekg ((long) (nblock * BLOCK_SIZE), ios::beg);
    // cout << " (sk " << nblock << ") ";
	}


	// le bytes
	void read(int nblock, char *buffer) {
		if (strlen(buffer) != BLOCK_SIZE) {
			cout << "tamanho do buffer errado\n";
      			exit(1);
		}
		seek(nblock);
		dsk.read(buffer, sizeof(buffer)); 
    // todo: testar, talvez eu tenha que mandar ler + de uma vez
		++nread;
	}

	// le superbloco
	void read(int nblock, SuperBlock *block) {
		seek(nblock);
		dsk.read((char*)(&block->size), sizeof(block->size));
		dsk.read((char*)(&block->iSize), sizeof(block->iSize));
		dsk.read((char*)(&block->freeList), sizeof(block->freeList));
		dsk.read((char*)(&block->inum), sizeof(block->inum));
		++nread;
	}

	// le inode
	void read(int nblock, InodeBlock *block) {
		seek(nblock);
		int i = -1;
		while(++i < BLOCK_SIZE / SIZE) {
			dsk.read((char*)(&(block->nodes[i]->flags)), 
          sizeof(block->nodes[i]->flags));	
			dsk.read((char*)(&(block->nodes[i]->owner)), 
          sizeof(block->nodes[i]->owner));
			dsk.read((char*)(&(block->nodes[i]->fileSize)), 
          sizeof(block->nodes[i]->fileSize));
			int j = -1; 
			while(++j < 13) 
				dsk.read((char*)(&(block->nodes[i]->pointer[j])), 
            sizeof(block->nodes[i]->pointer[j]));
		}
		++nread;
	}

	// le bloco indireto
	void read(int nblock, IndirectBlock *block) {
		seek(nblock);
		int i = -1; 
		while(++i < BLOCK_SIZE / 4) 
			dsk.read((char*)(&(block->pointer[i])), sizeof(block->pointer[i]));
		++nread;
	}




	// escreve bytes
	void write(int nblock, char *buffer) {
		if (strlen(buffer) != BLOCK_SIZE) {
			cout << "tamanho do buffer errado\n";
			cout << "buffer: " << strlen(buffer) 
        << ", correto: " << BLOCK_SIZE << "\n";
      cout << "a string: " << buffer << "\n";
      exit(1);
		}
		seek(nblock);
		dsk.write(buffer, sizeof(buffer)); 
    // todo: testar, talvez eu tenha que mandar escrever + de uma vez
		++nwrite;
	}

	// escreve superbloco
	void write(int nblock, SuperBlock *block) {
		seek(nblock);
		dsk.write((char*) &(block->size), sizeof(block->size));
		dsk.write((char*) &(block->iSize), sizeof(block->iSize));
		dsk.write((char*) &(block->freeList), sizeof(block->freeList));
		dsk.write((char*) &(block->inum), sizeof(block->inum));
		++nwrite;
    cout << "escreveu SuperBlock no DISK\n";
	}

	// escreve inodeBlock
	void write(int nblock, InodeBlock *block) {
		seek(nblock);
		int i = -1;
		while(++i < BLOCK_SIZE / SIZE) {
			dsk.write((char*)(&(block->nodes[i]->flags)), 
          sizeof(block->nodes[i]->flags));	
			dsk.write((char*)(&(block->nodes[i]->owner)), 
          sizeof(block->nodes[i]->owner));
			dsk.write((char*)(&(block->nodes[i]->fileSize)), 
          sizeof(block->nodes[i]->fileSize));
			int j = -1; 
			while(++j < 13) 
				dsk.write((char*)(&(block->nodes[i]->pointer[j])), 
            sizeof(block->nodes[i]->pointer[j]));
		}
		++nwrite;
	}

	// escreve bloco indireto
	void write(int nblock, IndirectBlock *block) {
		seek(nblock);
		int i = -1; 
		while(++i < BLOCK_SIZE / 4) 
			dsk.write((char*)(&(block->pointer[i])), sizeof(block->pointer[i]));
		++nwrite;
	}

	void print() {
		cout << "Foi realizado " 
      << nread << " leituras e " 
      << nwrite << " escritas no DISK.\n";
	}

	void stop(bool remover) {
		print();

		dsk.close();
		if (remover) {
			cout << "removendo DISK\n";
			if( remove( "DISK" ) != 0 ) cout << "erro ao remover DISK\n";
			else cout << "DISK removido com sucesso\n";	
		}

	}
	void stop() {stop(true);}



  // interface FileSystem
  DescritorArquivo descritorArquivos[20];
 

  // usando indirectBlock como bloco livre, utilizando apenas o primeiro
  // ponteiro
  int formatDisk(int size, int iSize) {
    // cria list de blocos livres, do bloco iSize+1 ~ size-1
    IndirectBlock idb;
    int i = iSize; while(++i < size) {
      idb.pointer[0] = i + 1;
      write(i, &idb);
    }
    idb.pointer[0] = 0;
    write(i, &idb);

    // cria inodeBlocks, do bloco 1 ~ iSize
    InodeBlock inb;
    i = 0; while(++i < iSize) write(i, &inb);

    cout << "DISK formatado\n";
    return 0;
  }

  int shutdown() {
    // fecha arquivos abertos e encerra disco simulado

    return 0;
  }

  int create(SuperBlock *sb) {
    // arquivo vazio.
    int ret = 0;

    // procura um iNodeBlock que tenha espaco livre
    InodeBlock *inb = new InodeBlock();
    int i = 0, j; while(++i < sb->iSize) {
      read(i, inb);
      j = -1; while(++j < BLOCK_SIZE / SIZE) {
        // dentro de cada inodeBlock tem varios inodes. procurar um livre.
        if (!inb->nodes[j]->owner) break; // encontrou um inode livre
      }
      if (j != BLOCK_SIZE / SIZE) break;
    }
    
    if (i != sb->iSize) {
      // ha inodes disponiveis
      ret = inb->nodes[j]->owner = ++(sb->inum);
      write(i, inb);
      cout << "Criado novo arquivo chamado " << ret << ".\n";
      ret = open(ret, sb);

    } else {
      // nao ha inodes disponiveis
      cout << "nao ha Inodes disponiveis. Criacao de arquivo cancelada.\n";
      ret = -1;
    }

    // retorna descritor de arquivo, 0 ~ 20.
    delete inb; // se o arquivo nao
    return ret;
  }

  int open(int inum, SuperBlock *sb) {
    // procura por descritor de arquivos disponivel
      int k = -1;
      while(++k < 20) if (descritorArquivos[k].inum == 0) break;
      if (k != 20) {
        // ha descritor de arquivo disponivel

        // procura pelo inode do arquivo, primeiro por todos os inodes.
        InodeBlock *inb = new InodeBlock();
        int i = 0, j; while(++i < sb->iSize) {
          read(i, inb);
          j = -1; while(++j < BLOCK_SIZE / SIZE) {
            // dentro de cada inodeBlock tem varios inodes. procurar um livre.
            if (inb->nodes[j]->owner == inum) break; // encontrou um inode livre
          }
          if (j != BLOCK_SIZE / SIZE) break;
        }

        if (i != sb->iSize) {
          // encontrou inode
          Inode *in = new Inode();
          in->fileSize = inb->nodes[j]->fileSize;
          in->flags = inb->nodes[j]->flags;
          in->owner = inb->nodes[j]->owner;
          int l = -1; while(++l < 13) 
            in->pointer[l] = inb->nodes[j]->pointer[l];

          descritorArquivos[k].inum = in->owner;
          descritorArquivos[k].in = in;
          delete inb;
          cout << "Arquivo " << inum << " aberto.\n";
          return k;
        } else {
          // nao encontrou inode
          cout << "O arquivo " << inum << " nao existe.\n";
          delete inb;
          return -1;
        }
      
      } else {
        // nao ha descritor de arquivo o suficiente
        cout << "espaco insuficiente para abrir o arquivo " << inum << ".\n";
        return -1;
      }

    // retorna descritos de arquivo, 0 ~ 20.
    return -1;
  }

  int inumber() {

    return 0;
  }

  int read() {



    return 0;
  }

  int write() {

    return 0;
  }

  int seek() {

    return 0;
  }

  int close() {

    return 0;
  }

  // nome com _ porque delete eh reservado.
  int _delete() {

    return 0;
  }


};

int main() {

  // abre arquivo [cria DISK]
  cout << "{instancia o DISK}\n";
	DISK *dsk = new DISK();

  // carrega SuperBlock na memoria RAM, e mostra ele
  cout << "\n{carrega e mostra superBlock}\n";
  SuperBlock *sb = new SuperBlock();
  dsk->read(0, sb);
  sb->print();
  cout << "\n";

  // demonstra blocos livres
  cout << "\n{Primeiro bloco livre}: " << sb->freeList << ", {segundo bloco livre}: ";
  IndirectBlock *idrb = new IndirectBlock();
  if (sb->freeList) dsk->read(sb->freeList, idrb);
  cout << idrb->pointer[0] << "\n";
  // foi mostrado o primeiro bloco livre, e dentro dele pega o indice do
  // segundo bloco livre.

  // mostra Inodes no primeiro InodeBlock
  cout << "\n{inodes no primeiro bloco}\n";
  InodeBlock *inb = new InodeBlock();
  dsk->read(1, inb);
  inb->print();
  cout << "\n";

  // cria dois arquivos em branco
  cout << "\n{cria dois arquivos em branco}\n";
  dsk->create(sb);
  dsk->create(sb);
  cout << "\n";

  // ao finalizar, deve-se salvar o superbloco no DISK
  cout << "\n{salva superBlock no DISK}\n";
  dsk->write(0, sb);
  cout << "\n";

  // para o DISK, sem remove-lo
	cout << "\n{para o DISK}\n";
  dsk->stop(false);
  cout << "\n";
	return 0;
}



