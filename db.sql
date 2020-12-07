DROP DATABASE IF EXISTS cuni;

CREATE DATABASE cuni;

USE cuni;

CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(id),
    regDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` CHAR(100) NOT NULL
);

INSERT INTO article
SET regDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
title = '제목3',
`body` = '내용3'; 
