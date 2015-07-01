/*
 * bitmap.c
 *
 *  Created on: 2013/03/30
 *      Author: masaomi
 */

#include "import.h"
#include "imgutil.h"
#include "word.h"
#include "word-r.h"
#include <stdio.h>
#include <stdlib.h>

int readImage(FILE *fp, IMAGE *img){
	int flag=1, ret, colors, i, c;
	int x,y;
	WORD bfType; /* 2byte=WORD */
	DWORD bfSize;
	WORD bfReserved1;
	WORD bfReserved2;
	DWORD bfOffset; /* 4byte=DWORD */
	DWORD biSize;
	DWORD biWidth;
	DWORD biHeight;
	WORD biPlanes;
	WORD biBitCount;
	DWORD biCompression;
	DWORD biSizeImage;
	DWORD biXPelsPerMeter;
	DWORD biYPelsPerMeter;
	DWORD biClrUsed;
	DWORD biClrImportant;
	PIXEL palet[MAXCOLORS];

	ret=freadWORD(&bfType, fp);
	flag=flag*ret;

	ret=freadDWORD(&bfSize, fp);
	flag=flag*ret;

	ret=freadWORD(&bfReserved1, fp);
	flag=flag*ret;

	ret=freadWORD(&bfReserved2, fp);
	flag=flag*ret;

	ret=freadDWORD(&bfOffset, fp);
	flag=flag*ret;

	ret=freadDWORD(&biSize, fp);
	flag=flag*ret;

	ret=freadDWORD(&biWidth, fp);
	flag=flag*ret;

	ret=freadDWORD(&biHeight, fp);
	flag=flag*ret;

	ret=freadWORD(&biPlanes, fp);
	flag=flag*ret;

	ret=freadWORD(&biBitCount, fp);
	flag=flag*ret;

	ret=freadDWORD(&biCompression, fp);
	flag=flag*ret;

	ret=freadDWORD(&biSizeImage, fp);
	flag=flag*ret;

	ret=freadDWORD(&biXPelsPerMeter, fp);
	flag=flag*ret;

	ret=freadDWORD(&biYPelsPerMeter, fp);
	flag=flag*ret;

	ret=freadDWORD(&biClrUsed, fp);
	flag=flag*ret;

	ret=freadDWORD(&biClrImportant, fp);
	flag=flag*ret;

	if(biCompression!=0){
		printf("Input file have to be uncompressed\n");
		return 0;
	}

	//printf("bfType : %hx\n", bfType);

	/* BMP must start 0x4d42*/
	if(bfType!=0x4d42){
		return 0;
	}

	/* If target BMT is not Windows BMP, return zero! */
	if(biSize!=40){
		return 0;
	}

	if(biClrUsed==0){
		switch(biBitCount){
		case 1:
			colors=2;
			break;
		case 4:
			colors=16;
			break;
		case 8:
			colors=256;
			break;
		default:
			colors=0;
			break;
		}
	}else{
		colors=biClrUsed;
	}

	for(i=0; i<colors; i++){
		c=getc(fp);
		if(c!=EOF){
			palet[i].b=c;
		}else{
			return 0;
		}
		c=getc(fp);
		if(c!=EOF){
			palet[i].g=c;
		}else{
			return 0;
		}
		c=getc(fp);
		if(c!=EOF){
			palet[i].r=c;
		}else{
			return 0;
		}
		c=getc(fp);
		if(c!=EOF){
		}else{
			return 0;
		}
	}


	img->height=biHeight;
	img->width=biWidth;
	img->depth=biBitCount;
	img->pixels=(PIXEL *)malloc(sizeof(PIXEL)*biHeight*biWidth);

	i=0;
	for(y=0; y<img->height; y++){
		for(x=0; x<img->width; x++){
			if(biBitCount==8){
				c=fgetc(fp);
				if(c==EOF){
					return 0;
				}
				img->pixels[i].r=palet[c].r;
				img->pixels[i].g=palet[c].g;
				img->pixels[i].b=palet[c].b;
			}else if(biBitCount==24){
				c=fgetc(fp);
				if(c==EOF){
					return 0;
				}
				img->pixels[i].b=c;
				c=fgetc(fp);
				if(c==EOF){
					return 0;
				}
				img->pixels[i].g=c;
				c=fgetc(fp);
				if(c==EOF){
					return 0;
				}
				img->pixels[i].r=c;
			}
			i++;
		}
	}
	return flag;
}
