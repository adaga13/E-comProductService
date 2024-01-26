CREATE TABLE category
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    is_deleted      BIT(1) NULL,
    last_updated_at datetime NULL,
    name            VARCHAR(255) NULL,
    CONSTRAINT PK_CATEGORY PRIMARY KEY (id)
);

CREATE TABLE product
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime NULL,
    is_deleted      BIT(1) NULL,
    last_updated_at datetime NULL,
    `description`   VARCHAR(255) NULL,
    image_url       VARCHAR(255) NULL,
    price           DOUBLE NULL,
    title           VARCHAR(255) NULL,
    category_id     BIGINT NULL,
    CONSTRAINT PK_PRODUCT PRIMARY KEY (id)
);

CREATE INDEX FK1mtsbur82frn64de7balymq9s ON product (category_id);

ALTER TABLE product
    ADD CONSTRAINT FK1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE RESTRICT;