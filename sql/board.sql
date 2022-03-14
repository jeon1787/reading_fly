-- 게시판
drop table board;

create table board(
  bnum NUMBER(15) not null,         -- 게시글번호
  btitle VARCHAR2(50) not null,     -- 게시글제목
  bcdate TIMESTAMP default systimestamp, -- 게시글작성일
  budate TIMESTAMP default systimestamp, -- 게시글수정일
  bcontent CLOB not null,           -- 게시글내용
  bhit NUMBER(5) default 0,         -- 게시글조회수
  bstatus char(1) default 'E',        --게시글 상태(E:exist, D:delete)
  bid VARCHAR2(40) not null         -- 아이디
);

-- 게시글 목록조회
select row_number() over (order by bcdate) as num, bnum, btitle, bcdate, budate, bhit, nickname
from board, member
where board.bid = member.id
order by bcdate desc;

-- 게시글 상세조회
select bnum, btitle, bcdate, budate, bcontent, bhit, nickname
from board, member
where board.bid = member.id and bnum = 4;

--게시판 작성
insert into board (bnum, btitle, bcontent, bid)
values (board_bnum_seq.nextval, '게시판제목5', '게시판내용5', 'user1');

--게시글 내용 수정
update board
set btitle = '제목1수정', bcontent = '내용1수정', budate = systimestamp
where bnum = 1 and bid = 'user1';

--게시글 삭제
--댓글 없는 게시글 삭제
delete from board
where bnum = 15 and bid = 'user3';

--댓글 있는 게시글 삭제
update board
set bstatus = 'D', bcontent = '삭제된 게시글입니다'
where bnum = 1 and bid = 'user1';

--게시글 조회수 증가
update board
set bhit = bhit+1
where bnum = 1;

rollback;