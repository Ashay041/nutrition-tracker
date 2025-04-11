#!/bin/bash

# Build the WAR file
mvn clean package

#remove old docker image
docker rim nutrition-tracker

# Copy the WAR file to the root directory and rename it to ROOT.war
cp target/ROOT.war docker/ROOT.war

# Build the Docker image
docker build -t nutrition-tracker docker

# Run the Docker container
echo "To run the Docker container, use:"
echo "docker run --rm -it -p 8080:8080 nutrition-tracker"
