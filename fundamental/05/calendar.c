#include "stdio.h"
#include "stdlib.h"

char weekDayName[7][10] = {"Sunday", "Monday", "Tuesday", 
      "Wednesday", "Thursday", "Friday", "Saturday"};
char monthName[12][10] = {"January", "February", "March", "April", "May", 
      "June", "July", "August", "September", "October", "November", "December"};
int endDays[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

// based on Zeller's congruence. 
// For the first day, months varying between [1,12], year having four digits. 
// Returns [0,6] compatible with the weekDayName array.
int getDayOfWeek_FirstDay(int month, int year) {
  if (month < 3) {
    year--;
    month += 12;
  }
  int r = 1 + (13 * month + 8) / 5 + year + year / 4 + year / 400 - year / 100;
  return r % 7;
}

// Returns the number of the last day in a given month. 
// Uses the endDays array, and considers leap years (when Feb may get an additional day - then the condition below turns to "1", to be added to Feb. Otherwise, when false, "0" is added).
int getEndDayOfMonth(int month, int year) {
    return endDays[month - 1] 
      + (month == 2 && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
}

void printMonthName(int month, int year) {
  printf("%s, %d\n", monthName[month - 1], year);
}

// get the number of the week for the first day in a given month and year.
int getNumberOfWeek(int month, int year) {
  int days = getDayOfWeek_FirstDay(1, year);
  for (int i = 1; i < month; i++) { 
    days += getEndDayOfMonth(i, year); // days from past months
  }
  return days / 7 + 1;
}

// prints a given month's information, in a given year.
void printMonthCalendar(int month, int year) {
  int endDay = getEndDayOfMonth(month, year),
      weekNumber = getNumberOfWeek(month, year),
      weekDay = getDayOfWeek_FirstDay(month, year);

    // extra - month name
    //printf("            %.3s\n", monthName[month - 1]);

    // extra - Su, Mo, Tu, We, Th, Fe, Sa.
    printf("   ");
    for(int i = 0; i < 7; i++) {
      printf(" %.2s", weekDayName[i]);
    }

    if (weekDay % 7 != 0) { // if it's NOT sunday, print weekNumber.
      printf("\n%2d:", weekNumber++);
      for (int i = 0; i < weekDay; i++) { // offset from the last week of the last month.
        printf("   "); 
      }
    }
    weekDay--; // Offset for the first iteration, since the loop always increments weekDay.
    for(int i = 1; i <= endDay; i++) {
      weekDay = (weekDay + 1) % 7;
      if (weekDay == 0) { // when a new line starts..
        printf("\n%2d:", weekNumber++); // print the weekNumber.
      }    
      printf(" %2d", i);
    }
}

