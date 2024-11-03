create table movimentos_tags(
  movimento_id bigint not null,
  tag_id bigint not null,
  constraint pk_movimentos_tags primary key(movimento_id,tag_id),
  constraint fk_movtag_movimento foreign key (movimento_id) references movimentos (id),
  constraint fk_movtag_tag foreign key (tag_id) references tags (id)
);