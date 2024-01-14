CREATE SEQUENCE rules_id_seq;

CREATE TABLE rules (   id INTEGER DEFAULT nextval('rules_id_seq') PRIMARY KEY,
                       url VARCHAR(255) UNIQUE NOT NULL,
                       data TEXT,
                       state BOOLEAN );
