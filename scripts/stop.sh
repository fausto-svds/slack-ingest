#!/usr/bin/env bash

docker-compose stop


#all slack
docker ps | grep slack_slack | cut -d ' ' -f 1  | xargs docker kill