CREATE TABLE ADDRESS_BOOK(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    BOOK VARCHAR(10) NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PHONE(
    OWNER_ID BIGINT NOT NULL,
    PHONE_NUMBER VARCHAR(50) NOT NULL,
    FOREIGN KEY (OWNER_ID) REFERENCES ADDRESS_BOOK(ID)
);

CREATE INDEX INDEX_ADD_BOOK on ADDRESS_BOOK(BOOK);