#include <stdlib.h>
#include <string.h>
#include "plan.h"
int main(int argc, char **argv){
  int n=0;
  SCHEDULE *s=(SCHEDULE *)malloc(sizeof(SCHEDULE));
  SCHEDULE *scheduleArray=(SCHEDULE *)malloc(100*sizeof(SCHEDULE));
  fileReader("plan.txt", scheduleArray, &n);
  printSchedule(scheduleArray, n, 2015, 5, 15);
  char line[100];
  strcpy(line, "2015 5 20 12:10 lunch restaurant sushi");
  split(line, s);
  scheduleArray[n]=*s;
  n++;
  printAllSchedule(scheduleArray, n);
  fileWriter("plan.txt", scheduleArray, n);
  free(s);
}
