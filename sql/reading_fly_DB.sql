------------------
--reading_fly_DB--
------------------

--1. 기존 데이터 삭제
--테이블 삭제
drop table member cascade constraints;      -- 회원
drop table book cascade constraints;        -- 도서정보
drop table book_shelf cascade constraints;  -- 책장
drop table document cascade constraints;    -- 도서기록
drop table review cascade constraints;      -- 리뷰
drop table code cascade constraints;        -- 코드
drop table board cascade constraints;       -- 게시판
drop table uploadfile cascade constraints;  -- 게시판_파일첨부
drop table comments cascade constraints;    -- 게시판_댓글
drop table notice cascade constraints;      -- 공지사항
drop table qna cascade constraints;         -- Q&A

--시퀀스 삭제
drop sequence book_shelf_snum_seq;        -- 책장 시퀸스 삭제
drop sequence document_dnum_seq;          -- 도서기록 시퀸스 삭제
drop sequence review_rnum_seq;            -- 리뷰 시퀸스 삭제
drop sequence board_bnum_seq;             -- 게시판 시퀸스 삭제
drop sequence uploadfile_fnum_seq;        -- 게시판_파일첨부 시퀸스 삭제
drop sequence comments_cnum_seq;          -- 게시판_댓글 시퀸스 삭제
drop sequence notice_nnum_seq;            -- 공지사항 시퀀스 삭제
drop sequence qna_qnum_seq;               -- Q&A 시퀀스 삭제

--2. 회원
--회원 테이블 생성
create table member(
  id VARCHAR2(40) not null,                            -- 아이디
  pw VARCHAR2(70) not null,                            -- 비밀번호/  비밀번호 암호화 적용
  name VARCHAR2(20) not null,                          -- 이름
  email VARCHAR2(50) not null,                         -- 이메일
  nickname VARCHAR2(30) not null,                      -- 닉네임
  admin_fl NUMBER(1) default 3,                        -- 관리자 2 사용자 3
  signup_dt TIMESTAMP  default systimestamp,           -- 가입시간
  leave_fl NUMBER(1) default 0,                        -- 탈퇴여부 0 회원 1 탈퇴
  leave_dt TIMESTAMP                                   -- 탈퇴시간 (sql의 프로시저 기능 사용을 위하여 default systimestamp 미적용)
);

--회원 제약조건 생성
alter table member add Constraint member_id_pk primary key (id);                           -- 기본키 생성
alter table member add constraint member_email_uk unique(email);                           -- 유니크키 생성
alter table member add constraint member_nickname_uk unique(nickname);                     -- 유니크키 생성
alter table member add constraint member_id_ck check (REGEXP_LIKE(id,'^[A-Za-z0-9]*$'));   -- 정규식을 이용하여 영문 대문자, 소문자, 숫자 조건에 맞는 체크키 생성
alter table member add constraint member_pw_ck check ( REGEXP_LIKE(pw, '[a-z]')
   AND REGEXP_LIKE(pw, '[A-Z]') AND REGEXP_LIKE(pw, '[0-9]')  AND REGEXP_LIKE(pw, '[[:punct:]]')
   AND REGEXP_LIKE(pw, '{8,15}'));                                                         -- 정규식을 이용하여 소문자+대문자+특수문자 포함 8~15자 조건에 맞는 체크키 생성
alter table member add constraint member_name_ck check (REGEXP_LIKE(name,'^[가-힝]*$'));   -- 정규식을 이용하여 한글만 조건에 맞는 체크키 생성
alter table member add constraint member_email_ck check (email like '%@%.%');              -- 정규식을 이용하여 @와 .이 들어가는 이메일 체크키 생성
alter table member add constraint member_admin_fl_ck check (admin_fl in (2,3));            -- 관리지 여부 확인용 체크키 생성 (관리자 2, 사용자3)
alter table member add constraint member_leave_fl_ck check (leave_fl in (0,1));            -- 탈퇴 여부 확인용 체크키 생성 (회원 0, 탈퇴 1)

