#ifndef PLAN_H_
#define PLAN_H_

#include <stdio.h>

struct schedule
{
  int year;
  int month;
  int day;
  char time[6];
  char title[1024];
  char place[1024];
  char comment[2048];
};

typedef struct schedule SCHEDULE;

void split(char *originalString, SCHEDULE *s); 
int fileReader(char *fileName, SCHEDULE *scheduleArray, int *scheduleNum); 
int fileWriter(char *fileName, SCHEDULE *scheduleArray, int scheduleNum); 
void printAllSchedule(SCHEDULE *scheduleArray, int N); 
void printSchedule(SCHEDULE *scheduleArray, int N, int year, int month, int day); 

#endif
