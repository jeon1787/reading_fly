drop table notice; -- 공지사항
create table notice(
  nnum NUMBER(15) not null,         -- 공지사항번호
  ntitle VARCHAR2(60) not null,     -- 공지사항제목
  ncontent CLOB not null,          -- 공지사항내용
  nhit NUMBER(5) default 0,        -- 조회수  
  ncdate TIMESTAMP default systimestamp, -- 공지사항작성일
  nudate TIMESTAMP default systimestamp  -- 공지사항수정일  
);

-- 1) 공지사항 기본키 생성
alter table notice add Constraint notice_nnum_pk primary key (nnum);                                        -- 기본키 생성

drop sequence notice_nnum_seq;  --공지사항 시퀀스 삭제
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

--Q&A
drop table qna;
create table qna(
    qnum        number(10),         --게시글 번호
    qtitle       varchar2(150),      --제목
    qnickname    varchar2(30),       --별칭
    qhit         number(5) default 0,          --조회수
    qcontent    clob,               --본문
    pqnum       number(10),         --부모 게시글번호
    qgroup      number(10),         --답글그룹
    qstep        number(3) default 0,          --답글단계
    qindent     number(3) default 0,          --답글들여쓰기
    qstatus      char(1) default 'Q',              --답글상태  (답글: 'A', 원글'Q')
    qcdate       timestamp default systimestamp,         --생성일시
    qudate       timestamp default systimestamp          --수정일시
);

--기본키
alter table qna add Constraint qna_qnum_pk primary key (qnum);

--외래키
alter table qna add constraint qna_pqnum_fk
    foreign key(pqnum) references qna(qnum);

--제약조건
alter table qna modify qtitle constraint qna_qtitle_nn not null;
alter table qna modify qnickname constraint qna_qnickname_nn not null;
alter table qna modify qcontent constraint qna_qcontent_nn not null;

--시퀀스
drop sequence qna_qnum_seq;
create sequence qna_qnum_seq;

--원글작성    
insert into qna (qnum,qtitle,qnickname,qcontent,qgroup)
values(qna_qnum_seq.nextval,'독서 기록은 어떻게 하나요?','테스터1','기록은 어떻게 하면 되나요?',qna_qnum_seq.currval);

--답글작성
insert into qna (qnum,qtitle,qnickname,pqnum,qcontent,qgroup,qstep,qindent,qstatus)
values(qna_qnum_seq.nextval,'이렇게 하면 됩니다.','관리자',1,'책장을 이용하여 작성하며 됩니다.',1,1,1,'A');

--최신글 번호 조회
select qna_qnum_seq.currval from dual;

--상세조회
select * from qna;
select qnum,qtitle,qnickname,qcontent,qgroup
  from qna
 where qnum = 1;
 
 --삭제(오류있음)
DELETE FROM qna
 WHERE qnum = 1;
 
--수정 
UPDATE qna
  SET qtitle = '수정후 제목1',
      qcontent = '수정후 본문1',
      qudate = systimestamp
 WHERE qnum = 1;     

--조회 증가
update qna
  set qhit = qhit + 1
 where qnum = 1; 

--게시글 총수
select count(*) from qna;

--전체조회
select * from qna;

commit;