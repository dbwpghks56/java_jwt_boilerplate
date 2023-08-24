CREATE TABLE tb_todo (
    seq BIGSERIAL PRIMARY KEY,
    name VARCHAR(250),
    description VARCHAR(250),
    user_id BIGINT REFERENCES tb_user(seq)
);