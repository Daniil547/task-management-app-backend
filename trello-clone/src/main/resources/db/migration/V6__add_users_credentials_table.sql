create table users_credentials
(
    user_id  uuid primary key references users (id),

    username varchar(20)  not null unique,
    email    varchar(254) not null unique,

    password varchar(256) /*what length?*/
)