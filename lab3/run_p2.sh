#!/bin/bash
if [ "$#" -eq 1 ]; then
N=$1
else
N=1
fi
for i in `seq 1 $N`; do
./cpu_burn &
arr[i]=$!
done
sleep 1s
wait ${arr[*]}