#include <stdio.h> /* printf, scanf, NULL */
#include <stdlib.h> /* malloc, free, rand */
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