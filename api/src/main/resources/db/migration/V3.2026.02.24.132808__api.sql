create table patients
(
    id         uuid primary key     default gen_random_uuid(),

    last_name  text        not null,
    first_name text        not null,
    birth_date date        not null,
    sex        text check (sex in ('M', 'F')),
    phone      text,
    email      text,

    constraint ck_patients_email_or_phone_required
        check (nullif(trim(email), '') is not null or
               nullif(trim(phone), '') is not null),

    updated_at timestamptz not null default now(),
    created_at timestamptz not null default now()
);

create index idx_patients_name on patients (lower(last_name), lower(first_name));

create unique index ux_patients_email_not_null
    on patients (lower(email))
    where email is not null;

create unique index ux_patients_phone_not_null
    on patients (phone)
    where phone is not null;
