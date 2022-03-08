
-- 테이블 삭제(초기화)
drop table member cascade constraints;      -- 회원
drop table book cascade constraints;        -- 도서정보
drop table book_shelf cascade constraints;  -- 책장
drop table document cascade constraints;    -- 도서기록
drop table review cascade constraints;      -- 리뷰
drop table board cascade constraints;       -- 게시판
drop table upload_file cascade constraints; -- 게시판_파일첨부
drop table comments cascade constraints;     -- 게시판_댓글
drop table notion cascade constraints;      -- 공지사항
drop table question cascade constraints;    -- Q&A_Q
drop table answer cascade constraints;      -- Q&A_A

-- 시퀸스 삭제
drop sequence book_shelf_snum_seq;        -- 책장 시퀸스 삭제
drop sequence document_dnum_seq;          -- 도서기록 시퀸스 삭제
drop sequence review_rnum_seq;            -- 리뷰 시퀸스 삭제
drop sequence board_bnum_seq;             -- 게시판 시퀸스 삭제
drop sequence upload_file_fnum_seq;       -- 게시판_파일첨부 시퀸스 삭제
drop sequence comments_cnum_seq;          -- 게시판_댓글 시퀸스 삭제
drop sequence notion_nnum_seq;            -- 공지사항 시퀸스 삭제
drop sequence question_qnum_seq;          -- Q&A_Q 시퀸스 삭제
drop sequence question_anum_seq;          -- Q&A_A 시퀸스 삭제

-- 회원
create table member(
  id VARCHAR2(40) not null,         -- 아이디
  pw VARCHAR2(30) not null,         -- 비밀번호
  name VARCHAR2(20) not null,       -- 이름
  email VARCHAR2(50) not null,      -- 이메일
  nickname VARCHAR2(30) not null,   -- 닉네임
  leave_fl NUMBER(1) default 0,     -- 탈퇴여부
  leave_dt TIMESTAMP                -- 탈퇴시간
);

-- 1)회원 기본키 생성
alter table member add Constraint member_id_pk primary key (id);                  -- 기본키 생성
alter table member add constraint member_email_uk unique(email);                  -- 유니크키 생성
alter table member add constraint member_nickname_uk unique(nickname);            -- 유니크키 생성
alter table member add constraint member_leave_fl_ck check (leave_fl in (0,1));   -- 체크키 생성

-- 도서정보
create table book(
  isbn NUMBER(13) not null,             -- 도서번호
  title VARCHAR2(100) not null,         -- 제목
  author VARCHAR2(100),                 -- 저자
  publisher VARCHAR2(100),              -- 출판사
  translator VARCHAR2(100),             -- 번역가
  thumbnail VARCHAR2(100) not null,     -- 표지URL
  publication_dt DATE                   -- 출판일
);

-- 1) 도서정보 기본키 생성
alter table book add Constraint book_isbn_pk primary key (isbn);                  -- 기본키 생성

-- 책장
create table book_shelf(
  snum NUMBER(15) not null,     -- 도서등록번호
  sgroup NUMBER(1) default 1,    -- 독서상태(도서번호 1=예정, 2=읽는중, 3=완독)
  spage  NUMBER(15),            -- 총페이지
  sisbn NUMBER(13) not null,    -- ISBN
  sid VARCHAR2(40) not null     -- 아이디
);

-- 1)책장 기본키 생성
alter table book_shelf add Constraint book_shelf_snum_pk primary key (snum);                                          -- 기본키 생성
alter table book_shelf add constraint book_shelf_uk unique(sisbn, sid);                                               -- 복합 유니크키 생성(2개의 값이 동일한 경우에 한하여 오류 확인)
alter table book_shelf add constraint book_shelf_sgroup_ck check (sgroup in (1,2,3));                                 -- 체크키 생성
alter table book_shelf add constraint book_shelf_sisbn_fk foreign key(sisbn) references book(isbn) on delete cascade; -- sisbn 의 외래키 = 도서정보의 기본키(isbn)
alter table book_shelf add constraint book_shelf_sid_fk foreign key(sid) references member(id) on delete cascade;     -- sid의 외래키 = 회원의 기본키(id)


