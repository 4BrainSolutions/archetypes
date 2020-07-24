#!/bin/bash

echo "Install archetype to local repository..."
mvn clean install

echo "Enter project name:"
read -r projectName

echo "Generate project..."
cd ../../
mvn archetype:generate -B -DarchetypeGroupId=com.fourbrainsolutions.archetypes -DarchetypeArtifactId=simple-app -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.fourbrainsolutions.app\
  -DartifactId="$projectName" -Dversion=0.1-SNAPSHOT

echo "Build project"
cd "$projectName"
mvn clean package

echo "Build project $projectName successful!"
open .