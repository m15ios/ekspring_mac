#!/bin/bash
docker-compose -f docker-compose-infra.yml down #--rmi all || true
docker-compose down --rmi all || true

docker-compose -f docker-compose-infra.yml up -d
sleep 50

#docker-compose up -d (as demon)
docker-compose up