-- 회원가입
insert into member (id, pw, name, email, nickname) values ('user5', 'Qwert12!', '홍길길', 'user5@test.com', '회원5'); 

-- 아이디 중복 확인 : 중복의 경우 값이 1
select count(id) from member where id = 'user2';
select count(email) from member where email = 'user2@test.com';
select count(nickname) from member where nickname = '회원2';

-- 로그인 : 값이 동일한지 확인
select id, pw from member where id = 'user5' and pw = 'Qwert12!' and leave_fl = 0;

-- 아이디 찾기 / pw 찾기
select id from member where name = '홍길남' and email = 'user2@test.com' and leave_fl = 0;
select pw from member where id = 'user2' and name = '홍길남' and email = 'user2@test.com' and leave_fl = 0;

-- 관리자 회원탈퇴 여부 확인 : 탙퇴가 맞으면 값이 1
select count(id) from member where id = 'user2' and leave_fl = 1;

-- 관리자 회원 목록 조회 : 탈퇴하지 않은 id 확인
select id from member where leave_fl = 0;

-- 회원정보 상세 검색
select id, name, email, nickname from member where id = 'user5' and pw = 'Qwert12!' and leave_fl = 0;

-- 회원정보 상세 수정
update member set nickname = '회원5수정' where id = 'user5' and pw = 'Qwert12!' and leave_fl = 0;

-- 회원정보 상세 수정 비밀번호 확인 : 값이 1
select count(pw) from member where id = 'user5' and pw = 'Qwert12!';

-- 회원탈퇴
update member set leave_fl = 1, leave_dt = systimestamp where id = 'user5' and  pw = 'Qwert12!';

select * from member;


-- 책장 등록의 도서 제목 조회
select book.title from book_shelf, book, member where book_shelf.sisbn = book.isbn and book_shelf.sid = member.id and sid = 'user2';

-- 도서 총페이지 수정
update book_shelf set spage = 200 where sid = 'user1' and sisbn = 1234567890123;

-- 독서기록 시작
update book_shelf set sgroup = 2 where sid = 'user1' and sisbn = 2987654321098;

-- 독서기록 수정
update document set dpage = 23 where did = 'user1' and dsnum = 1 and dnum = 1;

-- 독서기록 삭제
delete from document where dnum = 1 and did = 'user1';

-- 독서기록 전체 조회
select row_number() over (order by ddate) as num, ddate, dpage from document where dsnum = 1 and did = 'user1' order by ddate ;

-- 달력일자별 조회
--select row_number() over (order by ddate, dgroup) as num, dgroup, ddate, dpage from  document where did = 'user1' order by ddate, dgroup;

-- 달력일자별 표지 조회
select document.ddate, document.dgroup, book.thumbnail from document, book_shelf, book where document.dsnum = book_shelf.snum and book_shelf.sisbn = book.isbn and document.dgroup = 2 and document.did = 'user1';

-- 도서 리뷰 수정
update review set rcontent = '리뷰1수정', rudate = systimestamp where rnum = 3 and rid = 'user2';

-- 도서 리뷰 전체 조회
select row_number() over (order by rcdate) as num, rcontent, rstar,rcdate, rudate, rid  from review where risbn = '1234567890123' order by rcdate desc;

-- 게시글 목록조회
select row_number() over (order by bcdate) as num, btitle, bcdate,budate,bhit, bid from board order by bcdate desc;

-- 게시글 상세조회
select bnum, btitle, bcontent, bcdate, budate, bid from board where bid = 'user1' and bnum=1 ;
-- 게시글 내용 수정
update board set bcontent = '내용1수정', budate = systimestamp where bnum = 1 and bid = 'user1';


-- 게시글 목록조회
select row_number() over (order by bcdate) as num, bnum, btitle, bcdate, budate, bhit, nickname from board, member where board.bid = member.id order by bcdate desc;

-- 게시글 상세조회
select bnum, btitle, bcdate, budate, bcontent, bhit, nickname from board, member where board.bid = member.id and bnum = 4;

--게시판 작성
insert into board (bnum, btitle, bcontent, bid) values (board_bnum_seq.nextval, '게시판제목5', '게시판내용5', 'user1');

--게시글 내용 수정
update board set btitle = '제목1수정', bcontent = '내용1수정', budate = systimestamp where bnum = 1 and bid = 'user1';

--게시글 삭제
--댓글 없는 게시글 삭제
delete from board where bnum = 15 and bid = 'user3';

--댓글 있는 게시글 삭제
update board set bstatus = 'D', bcontent = '삭제된 게시글입니다' where bnum = 1 and bid = 'user1';

--게시글 조회수 증가
update board set bhit = bhit+1 where bnum = 1;


-- 댓글 불러오기
select row_number() over (order by ccdate) as num, ccdate, ccontent, cid from comments where cbnum = 1 order by ccdate desc;


-- 댓글 수정
update comments set ccontent = '댓글1수정', cudate = systimestamp where cnum = 1 and cid = 'user2';

-- 게시글 첨부파일 등록
insert into upload_file (fnum, fbnum, store_fname, upload_fname, fsize, ftype) values (upload_file_fnum_seq.nextval, 1,  '파일1', '파일1', '1mbyte',  'img'); 


-- 게시글 첨부파일 수정
update upload_file set store_fname = '파일1수정', upload_fname = '파일1수정', fsize = '1mbyte', ftype = 'img', fudate = systimestamp where fbnum = 1;

commit;

