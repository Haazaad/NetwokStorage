CREATE SEQUENCE user_auc_auc_id_seq;

CREATE TABLE IF NOT EXISTS public."user_auc"
(
    auc_id integer NOT NULL DEFAULT nextval('"user_auc_auc_id_seq"'::regclass),
    user_id integer NOT NULL,
    password character varying(33) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_auc_id PRIMARY KEY (auc_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public."user_auc"
    OWNER to postgres;

INSERT INTO "user_auc" (user_id, password) VALUES (1, 'b59c67bf196a4758191e42f76670ceba');