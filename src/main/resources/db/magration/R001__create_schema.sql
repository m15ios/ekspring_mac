-- queue execute by versions V001 V002
-- Vxx Uxx Rxx - diff types

insert into feed_links ( id, url_source, create_date, state, duration_date, count_attempts ) values ( gen_random_uuid(), 'https://izi.ru/marketplace/93526.xml', now(), 'CREATED', now(), 0 );