-- 2) 책장 시퀀스
create sequence book_shelf_snum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence book_shelf_snum_seq nocache;

-- 도서기록
create table document(
  dnum NUMBER(15) not null,       -- 기록No
  ddate DATE not null,            -- 기록일자
  dpage NUMBER(15) not null,      -- 기록쪽수
  dgroup NUMBER(1) default 2 not null, -- 독서상태  
  dsnum NUMBER(15) not null,     -- 도서등록No
  did VARCHAR2(40) not null       -- 아이디
);

-- 1)도서기록 기본키 생성
alter table document add Constraint document_dnum_pk primary key (dnum);                                                  -- 기본키 생성
alter table document add constraint document_dgroup_ck check (dgroup in (2,3));                                           -- 체크키 생성
alter table document add constraint document_dsnum_fk foreign key(dsnum) references book_shelf(snum) on delete cascade;   -- dsnum 의 외래키 = 책장의 기본키(snum)
alter table document add constraint document_did_fk foreign key(did) references member(id) on delete cascade;             -- did의 외래키 = 회원의 기본키(id)

-- 2) 도서기록 시퀀스
create sequence document_dnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence document_dnum_seq nocache;

-- 리뷰
create table review(
  rnum NUMBER(15) not null,                -- 리뷰No
  rcontent VARCHAR2(100) not null,         -- 리뷰내용
  rcdate TIMESTAMP default systimestamp,   -- 리뷰작성일
  rudate TIMESTAMP,                        -- 리뷰수정일
  rstar NUMBER(1) default 0,                -- 별점
  risbn NUMBER(13) not null,               -- 도서번호
  rid  VARCHAR2(40) not null                -- 아이디
);

-- 1) 리뷰 기본키 생성
alter table review add Constraint review_rnum_pk primary key (rnum);                                             -- 기본키 생성
alter table review add constraint review_rstar_ck check (rstar in(0,1,2,3,4,5));                                  -- 체크키 생성
alter table review add constraint review_uk unique(risbn, rid);                                                   -- 복합 유니크키 생성(2개의 값이 동일한 경우에 한하여 오류 확인)
alter table review add constraint reviewr_risbn_fk foreign key(risbn) references book(isbn) on delete cascade;    -- risbn 의 외래키 = 도서의 기본키(isbn)
alter table review add constraint review_rid_fk foreign key(rid) references member(id) on delete cascade;         -- rid의 외래키 = 회원의 기본키(id)

-- 2) 리뷰 시퀀스
create sequence review_rnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 리뷰 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence review_rnum_seq nocache;

-- 게시판
create table board(
  bnum NUMBER(15) not null,         -- 게시글번호
  btitle VARCHAR2(50) not null,     -- 게시글제목
  bcdate TIMESTAMP default systimestamp, -- 게시글작성일
  budate TIMESTAMP,                 -- 게시글수정일
  bcontent CLOB not null,           -- 게시글내용
  bhit NUMBER(10) not null,         -- 게시글조회수
  bid VARCHAR2(40) not null         -- 아이디
);

-- 1) 게시판기본키 생성
alter table board add Constraint board_bnum_pk primary key (bnum);                                        -- 기본키 생성
alter table board add constraint board_bid_fk foreign key(bid) references member(id) on delete cascade;   -- bid의 외래키 = 회원의 기본키(id)

-- 2) 게시판 시퀀스
create sequence board_bnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 게시판 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence board_bnum_seq nocache;

-- 게시판_파일첨부
create table upload_file(
  fnum NUMBER(15) not null,             -- 첨부파일번호
  store_fname VARCHAR2(50) not null,    -- 로컬파일명
  upload_fname VARCHAR2(50) not null,   -- 업로드파일명
  fsize VARCHAR2(50) not null,          -- 파일크기
  ftype VARCHAR2(50) not null,          -- 파일타입
  fcdate TIMESTAMP default systimestamp, -- 첨부날자
  fudate TIMESTAMP,                     -- 첨부수정날자
  fbnum NUMBER(15) not null             -- 게시글번호
);

-- 1) 게시판_파일첨부 기본키 생성
alter table upload_file add Constraint upload_file_fnum_pk primary key (fnum);                                              -- 기본키 생성
alter table upload_file add constraint upload_file_fbnum_fk foreign key(fbnum) references board(bnum) on delete cascade;    -- fbnum의 외래키 = 게시판의 기본키(bnum)

