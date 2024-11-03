create table users(
  id bigserial not null,
  email varchar(100) not null,
  password varchar(100) not null,
  user_role varchar(100) not null,
  constraint pk_users primary key(id),
  constraint ct_email unique(email)
);