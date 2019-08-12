DROP TABLE IF EXISTS BANKING_ACCOUNT;

CREATE TABLE BANKING_ACCOUNT(
   ID INT PRIMARY KEY NOT NULL,
   ACCT_NUM BIGINT(19) ,
   BALANCE DOUBLE(17),
   OWNER_NAME VARCHAR(255))
);

INSERT INTO BANKING_ACCOUNT VALUES(1, 123456789100, 500.0, 'accountA');
commit;
INSERT INTO BANKING_ACCOUNT VALUES(2, 123456789101, 1200.0, 'accountB');
commit;
INSERT INTO BANKING_ACCOUNT VALUES(3, 123456789102, 200.0, 'accountC');
commit;
INSERT INTO BANKING_ACCOUNT VALUES(4, 123456789103, 700.0, 'accountD');
commit;

CREATE SEQUENCE seq_account
  START WITH 4 INCREMENT BY 1;

commit;