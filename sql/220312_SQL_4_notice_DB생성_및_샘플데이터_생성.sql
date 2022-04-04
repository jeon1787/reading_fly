-- 공지사항
drop table notion;
drop sequence notion_nnum_seq;
drop table notice;
drop sequence notice_nnum_seq;

create table notice(
  nnum NUMBER(15) not null,         -- 공지사항번호
  ntitle VARCHAR2(60) not null,     -- 공지사항제목
  ncontent CLOB not null,           -- 공지사항내용
  nhit NUMBER(5) default 0,          -- 조회수 
  ncdate TIMESTAMP default systimestamp, -- 공지사항작성일
  nudate TIMESTAMP default systimestamp -- 공지사항수정일
);

-- 1) 공지사항 기본키 생성
alter table notice add Constraint notice_nnum_pk primary key (nnum);                                        -- 기본키 생성

-- 2) 공지사항 시퀀스
create sequence notice_nnum_seq
start with 1 --시작값
increment by 1 --증감치
minvalue 0    -- 최소값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

-- 3) 시퀸스 번호 증가 오류 대응(순차적 적용)
alter sequence notice_nnum_seq nocache;

-- 공지사항
insert into notice (nnum, ntitle, ncontent)
values (notice_nnum_seq.nextval, '사이트 이용방법', '사용자의 독서 기록을 등록할 수 있도록 지원합니다.'); 
insert into notice (nnum, ntitle, ncontent)
values (notice_nnum_seq.nextval, '게시판 이용규칙', '욕설 폭언 등 상대방에게 불쾌감을 줄 수 있는 단어는 사용하지 마세요'); 
commit;

--샘플데이터
--상세조회
select ntitle ,ncontent ,nudate 
  from notice
 where nnum  = 1;
 
 --수정
update notice
  set ntitle = '수정후 제목',
      ncontent = '수정후 본문',
      nudate  = systimestamp
 where nnum  = 1;      
      
 --삭제
delete from notice
 where nnum = 1;

--전체조회
select * from notice;

--조회수
update notice
  set nhit = nhit + 1
 where nnum = 1;
 
 commit;