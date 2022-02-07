/* 추천 장소 테이블*/
create table jboard_spot(
  spot_num number not null,
  title varchar2(150) not null,
  content clob not null,
  hit number(5) default 0 not null,
  reg_date date default sysdate not null,
  modify_date date,
  filename varchar2(150),
  ip varchar2(40) not null,
  user_num number not null,
  constraint jboard_spot_pk primary key (spot_num),
  constraint jboard_spot_fk foreign key (user_num) references juser (user_num)
);

create sequence jboard_spot_seq;

/*추천 장소 좋아요*/
create table jgood_spot(
    spot_num number(10) not null,
    user_num number(10) not null,
    good number(1) not null,
    constraint jboard_spot_like_fk foreign key(spot_num) references jboard_spot(spot_num),
    constraint jboard_spot_like_fk2 foreign key(user_num) references juser(user_num)
);

create table jcmt_spot(
  spotcmt_num number not null,
  spot_num number not null,
  content varchar2(300) not null,
  reg_date date default sysdate not null,
  modify_date date,
  user_num number not null,
  constraint spotcmt_num_pk primary key (spotcmt_num),
  constraint spot_num_fk foreign key (spot_num) references jboard_spot (spot_num),
  constraint jcmt_spot_fk foreign key (user_num) references juser (user_num)
);

create sequence jcmt_spot_seq;

/*추천 장소 코멘트 좋아요*/
create table jcmtgood_spot(
    spotcmt_num number(10) not null,
    user_num number(10) not null,
    good number(1) not null,
    constraint jcmt_spot_like_fk foreign key(spotcmt_num) references jcmt_spot(spotcmt_num),
    constraint jcmt_spot_like_fk2 foreign key(user_num) references juser(user_num)
);