--회원 샘플데이터 등록 / id 한글 제외, 비밀번호 특수문자 포함 8~15, 이름 한글만 입력, email 형식 확인, 닉네임 유니크 확인
insert into member (id, pw, name, email, nickname, admin_fl) values ('admin', 'Qwert12!', '관리자', 'admin@test.com', '관리자1', 2); 
insert into member (id, pw, name, email, nickname) values ('user1', 'Qwert12!', '전은우', 'user1@test.com', 'ora-01002-error'); 
insert into member (id, pw, name, email, nickname) values ('user2', 'Qwert12!', '유기상', 'user2@test.com', '잠안와');
insert into member (id, pw, name, email, nickname) values ('user3', 'Qwert12!', '최재훈', 'user3@test.com', '회원3');
insert into member (id, pw, name, email, nickname) values ('user4', 'Qwert12!', '최수빈', 'user4@test.com', '회원4');
insert into member (id, pw, name, email, nickname) values ('user5', 'Qwert12!', '박현근', 'user5@test.com', '회원5');

select * from member;

--3. 도서정보
--도서정보 테이블 생성
create table book(
  isbn VARCHAR2(30) not null,           -- 도서번호
  title VARCHAR2(100) not null,         -- 제목
  author VARCHAR2(100),                 -- 저자
  publisher VARCHAR2(100),              -- 출판사
  translator VARCHAR2(100),             -- 번역가
  thumbnail VARCHAR2(200) not null,     -- 표지URL
  publication_dt DATE,                  -- 출판일
  bcontents CLOB                        -- 내용
);

--도서정보 기본키 생성
alter table book add Constraint book_isbn_pk primary key (isbn);    -- 기본키 생성

