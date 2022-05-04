create table attachments
(
    id        uuid primary key,
    name      varchar(40),
    type      varchar(20),
    file_path varchar(300),

    card_id   uuid     not null references cards (id),
    position  smallint not null
)