BEGIN 
DBMS_SCHEDULER.CREATE_JOB ( 
JOB_NAME => 'day_delete_member_JOB' , 
START_DATE => TRUNC(SYSDATE), 
--START_DATE => systimestamp, 
REPEAT_INTERVAL => 'FREQ=MINUTELY;INTERVAL=3;' ,      -- 3분마다 day_delete_member_JOB 스케줄러를 적용하여 삭제 기능을 활성화함
--repeat_interval => 'FREQ=daily=06',
END_DATE => NULL , 
JOB_CLASS => 'DEFAULT_JOB_CLASS' , 
JOB_TYPE => 'STORED_PROCEDURE' , 
JOB_ACTION => 'day_delete_member' , 
COMMENTS => 'JOB 회원 탈퇴 처리' ); 
DBMS_SCHEDULER.ENABLE('day_delete_member_JOB'); 
END;


-- sql 점유율이 높을 수 있음에 테스트 후에 꼭 스케줄러 중지 실행 할 것 ( 스케줄러 중지 및 재시작 이용 할 것)


