--------------------------------------------------------
--  DDL for Sequence BIN_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."BIN_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DROP TBL_IDF_TBL_BIN
--------------------------------------------------------

DROP TABLE TBL_IDF_TBL_BIN CASCADE CONSTRAINTS;
DROP TABLE TBL_JOURNAL_MESSAGE CASCADE CONSTRAINTS;
DELETE FROM TBL_BIN;
--------------------------------------------------------
-- ADD ID COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_BIN ADD (
    "PK_ID" NUMBER(10,0),
    "DESCRIPTION" VARCHAR2(50 CHAR)  )
;

COMMENT ON COLUMN "CAN"."TBL_BIN"."PK_ID" IS 'Llave primaria';
COMMENT ON COLUMN "CAN"."TBL_BIN"."DESCRIPTION" IS 'Breve descripción, producto o uso del BIN';

--------------------------------------------------------
--  DDL for Index NDX_BIN_ID
--------------------------------------------------------

CREATE UNIQUE INDEX "CAN"."NDX_BIN_ID" ON "CAN"."TBL_BIN" ("PK_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  Result script prefix-o.sql
--------------------------------------------------------

ALTER TABLE TBL_BIN DROP CONSTRAINT SYS_C007492;

--------------------------------------------------------
--  Constraints for Table TBL_BIN
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_BIN" MODIFY ("PK_ID" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_BIN" ADD PRIMARY KEY ("PK_ID");


--------------------------------------------------------
--  RENAME COLUMNS
--------------------------------------------------------

ALTER TABLE TBL_BIN RENAME COLUMN PK_BIN TO BIN;
ALTER TABLE TBL_BIN RENAME COLUMN PK_BIN_LEN TO BIN_LEN;
ALTER TABLE TBL_BIN RENAME COLUMN PK_PAN_LEN TO PAN_LEN;

--------------------------------------------------------
--  DDL for Index NDX_UK_COMPOSE_BIN
--------------------------------------------------------

CREATE UNIQUE INDEX "CAN"."NDX_UK_COMPOSE_BIN" ON "CAN"."TBL_BIN" (FIID, BIN, BIN_LEN, PAN_LEN)
    PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
    TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  Ref Constraints for Table TBL_APC
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_BIN" ADD CONSTRAINT "FK_BIN_FIID" FOREIGN KEY ("FIID")
    REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;

COMMIT;