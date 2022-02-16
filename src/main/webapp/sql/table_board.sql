create table jboard(
  board_num number not null,
  title varchar2(150) not null,
  content clob not null,
  hit number(5) default 0 not null,
  reg_date date default sysdate not null,
  modify_date date,
  filename varchar2(150),
  ip varchar2(40) not null,
  user_num number not null,
  constraint jboard_pk primary key (board_num),
  constraint jboard_fk foreign key (user_num) references juser (user_num)
);

create sequence jboard_seq;

create table jboard_reply(
  re_num number not null,
  re_content varchar2(900) not null,
  re_date date default sysdate not null,
  re_modifydate date,
  re_ip varchar2(40) not null,
  board_num number not null,
  user_num number not null,
  constraint jreply_pk primary key (re_num),
  constraint jreply_fk1 foreign key (board_num) references jboard (board_num),
  constraint jreply_fk2 foreign key (user_num) references juser (user_num)
);

create sequence jreply_seq;





