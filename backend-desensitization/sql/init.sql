-- pgsql：10.21-alpine

先手动创建个pgsql数据库：如LYDB

-- DROP SCHEMA desensitive;

CREATE SCHEMA desensitive AUTHORIZATION postgres;

-- DROP TABLE desensitive."user";

CREATE TABLE desensitive."user" (
                                    id int8 NOT NULL,
                                    usename varchar NULL,
                                    "password" varchar NULL,
                                    realname varchar NULL,
                                    gender int2 NULL,
                                    phone varchar NULL,
                                    id_card varchar NULL,
                                    bank_card varchar NULL,
                                    CONSTRAINT user_pk PRIMARY KEY (id)
);


INSERT INTO desensitive."user"
(id, usename, "password", realname, gender, phone, id_card, bank_card)
VALUES(1, 'lyflexi', 'jgfdhadsga45t2gdf', '刘岩', 0, '13700000000', '411381000000000000', '637900000000000000000');