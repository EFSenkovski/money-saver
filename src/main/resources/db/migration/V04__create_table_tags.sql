create table tags(
  id bigserial not null,
  descricao varchar(100) not null,
  user_id bigint not null,
  criado_em timestamp default now(),
  constraint pk_tags primary key(id),
  constraint fk_tag_user foreign key (user_id) references users (id)
);