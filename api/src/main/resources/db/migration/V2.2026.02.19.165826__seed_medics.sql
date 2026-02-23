insert into medics (id, email, password, first_name, last_name)
values ('00000000-0000-0000-0000-000000000001',
        'doctor@wardcare.fr',
        crypt('123456', gen_salt('bf')),
        'Richard',
        'Webber');

insert into medic_roles (medic_id, role)
values ('00000000-0000-0000-0000-000000000001', 'DOCTOR')