--도서정보 샘플데이터 등록
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('1158360959 9791158360955',   '마음아 안녕(그림책이참좋아 48)(양장본 HardCover)',   '최숙희',   '책읽는곰',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1585655%3Ftimestamp%3D20220405174305',   '18/05/22',   '힘들어 합니다. 특히나 목소리가 작은 아이, 수줍어서 다른 사람들에게 먼저 말 붙이기도 힘든 아이들에게는 날마다 타인과 관계 맺는 일 자체가 전쟁과도 같습니다. 《너는 기적이야》, 《열두 달 나무 아이》의 저자 최숙희 작가의 『마음아 안녕』은 어린 친구들에게, 그리고 자기 안의 어린이에게, 조금만 더 힘을 내서 마음을 표현해 보라고, 그러면 답답한 상황을 풀어 갈 실마리를 찾을 수 있을 거라고 이야기하는 마음 치유 그림책입니다.  한 아이가 자신의 주변엔' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('129201833X 9781292018331',   'Java',   'Savitch, Mock',   'Pearson',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F3383739%3Ftimestamp%3D20190220072908',   '16/02/01',   'Java: An Introduction to Problem Solving and Programming, 7e, is ideal for introductory Computer Science courses using Java, and other introductory programming courses in departments of Computer Science, Computer Engineering, CIS, MIS, IT, and Business.  Students'); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('8970505458 9788970505459',   '명품 HTML5+CSS3+Javascript 웹 프로그래밍(개정판)',   '황기태',   '생능출판',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5966482%3Ftimestamp%3D20220405185902',   '22/02/10',   '이 책은 HTML5, CSS3, 자바스크립트 언어, HTML5 API를 이용한 웹 애플리케이션을 다룬다. HTML5의 표준 기술을 전반적으로 학습할 수 있는 내용을 갖추고 있다.' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('894331177X 9788943311773',   '해님의 휴가(보드북)',   '변정원',   '보림',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F590495%3Ftimestamp%3D20220405191309',   '18/07/30',   '비가 계속 내리는 장마철이 되었어요. 하늘은 짙은 회색빛을 띄고, 멀리서 우르르 쾅쾅 천둥소리가 울려요. 번개가 번쩍, 하고 내리칠 때에는 굵은 빗줄기가 창문을 타고 줄줄 흐르는 게 보여요. 어제까지만 해도 이 시간에는 햇빛으로 쨍쨍 타올랐는데 말이에요. 해님은 비구름에 가려 보이지 않네요. 비가 ‘열일’하는 지금, 해님은 어디에서 무얼 하고 있을까요? 모처럼 갖게 된 해님의 휴가를 살짝 들여다볼까요?' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('8936455303 9788936455309',   '안녕(양장본 HardCover)',   '안녕달',   '창비',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F533697%3Ftimestamp%3D20220405190658',   '18/07/20',   '《수박 수영장》, 《할머니의 여름휴가》의 저자 안녕달이 그려낸 광활한 우주 속 어느 별에 사는 소시지 할아버지와 개의 아름다운 이야기 『안녕』. 그림책 서사의 새로운 가능성을 탐구하는 저자의 열정이 고스란히 담긴 이 작품은 페이지 총 264면, 662컷의 그림 구성으로 구성되어 있다. 모두 네 편의 이야기로 나누어 소시지 할아버지의 탄생부터 소시지 할아버지와 개의 만남, 이별, 사후 세계의 별에서 지내는 소시지 할아버지의 모습을 담아냈다.  오래되고' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('8943311281 9788943311285',   '안녕, 내 친구!(나비잠)(보드북)',   '로드 캠벨',   '보림',   '이상희',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F590553%3Ftimestamp%3D20220405174844',   '19/11/15',   '1982년 첫 출간 이후, 35년이 넘는 시간동안 꾸준히 판매 기록을 올린 《안녕, 내 친구!》. 전 세계 베스트셀러로, 어린이들의 사랑을 받는 고전 플랩북입니다. 800만부 돌파 기념을 축하하며 한국어판으로 출간하게 되었어요. 이 책은 두꺼운 보드북으로 만들어져 쉽게 망가지지 않아요. 또, 여느 보드북들보다 비교적 가벼운 무게로 어린 친구들도 어렵지 않게 책을 넘겨 볼 수 있답니다.' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('8970940561 9788970940564',   '달님 안녕(2판)(양장본 HardCover)',   '하야시 아키코',   '한림출판사',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F975158%3Ftimestamp%3D20220405175620',   '10/12/24',   '『달님 안녕』은 달님이 점차 환하게 떠오르다가 구름에 가려지고 다시 달님이 모습을 드러내는 늘 볼 수 있는 현상에 의인화하여 섬세하게 표현한 그림책입니다. 단순한 이야기지만 밤하늘과 달님 얼굴, 구름, 집, 고양이 그림이 쉽고 간결한 언어로 어우러져 아름답게 펼쳐집니다. 쪽빛하늘 밑에 어두운 집과 고양이 한 마리가 웅크리고 있습니다. 감청빛으로 변한 하늘과 불이 켜진 집으로 밤을 알리며 어디선가 나타난 고양이가 함께 움직임을 보입니다. 작은 집 뒤로' ); 
insert into book (isbn, title, author, publisher, translator, thumbnail, publication_dt, bcontents) values ('1168410797 9791168410794',   '내일은 실험왕 시즌 2. 2: SNS와 소통의 원리',   '스토리 a.',   '미래엔아이세움',   '-',   'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5936295%3Ftimestamp%3D20220405193719',   '22/01/03',   '내일은 실험왕 시즌 2, 이번엔 미래 과학이다! 개성 만점 주인공들이 펼치는 박진감 넘치는 실험 대결 만화로 꾸준히 사랑받아 온 《내일은 실험왕》 시리즈가 시즌 2로 돌아왔습니다. 시즌 2에서는 초·중등 교과서에 수록된 과학 원리는 물론, 4차 산업 혁명의 시대를 이끌어 갈 어린이들이 꼭 알아야 할 미래 과학 상식을 만화와 정보 페이지로 풀어 냈습니다. 생생한 만화를 통해 미래 과학 기술이 실생활에서 어떻게 활용되는지 살펴보고, 과학 기술의 발전이' ); 

select * from book;

--4. 책장
--책장 테이블 생성
create table book_shelf(
  snum NUMBER(15) not null,     -- 도서등록번호
  spage  NUMBER(15),            -- 총페이지
  sisbn VARCHAR2(30) not null,  -- ISBN
  sid VARCHAR2(40) not null     -- 아이디
);

--책장 제약조건 생성
alter table book_shelf add Constraint book_shelf_snum_pk primary key (snum);                                          -- 기본키 생성
alter table book_shelf add constraint book_shelf_uk unique(sisbn, sid);                                               -- 복합 유니크키 생성(2개의 값이 동일한 경우에 한하여 오류 확인)
alter table book_shelf add constraint book_shelf_sisbn_fk foreign key(sisbn) references book(isbn) on delete cascade; -- sisbn 의 외래키 = 도서정보의 기본키(isbn)
alter table book_shelf add constraint book_shelf_sid_fk foreign key(sid) references member(id) on delete cascade;     -- sid의 외래키 = 회원의 기본키(id)

--책장 시퀀스 생성
create sequence book_shelf_snum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--책장 샘플데이터 등록 / sgroup 독서상태(도서번호 1=예정, 2=읽는중, 3=완독)
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 500, '1158360959 9791158360955', 'user1');
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 400, '894331177X 9788943311773', 'user1');
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 300, '8936455303 9788936455309', 'user1');
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 200, '8970940561 9788970940564', 'user1');
--
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 100, '129201833X 9781292018331', 'user2');
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 200, '8970505458 9788970505459', 'user2');
--
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 400, '8943311281 9788943311285', 'user3');
--insert into book_shelf (snum, spage, sisbn, sid) values (book_shelf_snum_seq.nextval, 500, '1168410797 9791168410794', 'user3');

