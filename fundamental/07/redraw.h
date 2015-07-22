#ifndef REDRAW_H_
#define REDRAW_H_

struct position{
 int x, y;
};

typedef struct position POSITION;

void redraw();
void getMonsterLocation(POSITION *monster);

#endif /* REDRAW_H_ */

