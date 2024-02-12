#!/bin/bash
./gradlew clean
./gradlew build
#cp ./Dockerfile .
#cp ./docker-compose.yaml ./dev/deploy
cp ./build/libs/employee-crud-backend-0.0.1-SNAPSHOT.jar ./dev/deploy
cp ./build/libs/employee-crud-backend-0.0.1-SNAPSHOT-plain.jar ./dev/deploy
#docker-compose -f docker-compose.yaml down --rmi all
docker-compose -f docker-compose.yaml down
docker rmi $(docker images 'empcrud-backend' -a -q)
docker-compose -f docker-compose.yaml up -d
docker logs -f $(docker ps -a | grep "empcrud-backend" | cut -d " " -f 1)