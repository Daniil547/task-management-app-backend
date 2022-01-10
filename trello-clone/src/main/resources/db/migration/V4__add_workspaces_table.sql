create table workspaces
(
    id                  uuid primary key,

    page_title          varchar(20)          not null default 'My Workspace',
    page_name           varchar(25)          not null unique,
    page_description    varchar(1000),

    company_website_url varchar(75),
    visibility          workspace_visibility not null default 'PRIVATE'
)