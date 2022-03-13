
-- 스케줄러 작업 중지
EXECUTE DBMS_SCHEDULER.DISABLE('day_delete_member_JOB');

-- 스케줄러 작업 실시
EXECUTE DBMS_SCHEDULER.ENABLE('day_delete_member_JOB');


