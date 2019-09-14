insert into library.library
values ('1', getDate(), getDate(), 'LocalLibrary', null, null);

insert into library.roles
values ('1', getDate(), getDate(), 'ROLE_GLOBAL', null, null);
insert into library.roles
values ('2', getDate(), getDate(), 'ROLE_ADMIN', null, null);
insert into library.roles
values ('3', getDate(), getDate(), 'ROLE_LIBRARIER', null, null);
insert into library.roles
values ('4', getDate(), getDate(), 'ROLE_READER', null, null);

insert into library.user
values ('1', getDate(), getDate(), 'Глобальный', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Администратор', 'Системы', 'admin', null, null, '1');
      
insert into library.user
values ('2', getDate(), getDate(), 'Владимир', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Алексеевич', 'Попов', 'adm', '1', '1', '1');

insert into library.user
values ('3', getDate(), getDate(), 'Алексей', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Дмитриевич', 'Попов', 'lib', '1', '1', '1');

insert into library.user
values ('4', getDate(), getDate(), 'Алексей', '$2a$04$YVGeK5dflU1yZykXJx7eyOPtkSwudO94sXWsXtVk1UFPtNnHpegkO',
        'Дмитриевич', 'Попов', 'usr', '1', '1', '1');

insert into LIBRARY.USER_ROLES
values ('1', '1');

insert into LIBRARY.USER_ROLES
values ('2', '2');

insert into LIBRARY.USER_ROLES
values ('3', '3');

insert into LIBRARY.USER_ROLES
values ('4', '4');