#ifndef ZOMBIE_H_
#define ZOMBIE_H_
#include "redraw2.h"
#define MAX_ZOMBIE 50

int caughtByZombie(POSITION *player, POSITION *zombie, int zombieNum);
void initZombie(POSITION *zombie, int zombieNum);
POSITION distance(POSITION *a, POSITION *b, int speed);
void getZombieLocation(POSITION *zombie, int i, POSITION *player, POSITION *treasure, int *numTreasure);
int getHighScore(int highScore, int score);
int loadHighScore();
void saveHighScore(int score);
void highScoreDisp(int highScore);

#endif
