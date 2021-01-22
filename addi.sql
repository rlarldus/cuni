# 랜덤 게시물 생성
INSERT INTO article
SET regDate = NOW(),
boardId = IF(RAND() > 0.5, 1, 2),
memberId = IF(RAND() > 0.5, 1, 2),
title = CONCAT('제목-', UUID()),
`body` = CONCAT('내용-', UUID()),
hit = CEIL(RAND() * 1000);