#include <ncurses.h>
#include <time.h>
#include <stdlib.h>


struct position{
 int x, y;
};
typedef struct position POSITION;

void getMonsterLocation(POSITION *monster) { // from randomWalk program
  int dx = rand() % 3 - 1, dy = rand() % 3 - 1;
  int x2 = monster->x + dx, y2 = monster->y + dy;
  monster->x += dx + (x2 < 0 ? -x2 : x2 > COLS - 1 ? COLS - x2 - 1 : 0);
  monster->y += dy + (y2 < 0 ? -y2 : y2 > LINES - 1 ? LINES - y2  - 1: 0);
}

void redraw() {
  initscr();
  noecho();
  curs_set(0x000000);
  timeout(100); // delay in ms
  srand((unsigned)time(NULL));


  POSITION *monster = (POSITION*) malloc(sizeof(POSITION));
  monster->x = COLS / 2;
  monster->y = LINES / 2;

  char ch = '\0';
  while(ch != 'q') {
    mvprintw(monster->y, monster->x, " "); 
    getMonsterLocation(monster);
    mvprintw(monster->y, monster->x, "M"); 
    cbreak(); // before input
    ch = getch(); // for input
    refresh(); // update screen
  }

  endwin(); // exit ncurses
}



