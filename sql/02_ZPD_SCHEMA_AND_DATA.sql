USE zpd_db;

-- 1. 외래키 종속성에 따른 기존 테이블 역순 삭제
DROP TABLE IF EXISTS zpd_case;
DROP TABLE IF EXISTS zpd_officer;
DROP TABLE IF EXISTS zpd_user;

-- 2. 사용자 계정 테이블 (ADMIN, OFFICER, CITIZEN 공통)
CREATE TABLE zpd_user (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 식별자',
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '로그인 아이디',
                          password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호',
                          nickname VARCHAR(50) NOT NULL COMMENT '사용자 이름/닉네임',
                          role VARCHAR(20) NOT NULL COMMENT '권한 (ADMIN, OFFICER, CITIZEN)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 경찰관 프로필 테이블 (1001번 사번부터 시작)
CREATE TABLE zpd_officer (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '경찰관 고유 사번',
                             name VARCHAR(50) NOT NULL COMMENT '경찰관 풀네임',
                             species VARCHAR(50) NOT NULL COMMENT '동물 종족 (Enum 매핑 코드)',
                             size VARCHAR(20) NOT NULL COMMENT '체급 (SMALL, MEDIUM, LARGE)',
                             district VARCHAR(30) NOT NULL COMMENT '관할 구역',
                             status VARCHAR(20) NOT NULL DEFAULT 'STANDBY' COMMENT '근무 상태 (STANDBY, WORKING, RETIRED)',
                             user_id BIGINT NOT NULL UNIQUE COMMENT '연결 계정 식별자 (zpd_user.id)',
                             CONSTRAINT fk_zpd_officer_user FOREIGN KEY (user_id) REFERENCES zpd_user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 사건 관리 테이블 (초 단위 표기 DATETIME)
CREATE TABLE zpd_case (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사건 번호',
                          title VARCHAR(100) NOT NULL COMMENT '사건 제목',
                          content TEXT NOT NULL COMMENT '사건 상세 내용',
                          district VARCHAR(30) NOT NULL COMMENT '발생 구역',
                          status VARCHAR(30) NOT NULL DEFAULT 'WAITING' COMMENT '사건 상태 (WAITING, IN_PROGRESS, SOLVED, FALSE_ALARM, CLOSED, CANCELLED)',
                          reporter_id BIGINT NOT NULL COMMENT '신고 시민 식별자 (zpd_user.id)',
                          officer_id BIGINT NULL COMMENT '담당 경찰관 식별자 (zpd_officer.id)',
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사건 접수 일시',
                          closed_at DATETIME NULL COMMENT '종결/취소 일시',
                          CONSTRAINT fk_zpd_case_reporter FOREIGN KEY (reporter_id) REFERENCES zpd_user(id),
                          CONSTRAINT fk_zpd_case_officer FOREIGN KEY (officer_id) REFERENCES zpd_officer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================================
-- 초기 시드 데이터 (Seed Data)
-- ========================================================

-- [1] 계정 데이터 (zpd_user: 관리자 1 + 경찰관 10 + 시민 10 = 총 21명)
INSERT INTO zpd_user (id, username, password, nickname, role) VALUES
-- 관리자
(1, 'admin', 'admin1234', '보고 서장', 'ADMIN'),

-- 경찰관 계정 (officer01 ~ officer10 / 비밀번호: officer1234)
(2, 'officer01', 'officer1234', '주디 홉스', 'OFFICER'),
(3, 'officer02', 'officer1234', '닉 와일드', 'OFFICER'),
(4, 'officer03', 'officer1234', '벤자민 클로하우저', 'OFFICER'),
(5, 'officer04', 'officer1234', '피터 맥혼', 'OFFICER'),
(6, 'officer05', 'officer1234', '패트릭 팽마이어', 'OFFICER'),
(7, 'officer06', 'officer1234', '델가토', 'OFFICER'),
(8, 'officer07', 'officer1234', '히긴스', 'OFFICER'),
(9, 'officer08', 'officer1234', '프랜신 페닝턴', 'OFFICER'),
(10, 'officer09', 'officer1234', '울포드', 'OFFICER'),
(11, 'officer10', 'officer1234', '보그 형사', 'OFFICER'),

-- 시민 계정 (citizen01 ~ citizen10 / 비밀번호: citizen1234)
(12, 'citizen01', 'citizen1234', '플래시 슬로스모어', 'CITIZEN'),
(13, 'citizen02', 'citizen1234', '프리실라 슬로스', 'CITIZEN'),
(14, 'citizen03', 'citizen1234', '미스터 빅', 'CITIZEN'),
(15, 'citizen04', 'citizen1234', '프루 프루', 'CITIZEN'),
(16, 'citizen05', 'citizen1234', '던 벨웨더', 'CITIZEN'),
(17, 'citizen06', 'citizen1234', '리오도어 라이언하트', 'CITIZEN'),
(18, 'citizen07', 'citizen1234', '가젤', 'CITIZEN'),
(19, 'citizen08', 'citizen1234', '듀크 위즐턴', 'CITIZEN'),
(20, 'citizen09', 'citizen1234', '에밋 오터톤', 'CITIZEN'),
(21, 'citizen10', 'citizen1234', '약스', 'CITIZEN');


-- [2] 경찰관 상세 정보 (zpd_officer: 10명 / species 영문 Enum 코드 적용 / 사번 1001 ~ 1010)
INSERT INTO zpd_officer (name, species, size, district, status, user_id) VALUES
                                                                             ('주디 홉스', 'RABBIT', 'SMALL', 'DOWNTOWN', 'WORKING', 2),                 -- 사번: 1001
                                                                             ('닉 와일드', 'FOX', 'MEDIUM', 'DOWNTOWN', 'WORKING', 3),                     -- 사번: 1002
                                                                             ('벤자민 클로하우저', 'CHEETAH', 'LARGE', 'DOWNTOWN', 'STANDBY', 4),            -- 사번: 1003
                                                                             ('피터 맥혼', 'RHINOCEROS', 'LARGE', 'SAHARA_SQUARE', 'WORKING', 5),          -- 사번: 1004
                                                                             ('패트릭 팽마이어', 'TIGER', 'LARGE', 'RAINFOREST', 'STANDBY', 6),             -- 사번: 1005
                                                                             ('델가토', 'LION', 'LARGE', 'TUNDRA_TOWN', 'WORKING', 7),                     -- 사번: 1006
                                                                             ('히긴스', 'HIPPOPOTAMUS', 'LARGE', 'LITTLE_RODENTIA', 'RETIRED', 8),         -- 사번: 1007 (퇴직 상태)
                                                                             ('프랜신 페닝턴', 'ELEPHANT', 'LARGE', 'SAHARA_SQUARE', 'STANDBY', 9),         -- 사번: 1008
                                                                             ('울포드', 'WOLF', 'MEDIUM', 'TUNDRA_TOWN', 'WORKING', 10),                  -- 사번: 1009
                                                                             ('보그 형사', 'HIPPOPOTAMUS', 'LARGE', 'RAINFOREST', 'STANDBY', 11);          -- 사번: 1010


-- [3] 사건 관리 초기 더미 데이터 (zpd_case: 10건)
INSERT INTO zpd_case (title, content, district, status, reporter_id, officer_id, created_at, closed_at) VALUES
-- 사건 1: 오터톤 실종 사건 (수사 중, 주디 홉스 배정)
('오터톤 씨 실종 수사의뢰', '플로리다 수달 에밋 오터톤이 퇴근 후 귀가하지 않고 행방불명되었습니다. 리무진 탑승 목격담이 있습니다.', 'RAINFOREST', 'IN_PROGRESS', 20, 1001, '2026-10-01 09:30:00', NULL),

-- 사건 2: 밤의 울음꾼 구근 절도 사건 (해결 완료, 주디 홉스)
('야생 양파(밤의 울음꾼) 대량 도난', '식물원에서 신원미상의 족제비가 특수 구근 가방을 훔쳐 도주했습니다.', 'LITTLE_RODENTIA', 'SOLVED', 15, 1001, '2026-10-02 11:15:00', '2026-10-03 18:00:00'),

-- 사건 3: 차량 과속 질주 신고 (접수 대기 중, 미배정)
('도심 내 시속 180km 광란의 레이싱', '하얀색 세단 스포츠카가 다운타운 중앙대로를 위험천만하게 과속 질주 중입니다. 면허 조회가 급합니다.', 'DOWNTOWN', 'WAITING', 18, NULL, '2026-10-03 14:00:00', NULL),

-- 사건 4: 무허가 팝시클 불법 유통 (수사 중, 닉 와일드 배정)
('점보 하드 재가공 및 발바닥 아이스크림 무허가 판매', '코끼리 점보 하드를 녹여 작은 동물 구역에 무허가 재판매 및 막대 목재 무단 반출 혐의가 포착되었습니다.', 'DOWNTOWN', 'IN_PROGRESS', 19, 1002, '2026-10-04 10:20:00', NULL),

-- 사건 5: 리틀 로덴시아 거대 도넛 소동 (퇴직한 히긴스 형사가 과거 종결한 사건 기록 유지)
('대형 제과점 도넛 조형물 굴림 난동', '다운타운 제과점 지붕에서 굴러떨어진 거대 도넛 조형물이 쥐 마을을 덮칠 뻔한 아찔한 소동이 일어났습니다.', 'LITTLE_RODENTIA', 'CLOSED', 15, 1007, '2026-10-05 16:45:00', '2026-10-05 20:00:00'),

-- 사건 6: 사하라 스퀘어 리무진 파손 (수사 중, 피터 맥혼 배정)
('리무진 내부 발톱 할큄 및 폭력 난동', '툰드라 타운 소속 고급 리무진 뒷좌석이 날카로운 발톱에 의해 심하게 찢겨 나갔고 기사가 위협받았습니다.', 'SAHARA_SQUARE', 'IN_PROGRESS', 14, 1004, '2026-10-06 08:10:00', NULL),

-- 사건 7: 기차역 야간 소란 (오인 신고, 벤자민 클로하우저)
('다운타운 기차역 인근 야간 늑대 울음소리 소동', '맹수 포효인 줄 알고 주민들이 패닉에 빠졌으나 확인 결과 늑대 경비원들의 연쇄 하울링 장난이었습니다.', 'DOWNTOWN', 'FALSE_ALARM', 13, 1003, '2026-10-06 23:30:00', '2026-10-07 01:10:00'),

-- 사건 8: 불법 DVD 판매 (접수 대기 중, 미배정)
('미개봉 불법 복제 DVD 해적판 유통', '다운타운 골목길에서 족제비가 코끼리 코트에 감춘 채 해적판 영화 DVD를 호객 행위하고 있습니다.', 'DOWNTOWN', 'WAITING', 16, NULL, '2026-10-07 13:50:00', NULL),

-- 사건 9: 냉동창고 불법 협박 제보 (수사 중, 델가토 배정)
('툰드라 타운 냉동 창고 불법 감금 위협', '북극곰 경호원들이 특정 상인을 얼음 구멍에 빠뜨리려 했다는 협박 제보가 접수되었습니다.', 'TUNDRA_TOWN', 'IN_PROGRESS', 19, 1006, '2026-10-08 17:00:00', NULL),

-- 사건 10: DMV 업무 지연 항의 (취소 처리, 미배정)
('차량등록국 대기 시간 6시간 초과 항의', '창구 직원들의 처리 속도가 너무 느려 민원인 간의 고성과 밀침이 발생했습니다. (현장 안정화로 자진 취소)', 'DOWNTOWN', 'CANCELLED', 12, NULL, '2026-10-08 18:20:00', '2026-10-08 19:00:00');