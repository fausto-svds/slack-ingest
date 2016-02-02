#!/usr/bin/env bash

echo "starting application"

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


export SLACK_API='https://slack.com/api/rtm.start?token={token}&pretty=1'
echo "slack api: "
echo $SLACK_API


docker-compose up