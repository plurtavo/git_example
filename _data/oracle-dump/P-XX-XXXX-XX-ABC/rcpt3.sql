
--------------------------------------------------------
--  Constraints for Table TBL_RCPT
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_RCPT" MODIFY ("PK_ID" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_RCPT" ADD PRIMARY KEY ("PK_ID");

--------------------------------------------------------
--  RENAME COLUMNS
--------------------------------------------------------

ALTER TABLE TBL_RCPT RENAME COLUMN PK_FIID TO FIID;
ALTER TABLE TBL_RCPT RENAME COLUMN PK_TRAN_CODE TO TRAN_CODE;
ALTER TABLE TBL_RCPT RENAME COLUMN PK_COSTUMER TO COSTUMER;


--------------------------------------------------------
--  Ref Constraints for Table TBL_RCPT
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_RCPT" ADD CONSTRAINT "FK_RCPT_FIID" FOREIGN KEY ("FIID")
    REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;


COMMIT;