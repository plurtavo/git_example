--------------------------------------------------------
--  DDL for Sequence APC_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."APC_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DROP TBL_IDF_TBL_APC
--------------------------------------------------------

DROP TABLE TBL_IDF_TBL_APC CASCADE CONSTRAINTS;
DELETE FROM TBL_APC;
--------------------------------------------------------
-- ADD ID COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_APC ADD (
    "PK_ID" NUMBER(10,0)  );

COMMENT ON COLUMN "CAN"."TBL_APC"."PK_ID" IS 'Llave primaria';

--------------------------------------------------------
--  DDL for Index NDX_APC_ID
--------------------------------------------------------

CREATE UNIQUE INDEX "CAN"."NDX_APC_ID" ON "CAN"."TBL_APC" ("PK_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  Result script apc-o.sql
--------------------------------------------------------

ALTER TABLE TBL_APC DROP CONSTRAINT SYS_C007469;

--------------------------------------------------------
--  Constraints for Table TBL_APC
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_APC" MODIFY ("PK_ID" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_APC" ADD PRIMARY KEY ("PK_ID");


--------------------------------------------------------
--  RENAME COLUMNS
--------------------------------------------------------

ALTER TABLE TBL_APC RENAME COLUMN PK_FIID TO FIID;
ALTER TABLE TBL_APC RENAME COLUMN PK_TRAN_CODE TO TRAN_CODE;
ALTER TABLE TBL_APC RENAME COLUMN PK_FROM_ACCT TO FROM_ACCT;
ALTER TABLE TBL_APC RENAME COLUMN PK_TO_ACCT TO TO_ACCT;

--------------------------------------------------------
--  DDL for Index NDX_UK_COMPOSE_APC
--------------------------------------------------------

CREATE UNIQUE INDEX "CAN"."NDX_UK_COMPOSE_APC" ON "CAN"."TBL_APC" (FIID, TRAN_CODE, FROM_ACCT, TO_ACCT)
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  Ref Constraints for Table TBL_APC
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_APC" ADD CONSTRAINT "FK_APC_FIID" FOREIGN KEY ("FIID")
    REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;

COMMIT;