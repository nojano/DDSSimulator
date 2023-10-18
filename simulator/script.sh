#!/bin/bash

docker build ./coordinator -t coordinator

echo "Please enter how many processes you want to instantiate"
read N
echo "Please enter how many rounds the algorithm must execute"
read R
R=$((R - 1))

echo "Please enter how many Byzantine processes you want"
read B
echo "The Byzantine processes are chosen randomly. Please enter 1 if you want the Byzantine process to send no message; enter 2 if the Byzantine process must send 1."
D=1
read C
docker run -d --network host coordinator $N $B $R $D

if [ "$C" -eq 1 ]; then
    docker build ./processNoMsg -t processnomsg

    for ((i = 0; i < N; i++))
    do
        docker run -d --network host processnomsg $N $B $R $C $i
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
else
    docker build ./processMsg1 -t processmsg1

    for ((i = 0; i < N; i++))
    do
        docker run -d --network host processmsg1 $N $B $R $C $i
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
fi



