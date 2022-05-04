create type reminder_action as enum ('SEND_EMAIL');

create table reminders
(
    id           uuid primary key,

    message      varchar(60)              not null,
    action       reminder_action          not null,
    start_or_due timestamp with time zone not null,
    end          timestamp with time zone null,
    remind_on    timestamp with time zone not null,
    completed    bool                     not null default false,
    gone_off     bool                     not null default false
);