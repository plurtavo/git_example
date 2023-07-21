--------------------------------------------------------
--  DDL for Sequence ALGORITHM_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."JOURNAL_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

--------------------------------------------------------
--  DROP TBL_ATD_TBL_JOURNAL
--------------------------------------------------------

DROP TABLE TBL_ATD_TBL_JOURNAL CASCADE CONSTRAINTS;

--------------------------------------------------------
-- ADD TEMP COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_JOURNAL ADD (
    TEMP VARCHAR2(16 CHAR) );

--------------------------------------------------------
-- UPDATE TEMP
--------------------------------------------------------
UPDATE CAN.TBL_JOURNAL SET CAN.TBL_JOURNAL.TEMP = CAN.TBL_JOURNAL.TERMINAL_ID;

--------------------------------------------------------
-- NULL TERMINAL_ID
--------------------------------------------------------
UPDATE CAN.TBL_JOURNAL SET CAN.TBL_JOURNAL.TERMINAL_ID = NULL;


--------------------------------------------------------
--  Ref Constraints for Table TBL_DISPENSED_ALGORITHM
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_JOURNAL" ADD CONSTRAINT "FK_JOURNAL_TERMINAL" FOREIGN KEY ("TERMINAL_ID")
	  REFERENCES "CAN"."TBL_ATD" ("PK_TERMINAL_ID") ENABLE;   
      
--------------------------------------------------------
-- UPDATE TERMINAL_ID 
--------------------------------------------------------
UPDATE CAN.TBL_JOURNAL SET CAN.TBL_JOURNAL.TERMINAL_ID = CAN.TBL_JOURNAL.TEMP;

--------------------------------------------------------
--  Constraints for Table TBL_JOURNAL
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_JOURNAL" MODIFY ("TERMINAL_ID" NOT NULL ENABLE);


--------------------------------------------------------
--  DROP TEMP COLUMN
--------------------------------------------------------
ALTER TABLE TBL_JOURNAL DROP COLUMN TEMP;

COMMIT;