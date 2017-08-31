#include <math.h>
#include <stdio.h>
#include <sys/time.h>
#include <inttypes.h>

int main() {
	struct timeval start;
	struct timeval end;

	gettimeofday(&start, NULL);

	int num = 0; 
    while(num < 478000000) {
	    pow(1.2, 2.3); //O número elevado ao segundo número
	    num++;
    }

    gettimeofday(&end, NULL);

    uint64_t sTimestamp = (start.tv_sec*1000000L) + start.tv_usec;
    uint64_t eTimestamp = (end.tv_sec*1000000L) + end.tv_usec;


    printf("%lu %lu\n", sTimestamp, eTimestamp);

    return 0;
}
