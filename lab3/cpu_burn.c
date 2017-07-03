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
	    pow(1.2, 2.3);
	    num++;
    }

    gettimeofday(&end, NULL);

    uint64_t sTimestamp = (start.tv_sec*1000000L) + start.tv_usec;
    uint64_t eTimestamp = (end.tv_sec*1000000L) + end.tv_usec;


    printf("%llu %llu %d \n", sTimestamp, eTimestamp, num);
    printf("Time in seconds: %llu microsseconds\n", eTimestamp - sTimestamp); 

    return 0;
}
