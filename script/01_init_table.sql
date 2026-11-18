/*
create database demoyofi;
*/

-- drop table MST_SYSPARAM;
-- create table MST_SYSPARAM (
--       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--       header varchar(30) not null,
--       code varchar(15) not null,
--       value varchar(80),
--       valueint float,
--       description varchar(100),
--       status int default 1,
--       createddate timestamp default current_timestamp,
--       updateddate timestamp default current_timestamp,
--       unique (header,code)
-- );
-- 
-- insert into MST_SYSPARAM (header,code,value) values ('ROLE','ADMIN','ADMIN');
-- insert into MST_SYSPARAM (header,code,value) values ('ROLE','USER','USER');
-- insert into MST_SYSPARAM (header,code,valueint) values ('LOGIN','MAXLOGINFAILED',3);

drop table MST_USER;
create table MST_USER (
      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      login varchar(15) not null,
      fullname varchar(30) not null,
      role varchar(20) not null default 'USER',
      password varchar(80) not null default 'password',
      plainpswd int not null default 1,
      status int default 1,
      deleted int default 0,
      createddate timestamp default current_timestamp,
      updateddate timestamp default current_timestamp,
      UNIQUE (login)
);

insert into mst_user (login, fullname,role) values ('admin','Admin','ADMIN');
insert into mst_user (login, fullname) values ('yofi','Yofi');
insert into mst_user (login, fullname) values ('wick','John Wick');
insert into mst_user (login, fullname) values ('rambo','John Rambo');



