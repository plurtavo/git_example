--------------------------------------------------------
--  DROP TBL_IDF_TBL_IMAGES
--------------------------------------------------------

DROP TABLE TBL_IDF_TBL_IMAGES CASCADE CONSTRAINTS;

--------------------------------------------------------
-- ADD FIID COLUMN
--------------------------------------------------------
ALTER TABLE CAN.TBL_IMAGES ADD (
    FIID VARCHAR2(4 CHAR) );

COMMENT ON COLUMN "CAN"."TBL_IMAGES"."FIID" IS 'Institución dueña del script';
--------------------------------------------------------
--  Ref Constraints for Table TBL_IMAGES
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_IMAGES" ADD CONSTRAINT "FK_IMAGE_FIID" FOREIGN KEY ("FIID")
    REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;

--------------------------------------------------------
--  UPDATES FOR VALUES
--------------------------------------------------------
UPDATE CAN.TBL_IMAGES SET CAN.TBL_IMAGES.FIID = 'T139' WHERE PK_NAME = 'PB_ACM_01';
UPDATE CAN.TBL_IMAGES SET CAN.TBL_IMAGES.FIID = 'T139' WHERE PK_NAME = 'BG_ACM_01';
--------------------------------------------------------
--------------------------------------------------------
--  Constraints for Table TBL_IMAGES
--------------------------------------------------------

ALTER TABLE "CAN"."TBL_IMAGES" MODIFY ("FIID" NOT NULL ENABLE);


commit;