select * from book_shelf;

--5. 도서기록
--도서기록 테이블 생성
create table document(
  dnum NUMBER(15) not null,       -- 기록No
  ddate DATE not null,            -- 기록일자
  dpage NUMBER(15) not null,      -- 기록쪽수 
  dsnum NUMBER(15) not null,      -- 도서등록No
  did VARCHAR2(40) not null       -- 아이디
);

--도서기록 제약조건 생성
alter table document add Constraint document_dnum_pk primary key (dnum);                                                  -- 기본키 생성
alter table document add constraint document_dsnum_fk foreign key(dsnum) references book_shelf(snum) on delete cascade;   -- dsnum 의 외래키 = 책장의 기본키(snum)
alter table document add constraint document_did_fk foreign key(did) references member(id) on delete cascade;             -- did의 외래키 = 회원의 기본키(id)

--도서기록 시퀀스 생성
create sequence document_dnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--도서기록 샘플데이터 등록
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-01', 20, 1, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-08', 34, 1, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-20', 50, 1, 'user1');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-21', 20, 2, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-22', 25, 2, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-03-22', 34, 2, 'user1');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-01', 20, 3, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-04', 25, 3, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-27', 34, 3, 'user1');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-06', 20, 4, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-12', 25, 4, 'user1');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-13', 34, 4, 'user1');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-05', 20, 1, 'user2');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-12', 25, 1, 'user2');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-13', 34, 1, 'user2');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-18', 20, 2, 'user2');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-19', 25, 2, 'user2');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-22', 34, 2, 'user2');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-01', 20, 1, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-06', 25, 1, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-01-22', 34, 1, 'user3');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-01', 20, 2, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-05', 25, 2, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-06', 34, 2, 'user3');
--
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-02-02', 20, 3, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-03-03', 25, 3, 'user3');  
--insert into document (dnum, ddate, dpage, dsnum, did) values (document_dnum_seq.nextval, '2022-03-09', 34, 3, 'user3');

select * from document;

--6. 리뷰
--리뷰 테이블 생성
create table review(
  rnum NUMBER(15) not null,                -- 리뷰No
  rcontent VARCHAR2(100) not null,         -- 리뷰내용
  rcdate TIMESTAMP default systimestamp,   -- 리뷰작성일
  rudate TIMESTAMP default systimestamp,   -- 리뷰수정일
  rstar NUMBER(1) default 0,               -- 별점
  risbn VARCHAR2(30) not null,             -- 도서번호
  rid  VARCHAR2(40) not null               -- 아이디
);

