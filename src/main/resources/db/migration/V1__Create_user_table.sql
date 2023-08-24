CREATE TABLE tb_user (
    seq BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickName VARCHAR(100) NOT NULL,
    phone VARCHAR(255),
    crn VARCHAR(255),
    createdDTime TIMESTAMP,
    modifiedDTime TIMESTAMP,
    status INT DEFAULT 1
);