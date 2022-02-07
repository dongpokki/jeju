/*추천 코스 테이블* ㅠㅠ/
create table jboard_course(
    course_num number not null,
    title varchar2(150) not null,
    content clob not null,
    hit number(5) not null,
    reg_date date default sysdate not null,
    modify_date date,
    filename varchar2(150),
    ip varchar2(40) not null,
    user_num number(10) not null,
    constraint jboard_course_pk primary key(course_num),
    constraint jboard_course_fk foreign key(user_num) references juser(user_num)
);
create sequence jboard_course_seq;

/*추천 코스 좋아요*/
create table jgood_course(
	coursegood_num number not null,
    course_num number(10) not null,
    user_num number(10) not null,
    good number(1) not null,
    constraint jgood_course_pk primary key(coursegood_num),
    constraint jgood_course_fk foreign key(course_num) references jboard_course(course_num),
    constraint jgood_course_fk2 foreign key(user_num) references juser(user_num)
);

/*추천 코스 코멘트*/
create table jcmt_course(
    coursecmt_num number not null,
    course_num number not null,
    content varchar2(300) not null,
    reg_date date default sysdate not null,
    modify_date date,
    user_num number not null,
    constraint jcmtcourse_pk primary key(coursecmt_num),
    constraint jcmtcourse_fk foreign key(course_num) references jboard_course(course_num),
    constraint jcmtcourse_fk2 foreign key(user_num) references juser(user_num)
);

create sequence jcmt_course_seq;

/*추천 코스 코멘트 좋아요*/
create table jcmtgood_course(
	coursecmtgood_num number not null,
    coursecmt_num number not null,
    user_num number not null,
    good number(1) not null,
    constraint jcmtgood_course_pk primary key(coursegood_num),
    constraint jcmtgood_course_fk foreign key(coursecmt_num) references jcmt_course(coursecmt_num),
    constraint jcmtgood_course_fk2 foreign key(user_num) references juser(user_num)
);
