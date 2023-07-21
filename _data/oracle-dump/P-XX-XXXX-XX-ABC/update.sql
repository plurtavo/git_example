INSERT INTO CAN.TBL_ROLE_TBL_PRIVILEGE ( TBL_ROLE_NAME, PRIVILEGES_NAME ) VALUES ( 'ATM_BASICO', 'transactional' ); 
INSERT INTO CAN.TBL_ROLE_TBL_PRIVILEGE ( TBL_ROLE_NAME, PRIVILEGES_NAME ) VALUES ( 'ATM_ADVANCE', 'transactional' ); 
INSERT INTO CAN.TBL_ROLE_TBL_PRIVILEGE ( TBL_ROLE_NAME, PRIVILEGES_NAME ) VALUES ( 'ATM_FULL', 'transactional' ); 

DELETE FROM CAN.TBL_USER_TBL_ROLE WHERE TBL_USER_PK_ID = 'DASH-CORE';
DELETE FROM CAN.TBL_USER_TBL_ROLE WHERE TBL_USER_PK_ID = 'ISO-CORE';
DELETE FROM CAN.TBL_USER_TBL_ROLE WHERE TBL_USER_PK_ID = 'JSE-CORE';


DELETE FROM CAN.TBL_USER WHERE PK_ID = 'DASH-CORE';
DELETE FROM CAN.TBL_USER WHERE PK_ID = 'ISO-CORE';
DELETE FROM CAN.TBL_USER WHERE PK_ID = 'JSE-CORE';

DELETE FROM CAN.TBL_ROLE_TBL_PRIVILEGE WHERE TBL_ROLE_NAME = 'APP_TRANSACTIONAL';
DELETE FROM CAN.TBL_ROLE_TBL_PRIVILEGE WHERE TBL_ROLE_NAME = 'APP_SOCKET';

DELETE FROM CAN.TBL_ROLE WHERE NAME = 'APP_TRANSACTIONAL';
DELETE FROM CAN.TBL_ROLE WHERE NAME = 'APP_SOCKET';

COMMIT;
