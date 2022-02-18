create table jboard(
  board_num number not null,
  title varchar2(150) not null,
  content clob not null,
  hit number(5) default 0 not null,
  reg_date date default sysdate not null,
  modify_date date,
  filename varchar2(150),
  ip varchar2(40) not null,
  course clob,
  user_num number not null,
  constraint jboard_pk primary key (board_num),
  constraint jboard_fk foreign key (user_num) references juser (user_num)
);

create sequence jboard_seq;

create table jcmt_board(
  boardcmt_num number not null,
  board_num number not null,
  cmt_content varchar2(300) not null,
  reg_date date default sysdate not null,
  modify_date date,
  user_num number not null,
  constraint boardcmt_num_pk primary key (boardcmt_num),
  constraint board_num_fk foreign key (board_num) references jboard (board_num),
  constraint jcmt_board_fk foreign key (user_num) references juser (user_num)
);

create sequence jcmt_board_seq;




