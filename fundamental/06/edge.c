#include <math.h>     
#include <stdio.h>
#include <stdlib.h>
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
        getLabel(x - 1, y, imgIn->width), // left pixel label
        getLabel(x + 1, y, imgIn->width), // right pixel label
        getLabel(x, y - 1, imgIn->width), // down pixel label
        getLabel(x, y + 1, imgIn->width), // up pixel label
        getLabel(x, y, imgIn->width) // label of the pixel that will be changed
      };
      imgOut->pixels[labels[4]].r = 0xFF - sqrt(
        pow(imgIn->pixels[labels[0]].r - imgIn->pixels[labels[1]].r, 2) + 
        pow(imgIn->pixels[labels[2]].r - imgIn->pixels[labels[3]].r, 2)); // color calculation
      imgOut->pixels[labels[4]].g = 0xFF - sqrt(
        pow(imgIn->pixels[labels[0]].g - imgIn->pixels[labels[1]].g, 2) + 
        pow(imgIn->pixels[labels[2]].g - imgIn->pixels[labels[3]].g, 2));
      imgOut->pixels[labels[4]].b = 0xFF - sqrt(
        pow(imgIn->pixels[labels[0]].b - imgIn->pixels[labels[1]].b, 2) + 
        pow(imgIn->pixels[labels[2]].b - imgIn->pixels[labels[3]].b, 2));
    }
  }
  for (int y = 0; y < imgIn->height - 1; y++) { // change the left and right corner to white
    long int label = getLabel(0, y, imgIn->width);
    imgOut->pixels[label].r = imgOut->pixels[label].g = imgOut->pixels[label].b = 0xFF;
    imgOut->pixels[label + imgIn->width - 1].r = imgOut->pixels[label + imgIn->width - 1].g = imgOut->pixels[label + imgIn->width - 1].b = 0xFF;
  }
  for (int x = 0; x < imgIn->width - 1; x++) { // change the bottom and top corner to white
    long int label = getLabel(x, imgOut->height - 1, imgIn->width);
    imgOut->pixels[x].r = imgOut->pixels[x].g = imgOut->pixels[x].b = 0xFF;
    imgOut->pixels[label].r = imgOut->pixels[label].g = imgOut->pixels[label].b = 0xFF;
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
