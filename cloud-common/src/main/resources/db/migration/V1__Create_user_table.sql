CREATE SEQUENCE users_user_id_seq;

CREATE TABLE IF NOT EXISTS public."users"
(
    user_id integer NOT NULL DEFAULT nextval('"users_user_id_seq"'::regclass),
    login character varying(80) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (user_id)
)

TABLESPACE pg_default;

ALTER TABLE public."users" OWNER to postgres;

INSERT INTO "users" (login) VALUES('Test');