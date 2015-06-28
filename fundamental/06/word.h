/*
 * word.h
 *
 *  Created on: 2013/03/31
 *      Author: masaomi
 */

#ifndef WORD_H_
#define WORD_H_
#include <stdio.h>

typedef unsigned short WORD;
typedef unsigned long DWORD;
typedef unsigned char BYTE;

void fwriteWORD(WORD w, FILE *fp);
void fwriteDWORD(DWORD dw, FILE *fp);

#endif /* WORD_H_ */
