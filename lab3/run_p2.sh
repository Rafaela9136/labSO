#!/bin/bash

i=0
while (( i++ < $1 )); do 
	./cpu_burn &
done
