
-- 테이블 삭제(초기화)
-- 공지사항 및 Q&A는 별도 작업 예정임에 제외됨
drop table member cascade constraints;      -- 회원
drop table book cascade constraints;        -- 도서정보
drop table book_shelf cascade constraints;  -- 책장
drop table document cascade constraints;    -- 도서기록
drop table review cascade constraints;      -- 리뷰
drop table board cascade constraints;       -- 게시판
drop table upload_file cascade constraints; -- 게시판_파일첨부
drop table comments cascade constraints;     -- 게시판_댓글

-- 시퀸스 삭제
drop sequence book_shelf_snum_seq;        -- 책장 시퀸스 삭제
drop sequence document_dnum_seq;          -- 도서기록 시퀸스 삭제
drop sequence review_rnum_seq;            -- 리뷰 시퀸스 삭제
drop sequence board_bnum_seq;             -- 게시판 시퀸스 삭제
drop sequence upload_file_fnum_seq;       -- 게시판_파일첨부 시퀸스 삭제
drop sequence comments_cnum_seq;          -- 게시판_댓글 시퀸스 삭제


-- 회원
create table member(
  id VARCHAR2(40) not null,                            -- 아이디
  pw VARCHAR2(30) not null,                            -- 비밀번호
  name VARCHAR2(20) not null,                          -- 이름
  email VARCHAR2(50) not null,                         -- 이메일
  nickname VARCHAR2(30) not null,                      -- 닉네임
  admin_fl NUMBER(1) default 3,                        -- 관리자 2 사용자 3
  signup_dt TIMESTAMP  default systimestamp,           -- 가입시간
  leave_fl NUMBER(1) default 0,                        -- 탈퇴여부 0 회원 1 탈퇴
  leave_dt TIMESTAMP                                   -- 탈퇴시간 (sql의 프로시저 기능 사용을 위하여 default systimestamp 미적용)
);

-- 1)회원 기본키 생성
alter table member add Constraint member_id_pk primary key (id);                            -- 기본키 생성
alter table member add constraint member_email_uk unique(email);                            -- 유니크키 생성
alter table member add constraint member_nickname_uk unique(nickname);                      -- 유니크키 생성
alter table member add constraint member_id_ck check (REGEXP_LIKE(id,'^[A-Za-z0-9]*$'));   -- 정규식을 이용하여 영문 대문자, 소문자, 숫자 조건에 맞는 체크키 생성
alter table member add constraint member_pw_ck check ( REGEXP_LIKE(pw, '[a-z]')
   AND REGEXP_LIKE(pw, '[A-Z]') AND REGEXP_LIKE(pw, '[0-9]')  AND REGEXP_LIKE(pw, '[[:punct:]]')
   AND REGEXP_LIKE(pw, '{8,15}'));                                                         -- 정규식을 이용하여 소문자+대문자+특수문자 포함 8~15자 조건에 맞는 체크키 생성
alter table member add constraint member_name_ck check (REGEXP_LIKE(name,'^[가-힝]*$'));   -- 정규식을 이용하여 한글만 조건에 맞는 체크키 생성
alter table member add constraint member_email_ck check (email like '%@%.%');              -- 정규식을 이용하여 @와 .이 들어가는 이메일 체크키 생성
alter table member add constraint member_admin_fl_ck check (admin_fl in (2,3));             -- 관리지 여부 확인용 체크키 생성 (관리자 2, 사용자3)
alter table member add constraint member_leave_fl_ck check (leave_fl in (0,1));             -- 탈퇴 여부 확인용 체크키 생성 (회원 0, 탈퇴 1)

-- 2) 회원 등록 / id 한글 제외, 비밀번호 특수문자 포함 8~15, 이름 한글만 입력, email 형식 확인, 닉네임 유니크 확인
insert into member (id, pw, name, email, nickname, admin_fl) values ('admin', 'Qwert12!', '관리자', 'admin@test.com', '관리자1', 2); 
insert into member (id, pw, name, email, nickname) values ('user1', 'Qwert12!', '홍길동', 'user1@test.com', '회원1'); 
insert into member (id, pw, name, email, nickname) values ('user2', 'Qwert12!', '홍길남', 'user2@test.com', '회원2');
insert into member (id, pw, name, email, nickname) values ('user3', 'Qwert12!', '홍길서', 'user3@test.com', '회원3');
insert into member (id, pw, name, email, nickname) values ('user4', 'Qwert12!', '홍길북', 'user4@test.com', '회원4');

select *from member;


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

-- 2) 도서 정보 등록
insert into book (isbn, title, thumbnail) values (1234567890123, '제목1', 'https://cdn.pixabay.com/photo/2016/03/27/19/32/book-1283865_960_720.jpg'); 
insert into book (isbn, title, thumbnail) values (2987654321098, '제목2', 'https://cdn.pixabay.com/photo/2015/12/19/20/32/paper-1100254_960_720.jpg'); 
insert into book (isbn, title, thumbnail, publication_dt) values (4987654321092, '제목3', 'https://cdn.pixabay.com/photo/2015/05/11/14/51/heart-762564_960_720.jpg', '2022-02-21' ); 
insert into book (isbn, title, thumbnail, publication_dt) values (4987654321090, '제목4', 'https://cdn.pixabay.com/photo/2015/05/11/14/51/heart-762564_960_720.jpg', '2022-02-23' ); 

