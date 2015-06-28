#include "imgutil.h"

typedef unsigned short WORD;
typedef unsigned long DWORD;
typedef unsigned char BYTE;

void fwriteWORD(WORD w, FILE *fp) {
  fputc(w & 0xFF, fp);
  fputc(w >> 8, fp);
}

void fwriteDWORD(DWORD dw, FILE *fp) {
  fwriteWORD(dw & 0xFFFF, fp);
  fwriteWORD(dw >> 16, fp);
}

