#include "plan.h"
#include "calendar.h"
#include <stdlib.h>
#include <string.h>
#include <time.h>
  const char options[] = 
    "c:(calendar)\na:(add)\nl:(list)\nt:(today)\nq:(quit)\n";
  const char errorWrongFormat[] = 
    "error: wrong format.\n";
  const int lineLength = 4116;

int valid(char *s) {
  int i = 0, spaces = 0;
  // Check if the string have at least 5 space characters, and if the string's buffer wasn't overflowed by gets function.
  for (; s[i] != '\0' && spaces < 5 && i < lineLength; spaces += s[i++] == ' ');
  return spaces == 5 && i < lineLength;
}

void menu() {
  int n = 0; // Number of schedules.
  SCHEDULE *scheduleArray = (SCHEDULE *) malloc(1000 * sizeof(SCHEDULE));
  char line[lineLength];
  time_t now; struct tm *now_tm;
  fileReader("testSchedule.txt", scheduleArray, &n);
  for(int ret = 1; ret == 1; ) {
    now = time(NULL);
    now_tm = localtime(&now);
    printf("schedule> ");
    fflush(stdin);
    gets(line);
    if (line[1] != '\0') { // Only accepts strictly valid inputs.
      printf(options);
      continue;
    }
    switch(line[0]) {
      case 'c': // calendar display
        printMonthName(now_tm->tm_mon + 1, now_tm->tm_year + 1900);
        printMonthCalendar(now_tm->tm_mon + 1, now_tm->tm_year + 1900);
        break;
      case 'a': // schedule input
        if (gets(line)  == NULL || !valid(line))  {
          printf(errorWrongFormat);
          break;
        }
        SCHEDULE* s = (SCHEDULE*) malloc(sizeof(SCHEDULE));
        split(line, s);
        scheduleArray[n++] = *s;
        free(s);
        break;
      case 'l': // list all schedules
        printAllSchedule(scheduleArray, n);
        break;
      case 't': // display today's schedules
        printSchedule(scheduleArray, n, now_tm->tm_year + 1900, 
          now_tm->tm_mon + 1, now_tm->tm_mday);
        break;
      case 'q': // quit
        fileWriter("testSchedule.txt", scheduleArray, n);
        free(scheduleArray);
        ret = 0;
        break;
      default:
        printf(options);
        break;
    }
  }
}
