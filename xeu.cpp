#include "xeu_utils/StreamParser.h"

#include <iostream>
#include <vector>
#include <stdio.h> /* printf, scanf, NULL */
#include <stdlib.h> /* malloc, free, rand */
#include <string.h>

#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <sys/wait.h>
#define gettid() syscall(SYS_gettid)

using namespace xeu_utils;
using namespace std;

int main()
{
	int fd[2];
	pid_t pid1, pid2;

	while(true) {

		//User input
		printf("%s=> ", getenv("USER"));
		ParsingState p = StreamParser().parse();
		vector<Command> commands = p.commands();

		const char* filename = commands.at(0).filename();	
		char* const* args = commands.at(0).argv();
		char* const* args2 = commands.at(1).argv();

		if(strcmp(filename, "exit") == 0) {
			break;
		}

		//Pipe
		pipe(fd);

		//First child
		pid1 = fork();

		if (pid1 < 0) {
			perror("First fork() failed!");
			return -1;
		}

		if (pid1 == 0) {
			// Set the process output to the input of the pipe
			close(1);
			dup(fd[1]);
			close(fd[0]);
			close(fd[1]);
	
			execvp(args[0],commands.at(0).argv());
			perror("First execvp() failed");
			return -1;
		}

		//Second child
		pid2 = fork();

		if (pid2 < 0) {
			perror("Second fork() failed!");
			return -1;
		}

		if (pid2 == 0) {
			// Set the process input to the output of the pipe
			close(0);
			dup(fd[0]);
			close(fd[0]);
			close(fd[1]);

			execvp(args2[0], commands.at(1).argv());
			perror("Second execvp() failed");
			return -1;
		}

		close(fd[0]);
		close(fd[1]);

		// Wait for the children to finish, then exit
		waitpid(pid1,NULL,0);
		waitpid(pid2,NULL,0);
	}

	return 0;
}
