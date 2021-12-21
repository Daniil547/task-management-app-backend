create table reminders
(
    card_id      uuid references cards (id) primary key,

    start_or_due timestamptz not null,
    "end"        timestamptz null,
    remind_on    timestamptz not null,
    completed    bool        not null default false
)