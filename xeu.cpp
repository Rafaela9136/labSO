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

int main() {
	
	bool exitXeu;
	
	while(!exitXeu) {
		pid_t pid, t_pid;
		int child_status;

		printf("%s=> ", getenv("USER"));
		ParsingState p = StreamParser().parse();

		std::vector<Command> commands = p.commands();
		const char* filename = commands.front().filename();
		char* const* argv = commands.front().argv();

		if(strcmp(filename, "exit") == 0) {
			exitXeu = true;
		}

		pid = fork();

		if(pid == -1) {
			fprintf(stderr, "Fork failed!");
		}
		
		if(pid == 0) {
			execvp(filename, argv);
		} else {
			do {
				t_pid = waitpid(pid, &child_status, WUNTRACED | WCONTINUED);
				if(t_pid == -1) {
					perror("waitpid");
					exit(EXIT_FAILURE);
				}	
			} while(t_pid != pid);
		}
	}

	return 0;
}
