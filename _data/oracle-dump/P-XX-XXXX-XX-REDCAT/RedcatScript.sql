CREATE SEQUENCE  "CAN"."REDCAT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
--------------------------------------------------------
--  CREATING NEW TABLE FOR REDCAT
--------------------------------------------------------

CREATE TABLE "CAN"."TBL_REDCAT" (
    "PK_ID" NUMBER (10,0),
	"FK_FIID" VARCHAR (4),
	"FK_TERMINAL_ID" VARCHAR(19), 
	"COUT1" NUMBER(10,0), 
	"COUT2" NUMBER(10,0),
	"COUT3" NUMBER(10,0), 
	"COUT4" NUMBER(10,0), 
	"COUT5" NUMBER(10,0),
	"COUT6" NUMBER(10,0), 
	"END1" NUMBER(10,0), 
	"END2" NUMBER(10,0),
	"END3" NUMBER(10,0), 
	"END4" NUMBER(10,0), 
	"END5" NUMBER(10,0),
	"END6" NUMBER(10,0), 
	"CDE1" VARCHAR(3 ), 
	"CDE2" VARCHAR(3 ),
	"CDE3" VARCHAR(3 ), 
	"CDE4" VARCHAR(3 ), 
	"CDE5" VARCHAR(3 ),
	"CDE6" VARCHAR(3 ),
	"DENOMINATION1" NUMBER(10,0), 
	"DENOMINATION2" NUMBER(10,0),
	"DENOMINATION3" NUMBER(10,0), 
	"DENOMINATION4" NUMBER(10,0), 
	"DENOMINATION5" NUMBER(10,0),
	"DENOMINATION6" NUMBER(10,0), 
	"REMANENTE_USD"  NUMBER(10,0),
	"REMANENTE_MXN"  NUMBER(10,0),
	"TOTAL_OUT_USD"  NUMBER(10,0),
	"TOTAL_OUT_MXN"  NUMBER(10,0),
	"DIFFERENCE_USD" NUMBER(10,0),
	"DIFFERENCE_MXN" NUMBER(10,0),
	"START_DATE" DATE,
	"FINAL_DATE" DATE,
	"COUT_TYPE" VARCHAR(3)
   );
   

--------------------------------------------------------
--  MAKING NOT NULL ALL PRIMARY AND FOREIGN KEYS
--------------------------------------------------------


ALTER TABLE "CAN"."TBL_REDCAT" MODIFY ("PK_ID" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_REDCAT" MODIFY ("FK_FIID" NOT NULL ENABLE);
ALTER TABLE "CAN"."TBL_REDCAT" MODIFY ("FK_TERMINAL_ID" NOT NULL ENABLE);
  
  --------------------------------------------------------
--  CREATING INDEXES
--------------------------------------------------------
  
   
CREATE UNIQUE INDEX "CAN"."NDX_TBL_REDCAT" ON "CAN"."TBL_REDCAT" ("PK_ID") 
PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
TABLESPACE "CAN_NDX" ;

--------------------------------------------------------
--  CONSTRAINT FOR TABLES
--------------------------------------------------------
  
ALTER TABLE "CAN"."TBL_REDCAT" ADD CONSTRAINT "FK_FIID_REDCAT" FOREIGN KEY ("FK_FIID")
  REFERENCES "CAN"."TBL_IDF" ("PK_FIID") ENABLE;
  
ALTER TABLE "CAN"."TBL_REDCAT" ADD CONSTRAINT "FK_TERMINAL_ID_REDCAT" FOREIGN KEY ("FK_TERMINAL_ID")
	  REFERENCES "CAN"."TBL_ATD" ("PK_TERMINAL_ID") ENABLE;
	  
--------------------------------------------------------
--  INSERT A NEW MENU
--------------------------------------------------------
Insert into CAN.TBL_MENU (PK_ID,MENU,PARAMS) values (13,'Redcat',null);

Insert into CAN.TBL_ROLE_MENU (NAME, PK_ID) values ('ADMINISTRADOR', 13);

--------------------------------------------------------
--  CREATE A NEW PRIVILEGES TO REDCAT
--------------------------------------------------------


Insert into CAN.TBL_PRIVILEGE (NAME, DESCRIPCION, APP) values ( 'read-redcat', 'Permite consultar los reportes de redcat', 0 );
Insert into CAN.TBL_PRIVILEGE (NAME, DESCRIPCION, APP) values ( 'write-redcat', 'Permite crear un corte forzado de redcat', 0 );

Insert into CAN.TBL_ROLE_TBL_PRIVILEGE (TBL_ROLE_NAME, PRIVILEGES_NAME) values ('ADMINISTRADOR', 'read-redcat' );
Insert into CAN.TBL_ROLE_TBL_PRIVILEGE (TBL_ROLE_NAME, PRIVILEGES_NAME) values ('ADMINISTRADOR', 'write-redcat');
																							   


--------------------------------------------------------
--  COMMENTS FOR TABLE
--------------------------------------------------------	  
	  
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."PK_ID" IS 'Id autoincrementable';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."FK_FIID" IS 'FIID asociado a este reporte';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."FK_TERMINAL_ID" IS 'ID del cajero que genero este reporte';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT1" IS 'Cuenta total de billetes dispensados de casetera 1';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT2" IS 'Cuenta total de billetes dispensados de casetera 2';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT3" IS 'Cuenta total de billetes dispensados de casetera 3';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT4" IS 'Cuenta total de billetes dispensados de casetera 4';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT5" IS 'Cuenta total de billetes dispensados de casetera 5';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT6" IS 'Cuenta total de billetes dispensados de casetera 6';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END1" IS 'Cuenta total de billetes restantes en casetera 1';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END2" IS 'Cuenta total de billetes restantes en casetera 2';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END3" IS 'Cuenta total de billetes restantes en casetera 3';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END4" IS 'Cuenta total de billetes restantes en casetera 4';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END5" IS 'Cuenta total de billetes restantes en casetera 5';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."END6" IS 'Cuenta total de billetes restantes en casetera 6';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE1" IS 'Tipo de moneda para la casetera 1';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE2" IS 'Tipo de moneda para la casetera 2';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE3" IS 'Tipo de moneda para la casetera 3';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE4" IS 'Tipo de moneda para la casetera 4';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE5" IS 'Tipo de moneda para la casetera 5';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."CDE6" IS 'Tipo de moneda para la casetera 6';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION1" IS 'Denominacion de moneda 1';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION2" IS 'Denominacion de moneda 2';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION3" IS 'Denominacion de moneda 3';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION4" IS 'Denominacion de moneda 4';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION5" IS 'Denominacion de moneda 5';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DENOMINATION6" IS 'Denominacion de moneda 6';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."REMANENTE_USD" IS 'Remanente total en dolares';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."REMANENTE_MXN" IS 'Remanente total en pesos';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."TOTAL_OUT_USD" IS 'Total dispensando de dolares';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."TOTAL_OUT_MXN" IS 'Total dispensado de pesos';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DIFFERENCE_USD" IS 'Diferencia en dolares';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."DIFFERENCE_MXN" IS 'Diferencia en pesos';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."START_DATE" IS 'Fecha final de corte';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."FINAL_DATE" IS 'Fecha inicial de corte';
COMMENT ON COLUMN "CAN"."TBL_REDCAT"."COUT_TYPE" IS 'Tipo de corte';
