SELECT 'UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.FIID = ''' ||
       TBL_IDF_PK_FIID || ''' WHERE PK_GROUP_ID =  ''' || SCREENS_PK_GROUP_ID || ''';'
FROM TBL_IDF_TBL_SCREEN_GROUP;

SELECT 'UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = NULL WHERE PK_GROUP_ID = ''' ||
       PK_GROUP_ID|| ''';'
FROM TBL_SCREEN_GROUP;

SELECT 'UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = NULL WHERE PK_GROUP_ID = ''' ||
       PK_GROUP_ID|| ''';'
FROM TBL_SCREEN_GROUP;

SELECT 'UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.BACK_GROUND = ''' ||
       BACK_GROUND || ''' WHERE PK_GROUP_ID = ''' || PK_GROUP_ID ||''';'
FROM TBL_SCREEN_GROUP;

SELECT 'UPDATE CAN.TBL_SCREEN_GROUP SET CAN.TBL_SCREEN_GROUP.PUBLICITY_NAME = ''' ||
       PUBLICITY_NAME || ''' WHERE PK_GROUP_ID = ''' || PK_GROUP_ID ||''';'
FROM TBL_SCREEN_GROUP;
