#include "word.h"
#include "imgutil.h"
#include "export.h"

int saveImage(FILE *fp, IMAGE *img){

	WORD bfType=0x4d42; /* 2byte=WORD */
	DWORD bfSize=40;
	WORD bfReserved1=0;
	WORD bfReserved2=0;
	DWORD bfOffset=54; /* 4byte=DWORD */
	DWORD biSize=40;
	DWORD biWidth=img->width;
	DWORD biHeight=img->height;
	WORD biPlanes=1;
	WORD biBitCount=img->depth;
	DWORD biCompression=0;
	DWORD biSizeImage=0;
	DWORD biXPelsPerMeter=300;
	DWORD biYPelsPerMeter=300;
	DWORD biClrUsed=0;
	DWORD biClrImportant=0;
	int x,y,i=0;
	PIXEL p;

		printf("Start.\n");
		// This program supports only 24bit depth for simplicity.
		if(img->depth!=24){
			printf("Sorry, this supports only 24bit depth.\n");
			return 0;
		}
		printf("Writing header...\n");
		fwriteWORD(bfType, fp);
		fwriteDWORD(bfSize, fp);
		fwriteWORD(bfReserved1, fp);
		fwriteWORD(bfReserved2, fp);
		fwriteDWORD(bfOffset, fp);
		fwriteDWORD(biSize, fp);
		fwriteDWORD(biWidth, fp);
		fwriteDWORD(biHeight, fp);
		fwriteWORD(biPlanes, fp);
		fwriteWORD(biBitCount, fp);
		fwriteDWORD(biCompression, fp);
		fwriteDWORD(biSizeImage, fp);
		fwriteDWORD(biXPelsPerMeter, fp);
		fwriteDWORD(biYPelsPerMeter, fp);
		fwriteDWORD(biClrUsed, fp);
		fwriteDWORD(biClrImportant, fp);

		printf("Writing data...\n");
    for(i = 0; i < img->width * img->height; i++) {
      fputc(img->pixels[i].b, fp);
      fputc(img->pixels[i].g, fp);
      fputc(img->pixels[i].r, fp);
    }
		printf("done!\n");

		return 1;
}
