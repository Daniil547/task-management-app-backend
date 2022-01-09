alter table workspaces
    drop column created_when;
alter table boards
    drop column created_when;
alter table cardlists
    drop column created_when;
alter table cards
    drop column created_when;
alter table users
    drop column created_when;

