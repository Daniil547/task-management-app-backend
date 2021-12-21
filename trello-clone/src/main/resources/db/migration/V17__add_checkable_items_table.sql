create table checkable_items
(
    description  varchar(50),
    checked      bool                            not null default false,

    checklist_id uuid references checklists (id) not null,
    position     smallint                        not null,
    primary key (checklist_id, position)
)