--리뷰 제약조건 생성
alter table review add Constraint review_rnum_pk primary key (rnum);                                             -- 기본키 생성
alter table review add constraint review_rstar_ck check (rstar in(0,1,2,3,4,5));                                 -- 체크키 생성
alter table review add constraint review_uk unique(risbn, rid);                                                  -- 복합 유니크키 생성(2개의 값이 동일한 경우에 한하여 오류 확인)
alter table review add constraint reviewr_risbn_fk foreign key(risbn) references book(isbn) on delete cascade;   -- risbn 의 외래키 = 도서의 기본키(isbn)
alter table review add constraint review_rid_fk foreign key(rid) references member(id) on delete cascade;        -- rid의 외래키 = 회원의 기본키(id)

--리뷰 시퀀스 생성
create sequence review_rnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--리뷰 샘플데이터 등록
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '리뷰받아라~!', 2, '1158360959 9791158360955', 'user1');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '재밌는데?', 5, '1158360959 9791158360955', 'user2');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '이 책 보지마세요', 3, '129201833X 9781292018331', 'user2');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '보지말라면서 별점은 5?', 1, '129201833X 9781292018331', 'user3');
insert into review (rnum, rcontent, rstar, risbn, rid) values (review_rnum_seq.nextval, '킬링타임용', 2, '129201833X 9781292018331', 'admin');

select * from review;

--7. 코드
--코드 테이블 생성
create table code(
    code_id     varchar2(11),                   -- 코드
    decode      varchar2(30),                   -- 코드명
    discript    clob,                           -- 코드설명
    pcode_id    varchar2(11),                   -- 상위코드
    useyn       char(1) default 'Y',            -- 사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp, -- 생성일시
    udate       timestamp default systimestamp  -- 수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
alter table code add constraint bbs_pcode_id_fk
    foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--샘플데이터 of code
insert into code (code_id,decode,pcode_id,useyn) values ('C01','커뮤니티',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('C0101','게시판','C01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('C0102','공지사항','C01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('C0103','QNA','C01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F01','첨부',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0101','파일','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0102','이미지','F01','Y');

--7. 게시판
--게시판 테이블 생성
create table board(
  bnum NUMBER(15) not null,                   -- 게시글번호
  bcategory varchar2(11),                     -- 분류카테고리
  btitle VARCHAR2(60) not null,               -- 게시글제목
  bcdate TIMESTAMP default systimestamp,      -- 게시글작성일
  budate TIMESTAMP default systimestamp,      -- 게시글수정일
  bcontent CLOB not null,                     -- 게시글내용
  bhit NUMBER(5) default 0,                   -- 게시글조회수
  bstatus char(1) default 'E',                -- 게시글 상태(E:exist, D:delete)
  bid VARCHAR2(40) not null                   -- 아이디
);

--게시판 제약조건 생성
alter table board add Constraint board_bnum_pk primary key (bnum);                                        -- 기본키 생성
alter table board add constraint board_bid_fk foreign key(bid) references member(id) on delete cascade;   -- bid의 외래키 = 회원의 기본키(id)
alter table board add constraint board_bstatus_ck check (bstatus in ('E','D'));                           -- 게시글 상태(E:exist, D:delete)
alter table board add constraint board_bcategory_fk foreign key(bcategory) references code(code_id);

--게시판 시퀀스 생성
create sequence board_bnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--게시판 샘플데이터 등록
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '볼만한 책 소개합니다', '그런거 없어', 'user1'); 
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', 'sf소설 추천 좀', '제곧내', 'user1'); 
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목1', '게시판내용1', 'user2'); 
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목2', '게시판내용2', 'admin');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목3', '게시판내용3', 'admin');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목4', '게시판내용4', 'user3');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목5', '게시판내용5', 'user4');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목6', '게시판내용6', 'user3');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목7', '게시판내용7', 'admin');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목8', '게시판내용8', 'user2');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목9', '게시판내용9', 'user1');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목10', '게시판내용10', 'user2');
insert into board (bnum, bcategory, btitle, bcontent, bid) values (board_bnum_seq.nextval, 'C0101', '게시판제목11', '게시판내용11', 'admin');

select * from board;

