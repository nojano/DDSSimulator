#!/bin/bash

docker build ./coordinator -t coordinator

echo "Please enter 1 for the Garay algorithm, enter 2 for the Bonnet algorithm, enter 3 for the Sasaki algorithm"
read A
case $A in 
	
	
	
	1)
	cd Garay
	    
	echo "Please enter how many processes you want to instantiate"
	read N
	echo "Please enter how many rounds the algorithm must execute"
	read R
	R=$((R - 1))

	echo "Please enter how many Byzantine processes you want"
	read B
	echo "Please enter 1 if you want the Byzantine process to send no message;"
	echo "Please enter 2 if the Byzantine process must send 1;"
	echo "Please enter 3 if you want to choose the decisions of the correct processes and the Byzantine process makes its decision based on what is the majority in the decisions;"
	echo "Please enter 4 if you want to choose the decisions of the correct processes and the Byzantine process makes its decision based on what is the majority in the decisions; "
	echo "Please enter 5 for the worst case;"
	D=1
	read C
	docker run -d --network host coordinator $N $B $R $D

	if [ "$C" -eq 1 ]; then
	    docker build ./processNoMsg -t garayprocessnomsg

	    for ((i = 0; i < N; i++))
	    do
	    	echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host garayprocessnomsg $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 2 ]; then
	    docker build ./processMsg1 -t garayprocessmsg1

	    for ((i = 0; i < N; i++))
	    do
		docker run -d --network host processmsg1 $N $B $R $C $i
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 3 ]; then
	    docker build ./processUpToYou -t garayprocessuptoyou

	    for ((i = 0; i < N; i++))
	    do
	    	echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host garayprocessuptoyou $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 4 ]; then
	    docker build ./processUpToYou2 -t garayprocessuptoyou2

	    for ((i = 0; i < N; i++))
	    do
	    	echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host garayprocessuptoyou2 $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 5 ]; then
	    docker build ./worstCase -t garayworstcase

	    for ((i = 0; i < N; i++))
	    do
	    	echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host garayworstcase $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 6 ]; then
	    docker build ./try -t try

	    for ((i = 0; i < N; i++))
	    do
	    	echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host try $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done    
	    elif [ "$C" -eq 7 ]; then
	    docker build ./processNoMsgTry -t processnomsgtry

	    for ((i = 0; i < N; i++))
	    do
		docker run -d --network host processnomsgtry $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	fi

	;;
	    
    
    
    
    
    
    
	2)
	cd Bonnet
	    
	echo "Please enter how many processes you want to instantiate"
	read N
	echo "Please enter how many rounds the algorithm must execute"
	read R
	R=$((R - 1))

	echo "Please enter how many Byzantine processes you want"
	read B
	echo " Please enter 1 if you want the Byzantine process to send no message;"
	echo " Please enter 2 if you want the worst case;"
	D=1
	read C
	docker run -d --network host coordinator $N $B $R $D
	
	if [ "$C" -eq 1 ]; then
	    docker build ./processNoMsg -t bonnetprocessnomsg

	    for ((i = 0; i < N; i++))
	    do
		echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host bonnetprocessnomsg $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 2 ]; then
	    docker build ./worstCase -t bonnetworstcase

	    for ((i = 0; i < N; i++))
	    do
		echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host bonnetworstcase $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	fi
	
	  ;;
	  
	
	3)
	cd Sasaki
	
	echo "Please enter how many processes you want to instantiate"
	read N
	echo "Please enter how many rounds the algorithm must execute"
	read R
	R=$((R - 1))

	echo "Please enter how many Byzantine processes you want"
	read B
	echo " Please enter 1 if you want the Byzantine process to send no message;"
	echo " Please enter 2 if you want the worst case;"
	D=1
	read C
	docker run -d --network host coordinator $N $B $R $D   
	if [ "$C" -eq 1 ]; then
	    docker build ./processNoMsg -t sasakiprocessnomsg

	    for ((i = 0; i < N; i++))
	    do
		echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host sasakiprocessnomsg $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	elif [ "$C" -eq 2 ]; then
	    docker build ./worstCase -t sasakiworstcase

	    for ((i = 0; i < N; i++))
	    do
		echo "Please enter the initialization value of $i"
		read V
		docker run -d --network host sasakiworstcase $N $B $R $C $i $V
		echo "I have done with process number $i"
		# Add -d to the command above to not show the container running
	    done
	fi
	
	  ;;
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    *)
	    echo "Not valid input, please insert only 1 or 2."
	    exit 1
	    ;;
	esac
    




