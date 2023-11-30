#!/bin/bash
set -e

# code template
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

  create table ek_tests
  (
      id          uuid      not null
          constraint pk_ek_tests
              primary key,
      description text,
      create_date timestamp not null
  );



EOSQL