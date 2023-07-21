--------------------------------------------------------
--  DROP TBL_IDF_TBL_SCREEN_GROUP
--------------------------------------------------------

DROP TABLE TBL_IDF_TBL_SCREEN_GROUP CASCADE CONSTRAINTS;
DROP TABLE TBL_ATD_TBL_UP_TIME CASCADE CONSTRAINTS;

--------------------------------------------------------
-- ADD FIID COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_SCREEN_GROUP ADD (
    FIID VARCHAR2(4 CHAR) );

COMMENT ON COLUMN "CAN"."TBL_SCREEN_GROUP"."FIID" IS 'Institución dueña del screen';
--------------------------------------------------------
--  Ref Constraints for Table TBL_SCREEN_GROUP
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_SCREEN_GROUP" ADD CONSTRAINT "FK_SCREEN_FIID" FOREIGN KEY ("FIID")
    REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;

--------------------------------------------------------
--  UPDATES FOR VALUES
--------------------------------------------------------
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.FIID = 'T139' WHERE PK_GROUP_ID = 'T139_ATM_01';

UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.FIID = 'T139' WHERE PK_GROUP_ID = 'T139_ATM_02';

--------------------------------------------------------
--  Constraints for Table TBL_SCREEN_GROUP
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_SCREEN_GROUP" MODIFY ("FIID" NOT NULL ENABLE);

--------------------------------------------------------
--  UPDATES FOR VALUES
--------------------------------------------------------
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = NULL WHERE PK_GROUP_ID = 'T139_ATM_01';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = NULL WHERE PK_GROUP_ID = 'T139_ATM_01';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = NULL WHERE PK_GROUP_ID = 'T139_ATM_02';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = NULL WHERE PK_GROUP_ID = 'T139_ATM_02';

--------------------------------------------------------
--  Ref Constraints for Table TBL_SCREEN_GROUP
--------------------------------------------------------
ALTER TABLE "CAN"."TBL_SCREEN_GROUP" ADD CONSTRAINT "FK_PUBLICITY_IMAGE" FOREIGN KEY ("PUBLICITY_NAME")
    REFERENCES "CAN"."TBL_IMAGES" ("PK_NAME") ENABLE;
ALTER TABLE "CAN"."TBL_SCREEN_GROUP" ADD CONSTRAINT "FK_BACKGROUND_IMAGE" FOREIGN KEY ("BACK_GROUND")
    REFERENCES "CAN"."TBL_IMAGES" ("PK_NAME") ENABLE;

--------------------------------------------------------
--  UPDATES FOR VALUES
--------------------------------------------------------
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = 'BG_ACM_01' WHERE PK_GROUP_ID = 'T139_ATM_01';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = 'PB_ACM_01' WHERE PK_GROUP_ID = 'T139_ATM_01';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = 'BG_ACM_01' WHERE PK_GROUP_ID = 'T139_ATM_02';
UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = 'PB_ACM_01' WHERE PK_GROUP_ID = 'T139_ATM_02';

--------------------------------------------------------
--  Constraints for Table TBL_SCREEN_GROUP
--------------------------------------------------------
ALTER TABLE "CAN"."TBL_SCREEN_GROUP" MODIFY ("BACK_GROUND" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_SCREEN_GROUP" MODIFY ("PUBLICITY_NAME" NOT NULL ENABLE);
commit;