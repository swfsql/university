#include <math.h>     
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "import.h"
#include "export.h"


void edge() {

  IMAGE *imgIn = (IMAGE *) malloc(sizeof(IMAGE));
	FILE *fpIn = fopen("photo.bmp", "r");
	printf("Opening image file...\n");
  if (!readImage(fpIn, imgIn)) {
		printf("ERROR -- could not read the image.");
    return;
  }
	printf("Image file loaded!\n");
	fclose(fpIn);

  IMAGE *imgOut = (IMAGE *) malloc(sizeof(IMAGE));
  imgOut->width = imgIn->width;
  imgOut->height = imgIn->height;
  imgOut->depth = imgIn->depth;
  imgOut->pixels = (PIXEL *)malloc(imgOut->width * imgOut->height * sizeof(PIXEL));

  for (int y = 1; y < imgIn->height - 1; y++) {
    for (int x = 1; x < imgIn->width - 1; x++) {
      long int labels[5] = {
        getLabel(x - 1, y, imgIn->width),
        getLabel(x + 1, y, imgIn->width),
        getLabel(x, y - 1, imgIn->width),
        getLabel(x, y + 1, imgIn->width),
        getLabel(x, y, imgIn->width)
      };
      imgOut->pixels[labels[4]].r = 255 - sqrt(pow(imgIn->pixels[labels[0]].r - imgIn->pixels[labels[1]].r, 2) + pow(imgIn->pixels[labels[2]].r - imgIn->pixels[labels[3]].r, 2));
      imgOut->pixels[labels[4]].g = 255 - sqrt(pow(imgIn->pixels[labels[0]].g - imgIn->pixels[labels[1]].g, 2) + pow(imgIn->pixels[labels[2]].g - imgIn->pixels[labels[3]].g, 2));
      imgOut->pixels[labels[4]].b = 255 - sqrt(pow(imgIn->pixels[labels[0]].b - imgIn->pixels[labels[1]].b, 2) + pow(imgIn->pixels[labels[2]].b - imgIn->pixels[labels[3]].b, 2));
    }
  }
  for (int y = 0; y < imgIn->height - 1; y++) {
    long int label = getLabel(0, y, imgIn->width);
    imgOut->pixels[label].r = 0;
    imgOut->pixels[label].g = 0;
    imgOut->pixels[label].b = 0;
    imgOut->pixels[label + imgIn->width - 1].r = 0;
    imgOut->pixels[label + imgIn->width - 1].g = 0;
    imgOut->pixels[label + imgIn->width - 1].b = 0;
  }
  for (int x = 0; x < imgIn->width - 1; x++) {
    long int label = getLabel(x, imgOut->height - 1, imgIn->width);
    imgOut->pixels[x].r = 0;
    imgOut->pixels[x].g = 0;
    imgOut->pixels[x].b = 0;
    imgOut->pixels[label].r = 0;
    imgOut->pixels[label].g = 0;
    imgOut->pixels[label].b = 0;
  }

	printf("Save data as a file, photo-edge.bmp.\n");
	FILE *fpOut = fopen("photo-edge.bmp", "w");
	printf("Save!\n");
	if(!saveImage(fpOut, imgOut)){
		printf("ERROR -- could not write the image.");
		return;
	}
	printf("done.");
	fclose(fpOut);
	return;
  

}
