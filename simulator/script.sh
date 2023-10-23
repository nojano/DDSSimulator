#!/bin/bash

docker build ./coordinator -t coordinator

echo "Please enter how many processes you want to instantiate"
read N
echo "Please enter how many rounds the algorithm must execute"
read R
R=$((R - 1))

echo "Please enter how many Byzantine processes you want"
read B
echo "The Byzantine processes are chosen randomly." 
echo" Please enter 1 if you want the Byzantine process to send no message;"
echo" Please enter 2 if the Byzantine process must send 1;"
echo "Please enter 3 if you want to choose the decisions of the correct processes and the Byzantine process makes its decision based on what is the majority in the decisions;"
echo "Please enter 4 if you want to choose the decisions of the correct processes and the Byzantine process makes its decision based on what is the majority in the decisions; "
echo "Please enter 5 for the worst case;"
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
elif [ "$C" -eq 2 ]; then
    docker build ./processMsg1 -t processmsg1

    for ((i = 0; i < N; i++))
    do
        docker run -d --network host processmsg1 $N $B $R $C $i
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
elif [ "$C" -eq 3 ]; then
    docker build ./processUpToYou -t processuptoyou

    for ((i = 0; i < N; i++))
    do
    	echo "Please enter the initialization value of $i"
	read V
        docker run -d --network host processuptoyou $N $B $R $C $i $V
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
elif [ "$C" -eq 4 ]; then
    docker build ./processUpToYou2 -t processuptoyou2

    for ((i = 0; i < N; i++))
    do
    	echo "Please enter the initialization value of $i"
	read V
        docker run -d --network host processuptoyou2 $N $B $R $C $i $V
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
elif [ "$C" -eq 5 ]; then
    echo "In order to reach the worst case, you have to initialize the even process with 1 and the odd ones with 0"
    docker build ./worstCase -t worstcase

    for ((i = 0; i < N; i++))
    do
    	echo "Please enter the value for $i"
	read V
        docker run -d --network host worstcase $N $B $R $C $i $V
       
        echo "I have done with process number $i"
        # Add -d to the command above to not show the container running
    done
    
fi



