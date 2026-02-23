create extension if not exists pgcrypto;

create table medics
(
    id         uuid primary key     default gen_random_uuid(),
    email      text        not null unique,
    password   text        not null,
    first_name text        not null,
    last_name  text        not null,
    created_at timestamptz not null default now()
);

create table medic_roles
(
    medic_id uuid not null references medics (id) on delete cascade,
    role     text not null,
    primary key (medic_id, role)
)