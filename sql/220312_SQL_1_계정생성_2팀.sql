-- 계정 삭제 (모든 계정이 관련된 것을 삭제)  -> SYSTEM으로 실행
DROP USER c##reading_fly CASCADE;
-- 계정 생성하면서 할당
CREATE USER c##reading_fly IDENTIFIED BY 1234 DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp PROFILE DEFAULT;
-- 계정 권한 설정
GRANT CONNECT, RESOURCE TO c##reading_fly; 
GRANT CREATE VIEW, CREATE SYNONYM TO c##reading_fly; 
GRANT UNLIMITED TABLESPACE TO c##reading_fly; 
ALTER USER c##reading_fly ACCOUNT UNLOCK;

-- 스케줄러 사용 권한 추가
grant create any job to c##reading_fly;