#include <ncurses.h>
#include <time.h> // for the time()
#include <stdlib.h>
#include <unistd.h> // for the sleep

struct position{
 int x, y;
};
typedef struct position POSITION;

// generalized "positioned" print
void printPosition(POSITION *p, char *f) {
  mvprintw(p->y, p->x, f); 
}
// generalized "unit" initialization.
void initPositionLocation(POSITION *p, int x, int y, char *f) {
  printPosition(p, " ");
  p->x = x;
  p->y = y;
  printPosition(p, f);
}
// generalized "inside the screen" movement.
void getPositionLocation(POSITION *c, int dx, int dy) {
  int x2 = c->x + dx, y2 = c->y + dy;
  c->x += dx + (x2 < 0 ? -x2 : x2 > COLS - 1 ? COLS - x2 - 1 : 0);
  c->y += dy + (y2 < 0 ? -y2 : y2 > LINES - 1 ? LINES - y2  - 1: 0);
}

// return if the position of a and b are equal.
bool samePosition(POSITION *a, POSITION *b) {
  return a->x == b->x && a->y == b->y;
}
// copy position from a to b.
void copyPosition(POSITION *a, POSITION *b) {
  b->x = a->x;
  b->y = a->y;
}

void initPlayerLocation(POSITION *player) {
  initPositionLocation(player, 5, 5, "@");
}
void initMonsterLocation(POSITION *monster) {
}
void initTreasureLocation(POSITION *treasure, int *numTreasure) {
  *numTreasure = 0;
  printPosition(treasure, " ");
}

void getPlayerLocation(POSITION *player, int key) {
  getPositionLocation(player, 
    key == KEY_LEFT ? -1 : key == KEY_RIGHT ? 1 : 0, 
    key == KEY_UP ? -1 : key == KEY_DOWN ? 1 : 0); 
}

void getMonsterLocation(POSITION *monster, POSITION *treasure, int *numTreasure) { 
  POSITION origin;
  copyPosition(monster, &origin);
  getPositionLocation(monster, rand() % 3 - 1, rand() % 3 - 1);

  // don't let the monster walk to the treasure
  if (*numTreasure == 1 && samePosition(monster, treasure)) {
    copyPosition(&origin, monster);
  }
  
  // create a treasure
  int dice = rand() % 10;
  if (dice == 0 && *numTreasure == 0 && !samePosition(monster, &origin)) {
    *numTreasure = 1;
    copyPosition(&origin, treasure);
    printPosition(treasure, "T");
  }
}

int getTreasure(POSITION *player, POSITION* treasure, int *numTreasure) {
  int same = samePosition(player, treasure);
  if (*numTreasure && same) {
    printPosition(treasure, " ");
    *numTreasure--;
  }
  return same;
}

int encounter(POSITION *player, POSITION *monster) {
  return samePosition(player, monster);
}

char gameOver() {
  POSITION c = {COLS / 2 - 4, LINES / 2 - 4};
  printPosition(&c, "GAME OVER");
  refresh();
  sleep(2);
  return 'q';
}



