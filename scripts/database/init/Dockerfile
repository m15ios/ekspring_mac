FROM postgres:16

RUN apt-get update && apt-get -y install pgagent
COPY init-database.sh /docker-entrypoint-initdb.d/init-database.sh

EXPOSE 5432