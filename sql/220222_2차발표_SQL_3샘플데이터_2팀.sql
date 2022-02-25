-- 샘플 데이터 입력

-- 회원 등록
insert into member (id, pw, name, email, nickname)
values ('admin', '1234', '관리자', 'admin@test.com', '관리자1'); 
insert into member (id, pw, name, email, nickname)
values ('user1', '1234', '사용자', 'user1@test.com', '회원1');
insert into member (id, pw, name, email, nickname)
values ('user2', '1234', '사용자', 'user1@test.com', '회원1');     -- 이메밀, 닉네임을 각각 유니크 처리
insert into member (id, pw, name, email, nickname)
values ('user2', '1234', '사용자', 'user2@test.com', '회원2');
insert into member (id, pw, name, email, nickname)
values ('user3', '1234', '사용자', 'user3@test.com', '회원3');

-- 도서 정보 등록
insert into book (isbn, title, thumbnail)
values (1234567890123, '어서와요 리플입니다', '2팀'); 
insert into book (isbn, title, thumbnail)
values (1234567550123, '리플을 알려드립니다', '2팀'); 


-- 책장 등록
insert into book_shelf (snum, sgroup, sisbn, sid)
values (book_shelf_snum_seq.nextval, 2, 1234567550123, 'user1'); 
insert into book_shelf (snum, sgroup, sisbn, sid)
values (book_shelf_snum_seq.nextval, 1, 1234567550123, 'user1');  -- sisbn, sid 2개를 1쌍으로 보니 유니크(복합 유니크)임에 따라 2개의 값이 동일할 경우에 한하여 오류
insert into book_shelf (snum, sgroup, sisbn, sid)
values (book_shelf_snum_seq.nextval, 1, 1234567890123, 'user1'); 
insert into book_shelf (snum, sgroup, sisbn, sid)
values (book_shelf_snum_seq.nextval, 1, 1234567890123, 'admin'); 


-- 도서기록
insert into document (dnum, ddate, dpage, dgroup, dsnum, did)
values (document_dnum_seq.nextval, '2022-02-21', 10, 1, 1, 'user1');  -- dgroup의 check 값이 2, 3임에 따라 오류
insert into document (dnum, ddate, dpage, dgroup, dsnum, did)
values (document_dnum_seq.nextval, '2022-02-20', 20, 2, 1, 'user1'); 


-- 리뷰
insert into review (rnum, rcontent, rstar, risbn, rid)
values (review_rnum_seq.nextval, '너무 재미있습니다. 다음에 또 다른 책을 보고 싶네요', 2, 1234567890123, 'user1'); 
insert into review (rnum, rcontent, rstar, risbn, rid)
values (review_rnum_seq.nextval, '너무 재미있습니다. 너무 재미있어요~~~', 3, 1234567890123, 'user1');    -- risbn, rid 2개를 1쌍으로 보니 유니크(복합 유니크)임에 따라 2개의 값이 동일할 경우에 한하여 오류
insert into review (rnum, rcontent, rstar, risbn, rid)
values (review_rnum_seq.nextval, '너무 재미있습니다. 다음에 또 다른 책을 보고 싶네요', 3, 1234567890123, 'admin'); 

-- 게시판
insert into board (bnum, btitle, bcontent, bhit, bid)
values (board_bnum_seq.nextval, '이렇게 작성하나요?', '작성하는 방법은 이것이 맞을까요? 맞겠죠?!!', 1, 'user1'); 
insert into board (bnum, btitle, bcontent, bhit, bid)
values (board_bnum_seq.nextval, '오늘 재미있네요', '시간이 모자르게 재미있네요', 1, 'admin'); 

-- 게시판_댓글
insert into comments (cnum, ccontent, cid, cbnum)
values (comments_cnum_seq.nextval, '맞게 작성하고 있어요. 괜찮아요', 'admin', 2); 
insert into comments (cnum, ccontent, cid, cbnum)
values (comments_cnum_seq.nextval, '맞게 작성하고 있어요. 괜찮아요', 'admin', 2); 

-- 공지사항
insert into notion (nnum, ntitle, ncontent, nid)
values (notion_nnum_seq.nextval, '사이트 이용방법', '사용자의 독서 기록을 등록할 수 있도록 지원합니다.', 'admin'); 
insert into notion (nnum, ntitle, ncontent, nid)
values (notion_nnum_seq.nextval, '게시판 이용규칙', '욕설 폭언 등 상대방에게 불쾌감을 줄 수 있는 단어는 사용하지 마세요', 'admin'); 

-- Q&A_Q
insert into question (qnum, qtitle, qcontent, qid)
values (question_qnum_seq.nextval, '독서 기록은 어떻게 하나요?', '기록은 어떻게 하면 되나요?', 'user1'); 

-- Q&A_A
insert into answer (anum, atitle, acontent, aqnum, aid)
values (question_anum_seq.nextval, '이렇게 하면 됩니다.', '책장을 이용하여 작성하며 됩니다.', 1, 'admin'); 


commit;
