create table workspaces
(
    id                  uuid primary key,

    /* replaced by adding "OWNER" option to the Role enum
    created_by uuid references workspace_members(user_id, workspace_id) not null,
     */
    created_when        timestamptz          not null,
    /* problematic, better be implemented via logging and aspects
    last_updated_by uuid references workspace_members(user_id, workspace_id),
    last_updated_when timestamptz,
    */

    page_title          varchar(20)          not null default 'My Workspace',
    page_name           varchar(25)          not null unique,
    page_description    varchar(1000),

    company_website_url varchar(75),
    visibility          workspace_visibility not null default 'PRIVATE'
)