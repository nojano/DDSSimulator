#################################################################

 docker build ./process -t process
 docker build ./coordinator -t coordinator

#################################################################


echo "Please digit how many processes you want to instantiate"
read N;
echo "please digit how many rounds the algorithm must execute"
read R;
R=$((R - 1));

echo "Please digit how many byzantine per round do you want"
read B;
echo "Please digit 1 for Byzantines that send no message and for each round they are chosen randomly"
read C;
docker run -d --network host coordinator $N $B $R $C
for (( i = 0; i < $N; i++ ))
do
     docker run -d --network host process $N $B $R $C $i
    echo "I have done with the process number $i"
    #add -d to the command above to not show the consumer running
    done
    


