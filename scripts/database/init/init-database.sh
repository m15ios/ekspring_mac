#!/bin/bash
set -e

# code template
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

CREATE TABLE IF NOT EXISTS public.feed_links
(
    id    uuid not null
          constraint pk_ek_tests primary key,
    url_source text,
    create_date timestamp           not null,
    state text                      not null,
    duration_date timestamp         null,
    count_attempts smallint default 0 not null,
    last_date_attempt timestamp     null,

    constraint ckc
        check (state in (
                'CREATED',
                'INLINE',
                'FAILED'
            )
        )
);


EOSQL