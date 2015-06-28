/*
 * test.c
 *
 *  Created on: 2013/03/31
 *      Author: masaomi
 */

#include "imgutil.h"
#include "export.h"
#include <stdio.h>
#include <stdlib.h>

int graph(int x, int y);

int main(void){

	FILE *fp;
	IMAGE *img=(IMAGE *)malloc(sizeof(IMAGE));
	int ret,x=0,y=0;
	int height=120, width=120;
	PIXEL *pixels=(PIXEL *)malloc(height*width*sizeof(PIXEL));
	img->height=height;
	img->width=width;
	img->depth=24;
	img->pixels=pixels;
	long int label;

	label=0;
	for(y=img->height-1; y>=0; y--){
		for(x=0; x<img->width; x++){
			label=getLabel(x,y,img->width);
			if(graph(x,y)){
				img->pixels[label].r=0;
				img->pixels[label].g=0;
				img->pixels[label].b=255;
			}else{
				img->pixels[label].r=255;
				img->pixels[label].g=255;
				img->pixels[label].b=255;
			}
		}
	}

	printf("Save data as a file, check.bmp.\n");
	fp=fopen("check.bmp", "w");
	printf("Save!\n");
	ret=saveImage(fp, img);
	if(!ret){
		printf("ERROR -- could not write the image.");
		return 0;
	}
	printf("done.");
	fclose(fp);
	return 0;
}

int graph(int x, int y){
	if((x-60)*(x-60)+(y-60)*(y-60)>800 &&(x-60)*(x-60)+(y-60)*(y-60)<1000){
		return 1;
	}else{
		return 0;
	}
}
