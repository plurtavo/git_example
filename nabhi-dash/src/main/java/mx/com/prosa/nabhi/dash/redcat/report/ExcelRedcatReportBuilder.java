package mx.com.prosa.nabhi.dash.redcat.report;

import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Component
public class ExcelRedcatReportBuilder {

    private static final Logger LOG = LoggerFactory.getLogger( ExcelRedcatReportBuilder.class );
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public ByteArrayOutputStream buildRedcatBook( BookJsonRedcat bookJsonRedcat, String fiid ) throws  RedcatException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( fiid );
        }
        try (Workbook workbook = new XSSFWorkbook() ){
            for ( SheetJsonRedcat jsonRedcat : bookJsonRedcat.getSheetJsonRedcats() ){
                buildSheet( jsonRedcat, workbook );
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            java.util.Date tempD = new java.util.Date();
            Date endDate = new Date( tempD.getTime() );

            formatter.format( endDate );
            workbook.write(stream);
            return stream;
        } catch ( IOException e ) {
            LOG.error( e.getMessage() );
            throw new RedcatException( CatalogError.REDCAT_ERROR_EXCEL_BUILD, "Hubo un problema al guardar el archivo de excel" );
        }
    }

    private void buildSheet( SheetJsonRedcat sheetJsonRedcat, Workbook workbook ) {
        Sheet sheet = workbook.createSheet( sheetJsonRedcat.getSheetName() );
        Font headerFont = workbook.createFont();
        headerFont.setBold( true );
        headerFont.setColor( IndexedColors.BLUE.getIndex() );
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont( headerFont );
        // Row for Header
        Row headerRow = sheet.createRow( 0 );
        // Header
        for ( int col = 0; col < sheetJsonRedcat.getColumnNames().length; col++ ) {
            Cell cell = headerRow.createCell( col );
            cell.setCellValue( sheetJsonRedcat.getColumnNames()[ col ] );
            cell.setCellStyle( headerCellStyle );
        }
        int rowIdx = 1;
        for ( Redcat redcat : sheetJsonRedcat.getRedcat() ) {
            Row row = sheet.createRow( rowIdx++ );
            row.createCell( 0 ).setCellValue( redcat.getFiid() );
            row.createCell( 1 ).setCellValue( redcat.getTermId() );
            row.createCell( 2 ).setCellValue( redcat.getCout1() );
            row.createCell( 3 ).setCellValue( redcat.getCout2() );
            row.createCell( 4 ).setCellValue( redcat.getCout3() );
            row.createCell( 5 ).setCellValue( redcat.getCout4() );
            row.createCell( 6 ).setCellValue( redcat.getCout5() );
            row.createCell( 7 ).setCellValue( redcat.getCout6() );

            row.createCell( 8 ).setCellValue( redcat.getTotalOutUSD() );
            row.createCell( 9 ).setCellValue( redcat.getTlfUSD() );
            row.createCell( 10 ).setCellValue( redcat.getDifferenceUSD() );

            row.createCell( 11 ).setCellValue( redcat.getTotalOutMXN() );
            row.createCell( 12 ).setCellValue( redcat.getTlfMXN() );
            row.createCell( 13).setCellValue( redcat.getDifferenceMXN() );

            row.createCell( 14).setCellValue( redcat.getEnd1() );
            row.createCell( 15).setCellValue( redcat.getEnd2() );
            row.createCell( 16).setCellValue( redcat.getEnd3() );
            row.createCell( 17).setCellValue( redcat.getEnd4() );
            row.createCell( 18).setCellValue( redcat.getEnd5() );
            row.createCell( 19).setCellValue( redcat.getEnd6() );

            row.createCell( 20 ).setCellValue( redcat.getRemanenteUSD() );
            row.createCell( 21 ).setCellValue( redcat.getRemanenteMXN() );
            row.createCell( 22).setCellValue( redcat.getDenomination1() );
            row.createCell( 23).setCellValue( redcat.getCurrency1() );
            row.createCell( 24).setCellValue( redcat.getDenomination2() );
            row.createCell( 25).setCellValue( redcat.getCurrency2() );
            row.createCell( 26).setCellValue( redcat.getDenomination3() );
            row.createCell( 27).setCellValue( redcat.getCurrency3() );
            row.createCell( 28).setCellValue( redcat.getDenomination4() );
            row.createCell( 29).setCellValue( redcat.getCurrency4() );
            row.createCell( 30).setCellValue( redcat.getDenomination5() );
            row.createCell( 31).setCellValue( redcat.getCurrency5() );
            row.createCell( 32).setCellValue( redcat.getDenomination6() );
            row.createCell( 33).setCellValue( redcat.getCurrency6() );
            row.createCell( 34).setCellValue( redcat.getStartDate().toString() );
            row.createCell( 35).setCellValue( redcat.getEndDate().toString() );
            row.createCell( 36).setCellValue( redcat.getCoutType() );
        }
    }
}


