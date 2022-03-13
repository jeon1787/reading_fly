-- 탈퇴 회원에 대한 스케줄러 등록을 위한 데이터 입력

select * from member;

insert into member (id, pw, name, email, nickname) values ('user10', 'Qwert12!', '홍길니', 'user10@test.com', '회원10');

update member set leave_fl = 1, leave_dt = systimestamp where id = 'user10' and  pw = 'Qwert12!';

select * from member;

insert into member (id, pw, name, email, nickname) values ('user11', 'Qwert12!', '홍길다', 'user11@test.com', '회원11');

update member set leave_fl = 1, leave_dt = systimestamp where id = 'user11' and  pw = 'Qwert12!';

select * from member;

commit;


-- 시스템용
--ALTER SYSTEM SET JOB_QUEUE_PROCESSES = 10;
