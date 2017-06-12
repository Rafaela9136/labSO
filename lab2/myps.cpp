#include <stdio.h> /* printf, scanf, NULL */
#include <stdlib.h> /* malloc, free, rand */
#include <dirent.h>
#include <ctype.h>
#include <vector>
using std::vector; 

vector<char*> getPids();

int main()
{
	vector<char*> pids = getPids();
    return (0);
}

vector<char*> getPids() {
	DIR *dir;
	struct dirent *dp;

	if((dir = opendir("/proc/")) == NULL) {
		perror("The directory cannot be open!");
		exit(1);
	}

	vector<char*> pids;
	char *pid;

	while((dp = readdir(dir)) != NULL) {
		pid = dp -> d_name;

		if(isdigit(*pid)) {
			pids.push_back(pid);
		}
	}

	return pids;
}

/*#include <stdio.h> /* printf, scanf, NULL 
#include <stdlib.h> /* malloc, free, rand 
#include <dirent.h>

int main()
{
	DIR *dir;
	struct dirent *dp;

	if((dir = opendir("./")) == NULL) {
		perror("The directory cannot be open!");
		exit(1);
	}

	while((dp = readdir(dir)) != NULL) {
		puts(dp -> d_name);
	}

    return (0);
}
*/
