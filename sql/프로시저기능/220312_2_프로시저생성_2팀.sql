create or replace PROCEDURE day_delete_member 
--AS 
is
BEGIN
  delete from member where leave_fl=1 and leave_dt < sysdate;   -- leave_fl=1을 추가하여 대상을 정확하게 확인함
--  delete from member where leave_dt < sysdate -1;
  commit;
  
-- update member set leave_fl = 1, leave_dt = systimestamp where id = 'user10' and  pw = 'Qwert12!'; 을 실행 후
-- commit; 을 실시하였는지 꼭 확인 할 것
  
END;


-- 프로시저는 현재의 sql문을 모두 가져감(주석 포함)에 따라 END; 아래에 다른 내용이 있을 경우 실행시 오류가 발생함
