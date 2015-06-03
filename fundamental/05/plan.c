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

// Extracts formatted information from a string to a schedule.
void split(char *originalString, SCHEDULE *s) {
  int n = sscanf(originalString, "%d %d %d %s %s %s %s", 
    &s->year, &s->month, &s->day, s->time, s->title, s->place, s->comment);
  if (n < 7) {
    s->comment[0] = '\0';
  }
}

// Based on Chapter 4, creates schedules in an array from a text file.
int fileReader(char *fileName, SCHEDULE *scheduleArray, int *scheduleNum) {
  *scheduleNum = 0; // Also sets scheduleNum accordingly.
  FILE *fp;
  char line[4116];
  fp=fopen(fileName, "r");
  if(fp==NULL){
    printf("Cannot open the file\n");
    return -1;
  }
  while(fgets(line, sizeof(line), fp)!=NULL){
    split(line, &(scheduleArray[(*scheduleNum)++]));
  }
  fclose(fp);
  return 0;
}

// Based on Chapter 4, creates an text file based on the schedules array.
int fileWriter(char *fileName, SCHEDULE *scheduleArray, int scheduleNum) {
  FILE *fpw;
  fpw=fopen(fileName, "w");
  if(fpw==NULL){
    printf("Cannot open the file\n");
    return -1;
  }
  int lineNumber = 1;
  for (int i = 0; i < scheduleNum; i++) {
    SCHEDULE *s = &scheduleArray[i];
    fprintf(fpw, "%d %d %d %s %s %s %s\n", 
      s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
  }
  fclose(fpw);
  printf("Output complete\n");
  return 0;
}

// Prints every schedule from the array.
void printAllSchedule(SCHEDULE *scheduleArray, int N) {
  for(int i = 0; i < N; i++) {
    SCHEDULE *s = &scheduleArray[i];
    printf("%d/%d/%d %s [%s @ %s] %s\n", 
      s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
  }
}

// Prints every schedule from the array with the same date as today.
void printSchedule(SCHEDULE *scheduleArray, int N, int year, int month, int day) {
  for(int i = 0; i < N; i++) {
    SCHEDULE *s = &scheduleArray[i];
    if (s->year == year && s->month == month && s->day == day) {
      printf("%d/%d/%d %s [%s @ %s] %s\n", 
        s->year, s->month, s->day, s->time, s->title, s->place, s->comment);
    }
  }
}
