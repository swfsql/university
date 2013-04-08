#include <errno.h>
#include <sys/syscall.h>
#include <unistd.h>

_syscall0(int, helloworld);

main () {
	helloworld();
}
