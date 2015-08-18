#include <math.h>     
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "import.h"
#include "export.h"

void thicken() {
  IMAGE *imgIn = (IMAGE *) malloc(sizeof(IMAGE));
	FILE *fpIn = fopen("photo-edge.bmp", "r");
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
      long int labels[3] = {
        getLabel(x, y, imgIn->width), // pixel to be modified
        getLabel(x - 1, y, imgIn->width), // left pixel label
        getLabel(x + 1, y, imgIn->width) // right pixel label
      };
      PIXEL* p = &imgIn->pixels[labels[0]];
      int minR = p->r;
      int minG = p->g;
      int minB = p->b;
      // set minR/G/B to the lowest (strongest) value between each 3 horizontally consecutive pixel
      for (int i = 1; i < 3; i++) { 
        p = &imgIn->pixels[labels[i]];
        minR = p->r < minR ? p->r : minR;
        minG = p->g < minG ? p->g : minG;
        minB = p->b < minB ? p->b : minB;
      }
      // set the middle pixel to those value. Don't change the value if its white (empty).
      p = &imgOut->pixels[labels[0]];
      p->r = p->r == 0xFF ? p->r : minR;
      p->g = p->g == 0xFF ? p->g : minG;
      p->b = p->b == 0xFF ? p->b : minB;
    }
  }

	printf("Save data as a file, photo-edge-thick.bmp.\n");
	FILE *fpOut = fopen("photo-edge-thick.bmp", "w");
	printf("Save!\n");
	if(!saveImage(fpOut, imgOut)){
		printf("ERROR -- could not write the image.");
		return;
	}
	printf("done.");
	fclose(fpOut);
	return;
  

}
