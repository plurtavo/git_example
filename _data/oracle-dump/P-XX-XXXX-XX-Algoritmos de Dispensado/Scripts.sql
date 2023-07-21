--------------------------------------------------------
--  DDL for Sequence ALGORITHM_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."ALGORITHM_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;


--------------------------------------------------------
--  DDL for Table TBL_DISPENSED_ALGORITHM
--------------------------------------------------------

  CREATE TABLE "CAN"."TBL_DISPENSED_ALGORITHM" 
   (	"PK_ID" NUMBER(10,0), 
	"NOMBRE" VARCHAR2(100 CHAR), 
	"TIPO_DISPENSADO" VARCHAR2(100 CHAR), 
	"LIMITE_NOTAS_20" NUMBER(10,0) default 0,
	"MONTO_MINIMO_20" NUMBER(10,0) default 0,
	"LIMITE_NOTAS_50" NUMBER(10,0) default 0,
	"MONTO_MINIMO_50" NUMBER(10,0) default 0,
	"LIMITE_NOTAS_100" NUMBER(10,0) default 0,
	"MONTO_MINIMO_100" NUMBER(10,0) default 0,
	"LIMITE_NOTAS_200" NUMBER(10,0) default 0,
	"MONTO_MINIMO_200" NUMBER(10,0) default 0,
	"LIMITE_NOTAS_500" NUMBER(10,0) default 0,
	"MONTO_MINIMO_500" NUMBER(10,0) default 0,
	"LIMITE_NOTAS_1000" NUMBER(10,0) default 0,
	"MONTO_MINIMO_1000" NUMBER(10,0) default 0,
	"FIID" VARCHAR2(4 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "CAN_DAT" ;
  
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."PK_ID" IS 'ID unico';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."NOMBRE" IS 'Nombre del perfil de dispensado';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."TIPO_DISPENSADO" IS 'Tipo de Algoritmo';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_20" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_20" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_50" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_50" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_100" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_100" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_200" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_200" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_500" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_500" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."LIMITE_NOTAS_1000" IS 'Limite maximo de notas a dispensar';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."MONTO_MINIMO_1000" IS 'Monto minimo para considerar la casetera';
   COMMENT ON COLUMN "CAN"."TBL_DISPENSED_ALGORITHM"."FIID" IS 'FIID de la institución dueña del algortimo';


--------------------------------------------------------
--  DDL ALTER for Table TBL_ATD
--------------------------------------------------------

ALTER TABLE CAN.TBL_ATD ADD (
    DISPENSED_ALGORITHM NUMBER(10,0) );

--------------------------------------------------------
--  DDL for Index NDX_DISPENSED_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_DISPENSED_ID" ON "CAN"."TBL_DISPENSED_ALGORITHM" ("PK_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "CAN_NDX" ;   
  

--------------------------------------------------------
--  DDL for Index NDX_UK_DISPENSED_NOMBRE
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_UK_DISPENSED_NOMBRE" ON "CAN"."TBL_DISPENSED_ALGORITHM" ("NOMBRE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "CAN_NDX" ;  
    
--------------------------------------------------------
--  Constraints for Table TBL_DISPENSED_ALGORITHM
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_DISPENSED_ALGORITHM" MODIFY ("PK_ID" NOT NULL ENABLE);
  ALTER TABLE "CAN"."TBL_DISPENSED_ALGORITHM" ADD PRIMARY KEY ("PK_ID")
  USING INDEX "CAN"."NDX_DISPENSED_ID"  ENABLE;  
  ALTER TABLE "CAN"."TBL_DISPENSED_ALGORITHM" ADD CONSTRAINT "NDX_UK_DISPENSED_NOMBRE" UNIQUE ("NOMBRE");
  ALTER TABLE "CAN"."TBL_DISPENSED_ALGORITHM" MODIFY ("FIID" NOT NULL ENABLE);
    
--------------------------------------------------------
--  Ref Constraints for Table TBL_DISPENSED_ALGORITHM
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_DISPENSED_ALGORITHM" ADD CONSTRAINT "FK_DISPENSED_FIID" FOREIGN KEY ("FIID")
	  REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;
	  

--------------------------------------------------------
--  Ref Constraints for Table TBL_ATD
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_ATD" ADD CONSTRAINT "FK_DISPENSED_ALGORITHM" FOREIGN KEY ("DISPENSED_ALGORITHM")
	  REFERENCES "CAN"."TBL_DISPENSED_ALGORITHM" ("PK_ID") ENABLE;  
	  
	  
INSERT INTO TBL_DISPENSED_ALGORITHM (PK_ID, NOMBRE, TIPO_DISPENSADO, FIID ) VALUES ( 1, 'MNB','LESS_NUMBER_OF_NOTES','PROS');
INSERT INTO TBL_DISPENSED_ALGORITHM (PK_ID, NOMBRE, TIPO_DISPENSADO, FIID ) VALUES ( 2, 'MNB1MD','LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR','PROS');
INSERT INTO TBL_DISPENSED_ALGORITHM (PK_ID, NOMBRE, TIPO_DISPENSADO, FIID ) VALUES ( 3, 'MNB3MD','LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR','PROS');
INSERT INTO TBL_DISPENSED_ALGORITHM (PK_ID, NOMBRE, TIPO_DISPENSADO, FIID ) VALUES ( 4, 'MIXTO','MIXED','PROS');

--------------------------------------------------------
--  Update TBL_ATD DISPENSED_ALGORITHM BY DEFAULT 2
--------------------------------------------------------

UPDATE TBL_ATD SET DISPENSED_ALGORITHM = 2;
commit;