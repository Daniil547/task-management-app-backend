create table attachments
(
    id                uuid primary key,
    name              varchar(40),
    extension         varchar(20),
    file_content_id   uuid,
    file_storage_type file_storage_type not null,

    card_id           uuid              not null references cards (id)
    /*,position  smallint not null*/
)
