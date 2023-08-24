CREATE TABLE tb_auth_token(
    seq TEXT PRIMARY KEY,
    userSeq BIGINT,
    accessToken VARCHAR(1000),
    refreshToken VARCHAR(1000),
    createdDTime TIMESTAMP,
    modifiedDTime TIMESTAMP,
    status INT DEFAULT 1
);