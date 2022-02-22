create table reminders
(
    id           uuid primary key,
    card_id      uuid unique references cards (id),

    start_or_due timestamp with time zone not null,
    end          timestamp with time zone null,
    remind_on    timestamp with time zone not null,
    completed    bool                     not null default false
)