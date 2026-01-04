#!/bin/bash
# Script to run MediTrack application

# Compile the project
echo "Compiling MediTrack..."
mvn clean compile

# Run the application with classpath
echo "Starting MediTrack..."
java -cp "target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" com.airtribe.meditrack.Main

