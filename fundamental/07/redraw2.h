#ifndef REDRAW2_H_
#define REDRAW2_H_

#define MAX_COUNT 100

struct position{
	int x;
	int y;
};
typedef struct position POSITION;

char gameOver();
int getTreasure(POSITION *player, POSITION *treasure, int *numTreasure);
int encounter(POSITION *player, POSITION *monster);
void initPlayerLocation(POSITION *player);
void initMonsterLocation(POSITION *monster);
void initTreasureLocation(POSITION *treasure, int *numTreasure);
void getPlayerLocation(POSITION *player, int ch);
void getMonsterLocation(POSITION *monster, POSITION *treasure, int *numTreasure);

#endif /* REDRAW2_H_ */
