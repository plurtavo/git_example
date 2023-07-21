 -- ----------------------------------------------------------------------------------------------------
 -- Nombre del Programa : Update_bin.sql                                                                
 -- Autor               : Angel Serralde López                                                          
 -- Compañia            : GONET                                                                         
 -- Proyecto/Procliente : P-81-7039-20                                                                  
 -- Fecha de creación   : 09/MAR/2021                                                                   
 -- Descripcion General : SCRIPT DE REGISTRO DE INSTITUCIONES Y BINES .                                 
 -- Programa Dependiente: N/A.                                                                          
 -- Programa Subsecuente: N/A.                                                                          
 -- Cond. de ejecucion  : SE DEBE EJECUTAR CON EL USUARIO INSTALADOR SI FALLA EL SCRIPT Update_bin.sql  
 -- Dias de ejecucion   : N/A                                 Horario: N/A                              
 --                                                                                                     
 -- Modifico            : N/A                        Fecha de modificación:   N/A                       
 -- Modificaciones      : N/A                                                                           
-- ------------------------------------------------------------------------------------------------------

Begin
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B759';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B798';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B772';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B804';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B799';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B122';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B146';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B147';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B139';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B159';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B175';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B176';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B145';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B789';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B141';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B791';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B794';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B103';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B209';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B203';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B215';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B769';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B184';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B153';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B214';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B195';
    Delete from CAN.tbl_idf_tbl_bin where tbl_idf_pk_fiid = 'B185';
    
    Delete from CAN.tbl_bin where pk_bin = '506247';
    Delete from CAN.tbl_bin where pk_bin = '506245';
    Delete from CAN.tbl_bin where pk_bin = '506313';
    Delete from CAN.tbl_bin where pk_bin = '506335';
    Delete from CAN.tbl_bin where pk_bin = '462278';
    Delete from CAN.tbl_bin where pk_bin = '506344';
    Delete from CAN.tbl_bin where pk_bin = '506352';
    Delete from CAN.tbl_bin where pk_bin = '506353';
    Delete from CAN.tbl_bin where pk_bin = '506343';
    Delete from CAN.tbl_bin where pk_bin = '506361';
    Delete from CAN.tbl_bin where pk_bin = '506384';
    Delete from CAN.tbl_bin where pk_bin = '506386';
    Delete from CAN.tbl_bin where pk_bin = '506374';
    Delete from CAN.tbl_bin where pk_bin = '506274';
    Delete from CAN.tbl_bin where pk_bin = '506350';
    Delete from CAN.tbl_bin where pk_bin = '506263';
    Delete from CAN.tbl_bin where pk_bin = '506251';
    Delete from CAN.tbl_bin where pk_bin = '526400';
    Delete from CAN.tbl_bin where pk_bin = '551353';
    Delete from CAN.tbl_bin where pk_bin = '506411';
    Delete from CAN.tbl_bin where pk_bin = '506410';
    Delete from CAN.tbl_bin where pk_bin = '506414';
    Delete from CAN.tbl_bin where pk_bin = '506320';
    Delete from CAN.tbl_bin where pk_bin = '506346';
    Delete from CAN.tbl_bin where pk_bin = '506355';
    Delete from CAN.tbl_bin where pk_bin = '506461';
    Delete from CAN.tbl_bin where pk_bin = '506402';
    Delete from CAN.tbl_bin where pk_bin = '506393';
    
    Delete from CAN.tbl_idf where pk_fiid = 'B759';
    Delete from CAN.tbl_idf where pk_fiid = 'B798';
    Delete from CAN.tbl_idf where pk_fiid = 'B772';
    Delete from CAN.tbl_idf where pk_fiid = 'B804';
    Delete from CAN.tbl_idf where pk_fiid = 'B799';
    Delete from CAN.tbl_idf where pk_fiid = 'B122';
    Delete from CAN.tbl_idf where pk_fiid = 'B146';
    Delete from CAN.tbl_idf where pk_fiid = 'B147';
    Delete from CAN.tbl_idf where pk_fiid = 'B139';
    Delete from CAN.tbl_idf where pk_fiid = 'B159';
    Delete from CAN.tbl_idf where pk_fiid = 'B175';
    Delete from CAN.tbl_idf where pk_fiid = 'B176';
    Delete from CAN.tbl_idf where pk_fiid = 'B145';
    Delete from CAN.tbl_idf where pk_fiid = 'B789';
    Delete from CAN.tbl_idf where pk_fiid = 'B141';
    Delete from CAN.tbl_idf where pk_fiid = 'B791';
    Delete from CAN.tbl_idf where pk_fiid = 'B794';
    Delete from CAN.tbl_idf where pk_fiid = 'B103';
    Delete from CAN.tbl_idf where pk_fiid = 'B209';
    Delete from CAN.tbl_idf where pk_fiid = 'B203';
    Delete from CAN.tbl_idf where pk_fiid = 'B215';
    Delete from CAN.tbl_idf where pk_fiid = 'B769';
    Delete from CAN.tbl_idf where pk_fiid = 'B184';
    Delete from CAN.tbl_idf where pk_fiid = 'B153';
    Delete from CAN.tbl_idf where pk_fiid = 'B214';
    Delete from CAN.tbl_idf where pk_fiid = 'B195';
    Delete from CAN.tbl_idf where pk_fiid = 'B185';


    Commit;
    
Exception
    When others then
    dbms_output.put_line('Se deshacen las modificaciones');
    Rollback;
end;
