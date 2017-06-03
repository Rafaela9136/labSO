#include "xeu_utils/StreamParser.h"
#include "xeu_utils/Command.h"
#include "xeu_utils/ParsingState.h"

#include <sstream>
#include <stdexcept>
#include <string>
#include <vector>

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
#define READ  0
#define WRITE 1

using namespace xeu_utils;
using namespace std;

int command(const char* args, char* const*  params, int input, int first, int last);
static void cleanup(int n);

int main()
{
	int numCmds;
	const char* arg;

	
	while(true) {

		//User input
		printf("%s=> ", getenv("USER"));
		ParsingState p = StreamParser().parse();
		vector<Command> commands = p.commands();	

		numCmds = commands.size();
		int input = 0;
		int first = 1;

		if(numCmds > 0) {
			for(int i = 0; i < numCmds-1; i++) {
				arg = commands.at(i).filename();

				if (strcmp(arg, "exit") == 0) {
          			return 0;
        		}

				input = command(arg, commands.at(i).argv(), input, first, 0);

				first = 0;
			}

			command(commands.at(numCmds-1).filename(), commands.at(numCmds-1).argv(), input, first, 1);
		}	

		cleanup(numCmds);
		numCmds = 0;
	}			
	return 0;
}

int command(const char* command, char* const* args, int input, int first, int last) 
{
	int pp[2];
	pipe(pp);

	//Fork
	pid_t pid = fork();

	if(pid == -1) {
		perror("Fork failed!");
		exit(EXIT_FAILURE);
	}

	if(pid == 0) {
		if(first == 1 && last == 0 && input == 0) {
			dup2(pp[WRITE], STDOUT_FILENO);
		} else if (first == 0 && last == 0 && input != 0) {
			dup2(input, STDIN_FILENO);
			dup2(pp[WRITE], STDOUT_FILENO);
		} else {
			dup2(input, STDIN_FILENO);
		}

		if(execvp(command, args) == -1) {
			perror("Exec failed!");
			exit(EXIT_FAILURE);
		}
	}

	if(input != 0) {
		close(input);
	}

	close(pp[WRITE]);

	if(last == 1) {
		close(pp[READ]);
	}

	return pp[READ];
}

static void cleanup(int n)
{
	int i;
	for (i = 0; i < n; ++i) 
		wait(NULL); 
}
