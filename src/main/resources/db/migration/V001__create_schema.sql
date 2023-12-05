-- queue execute by versions V001 V002
-- Vxx Uxx Rxx - diff types

select ( 345 + 5423 );
select ( 345 + 5424 );
select ( 345 + 5425 );
select ( 345 + 5426 );
select ( 345 + 5427 );
insert into feed_links ( id, url_source, create_date, state, duration_date, count_attempts ) values ( gen_random_uuid(), '33https://iziclick.ru/marketplace/93526.xml', now(), 'CREATED', now(), 0 );
