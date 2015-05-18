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

void split(char *originalString, SCHEDULE *s) {
  int n = sscanf(originalString, "%d %d %d %s %s %s %s", &s->year, &s->month, &s->day, s->time, s->title, s->place, s->comment);
  if (n < 7) {
    s->comment[0] = '\0';
  }
}

int fileReader(char *fileName, SCHEDULE *scheduleArray, int *scheduleNum) {
  *scheduleNum = 0;
  FILE *fp;
  char line[4096];
  fp=fopen(fileName, "r");
  if(fp==NULL){
    return -1;
  }
  while(fgets(line, sizeof(line), fp)!=NULL){
    split(line, &(scheduleArray[(*scheduleNum)++]));
  }
  fclose(fp);
  return 0;
}

int fileWriter(char *fileName, SCHEDULE *scheduleArray, int scheduleNum) {
  FILE *fpw;
  char line[4096];
  fpw=fopen(fileName, "w");
  if(fpw==NULL){
    return -1;
  }
  int lineNumber = 1;
  for (int i = 0; i < scheduleNum; i++) {
    SCHEDULE *s = &scheduleArray[i];
    fprintf(fpw, "%d %d %d %s %s %s %s\n", s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
  }
  fclose(fpw);
  return 0;
}

void printAllSchedule(SCHEDULE *scheduleArray, int N) {
  for(int i = 0; i < N; i++) {
    SCHEDULE *s = &scheduleArray[i];
    printf("%d/%d/%d %s [%s @ %s] %s\n", s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
  }
}

void printSchedule(SCHEDULE *scheduleArray, int N, int year, int month, int day) {
  for(int i = 0; i < N; i++) {
    SCHEDULE *s = &scheduleArray[i];
    if (s->year == year && s->month == month && s->day == day) {
      printf("%d/%d/%d %s [%s @ %s] %s\n", s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
    }
  }
}

