#include "stdio.h"
#include "stdlib.h"

// Used in printMonthCalendar function.
const char weekDayName[7][10] = {"Sunday", "Monday", "Tuesday", "Wednesday", 
  "Thursday", "Friday", "Saturday"};
// Used in printMonthName function.
const char monthName[12][10] = {"January", "February", "March", "April", "May",
  "June", "July", "August", "September", "October", "November", "December"};
// Used in getEndDayOfMonth function.
const int endDays[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

// Based on Zeller's congruence. Only for the first day, with months varying between [1,12], and years of four digits. Returns [0,6] compatible with the weekDayName array.
int getDayOfWeek_FirstDay(int month, int year) {
  if (month < 3) {
    year--;
    month += 12;
  }
  int r = 1 + (13 * month + 8) / 5 + year + year / 4 + year / 400 - year / 100;
  return r % 7;
}

// Returns the number of the last day in a given month. Uses the endDays array, and considers leap years (when Feb may get an additional day - then the condition below turns to "1", to be added to Feb. Otherwise, when false, "0" is added).
int getEndDayOfMonth(int month, int year) {
    return endDays[month - 1] + 
      (month == 2 && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
}

void printMonthName(int month, int year) {
  printf("%s, %d\n", monthName[month - 1], year);
}

// Get the number of the week for the first day in a given month and year.
int getNumberOfWeek(int month, int year) {
  // See the day of week as an offset for the starting number of weeks.
  int days = getDayOfWeek_FirstDay(1, year); 
  for (int i = 1; i < month; i++) { 
    // Days from past months
    days += getEndDayOfMonth(i, year); 
  }
  return days / 7 + 1;
}

// Prints a given month's information, in a given year.
void printMonthCalendar(int month, int year) {
  int endDay = getEndDayOfMonth(month, year),
      weekNumber = getNumberOfWeek(month, year),
      weekDay = getDayOfWeek_FirstDay(month, year);

    // extra - Su, Mo, Tu, We, Th, Fe, Sa.
    printf("   ");
    for(int i = 0; i < 7; i++) {
      printf(" %.2s", weekDayName[i]);
    }

    // if it's NOT sunday, print weekNumber.
    if (weekDay % 7 != 0) { 
      printf("\n%2d:", weekNumber++);
      // offset from the last week of the last month.
      for (int i = 0; i < weekDay; i++) { 
        printf("   "); 
      }
    }
    // Offset for the first iteration, since the loop always increments weekDay.
    weekDay--; 
    for(int i = 1; i <= endDay; i++) {
      weekDay = (weekDay + 1) % 7;
      // when a new line starts..
      if (weekDay == 0) { 
        // print the weekNumber.
        printf("\n%2d:", weekNumber++); 
      }    
      printf(" %2d", i);
    }
    printf("\n");
}

