Insert into CLIENT(id, email) values (2, 'test2@email.com');
Insert into CLIENT(id, email) values (3, 'test3@email.com');
Insert into CLIENT(id, email) values (4, 'test4@email.com');
Insert into CLIENT(id, email) values (5, 'test5@email.com');

Insert into ACCOUNT (ACCOUNT_ID,ACCOUNT_BALANCE,CREATE_DATE,CLIENT_ID) values ('2','200',to_timestamp('24/10/18 09:02:24,107000000','DD/MM/RR HH24:MI:SSXFF'),'3');
Insert into ACCOUNT (ACCOUNT_ID,ACCOUNT_BALANCE,CREATE_DATE,CLIENT_ID) values ('3','200',to_timestamp('24/10/18 09:02:24,107000000','DD/MM/RR HH24:MI:SSXFF'),'4');
Insert into ACCOUNT (ACCOUNT_ID,ACCOUNT_BALANCE,CREATE_DATE,CLIENT_ID) values ('4','0',to_timestamp('24/10/18 09:02:24,107000000','DD/MM/RR HH24:MI:SSXFF'),'5');