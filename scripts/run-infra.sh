#!/usr/bin/env bash

export DOCKER_MACHINE=$(docker-machine ip $(docker-machine active))
export SCHEMA_REGISTRY_PORT=8081
export KAFKA_PORT=9092
export ZOOKEEPER_PORT=2181

echo "Docker machine ip is: "
echo $DOCKER_MACHINE

export BOOTSTRAP_SERVER=$DOCKER_MACHINE:$KAFKA_PORT
echo "Bootstrap Url: "
echo $BOOTSTRAP_SERVER


export SCHEMA_REGISTRY_URL=http://$DOCKER_MACHINE:$SCHEMA_REGISTRY_PORT
echo "Schema registry url: "
echo $SCHEMA_REGISTRY_URL


echo "starting zookeeper"
docker run -d --name zookeeper -p $ZOOKEEPER_PORT:$ZOOKEEPER_PORT confluent/zookeeper

sleep 10

docker run -d --name kafka -p $KAFKA_PORT:$KAFKA_PORT --link zookeeper:zookeeper confluent/kafka

sleep 20


#start docker services
docker-compose -f ../docker/docker-compose-infra.yml up