select *from book;


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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음

-- 3) 책장 등록 / sgroup 독서상태(도서번호 1=예정, 2=읽는중, 3=완독)
insert into book_shelf (snum, sgroup, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 2, 100, 1234567890123, 'user1'); 
insert into book_shelf (snum, sisbn, sid) values (book_shelf_snum_seq.nextval, 2987654321098, 'user1');     -- sgroup default 1 입력 확인
insert into book_shelf (snum, sgroup, sisbn, sid) values (book_shelf_snum_seq.nextval, 2, 1234567890123, 'user2'); 
insert into book_shelf (snum, sgroup, sisbn, sid) values (book_shelf_snum_seq.nextval, 2, 4987654321090, 'user2'); 
insert into book_shelf (snum, sgroup, sisbn, sid) values (book_shelf_snum_seq.nextval, 2, 4987654321092, 'user3'); 

select *from book_shelf;


-- 도서기록
create table document(
  dnum NUMBER(15) not null,       -- 기록No
  ddate DATE not null,            -- 기록일자
  dpage NUMBER(15) not null,      -- 기록쪽수
  dgroup NUMBER(1) default 2,     -- 독서상태  
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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음

-- 3) 도서기록
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-02-20', 20, 2, 1, 'user1');  
insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-21', 25, 1, 'user1');   -- dgroup default 2 입력 확인
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-02-22', 34, 2, 1, 'user1');  
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-02-23', 50, 2, 1, 'user1');  
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-02-21', 20, 2, 1, 'user2');  
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-02-22', 25, 2, 4, 'user2');  
insert into document (dnum, ddate, dpage, dgroup, dsnum, did) values (document_dnum_seq.nextval, '2022-01-22', 34, 2, 2, 'user1');  

select *from document;


-- 리뷰
create table review(
  rnum NUMBER(15) not null,                -- 리뷰No
  rcontent VARCHAR2(100) not null,         -- 리뷰내용
  rcdate TIMESTAMP default systimestamp,   -- 리뷰작성일
  rudate TIMESTAMP default systimestamp,   -- 리뷰수정일
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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음

-- 3) 리뷰
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '리뷰내용1', 2, 1234567890123, 'user1');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '리뷰내용2', 3, 1234567890123, 'user2');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '리뷰내용3', 2, 4987654321090, 'user2');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '리뷰내용4', 1, 4987654321092, 'user3');

select *from review;


-- 게시판
create table board(
  bnum NUMBER(15) not null,                   -- 게시글번호
  btitle VARCHAR2(60) not null,               -- 게시글제목
  bcdate TIMESTAMP default systimestamp,      -- 게시글작성일
  budate TIMESTAMP default systimestamp,      -- 게시글수정일
  bcontent CLOB not null,                     -- 게시글내용
  bhit NUMBER(5) default 0,                   -- 게시글조회수
  bstatus char(1) default 'E',                --게시글 상태(E:exist, D:delete)
  bid VARCHAR2(40) not null                   -- 아이디
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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음

-- 3) 게시판
insert into board (bnum, btitle, bcontent, bid) values (board_bnum_seq.nextval, '게시판제목1', '게시판내용1', 'user1'); 
insert into board (bnum, btitle, bcontent, bid) values (board_bnum_seq.nextval, '게시판제목2', '게시판내용2', 'user1'); 
insert into board (bnum, btitle, bcontent, bid) values (board_bnum_seq.nextval, '게시판제목3', '게시판내용3', 'user2'); 
insert into board (bnum, btitle, bcontent, bid) values (board_bnum_seq.nextval, '게시판제목4', '게시판내용4', 'admin'); 

select *from board;


-- 게시판_파일첨부
create table upload_file(
  fnum NUMBER(15) not null,                     -- 첨부파일번호
  store_fname VARCHAR2(50) not null,            -- 로컬파일명
  upload_fname VARCHAR2(50) not null,           -- 업로드파일명
  fsize VARCHAR2(50) not null,                  -- 파일크기
  ftype VARCHAR2(50) not null,                  -- 파일타입
  fcdate TIMESTAMP default systimestamp,        -- 첨부날자
  fudate TIMESTAMP  default systimestamp,       -- 첨부수정날자
  fbnum NUMBER(15) not null                     -- 게시글번호
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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음


-- 게시판_댓글
create table comments(
  cnum NUMBER(15) not null,                 -- 댓글번호
  ccdate TIMESTAMP default systimestamp,    -- 댓글작성일
  cudate TIMESTAMP default systimestamp,    -- 댓글수정일
  ccontent VARCHAR2(50) not null,           -- 댓글내용
  cid VARCHAR2(40) not null,                -- 아이디
  cbnum NUMBER(15) not null                 -- 게시글번호
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
nocache        -- 시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle;  --순환하지않음

-- 3) 게시판_댓글
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글1', 'user2', 1); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글1-1', 'admin', 1); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글2', 'user2', 2); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글3', 'user3', 3); 

select *from comments;

commit;
 
 