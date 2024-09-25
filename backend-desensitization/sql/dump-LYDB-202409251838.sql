--
-- PostgreSQL database dump
--

-- Dumped from database version 10.21
-- Dumped by pg_dump version 15.3

-- Started on 2024-09-25 18:38:37

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "LYDB";
--
-- TOC entry 2963 (class 1262 OID 16384)
-- Name: LYDB; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE "LYDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


\connect "LYDB"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 10 (class 2615 OID 16417)
-- Name: desensitive; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA desensitive;


SET default_tablespace = '';

--
-- TOC entry 202 (class 1259 OID 16418)
-- Name: user; Type: TABLE; Schema: desensitive; Owner: -
--

CREATE TABLE desensitive."user" (
    id bigint NOT NULL,
    usename character varying,
    password character varying,
    realname character varying,
    gender smallint,
    phone character varying,
    id_card character varying,
    bank_card character varying
);


--
-- TOC entry 2964 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".id; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".id IS '主键id';


--
-- TOC entry 2965 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".usename; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".usename IS '用户名';


--
-- TOC entry 2966 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".password; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".password IS '用户密码';


--
-- TOC entry 2967 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".realname; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".realname IS '真实姓名';


--
-- TOC entry 2968 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".gender; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".gender IS '性别，0男1女';


--
-- TOC entry 2969 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".phone; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".phone IS '手机号';


--
-- TOC entry 2970 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".id_card; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".id_card IS '身份证号';


--
-- TOC entry 2971 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN "user".bank_card; Type: COMMENT; Schema: desensitive; Owner: -
--

COMMENT ON COLUMN desensitive."user".bank_card IS '银行卡号';


--
-- TOC entry 2957 (class 0 OID 16418)
-- Dependencies: 202
-- Data for Name: user; Type: TABLE DATA; Schema: desensitive; Owner: -
--

INSERT INTO desensitive."user" VALUES (1, 'lyflexi', 'jgfdhadsga45t2gdf', '刘岩', 0, '13700000000', '411381000000000000', '637900000000000000000');


--
-- TOC entry 2835 (class 2606 OID 16422)
-- Name: user user_pk; Type: CONSTRAINT; Schema: desensitive; Owner: -
--

ALTER TABLE ONLY desensitive."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


-- Completed on 2024-09-25 18:38:37

--
-- PostgreSQL database dump complete
--

