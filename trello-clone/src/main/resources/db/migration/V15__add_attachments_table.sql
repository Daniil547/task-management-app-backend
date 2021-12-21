create table attachments
(
    name      varchar(40),
    type      varchar(20),
    file_path varchar(300),

    card_id   uuid references cards (id) not null,
    position  smallint                   not null,
    primary key (card_id, position)
)