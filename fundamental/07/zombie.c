#include <ncurses.h>
#include <time.h> // for the time()
#include <stdlib.h>
#include <math.h>
#include "redraw2.h"

int caughtByZombie(POSITION *player, POSITION *zombie, int zombieNum) {
  for(int i = 0; i < zombieNum; i++) {
    if (samePosition(player, &zombie[i])) {
      return 1;
    }
  }
  return 0;
}

void initZombie(POSITION *zombie, int zombieNum) {
  for(int i = 0; i < zombieNum; i++) {
    initPositionLocation(&zombie[i], rand() % 20 - 10 + COLS / 2, rand() % 20 - 10 + LINES / 2, "Z");
  }
}

POSITION distance(POSITION *a, POSITION *b, int speed) {
  POSITION d = {a->x - b->x, a->y - b->y};
  double sq = sqrt(d.x * d.x + d.y * d.y);
  d.x = speed * d.x / sq;
  d.y = speed * d.y / sq;
  return d;
} 

void getZombieLocation(POSITION *zombie, int i, POSITION *player, POSITION *treasure, int *numTreasure) { 
  POSITION d = distance(player, &zombie[i], rand() % 2 + 1);  
  getPositionLocation(&zombie[i], d.x, d.y);
  if (samePosition(&zombie[i], treasure)) {
    *numTreasure = 0;
  }
}

int getHighScore(int highScore, int score) {
  return score > highScore ? score : highScore;
}

int loadHighScore() {
  FILE *fp;
  fp=fopen("highscore.txt", "r");
  if(fp==NULL){
    return 0;
  }
  int score = 0;
  fscanf(fp, "%d", &score);
  fclose(fp);
  return score;
}

void saveHighScore(int score) {
  FILE *fpw;
  fpw=fopen("highScore.txt", "w");
  if(fpw==NULL){
    return;
  }
  fprintf(fpw, "%d", score);
  fclose(fpw);
}

void highScoreDisp(int highScore) {
  mvprintw(1, COLS / 2 + 2, "HI SCORE : %d", highScore); 
}

