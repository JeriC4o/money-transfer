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
  CURRENCY_CODE varchar(10) not null ,
  primary key (ID)
);

create table MT_CURRENCY_COURSE (
  FROM_CURRENCY_CODE varchar(10) not null ,
  TO_CURRENCY_CODE varchar(10) not null ,
  CORRECTION double precision not null ,
  primary key (FROM_CURRENCY_CODE, TO_CURRENCY_CODE)
);

create table MT_TRANSACTION (
  ID uuid not null ,
  USER_ID uuid not null,
  primary key (ID)
);

create table MT_TRANSACTION_LOG  (
  TRANSACTION_ID uuid not null ,
  USER_ID uuid not null,
  ACCOUNT_ID uuid not null ,
  AMOUNT decimal(10, 2) not null ,
  primary key (ACCOUNT_ID, TRANSACTION_ID)
);

alter table MT_ACCOUNT add constraint FK_MT_ACCOUNT_USER foreign key (USER_ID) references MT_USER(ID) ON DELETE CASCADE;

alter table MT_TRANSACTION add constraint FK_MT_TRANSACTION_USER foreign key (USER_ID) references MT_USER(ID) ON DELETE CASCADE ;

alter table MT_TRANSACTION_LOG add constraint FK_MT_TRANSACTION_LOG_ACCOUNT foreign key (ACCOUNT_ID) references MT_ACCOUNT(ID) ON DELETE CASCADE;
alter table MT_TRANSACTION_LOG add constraint FK_MT_TRANSACTION_LOG_USER foreign key (USER_ID) references MT_USER(ID) ON DELETE CASCADE;
alter table MT_TRANSACTION_LOG add constraint FK_MT_TRANSACTION_LOG_TRANSACTION foreign key (TRANSACTION_ID) references MT_TRANSACTION(ID) ON DELETE CASCADE ;