#include <stdio.h>
#include <sys/select.h>

int main() {
    struct timeval t;

    while (1) {
        t.tv_sec = 3; // tempo em segundos
        t.tv_usec = 0;
        select(0, NULL, NULL, NULL, &t);
        printf("printk nao funcionou.\n");  
        syscall(351);
    }
    return 0;
}
