create table checkable_items
(
    id           uuid primary key,
    description  varchar(50),
    checked      bool     not null default false,

    checklist_id uuid     not null references checklists (id),
    position     smallint not null
)