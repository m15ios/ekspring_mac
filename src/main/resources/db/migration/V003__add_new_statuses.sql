alter table public.feed_links
    drop constraint ckc;

alter table public.feed_links
    add constraint ckc
        check (state in (
                         'CREATED',
                         'INLINE',
                         'FAILED',
                         'SAVED',
                         'IN_PARSE',
                         'PARSED',
                         'FAILED',
                         'FINISH',
                         'WAITING'));