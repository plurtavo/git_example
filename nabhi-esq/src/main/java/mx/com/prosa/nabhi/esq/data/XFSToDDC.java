package mx.com.prosa.nabhi.esq.data;

import mx.com.prosa.nabhi.esq.exception.XFSException;

public enum XFSToDDC {

    XFS_1( -200, "WFS_ERR_IDC_MEDIAJAM"               ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR                                                                                                , -2, 2),
    XFS_2( -201, "WFS_ERR_IDC_NOMEDIA"                ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR                                                                                                , -1, 2),
    XFS_3( -202, "WFS_ERR_IDC_MEDIARETAINED"          ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR                                                                                                , -1, 2),
    XFS_4( -203, "WFS_ERR_IDC_RETAINBINFULL"          ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR                                                                                                , -2, 2),
    XFS_5( -204, "WFS_ERR_IDC_INVALIDDATA"            , 901, "ATM,_READ_INTERNAL_ERROR"                                                                                                  ,  0, 2),
    XFS_6( -205, "WFS_ERR_IDC_INVALIDMEDIA"           , 307, XFSConstant.BAD_CARD_TRACK_2                                                                                                          ,  0, 2),
    XFS_7( -206, "WFS_ERR_IDC_FORMNOTFOUND"           , 307, XFSConstant.BAD_CARD_TRACK_2                                                                                                          , -1, 2),
    XFS_8( -207, "WFS_ERR_IDC_FORMINVALID"            , 307, XFSConstant.BAD_CARD_TRACK_2                                                                                                          , -1, 2),
    XFS_9( -208, "WFS_ERR_IDC_DATASYNTAX"             , 307, XFSConstant.BAD_CARD_TRACK_2                                                                                                          , -1, 2),
    XFS_10( -209, "WFS_ERR_IDC_SHUTTERFAIL"            , 662, "THE_OPEN_OF_THE_SECURITY_MODULE_FAILED"                                                                                    , -2, 2),
    XFS_11( -210, "WFS_ERR_IDC_SECURITYFAIL"           , 663, XFSConstant.THE_SECURITY_MODULE_HAS_FAILED                                                                                            , -2, 2),
    XFS_12( -211, "WFS_ERR_IDC_PROTOCOLNOTSUPP"        ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_13( -212, "WFS_ERR_IDC_ATRNOTOBTAINED"         , 307, XFSConstant.BAD_CARD_TRACK_2                                                                                                          , -1, 2),
    XFS_14( -213, "WFS_ERR_IDC_INVALIDKEY"             , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 2),
    XFS_15( -214, "WFS_ERR_IDC_WRITE_METHOD"           , 449, "ATM,_WRITE_INTERNAL_ERROR"                                                                                                 , -1, 2),
    XFS_16( -215, "WFS_ERR_IDC_CHIPPOWERNOTSUPP"       , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 2),
    XFS_17( -216, "WFS_ERR_IDC_CARDTOOSHORT"           , 306, "BAD_CARD_ACCT_NO"                                                                                                          , -1, 2),
    XFS_18( -217, "WFS_ERR_IDC_CARDTOOLONG"            , 306, "BAD_CARD_ACCT_NO"                                                                                                          , -1, 2),
    XFS_19( -218, "WFS_ERR_IDC_INVALID_PORT"           ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_20( -219, "WFS_ERR_IDC_POWERSAVETOOSHORT"      ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_21( -220, "WFS_ERR_IDC_POWERSAVEMEDIAPRESENT"  ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_22( -221, "WFS_ERR_IDC_CARDPRESENT"            ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_23( -222, "WFS_ERR_IDC_POSITIONINVALID"        , 540, "TERMINAL_-_SENT_A_REJECT"                                                                                                  , -1, 2),
    XFS_24( -223, "WFS_ERR_IDC_INVALIDTERMINALDATA"    , 669, "INVALID_STATEMENT_INFORMATION_RECEIVED_BY"                                                                                 , -1, 2),
    XFS_25( -224, "WFS_ERR_IDC_INVALIDAIDDATA"         , 669, "INVALID_STATEMENT_INFORMATION_RECEIVED_BY"                                                                                 , -1, 2),
    XFS_26( -225, "WFS_ERR_IDC_INVALIDKEYDATA"         , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 2),
    XFS_27( -226, "WFS_ERR_IDC_READERNOTCONFIGURED"    ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -2, 2),
    XFS_28( -227, "WFS_ERR_IDC_TRANSACTIONNOTINITIATED", 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 2),
    XFS_29( -228, "WFS_ERR_IDC_COMMANDUNSUPP"          , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 2),
    XFS_30( -229, "WFS_ERR_IDC_SYNCHRONIZEUNSUPP"      ,  10, XFSConstant.TERMINAL_CARD_READER_ERROR_1, -1, 2),
    XFS_31( -400, "WFS_ERR_PIN_KEYNOTFOUND"            , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 4),
    XFS_32( -401, "WFS_ERR_PIN_MODENOTSUPPORTED"       ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_33( -402, "WFS_ERR_PIN_ACCESSDENIED"           , 268, XFSConstant.INVALID_ATD_PIN_ENCR_TYPE_FOR_ATM                                                                                         , -1, 4),
    XFS_34( -403, "WFS_ERR_PIN_INVALIDID"              , 318, "TERMINAL_-_INVALID_DEVICE_ID_RECEIVED"                                                                                     , -1, 4),
    XFS_35( -404, "WFS_ERR_PIN_DUPLICATEKEY"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_36( -406, "WFS_ERR_PIN_KEYNOVALUE"             , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 4),
    XFS_37( -407, "WFS_ERR_PIN_USEVIOLATION"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_38( -408, "WFS_ERR_PIN_NOPIN"                  , 268, XFSConstant.INVALID_ATD_PIN_ENCR_TYPE_FOR_ATM                                                                                         , -1, 4),
    XFS_39( -409, "WFS_ERR_PIN_INVALIDKEYLENGTH"       , 268, XFSConstant.INVALID_ATD_PIN_ENCR_TYPE_FOR_ATM                                                                                         , -1, 4),
    XFS_40( -410, "WFS_ERR_PIN_KEYINVALID"             ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_41( -411, "WFS_ERR_PIN_KEYNOTSUPPORTED"        ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_42( -412, "WFS_ERR_PIN_NOACTIVEKEYS"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_43( -414, "WFS_ERR_PIN_NOTERMINATEKEYS"        ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_44( -415, "WFS_ERR_PIN_MINIMUMLENGTH"          ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_45( -416, "WFS_ERR_PIN_PROTOCOLNOTSUPP"        , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 4),
    XFS_46( -417, "WFS_ERR_PIN_INVALIDDATA"            ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_47( -418, "WFS_ERR_PIN_NOTALLOWED"             ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_48( -419, "WFS_ERR_PIN_NOKEYRAM"               ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_49( -420, "WFS_ERR_PIN_NOCHIPTRANSACTIVE"      ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_50( -421, "WFS_ERR_PIN_ALGORITHMNOTSUPP"       , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 4),
    XFS_51( -422, "WFS_ERR_PIN_FORMATNOTSUPP"          ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_52( -423, "WFS_ERR_PIN_HSMSTATEINVALID"        , 663, XFSConstant.THE_SECURITY_MODULE_HAS_FAILED                                                                                            , -1, 4),
    XFS_53( -424, "WFS_ERR_PIN_MACINVALID"             , 510, "TERMINAL_INDICATES_A_** MAC_**_FAILURE."                                                                                   , -1, 4),
    XFS_54( -425, "WFS_ERR_PIN_PROTINVALID"            , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 4),
    XFS_55( -426, "WFS_ERR_PIN_FORMATINVALID"          , 285, "ATM_-_INVALID_DATA_FOUND_IN_ENHANCED_SUPPLY_COUNTS_MESSAGE._ATD_BNA_COUNTERS_WILL_BE_SET_TO_0."                            , -1, 4),
    XFS_56( -427, "WFS_ERR_PIN_CONTENTINVALID"         , 663, XFSConstant.THE_SECURITY_MODULE_HAS_FAILED                                                                                            , -1, 4),
    XFS_57( -429, "WFS_ERR_PIN_SIG_NOT_SUPP"           , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 4),
    XFS_58( -431, "WFS_ERR_PIN_INVALID_MOD_LEN"        , 686, "ATM_-_The_ATDD1_record_for_this_terminal_contains_an_invalid_,offset/length_pair._Delete_and_readd_the_ATD_record."        , -1, 4),
    XFS_59( -432, "WFS_ERR_PIN_INVALIDCERTSTATE"       ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_60( -433, "WFS_ERR_PIN_KEY_GENERATION_ERROR"   , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 4),
    XFS_61( -434, "WFS_ERR_PIN_EMV_VERIFY_FAILED"      , 711, XFSConstant.LOADKEY_OPTION_NOT_SUPPORTED                                                                                              , -1, 4),
    XFS_62( -435, "WFS_ERR_PIN_RANDOMINVALID"          ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_63( -436, "WFS_ERR_PIN_SIGNATUREINVALID"       ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_64( -437, "WFS_ERR_PIN_SNSCDINVALID"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_65( -438, "WFS_ERR_PIN_NORSAKEYPAIR"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_66( -439, "WFS_ERR_PIN_INVALID_PORT"           ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_67( -440, "WFS_ERR_PIN_POWERSAVETOOSHORT"      ,   4, XFSConstant.TERMINAL_PIN_PAD_ERROR                                                                                                    , -1, 4),
    XFS_68( -100, "WFS_ERR_PTR_FORMNOTFOUND"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_69( -101, "WFS_ERR_PTR_FIELDNOTFOUND"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_70( -102, "WFS_ERR_PTR_NOMEDIAPRESENT"         ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_71( -103, "WFS_ERR_PTR_READNOTSUPPORTED"       ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_72( -104, "WFS_ERR_PTR_FLUSHFAIL"              , 435, "ERROR_WHILE_ATTEMPTING_TO_DELETE_TOKEN_FROM_THE_ATD_-_TRAN"                                                                , -1, 1),
    XFS_73( -105, "WFS_ERR_PTR_MEDIAOVERFcash"         , 424, "MAXIMUM_PRINTER_DATA_REACHED_FOR_ATM,_RECEIPT_GROUP,_TRAN_CODE_-_INCR_ATD_LINES/RCPT_OR_REDUCE_DATA_IN_RCPT_RECORD"        , -1, 1),
    XFS_74( -106, "WFS_ERR_PTR_FIELDSPECFAILURE"       ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_75( -107, "WFS_ERR_PTR_FIELDERROR"             , 280, "CPLF_PRINT_FUNCTION_ABORTED"                                                                                               , -1, 1),
    XFS_76( -108, "WFS_ERR_PTR_MEDIANOTFOUND"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_77( -109, "WFS_ERR_PTR_EXTENTNOTSUPPORTED"     ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_78( -110, "WFS_ERR_PTR_MEDIAINVALID"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_79( -111, "WFS_ERR_PTR_FORMINVALID"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_80( -112, "WFS_ERR_PTR_FIELDINVALID"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_81( -113, "WFS_ERR_PTR_MEDIASKEWED"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_82( -114, "WFS_ERR_PTR_RETRACTBINFULL"         ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_83( -115, "WFS_ERR_PTR_STACKERFULL"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_84( -116, "WFS_ERR_PTR_PAGETURNFAIL"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_85( -117, "WFS_ERR_PTR_MEDIATURNFAIL"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_86( -118, "WFS_ERR_PTR_SHUTTERFAIL"            , 272, "OPEN_ERROR_ON,_ERROR"                                                                                                      , -1, 1),
    XFS_87( -119, "WFS_ERR_PTR_MEDIAJAMMED"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_88( -120, "WFS_ERR_PTR_FILE_IO_ERROR"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_89( -121, "WFS_ERR_PTR_CHARSETDATA"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_90( -122, "WFS_ERR_PTR_PAPERJAMMED"            ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_91( -123, "WFS_ERR_PTR_PAPEROUT"               ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_92( -124, "WFS_ERR_PTR_INKOUT"                 ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_93( -125, "WFS_ERR_PTR_TONEROUT"               ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_94( -126, "WFS_ERR_PTR_LAMPINOP"               ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_95( -127, "WFS_ERR_PTR_SOURCEINVALID"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_96( -128, "WFS_ERR_PTR_SEQUENCEINVALID"        ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_97( -129, "WFS_ERR_PTR_MEDIASIZE"              ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_98( -130, "WFS_ERR_PTR_INVALID_PORT"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_99( -131, "WFS_ERR_PTR_MEDIARETAINED"          ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_100(-132, "WFS_ERR_PTR_BLACKMARK"              ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_101(-133, "WFS_ERR_PTR_DEFINITIONEXISTS"       ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_102(-134, "WFS_ERR_PTR_MEDIAREJECTED"          , 316, "TERMINAL_-_INVALID_RECEIPT_OPTION_SET_ON_ATD_FOR_STATEMENT._PRINT._RECEIPT_PRINTER_MUST_BE_USED"                          , -1, 1),
    XFS_103(-135, "WFS_ERR_PTR_MEDIARETRACTED"         ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_104(-136, "WFS_ERR_PTR_MSFERROR"               ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_105(-137, "WFS_ERR_PTR_NOMSF"                  ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_106(-138, "WFS_ERR_PTR_FILENOTFOUND"           ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_107(-139, "WFS_ERR_PTR_POWERSAVETOOSHORT"      ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_108(-140, "WFS_ERR_PTR_POWERSAVEMEDIAPRESENT"  ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_109(-141, "WFS_ERR_PTR_PASSBOOKCLOSED"         ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_110(-142, "WFS_ERR_PTR_LASTORFIRSTPAGEREACHED" ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -2, 1),
    XFS_111(-143, "WFS_ERR_PTR_COMMANDUNSUPP"          , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 1),
    XFS_112(-144, "WFS_ERR_PTR_SYNCHRONIZEUNSUPP"      ,   3, XFSConstant.TERMINAL_STATEMENT_PRINTER_ERROR                                                                                          , -1, 1),
    XFS_113(-300, "WFS_ERR_CDM_INVALIDCURRENCY"        , 299, "ATM_-_NO_CURRENCY_ACCEPTOR_DATA_WAS_FOUND_IN_THE_ENHANCED._SUPPLY_COUNTS_MESSAGE._MESSAGE_IGNORED."                        , -1, 3),
    XFS_114(-301, "WFS_ERR_CDM_INVALIDTELLERID"        , 318, "TERMINAL_-_INVALID_DEVICE_ID_RECEIVED"                                                                                     , -1, 3),
    XFS_115(-302, "WFS_ERR_CDM_CASHUNITERROR"          ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_116(-303, "WFS_ERR_CDM_INVALIDDENOMINATION"    , 319, "TERMINAL_-_INVALID_DENOMINATION_ID_RECEIVED"                                                                               , -1, 3),
    XFS_117(-304, "WFS_ERR_CDM_INVALIDMIXNUMBER"       , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 3),
    XFS_118(-305, "WFS_ERR_CDM_NOCURRENCYMIX"          ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_119(-306, "WFS_ERR_CDM_NOTDISPENSABLE"         , 281, "DISPENSE_ERROR_FOR_AMT_-_REVIEW_ATD"                                                                                       , -1, 3),
    XFS_120(-307, "WFS_ERR_CDM_TOOMANYITEMS"           , 281, "DISPENSE_ERROR_FOR_AMT_-_REVIEW_ATD"                                                                                       , -1, 3),
    XFS_121(-308, "WFS_ERR_CDM_UNSUPPOSITION"          ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_122(-310, "WFS_ERR_CDM_SAFEDOOROPEN"           , 598, "SAFE DOOR OPEN"                                                                      , -2, 3),
    XFS_123(-312, "WFS_ERR_CDM_SHUTTERNOTOPEN"         , 272, "SHUTTER OPEN_ERROR"                                                                                                      , -2, 3),
    XFS_124(-313, "WFS_ERR_CDM_SHUTTEROPEN"            , 598, "SHUTTER OPEN"                                                                      , -2, 3),
    XFS_125(-314, "WFS_ERR_CDM_SHUTTERCLOSED"          , 522, "SHUTTER CLOSED"                                                                                        , -2, 3),
    XFS_126(-315, "WFS_ERR_CDM_INVALIDCASHUNIT"        ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_127(-316, "WFS_ERR_CDM_NOITEMS"                ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_128(-317, "WFS_ERR_CDM_EXCHANGEACTIVE"         , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 3),
    XFS_129(-318, "WFS_ERR_CDM_NOEXCHANGEACTIVE"       , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 3),
    XFS_130(-319, "WFS_ERR_CDM_SHUTTERNOTCLOSED"       ,   7, "SHUTTER CLOSE_ERROR"                                                                                         , -2, 3),
    XFS_131(-320, "WFS_ERR_CDM_PRERRORNOITEMS"         ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_132(-321, "WFS_ERR_CDM_PRERRORITEMS"           ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_133(-322, "WFS_ERR_CDM_PRERRORUNKNOWN"         ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -2, 3),
    XFS_134(-323, "WFS_ERR_CDM_ITEMSTAKEN"             , 508, "TERMINAL_-_CUSTOMER_HAD_ACCESS_TO_THE_CASH_OR_NO_STACK_TO_PRESENT"                                                         ,  0, 3),
    XFS_135(-327, "WFS_ERR_CDM_INVALIDMIXTABLE"        ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_136(-328, "WFS_ERR_CDM_OUTPUTPOS_NOT_EMPTY"    ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         ,  0, 3),
    XFS_137(-329, "WFS_ERR_CDM_INVALIDRETRACTPOSITION" ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_138(-330, "WFS_ERR_CDM_NOTRETRACTAREA"         ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_139(-333, "WFS_ERR_CDM_NOCASHBOXPRESENT"       , 581, "TERMINAL_-_CASSETTE_#_LOW_OR_OUT_OF_CASH"                                                                                  , -1, 3),
    XFS_140(-334, "WFS_ERR_CDM_AMOUNTNOTINMIXTABLE"    , 299, "ATM_-_NO_CURRENCY_ACCEPTOR_DATA_WAS_FOUND_IN_THE_ENHANCED_SUPPLY_COUNTS_MESSAGE._MESSAGE_IGNORED."                         , -1, 3),
    XFS_141(-335, "WFS_ERR_CDM_ITEMSNOTTAKEN"          , 402, "COMPLETION_FROM_TERMINAL_TIMED_OUT"                                                                                        , -1, 3),
    XFS_142(-336, "WFS_ERR_CDM_ITEMSLEFT"              ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -2, 3),
    XFS_143(-337, "WFS_ERR_CDM_INVALID_PORT"           ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_144(-338, "WFS_ERR_CDM_POWERSAVETOOSHORT"      ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_145(-339, "WFS_ERR_CDM_POWERSAVEMEDIAPRESENT"  ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_146(-340, "WFS_ERR_CDM_POSITION_NOT_EMPTY"     ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -2, 3),
    XFS_147(-341, "WFS_ERR_CDM_INCOMPLETERETRACT"      ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -2, 3),
    XFS_148(-342, "WFS_ERR_CDM_COMMANDUNSUPP"          , 700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 3),
    XFS_149(-343, "WFS_ERR_CDM_SYNCHRONIZEUNSUPP"      ,   7, XFSConstant.TERMINAL_ENVELOPE_DISPENSER_ERROR                                                                                         , -1, 3),
    XFS_150(-802, "WFS_ERR_SIU_SYNTAX"                 ,	700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 8),
    XFS_151(-803, "WFS_ERR_SIU_PORT_ERROR"             ,	700, XFSConstant.INVALID_COMMAND                                                                                                           , -2, 8),
    XFS_152(-805, "WFS_ERR_SIU_COMMANDUNSUPP"          ,	700, XFSConstant.INVALID_COMMAND                                                                                                           , -1, 8),
    //door-siu-sensor -810 - -819
    XFS_153(-810, "WFS_NOT_SIU_0", 0, "AMBUSH DETECTOR OFF", -1, 8),
    XFS_154(-811, "WFS_NOT_SIU_1", 1, "AMBUSH DETECTOR ON", -1, 8),
    XFS_155(-812, "WFS_NOT_SIU_2", 2, "BURGLARY SENSOR OFF", -1, 8),
    XFS_156(-813, "WFS_NOT_SIU_3", 3, "BURGLARY SENSOR ON", -1, 8),
    XFS_157(-814, "WFS_NOT_SIU_4", 4, "SAFE DOOR OPEN", -1, 8),
    XFS_158(-815, "WFS_NOT_SIU_5", 5, "SAFE DOOR CLOSED", -1, 8),
    XFS_159(-816, "WFS_NOT_SIU_6", 6, "CABINET DOOR OPEN", -1, 8),
    XFS_160(-817, "WFS_NOT_SIU_7", 7, "CABINET DOOR CLOSED", -1, 8),
    XFS_161(-818, "WFS_NOT_SIU_8", 516, "SUPERVISOR SWITCH TURNED OFF", -1, 8),
    XFS_162(-819, "WFS_NOT_SIU_9", 518, "SUPERVISOR SWITCH TURNED ON", -1, 8),
    //output state device -820 - -829
    XFS_163(-820, "WFS_NOT_SIU_10", 273, "POSITION ERROR ON DISPENSER", -1, 8),
    XFS_164(-821, "WFS_NOT_SIU_11", 274, "POSITION ERROR ON CARD READER", -1, 8),
    XFS_165(-822, "WFS_NOT_SIU_12", 275, "POSITION ERROR ON RECEIPT PRINTER", -1, 8),
    XFS_166(-823, "WFS_NOT_SIU_13", 276, "POSITION ERROR ON PINPAD", -1, 8),
    XFS_167(-824, "WFS_NOT_SIU_14", 277, "POSITION ERROR ON ALARM-SENSOR", -1, 8),
    XFS_168(-825, "WFS_NOT_SIU_15", 273, "DISPENSER POSITION OK", -1, 8),
    XFS_169(-826, "WFS_NOT_SIU_16", 274, "CARD READER POSITION OK", -1, 8),
    XFS_170(-827, "WFS_NOT_SIU_17", 275, "RECEIPT PRINTER POSITION OK", -1, 8),
    XFS_171(-828, "WFS_NOT_SIU_19", 276, "PINPAD POSITION OK", -1, 8),
    XFS_172(-829, "WFS_NOT_SIU_20", 277, "ALARM-SENSOR POSITION OK", -1, 8),
    //cassette status -830 - -849
    XFS_173(-830, "WFS_NOT_SIU_21", 573, "CASSETTE 1 - CASH SUPPLY LOW", -1, 8),
    XFS_174(-831, "WFS_NOT_SIU_22", 573, "CASSETTE 2 - CASH SUPPLY LOW", -1, 8),
    XFS_175(-832, "WFS_NOT_SIU_23", 573, "CASSETTE 3 - CASH SUPPLY LOW", -1, 8),
    XFS_176(-833, "WFS_NOT_SIU_24", 573, "CASSETTE 4 - CASH SUPPLY LOW", -1, 8),
    XFS_177(-834, "WFS_NOT_SIU_25", 573, "CASSETTE 5 - CASH SUPPLY LOW", -1, 8),
    XFS_178(-835, "WFS_NOT_SIU_26", 581, "CASSETTE 1 OUT OF CASH", -1, 8),
    XFS_179(-836, "WFS_NOT_SIU_27", 581, "CASSETTE 2 OUT OF CASH", -1, 8),
    XFS_180(-837, "WFS_NOT_SIU_28", 581, "CASSETTE 3 OUT OF CASH", -1, 8),
    XFS_181(-838, "WFS_NOT_SIU_29", 581, "CASSETTE 4 OUT OF CASH", -1, 8),
    XFS_182(-839, "WFS_NOT_SIU_30", 581, "CASSETTE 5 OUT OF CASH", -1, 8),
    XFS_183(-840, "WFS_NOT_SIU_31", 590, "CASSETTE 1 REMOVED", -1, 8),
    XFS_184(-841, "WFS_NOT_SIU_32", 590, "CASSETTE 2 REMOVED", -1, 8),
    XFS_185(-842, "WFS_NOT_SIU_33", 590, "CASSETTE 3 REMOVED", -1, 8),
    XFS_186(-843, "WFS_NOT_SIU_34", 590, "CASSETTE 4 REMOVED", -1, 8),
    XFS_187(-844, "WFS_NOT_SIU_35", 590, "CASSETTE 5 REMOVED", -1, 8),
    XFS_188(-845, "WFS_NOT_SIU_36", 590, "CASSETTE 1 INSERTED", -1, 8),
    XFS_189(-846, "WFS_NOT_SIU_37", 590, "CASSETTE 2 INSERTED", -1, 8),
    XFS_190(-847, "WFS_NOT_SIU_38", 590, "CASSETTE 3 INSERTED", -1, 8),
    XFS_191(-848, "WFS_NOT_SIU_39", 590, "CASSETTE 4 INSERTED", -1, 8),
    XFS_192(-849, "WFS_NOT_SIU_40", 590, "CASSETTE 5 INSERTED", -1, 8),
    //receipt printer paper or supply status status -850 - -859
    XFS_193(-850, "WFS_NOT_SIU_41", 247, "PAPER LOW-RECEIPT", -1, 8),
    XFS_194(-851, "WFS_NOT_SIU_42", 195, "PAPER OUT-RECEIPT", -1, 8),
    XFS_195(-852, "WFS_NOT_SIU_43", 196, "CUT NOT COMPLETED-RECEIPT", -1, 8),

    //Devices HWError s status -860 - -869
    XFS_196(-860, "WFS_NOT_SIU_44", 192, "RECEIPT PRINTER FAULTED", -1, 8),
    XFS_197(-861, "WFS_NOT_SIU_45", 0, "RECEIPT PRINTER OK", -1, 8),
    XFS_198(-862, "WFS_NOT_SIU_46", 143, "CARD READER FAULTED", -1, 8),
    XFS_199(-863, "WFS_NOT_SIU_47", 0, "CARD READER OK", -1, 8),
    XFS_200(-864, "WFS_NOT_SIU_48", 56, "DISPENSER FAULTED", -1, 8),
    XFS_201(-865, "WFS_NOT_SIU_48", 0, "DISPENSER OK", -1, 8),
    ;

    XFSToDDC( int xfsCode, String xfsDescription, int ddcCode, String ddcDescription, int severity, int module ) {
        this.xfsCode = xfsCode;
        this.xfsDescription = xfsDescription;
        this.ddcCode = ddcCode;
        this.ddcDescription = ddcDescription;
        this.severity = severity;
        this.module = module;
    }

    private final int xfsCode;
    private final String xfsDescription;
    private final int ddcCode;
    private final String ddcDescription;
    private final int severity;
    private final int module;


    public int getXfsCode() {
        return xfsCode;
    }

    public static XFSEntity searchByXFSCode( final int xfsCode ) throws XFSException {
        for ( XFSToDDC code : XFSToDDC.values() ) {
            if ( xfsCode == code.xfsCode ) {
                return new XFSEntity( code.xfsCode, code.xfsDescription, code.ddcCode, code.ddcDescription, code.severity, code.module );
            }
        }
        throw new XFSException( "XFS Code not found" );
    }


    private static class XFSConstant{

        private static final String TERMINAL_CARD_READER_ERROR = "TERMINAL_CARD_READER ERROR";
        private static final String TERMINAL_CARD_READER_ERROR_1 = "TERMINAL_CARD_READER_ERROR";
        private static final String BAD_CARD_TRACK_2 = "BAD_CARD_TRACK_2";
        private static final String THE_SECURITY_MODULE_HAS_FAILED = "THE_SECURITY_MODULE_HAS_FAILED";
        private static final String LOADKEY_OPTION_NOT_SUPPORTED = "LOADKEY_OPTION_NOT_SUPPORTED";
        private static final String INVALID_COMMAND = "INVALID_COMMAND";
        private static final String TERMINAL_PIN_PAD_ERROR = "TERMINAL_PIN_PAD_ERROR";
        private static final String INVALID_ATD_PIN_ENCR_TYPE_FOR_ATM = "INVALID_ATD.PIN-ENCR-TYPE_FOR_ATM";
        private static final String TERMINAL_STATEMENT_PRINTER_ERROR = "TERMINAL_STATEMENT_PRINTER_ERROR";
        private static final String TERMINAL_ENVELOPE_DISPENSER_ERROR = "TERMINAL_ENVELOPE_DISPENSER_ERROR";
    }




}
