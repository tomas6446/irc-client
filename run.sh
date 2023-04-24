#!/bin/bash

# Check if the required number of arguments is provided
if [ "$#" -ne 2 ]; then
    echo "Usage: ./run.sh <server> <port>"
    exit 1
fi

# Run the IRC client using Maven
mvn compile exec:java -Dexec.args="$1 $2"
