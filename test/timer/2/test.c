#include <stdio.h>
#include <signal.h>
#include <sys/select.h>
#include <stdlib.h>
#include <linux/unistd.h>
#include <sys/syscall.h>

void onKill(int);

int i;

int main() {
    struct timeval t;
    i = 0;

    signal(SIGINT, onKill); 
    // não uso sigaction porque só vou tratar o sinal uma vez por processo.

    while (1) {
        t.tv_sec = 3; // tempo em segundos
        t.tv_usec = 0;
        select(0, NULL, NULL, NULL, &t);

        { // o que acontece de três em três segundos.
            ++i;
            printf("printk nao funcionou.\n");
            syscall(351);
        }
    }
    return 0;
}

void onKill(int sig) {
	printf("Processo interrompido, %d helloworlds\n", i);
	exit(0);
}
