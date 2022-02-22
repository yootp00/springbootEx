CREATE TABLE `board` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `code` int(11) NOT NULL DEFAULT 1000 COMMENT '게시판 구분 코드',
  `title` varchar(255) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '게시물 내용',
  `reg_id` varchar(20) NOT NULL COMMENT '작성자 id',
  `reg_date` datetime NOT NULL COMMENT '등록일',
  `mod_date` datetime DEFAULT NULL COMMENT '수정일',
  `count` int(11) DEFAULT 0 COMMENT '조회수',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='공통 게시판'

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '일련번호',
  `user_id` varchar(20) NOT NULL COMMENT '아이디',
  `email` varchar(50) NOT NULL COMMENT '이메일',
  `password` varchar(255) NOT NULL COMMENT '비밀번호',
  `name` varchar(10) NOT NULL COMMENT '이름',
  `level` smallint(5) NOT NULL DEFAULT 1 COMMENT '레벨',
  `status` char(1) NOT NULL DEFAULT 'Y' COMMENT '회원 상태 Y:정상, N:탈퇴, D:접근거부',
  `cert` tinyint(1) NOT NULL DEFAULT 0 COMMENT '인증여부 1:인증, 0:미인증',
  `reg_date` datetime NOT NULL COMMENT '가입일',
  `drop_date` datetime DEFAULT NULL COMMENT '탈퇴일',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_un_id` (`user_id`),
  UNIQUE KEY `member_un_email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='회원목록'

CREATE TABLE `upload_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '파일 id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '파일명',
  `file_path` varchar(255) DEFAULT NULL COMMENT '파일경로',
  `content_type` varchar(255) DEFAULT NULL COMMENT '파일 타입',
  `save_file_name` varchar(255) DEFAULT NULL COMMENT '저장된 파일명',
  `size` int(11) DEFAULT NULL COMMENT '파일 크기',
  `reg_date` datetime DEFAULT current_timestamp() COMMENT '등록일',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='에디터 이미지 첨부파일'

CREATE TABLE `file_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `board_id` int(11) NOT NULL COMMENT '게시물 id',
  `file_id` int(11) NOT NULL COMMENT '파일 id',
  PRIMARY KEY (`id`),
  KEY `fk_board_id` (`board_id`),
  KEY `fk_upload_file_id` (`file_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='게시물의 첨부파일 번호'

CREATE TABLE `refresh_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reg_date` datetime NOT NULL,
  `mod_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `refresh_count` int(11) NOT NULL DEFAULT 0,
  `token` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `token` (`token`),
  KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4