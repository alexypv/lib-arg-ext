insert into library.library
values ('1', getDate(), getDate(), 'DefaultLibrary', null, null);

insert into library.roles
values ('1', getDate(), getDate(), 'ROLE_GLOBAL', null, null);
insert into library.roles
values ('2', getDate(), getDate(), 'ROLE_ADMIN', null, null);
insert into library.roles
values ('3', getDate(), getDate(), 'ROLE_LIBRARIER', null, null);
insert into library.roles
values ('4', getDate(), getDate(), 'ROLE_READER', null, null);

insert into library.user
values ('1', getDate(), getDate(), 'Владимир', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Алексеевич', 'Попов', 'adm', null, null, '1');
insert into library.user
values ('2', getDate(), getDate(), 'Алексей', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Дмитриевич', 'Попов', 'usr', null, null, '1');

insert into LIBRARY.USER_ROLES
values ('1', '2');
insert into LIBRARY.USER_ROLES
values ('2', '4');