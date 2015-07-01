#include "randomWalk.h"
#include "imgutil.h"
#include "export.h"
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv){
  int totalPointNum=3;
  POINT *pointArray=(POINT *)malloc(totalPointNum*sizeof(POINT));
  int width=500;
  int height=500;
  int initX=width/2;
  int initY=height/2;
  int turns=100000;
  init(pointArray, totalPointNum, initX, initY);
  drawPoints(pointArray, width, height, totalPointNum, turns);
}
