#!/usr/bin/env bash

docker-compose stop

docker ps -a | xargs docker kill

docker ps -aq | xargs docker rm
