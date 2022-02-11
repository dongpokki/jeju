create table jboard_qna(
    qna_num number not null,
    title varchar2(150) not null,
    content clob not null,
    hit number(5) default 0 not null,
    viewable_check number(1) not null,
    reg_date date default sysdate not null,
    modify_date date,
   	filename varchar2(150),
    ip varchar2(40) not null,
    user_num number(10) not null,
    constraint jboard_qna_pk primary key(qna_num),
    constraint jboard_qna_fk foreign key(user_num) references juser(user_num)
);

create sequence jboard_qna_seq;

create table jcmt_qna(
    qnacmt_num number not null,
    qna_num number(10) not null,
    cmt_content varchar2(300) not null,
    reg_date date default sysdate not null,
    modify_date date,
    user_num number not null,
    constraint jcmt_qna_pk primary key(qnacmt_num),
    constraint jcmt_qna_fk foreign key(qna_num) references jboard_qna(qna_num),
    constraint jcmt_qna_fk2 foreign key(user_num) references juser(user_num)
);

create sequence jcmt_qna_seq;