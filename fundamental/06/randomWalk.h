#ifndef RANDOMWALK_H_
#define RANDOMWALK_H_

#include <time.h>

struct point
{
  int x;
  int y;
  unsigned char r;
  unsigned char g;
  unsigned char b;
};

typedef struct point POINT;
void init(POINT *pointArray, int totalPointNum, int initX, int initY);
void move(POINT *pointArray, int i, int w, int h);
void drawPoints(POINT *pointArray, int w, int h, int totalPointNum, int turns);

#endif /* RANDOMWALK_H_ */
