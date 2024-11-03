create table movimentos(
  id bigserial not null,
  descricao varchar(100) not null,
  valor numeric(15,6) not null,
  tipo char(1) not null check (tipo in ('E', 'S')),
  user_id bigint not null,
  conta_id bigint not null,
  data_efetivacao date not null,
  criado_em timestamp default now(),
  constraint pk_movimentos primary key(id),
  constraint fk_movimento_user foreign key (user_id) references users (id),
  constraint fk_movimento_conta foreign key (conta_id) references contas (id)
);