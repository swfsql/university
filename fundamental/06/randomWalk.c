#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "imgutil.h"
#include "export.h"

struct point
{
  int x;
  int y;
  unsigned char r;
  unsigned char g;
  unsigned char b;
};
typedef struct point POINT;


const int walkerColorNum = 3;
const int walkerColors[][3] = {{0xFF, 0x00, 0x00}, {0x00, 0x00, 0xFF}, {0x00, 0xFF, 0x00}};
void init(POINT *pointArray, int totalPointNum, int initX, int initY) {

  for (int i = 0; i < totalPointNum; i++) {
    pointArray[i].x = initX;
    pointArray[i].y = initY;
    pointArray[i].r = walkerColors[i % walkerColorNum][0];
    pointArray[i].g = walkerColors[i % walkerColorNum][1];
    pointArray[i].b = walkerColors[i % walkerColorNum][2];
  }
  srand((unsigned)time(NULL));
}


void move(POINT *pointArray, int i, int w, int h) {
  int r0 = rand() % 3,
      r1 = rand() % 3;
  pointArray[i].x += r0 == 0 ? (pointArray[i].x == w ? 0 : 1) : r0 == 1 ? (pointArray[i].x == 0 ? 0 : -1) : 0;
  pointArray[i].y += r1 == 0 ? (pointArray[i].y == h ? 0 : 1) : r1 == 1 ? (pointArray[i].y == 0 ? 0 : -1) : 0;
}


void drawPoints(POINT *pointArray, int w, int h, int totalPointNum, int turns) {
  IMAGE *img=(IMAGE *)malloc(sizeof(IMAGE));
  img->width=w;
  img->height=h;
  img->depth=24;
  img->pixels=(PIXEL *)malloc(img->width*img->height*sizeof(PIXEL));

  for (int i = 0; i < w * h; i++) {
    img->pixels[i].r = 0xFF;
    img->pixels[i].g = 0xFF;
    img->pixels[i].b = 0xFF;
  }

  for (int j = 0; j < turns; j++) {
    for (int i = 0; i < totalPointNum; i++) {
      move(pointArray, i, w, h);
      long int label = getLabel(pointArray[i].x, pointArray[i].y, img->width);
      //printf("x: %i, y: %i, label %li\n", pointArray[i].x, pointArray[i].y, label);
      img->pixels[label].r=pointArray[i].r;
      img->pixels[label].g=pointArray[i].g;
      img->pixels[label].b=pointArray[i].b;
    }
  }

	printf("Save data as a file, randomWalk.bmp.\n");
	FILE *fp = fopen("randomWalk.bmp", "w");
	printf("Save!\n");
	if(!saveImage(fp, img)){
		printf("ERROR -- could not write the image.");
		return;
	}
	printf("done.");
	fclose(fp);
	return;
}