-- 2) 게시판_파일첨부 시퀀스
create sequence upload_file_fnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 게시판_파일첨부 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence upload_file_fnum_seq nocache;

-- 게시판_댓글
create table comments(
  cnum NUMBER(15) not null,           -- 댓글번호
  ccdate TIMESTAMP default systimestamp, -- 댓글작성일
  cudate TIMESTAMP,                    -- 댓글수정일
  ccontent VARCHAR2(50) not null,     -- 댓글내용
  cid VARCHAR2(40) not null,          -- 아이디
  cbnum NUMBER(15) not null           -- 게시글번호
);

-- 1) 게시판_댓글 기본키 생성
alter table comments add Constraint comments_cnum_pk primary key (cnum);                                            -- 기본키 생성
alter table comments add constraint comments_cid_fk foreign key(cid) references member(id) on delete cascade;       -- cid의 외래키 = 회원의 기본키(id)
alter table comments add constraint comments_cbnum_fk foreign key(cbnum) references board(bnum) on delete cascade;  -- cbnum의 외래키 = 게시판의 기본키(bnum)

-- 2) 게시판_댓글 시퀀스
create sequence comments_cnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 게시판_댓글 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence comments_cnum_seq nocache;

-- 공지사항
create table notion(
  nnum NUMBER(15) not null,         -- 공지사항번호
  ntitle VARCHAR2(50) not null,     -- 공지사항제목
  ncdate TIMESTAMP default systimestamp, -- 공지사항작성일
  nudate TIMESTAMP,                 -- 공지사항수정일
  ncontent CLOB not null,          -- 공지사항내용
  nid VARCHAR2(40) not null         -- 아이디
);

-- 1) 공지사항 기본키 생성
alter table notion add Constraint notion_nnum_pk primary key (nnum);                                        -- 기본키 생성
alter table notion add constraint notion_nid_fk foreign key(nid) references member(id) on delete cascade;   -- nid의 외래키 = 회원의 기본키(id)

-- 2) 공지사항 시퀀스
create sequence notion_nnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence notion_nnum_seq nocache;

 -- Q&A_Q
 create table question(
  qnum NUMBER(15) not null,         -- 질문번호
  qtitle VARCHAR2(50) not null,     -- 질문제목
  qcdate TIMESTAMP default systimestamp, -- 질문작성일
  qudate TIMESTAMP,                 -- 질문수정일
  qcontent CLOB not null,           -- 질문내용
  qid VARCHAR2(40) not null         -- 아이디
 ); 

-- 1) Q&A_Q 기본키 생성
alter table question add Constraint question_qnum_pk primary key (qnum);                                          -- 기본키 생성
alter table question add constraint question_qid_fk foreign key(qid) references member(id) on delete cascade;     -- qid의 외래키 = 회원의 기본키(id)

-- 2) Q&A_Q 시퀀스
create sequence question_qnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) Q&A_Q 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence question_qnum_seq nocache;

 -- Q&A_A
 create table answer(
  anum NUMBER(15) not null,         -- 답변번호
  atitle VARCHAR2(50) not null,     -- 답변제목
  acdate TIMESTAMP default systimestamp, -- 답변작성일
  audate TIMESTAMP,                 -- 답변수정일
  acontent CLOB not null,           -- 답변내용
  aqnum NUMBER(15) not null,        --  질문번호
  aid VARCHAR2(40) not null         -- 아이디
 ); 

-- 1) Q&A_A 기본키 생성
alter table answer add Constraint answer_anum_pk primary key (anum);                                                -- 기본키 생성
alter table answer add constraint answer_aqnum_fk foreign key(aqnum) references question(qnum) on delete cascade;   -- aqnum의 외래키 = Q&A_Q의 기본키(qnum)
alter table answer add constraint answer_aid_fk foreign key(aid) references member(id) on delete cascade;           -- aid의 외래키 = 회원의 기본키(id)

-- 2) Q&A_A 시퀀스
create sequence question_anum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) Q&A_Q 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence question_anum_seq nocache;


 
 
 
 