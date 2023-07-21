--------------------------------------------------------
--  DDL for Sequence RCPT_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."RCPT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DROP TBL_IDF_TBL_RCPT
--------------------------------------------------------

DROP TABLE TBL_IDF_TBL_RCPT CASCADE CONSTRAINTS;

--------------------------------------------------------
--  CLEAR TBL_RCPT
--------------------------------------------------------

DELETE FROM TBL_RCPT;

--------------------------------------------------------
--  Constraints for Table TBL_RCPT
--------------------------------------------------------

ALTER TABLE TBL_RCPT ADD CONSTRAINT H_JSON CHECK (HEADER is JSON)  ENABLE;
ALTER TABLE TBL_RCPT ADD CONSTRAINT B_JSON CHECK (BODY is JSON)  ENABLE;
ALTER TABLE TBL_RCPT ADD CONSTRAINT T_JSON CHECK (TRAILER is JSON)  ENABLE;


--------------------------------------------------------
-- ADD ID COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_RCPT ADD (
    "PK_ID" NUMBER(10,0),
    "NOMBRE" VARCHAR2( 50 CHAR ) );

COMMENT ON COLUMN "CAN"."TBL_RCPT"."PK_ID" IS 'Llave primaria';

--------------------------------------------------------
--  DDL for Index NDX_DISPENSED_ID
--------------------------------------------------------

CREATE UNIQUE INDEX "CAN"."NDX_RCPT_ID" ON "CAN"."TBL_RCPT" ("PK_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  DDL for Index NDX_UK_GRUPO_NOMBRE
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_UK_NOMBRE_RCPT" ON "CAN"."TBL_RCPT" ("NOMBRE")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  TABLESPACE "CAN_NDX" ;
COMMIT;