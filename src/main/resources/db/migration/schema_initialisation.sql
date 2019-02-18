create table MT_USER (
  ID uuid not null ,
  EMAIL varchar(255) not null unique ,
  FIRST_NAME varchar(255) ,
  MIDDLE_NAME varchar(255) ,
  LAST_NAME varchar(255),
  COUNTRY_CODE varchar(10) not null,
  primary key (ID)
);

create table MT_ACCOUNT (
  ID uuid not null ,
  USER_ID uuid not null ,
  BALLANCE decimal(10, 2) not null default 0,
  CURRENCY_ID uuid not null ,
  primary key (ID)
);

create table MT_CURRENCY (
  ID uuid not null ,
  NAME varchar(255) not null ,
  SIGN varchar(10) not null ,
  primary key (ID)
);

create table MT_CURRENCY_COURSE (
  FROM_CURRENCY_ID uuid not null ,
  TO_CURRENCY_ID uuid not null ,
  CORRECTION double precision not null ,
  primary key (FROM_CURRENCY_ID, TO_CURRENCY_ID)
);

create table MT_TRANSACTION (
  ID uuid not null ,
  USER_ID uuid not null ,
  FROM_ACCOUNT_ID uuid not null ,
  TO_ACCOUNT_ID uuid not null ,
  AMMOUNT decimal(10, 2) not null ,
  primary key (ID)
);

alter table MT_ACCOUNT add constraint FK_MT_ACCOUNT_USER foreign key (USER_ID) references MT_USER(ID);
alter table MT_ACCOUNT add constraint FK_MT_ACCOUNT_CURRENCY foreign key (CURRENCY_ID) references MT_CURRENCY(ID);

alter table MT_CURRENCY_COURSE add constraint FK_MT_CURRENCY_COURSE_CURRENCY_FROM foreign key (FROM_CURRENCY_ID) references MT_CURRENCY(ID);
alter table MT_CURRENCY_COURSE add constraint FK_MT_CURRENCY_COURSE_CURRENCY_TO foreign key (TO_CURRENCY_ID) references MT_CURRENCY(ID);

alter table MT_TRANSACTION add constraint FK_MT_TRANSACTION_USER foreign key (USER_ID) references MT_USER(ID);
alter table MT_TRANSACTION add constraint FK_MT_TRANSACTION_ACCOUNT_FROM foreign key (FROM_ACCOUNT_ID) references MT_ACCOUNT(ID);
alter table MT_TRANSACTION add constraint FK_MT_TRANSACTION_ACCOUNT_TO foreign key (TO_ACCOUNT_ID) references MT_ACCOUNT(ID);