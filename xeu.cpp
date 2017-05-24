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
	
	while(true) {
		pid_t pid, t_pid;
		int child_status, exec_status;

		printf("%s=> ", getenv("USER"));
		ParsingState p = StreamParser().parse();

		std::vector<Command> commands = p.commands();
		const char* filename = commands.front().filename();
		char* const* argv = commands.front().argv();

		if(strcmp(filename, "exit") == 0) {
			break;
		}

		pid = fork();

		if(pid == -1) {
			fprintf(stderr, "Fork failed!\n");
		}
		
		if(pid == 0) {
			exec_status = execvp(filename, argv);

			if(exec_status == -1) {
				fprintf(stderr, "Something went wrong!\n");
				exit(EXIT_FAILURE);
			}
		} else {
			t_pid = waitpid(pid, &child_status, 0);
			
			if(t_pid == -1) {
				fprintf(stderr, "Waitpid!\n");
				exit(EXIT_FAILURE);
			}
		}
	}

	return 0;
}
