alter table public.feed_links add url_hash text;
alter table public.feed_links add links jsonb;

alter table public.feed_links add constraint uhash unique (url_hash);