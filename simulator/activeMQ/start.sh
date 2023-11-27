echo "Esecuzione di tshark..."
rm ./tshark/tshark.pcap
tshark -i any -f "port 61616" -w ./tshark/tshark.pcap &
echo "Esecuzione di ActiveMQ..."
./activemq console