--8. 게시판_댓글
--게시판_댓글 테이블 생성
create table comments(
  cnum NUMBER(15) not null,                 -- 댓글번호
  ccdate TIMESTAMP default systimestamp,    -- 댓글작성일
  cudate TIMESTAMP default systimestamp,    -- 댓글수정일
  ccontent VARCHAR2(50) not null,           -- 댓글내용
  cid VARCHAR2(40) not null,                -- 아이디
  cbnum NUMBER(15) not null                 -- 게시글번호
);

--게시판_댓글 제약조건 생성
alter table comments add Constraint comments_cnum_pk primary key (cnum);                                            -- 기본키 생성
alter table comments add constraint comments_cid_fk foreign key(cid) references member(id) on delete cascade;       -- cid의 외래키 = 회원의 기본키(id)
alter table comments add constraint comments_cbnum_fk foreign key(cbnum) references board(bnum) on delete cascade;  -- cbnum의 외래키 = 게시판의 기본키(bnum)

--게시판_댓글 시퀀스 생성
create sequence comments_cnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--게시판_댓글 샘플데이터 등록
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글1', 'user2', 1); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글2', 'admin', 1); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글3', 'user2', 2); 
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글4', 'user3', 3);
insert into comments (cnum, ccontent, cid, cbnum) values (comments_cnum_seq.nextval, '게시판_댓글5', 'user4', 3); 

select * from comments;

commit;
--9. 파일첨부
--파일첨부 테이블 생성
create table uploadfile(
  fnum NUMBER(15) not null,                     -- 첨부파일번호
  rnum NUMBER(15) not null,                     -- 참조번호(게시글번호 등)
  code varchar2(11),                            -- 분류코드
  store_filename VARCHAR2(50) not null,         -- 로컬파일명
  upload_filename VARCHAR2(50) not null,        -- 업로드파일명
  fsize VARCHAR2(50) not null,                  -- 파일크기(단위 byte)
  ftype VARCHAR2(50) not null,                  -- 파일타입(mimetype)
  fcdate TIMESTAMP default systimestamp,        -- 첨부날자
  fudate TIMESTAMP default systimestamp         -- 첨부수정날자
);

--파일첨부 제약조건 생성
alter table uploadfile add Constraint uploadfile_fnum_pk primary key (fnum);                            -- 기본키 생성
alter table uploadfile add constraint uploadfile_code_fk foreign key(code) references code(code_id);    -- 외래키 생성

--파일첨부 시퀀스 생성
create sequence uploadfile_fnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--select * from uploadfile;

--10. 공지사항
--공지사항 테이블 생성
create table notice(
  nnum NUMBER(15) not null,         -- 공지사항번호
  ncategory varchar2(11),           -- 분류카테고리
  ntitle VARCHAR2(60) not null,     -- 공지사항제목
  ncontent CLOB not null,           -- 공지사항내용
  nhit NUMBER(5) default 0,         -- 조회수  
  ncdate TIMESTAMP default systimestamp, -- 공지사항작성일
  nudate TIMESTAMP default systimestamp  -- 공지사항수정일  
);

--공지사항 기본키 생성
alter table notice add Constraint notice_nnum_pk primary key (nnum);     -- 기본키 생성
alter table notice add constraint notice_ncategory_fk foreign key(ncategory) references code(code_id);

--공지사항 시퀀스 생성
create sequence notice_nnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--공지사항 샘플데이터 생성
insert into notice (nnum, ncategory, ntitle, ncontent)
values (notice_nnum_seq.nextval, 'C0102', '사이트 이용방법', '사용자의 독서 기록을 등록할 수 있도록 지원합니다.'); 
insert into notice (nnum, ncategory, ntitle, ncontent)
values (notice_nnum_seq.nextval, 'C0102', '게시판 이용규칙', '욕설 폭언 등 상대방에게 불쾌감을 줄 수 있는 단어는 사용하지 마세요');
insert into notice (nnum, ncategory, ntitle, ncontent)
values (notice_nnum_seq.nextval, 'C0102', '개인정보처리방침 개정 안내', '개인정보처리방침이 아래와 같이 일부 개정될 예정입니다.');
insert into notice (nnum, ncategory, ntitle, ncontent)
values (notice_nnum_seq.nextval, 'C0102', '서버 점검 안내', '고객님들의 너그러운 양해 바랍니다.');
insert into notice (nnum, ncategory, ntitle, ncontent)
values (notice_nnum_seq.nextval, 'C0102', '검색이 업데이트 되었습니다.', '이제는 공백과 유사어를 검색하여 출력합니다.');

