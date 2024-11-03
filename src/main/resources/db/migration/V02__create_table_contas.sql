create table contas(
  id bigserial not null,
  nome varchar(100) not null,
  saldo numeric(15,6) not null,
  user_id bigint not null,
  criado_em timestamp default now(),
  constraint pk_contas primary key(id),
  constraint fk_conta_user foreign key (user_id) references users (id)
);