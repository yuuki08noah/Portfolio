CREATE TYPE roles AS enum ('admin', 'user', 'anonymous');

CREATE TABLE "user" (
  id varchar(500) NOT NULL PRIMARY KEY,
  name varchar(500) NOT NULL,
  password varchar(500) NOT NULL,
  email varchar(500) NOT NULL,
  phone varchar(500),
  profile varchar(500),
  role roles NOT NULL,
  bio varchar(500),
  lang varchar(500) NOT NULL,
  created_at timestamp with time zone NOT NULL,
  email_request boolean NOT NULL
);

CREATE INDEX index_1 ON "user" (id);