select * from notice;

--11. Q&A
--Q&A 테이블 생성
create table qna(
    qnum      number(10),                             -- 게시글 번호
    qcategory varchar2(11),                           -- 분류카테고리
    qtitle    varchar2(150),                          -- 제목
    qnickname varchar2(30),                           -- 별칭
    qhit      number(5) default 0,                    -- 조회수
    qcontent  clob,                                   -- 본문
    pqnum     number(10),                             -- 부모 게시글번호
    qgroup    number(10),                             -- 답글그룹
    qstep     number(3) default 0,                    -- 답글단계
    qindent   number(3) default 0,                    -- 답글들여쓰기
    qstatus   char(1) default 'Q',                    -- 답글상태  (답글: 'A', 원글'Q')
    qcdate    timestamp default systimestamp,         -- 생성일시
    qudate    timestamp default systimestamp          -- 수정일시
);

--Q&A 제약조건 생성
alter table qna add Constraint qna_qnum_pk primary key (qnum);
alter table qna add constraint qna_pqnum_fk
    foreign key(pqnum) references qna(qnum);
alter table qna modify qtitle constraint qna_qtitle_nn not null;
alter table qna modify qnickname constraint qna_qnickname_nn not null;
alter table qna modify qcontent constraint qna_qcontent_nn not null;
alter table qna add constraint qna_qstatus_ck check(qstatus in ('A','Q')); --답글상태  (답글: 'A', 원글'Q')
alter table qna add constraint qna_qcategory_fk foreign key(qcategory) references code(code_id);

--Q&A 시퀀스 생성
create sequence qna_qnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0 --최소값
maxvalue 9999999999 --최대값
nocache --시퀀스 순차 증가 오류에 대응을 하지만 메모리에 미리 할당하지 않음에 대량으로 필요할 시 병목 현상 발생
nocycle; --순환하지않음

--원글 샘플데이터 등록    
insert into qna (qnum,qcategory,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'C0103','독서 기록은 어떻게 하나요?','홍길동','기록은 어떻게 하면 되나요?',qna_qnum_seq.currval);
insert into qna (qnum,qcategory,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'C0103','회원 탈퇴는 어떻게 하나요?','홍길순','탈퇴는 어떻게 하면 되나요?',qna_qnum_seq.currval);
insert into qna (qnum,qcategory,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'C0103','회원정보 수정은 어떤 걸 할 수 있나요?','홍길남','정보 수정은 어떤 걸 할 수 있나요?',qna_qnum_seq.currval);
insert into qna (qnum,qcategory,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'C0103','카카오톡 로그인은 없나요?','홍길북','카톡 로그인은 없나요?',qna_qnum_seq.currval);
insert into qna (qnum,qcategory,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'C0103','달력은 무엇인가요?','홍길여','달력은 무엇인가요?',qna_qnum_seq.currval);

--답글 샘플데이터 등록
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'ㄴ답변:독서 기록은 어떻게 하나요?','관리자',1,'책장을 이용하여 작성하며 됩니다.',1,1,1,'A');
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'ㄴ답변:회원 탈퇴는 어떻게 하나요?','관리자',2,'마이페이지에서 하시면 됩니다.',2,1,1,'A');
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'ㄴ답변:회원정보 수정은 어떤 걸 할 수 있나요?','관리자',3,'이메일과 별칭이 수정됩니다.',3,1,1,'A');
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'ㄴ답변:카카오톡 로그인은 없나요?','관리자',4,'추후 업데이트할 예정입니다.',4,1,1,'A');
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'ㄴ답변:달력은 무엇인가요?','관리자',5,'읽은 책들을 표시해 주는 기능입니다.',5,1,1,'A');

commit;