#include "imgutil.h"

typedef unsigned short WORD;
typedef unsigned long DWORD;
typedef unsigned char BYTE;

void fwriteWORD(WORD w, FILE *fp) {
  fputc(w & 0xFF, fp); // last 8 bits
  fputc(w >> 8, fp); // last 8 bits, after the ones above
}

void fwriteDWORD(DWORD dw, FILE *fp) {
  fwriteWORD(dw & 0xFFFF, fp); // last 16 bits
  fwriteWORD(dw >> 16, fp); // last 16 bits, after the ones above
}
