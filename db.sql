--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

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
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: measurement_types; Type: TABLE; Schema: public; Owner: healthapp
--

CREATE TABLE public.measurement_types (
    measurement_type character varying(255) NOT NULL,
    unit character varying(255)
);


ALTER TABLE public.measurement_types OWNER TO healthapp;

--
-- Name: observations; Type: TABLE; Schema: public; Owner: healthapp
--

CREATE TABLE public.observations (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    measurement_type character varying(255),
    date date,
    patient integer,
    value double precision
);


ALTER TABLE public.observations OWNER TO healthapp;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: healthapp
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    role_name character varying(255)
);


ALTER TABLE public.roles OWNER TO healthapp;

--
-- Name: roles_seq; Type: SEQUENCE; Schema: public; Owner: healthapp
--

CREATE SEQUENCE public.roles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_seq OWNER TO healthapp;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: healthapp
--

CREATE TABLE public.user_role (
    username character varying(255) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.user_role OWNER TO healthapp;

--
-- Name: users; Type: TABLE; Schema: public; Owner: healthapp
--

CREATE TABLE public.users (
    username character varying(255) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255)
);


ALTER TABLE public.users OWNER TO healthapp;

--
-- Data for Name: measurement_types; Type: TABLE DATA; Schema: public; Owner: healthapp
--

COPY public.measurement_types (measurement_type, unit) FROM stdin;
heart-rate	beats/minute
skin-temperature	degrees Celcius
respiratory-rate	breaths/minute
\.


--
-- Data for Name: observations; Type: TABLE DATA; Schema: public; Owner: healthapp
--

COPY public.observations (id, measurement_type, date, patient, value) FROM stdin;
1b8b0797-f88c-4e6b-ada3-271d7c99f2eb	heart-rate	2023-09-06	101	65
035cd8c1-926c-49c3-bffc-228aff68a0c0	skin-temperature	2023-09-07	101	37.2
2a4722d4-b2b8-4274-b1f7-fcf859de1f5e	respiratory-rate	2023-09-06	101	15
f7c97079-bcac-4b7a-9f66-f70e47baff3e	heart-rate	2023-09-04	102	76
029a8298-6807-415c-93ee-32f6aa7566d8	heart-rate	2023-09-04	102	76
42e8c365-dcb2-46c9-9c4b-e2a23507713f	respiratory-rate	2023-09-04	102	18
27007646-0a1a-4052-a1e5-807a6f96b20a	skin-temperature	2023-09-05	103	37.8
a94d682f-e537-4d87-829f-c6d2af2ca0fc	skin-temperature	2023-09-05	103	37.8
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: healthapp
--

COPY public.roles (id, role_name) FROM stdin;
52	ADMIN
53	USER
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: healthapp
--

COPY public.user_role (username, id) FROM stdin;
admin	52
admin	53
dasunp	52
pretzel	52
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: healthapp
--

COPY public.users (username, first_name, last_name, password) FROM stdin;
admin	Admin	Admin	$2a$12$tPmqTN1l6EZls.2rFVxn5uaVZpY2jRlk8murkO1M4QDS6G7DzYrym
dasunp	Dasun	Pubudumal	$2a$12$u6n4o.vex31C2zTiXfVtceWHw3Pb/KrZw3emLWpzlEm3z7q2IduLC
pretzel	Pretzel	Retriever	$2a$12$TcUxCFWtnSOUoh/p42hGT.shL7DZ2kzroia6Ai5fZpNa.U86xDyiq
\.


--
-- Name: roles_seq; Type: SEQUENCE SET; Schema: public; Owner: healthapp
--

SELECT pg_catalog.setval('public.roles_seq', 101, true);


--
-- Name: measurement_types measurement_types_pkey; Type: CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.measurement_types
    ADD CONSTRAINT measurement_types_pkey PRIMARY KEY (measurement_type);


--
-- Name: observations observations_pkey; Type: CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.observations
    ADD CONSTRAINT observations_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (username, id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: user_role fk2svos04wv92op6gs17m9omli1; Type: FK CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk2svos04wv92op6gs17m9omli1 FOREIGN KEY (username) REFERENCES public.users(username);


--
-- Name: user_role fkhcg56a29q6so6e55f7gt8dflh; Type: FK CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkhcg56a29q6so6e55f7gt8dflh FOREIGN KEY (id) REFERENCES public.roles(id);


--
-- Name: observations observations_measurement_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: healthapp
--

ALTER TABLE ONLY public.observations
    ADD CONSTRAINT observations_measurement_type_fkey FOREIGN KEY (measurement_type) REFERENCES public.measurement_types(measurement_type);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO healthapp;


--
-- PostgreSQL database dump complete
--

