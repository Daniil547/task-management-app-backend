create table reminders
(
    card_id      uuid primary key references cards (id),

    start_or_due timestamp with time zone not null,
    "end"        timestamp with time zone null,
    remind_on    timestamp with time zone not null,
    completed    bool                     not null default false
)