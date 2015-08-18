#include "word-r.h"

int freadWORD(WORD *w, FILE *fp){
	int i,c;
	int value[2];
	for(i=0; i<2; i++){
		c=fgetc(fp);
		if(c==EOF){
			return 0;
		}else{
			value[i]=c;
		}
	}
	*w=256*value[1]+value[0];
	return 1;
}
int freadDWORD(DWORD *dw, FILE *fp){
	int i,c;
	int value[4];
	for(i=0; i<4; i++){
		c=fgetc(fp);
		if(c==EOF){
			return 0;
		}else{
			value[i]=c;
		}
	}
	*dw=256*256*256*value[3]+256*256*value[2]+256*value[1]+value[0];
	return 1;
}
