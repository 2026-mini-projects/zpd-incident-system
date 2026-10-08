-- 1. DB(스키마) 생성
CREATE DATABASE IF NOT EXISTS zpd_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. 프로젝트 전용 계정 생성
CREATE USER IF NOT EXISTS 'zpd_admin'@'localhost' IDENTIFIED BY 'zpd_admin';

-- 3. 계정에 zpd_db 전체 권한 부여
GRANT ALL PRIVILEGES ON zpd_db.* TO 'zpd_admin'@'localhost';

-- 4. 변경 권한 즉시 적용
FLUSH PRIVILEGES;