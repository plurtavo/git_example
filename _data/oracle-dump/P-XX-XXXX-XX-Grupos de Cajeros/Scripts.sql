--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

CREATE SEQUENCE  "CAN"."GROUP_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;


--------------------------------------------------------
--  DDL for Table TBL_LOG_CONECTION
--------------------------------------------------------

  CREATE TABLE "CAN"."TBL_GROUP" 
   (	"PK_ID" NUMBER(10,0), 
	"NOMBRE" VARCHAR2(50 CHAR), 
	"DESCRIPCION" VARCHAR2(250 CHAR), 
	"FIID" VARCHAR2(4 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "CAN_DAT" ;
  
   COMMENT ON COLUMN "CAN"."TBL_GROUP"."PK_ID" IS 'ID unico';
   COMMENT ON COLUMN "CAN"."TBL_GROUP"."NOMBRE" IS 'Nombre del grupo de cajeros';
   COMMENT ON COLUMN "CAN"."TBL_GROUP"."DESCRIPCION" IS 'Descripción del grupo: Ejem. Grupo de cajero del Norte';
   COMMENT ON COLUMN "CAN"."TBL_GROUP"."FIID" IS 'FIID de la institución dueña del grupo';


--------------------------------------------------------
--  DDL for Table TBL_GROUP_ATD
--------------------------------------------------------

  CREATE TABLE "CAN"."TBL_GROUP_ATD" 
   ( "PK_ID" NUMBER(10,0), 
	"PK_TERMINAL_ID" VARCHAR2(16 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "CAN_DAT" ;
  
   COMMENT ON COLUMN "CAN"."TBL_GROUP_ATD"."PK_ID" IS 'ID de la tabla TBL_GROUP';
   COMMENT ON COLUMN "CAN"."TBL_GROUP_ATD"."PK_TERMINAL_ID" IS 'ID del cajero de la tabla TBL_ATD'; 
   

--------------------------------------------------------
--  DDL for Index NDX_GROUP_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_GROUP_ID" ON "CAN"."TBL_GROUP" ("PK_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "CAN_NDX" ;   
  

--------------------------------------------------------
--  DDL for Index NDX_UK_GRUPO_NOMBRE
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_UK_GRUPO_NOMBRE" ON "CAN"."TBL_GROUP" ("NOMBRE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "CAN_NDX" ;  
  
  
--------------------------------------------------------
--  DDL for Index NDX_GROUP_ATD_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CAN"."NDX_GROUP_ATD_PK" ON "CAN"."TBL_GROUP_ATD" ("PK_ID", "PK_TERMINAL_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "CAN_NDX" ;
  
--------------------------------------------------------
--  Constraints for Table TBL_GROUP
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_GROUP" MODIFY ("PK_ID" NOT NULL ENABLE);
  ALTER TABLE "CAN"."TBL_GROUP" ADD PRIMARY KEY ("PK_ID")
  USING INDEX "CAN"."NDX_GROUP_ID"  ENABLE;  
  ALTER TABLE "CAN"."TBL_GROUP" ADD CONSTRAINT "NDX_UK_GRUPO_NOMBRE" UNIQUE ("NOMBRE");
  ALTER TABLE "CAN"."TBL_GROUP" MODIFY ("FIID" NOT NULL ENABLE);
  
  
--------------------------------------------------------
--  Constraints for Table TBL_GROUP_ATD
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_GROUP_ATD" MODIFY ("PK_ID" NOT NULL ENABLE);
  ALTER TABLE "CAN"."TBL_GROUP_ATD" MODIFY ("PK_TERMINAL_ID" NOT NULL ENABLE);
  ALTER TABLE "CAN"."TBL_GROUP_ATD" ADD PRIMARY KEY ("PK_ID", "PK_TERMINAL_ID")
  USING INDEX "CAN"."NDX_GROUP_ATD_PK"  ENABLE;   
  
  
--------------------------------------------------------
--  Ref Constraints for Table TBL_GROUP
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_GROUP" ADD CONSTRAINT "FK_GROUP_FIID" FOREIGN KEY ("FIID")
	  REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;  
	  
	  
--------------------------------------------------------
--  Ref Constraints for Table TBL_GROUP_ATD
--------------------------------------------------------

  ALTER TABLE "CAN"."TBL_GROUP_ATD" ADD CONSTRAINT "FK_TBL_GROUP_ATD_PK_ID" FOREIGN KEY ("PK_ID")
	  REFERENCES "CAN"."TBL_GROUP" ("PK_ID") ENABLE;
  ALTER TABLE "CAN"."TBL_GROUP_ATD" ADD CONSTRAINT "FK_TBL_GROUP_ATD_PK_TERMINAL_ID" FOREIGN KEY ("PK_TERMINAL_ID")
	  REFERENCES "CAN"."TBL_ATD" ("PK_TERMINAL_ID") ENABLE; 
	  
	  
commit;