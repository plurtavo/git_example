package mx.com.prosa.nabhi.dash.report;

import mx.com.prosa.nabhi.dash.model.uptime.SingleUptime;
import mx.com.prosa.nabhi.dash.model.uptime.TerminalUptime;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.UptimeException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelReportBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger( ExcelReportBuilder.class );

    public ByteArrayOutputStream buildBookUptime( BookJsonUptime bookJsonUptime ) throws UptimeException {
        try ( Workbook workbook = new XSSFWorkbook() ) {
            buildSheet( bookJsonUptime.getSummary(), workbook );
            for ( SheetJsonUptime jsonUptime : bookJsonUptime.getSheetJsonUptimes() ) {
                buildSheet( jsonUptime, workbook );
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write( stream );
            return stream;
        } catch ( IOException e ) {
            LOGGER.error( e.getMessage() );
            throw new UptimeException( CatalogError.UPTIME_ERROR_DATES_EMPTY );
        }
    }

    public ByteArrayOutputStream buildRawUptime( TerminalUptime terminalUptime ) throws UptimeException {
        try ( Workbook workbook = new XSSFWorkbook() ) {
            buildSheet( terminalUptime, workbook );
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write( stream );
            return stream;
        } catch ( IOException e ) {
            LOGGER.error( e.getMessage() );
            throw new UptimeException( CatalogError.UPTIME_ERROR_DATES_EMPTY );
        }
    }

    private void buildSheet( TerminalUptime terminalUptime, Workbook workbook ) {
        Sheet sheet = workbook.createSheet( terminalUptime.getTerminalId() );
        String id = terminalUptime.getTerminalId();
        font( workbook, sheet, "ID Cajero", "Fecha", "Uptime" );
        int rowIdx = 1;
        for ( SingleUptime singleUptime : terminalUptime.getUptimes() ) {
            Row row = sheet.createRow( rowIdx++ );
            row.createCell( 0 ).setCellValue( id );
            row.createCell( 1 ).setCellValue( singleUptime.getDate() );
            double value = ( singleUptime.getUptime() * 100f ) / 86400f;
            row.createCell( 2 ).setCellValue( value );
            criticalFont( workbook, value, row.getCell( 2 ) );
        }
    }

    private void buildSheet( SheetJsonUptime sheetJsonUptime, Workbook workbook ) {
        Sheet sheet = workbook.createSheet( sheetJsonUptime.getSheetName() );
        font( workbook, sheet, sheetJsonUptime.getColumnNames() );
        int rowIdx = 1;
        for ( RowJsonUptime jsonUptime : sheetJsonUptime.getRowJsonUptimes() ) {
            Row row = sheet.createRow( rowIdx++ );
            row.createCell( 0 ).setCellValue( jsonUptime.getTerminalId() );
            row.createCell( 1 ).setCellValue( jsonUptime.getLocation() );
            row.createCell( 2 ).setCellValue( jsonUptime.getFrom() );
            row.createCell( 3 ).setCellValue( jsonUptime.getTo() );
            row.createCell( 4 ).setCellValue( jsonUptime.getDays() );
            row.createCell( 5 ).setCellValue( jsonUptime.getUptime() );
            criticalFont( workbook, jsonUptime.getUptime(), row.getCell( 5 ) );
        }
        Row row = sheet.createRow( rowIdx );
        row.createCell( 4 ).setCellValue( sheetJsonUptime.getSummaryNames()[ 4 ] );
        row.createCell( 5 ).setCellValue( sheetJsonUptime.getUptime() );
        criticalFont( workbook, sheetJsonUptime.getUptime(), row.getCell( 5 ) );

    }

    private void buildSheet( Summary summary, Workbook workbook ) {
        Sheet sheet = workbook.createSheet( "Resumen" );
        font( workbook, sheet, summary.getColumnNames() );
        int rowIdx = 1;
        for ( InstitutionSummary institutionSummary : summary.getInstitutionSummaries() ) {
            Row row = sheet.createRow( rowIdx++ );
            row.createCell( 0 ).setCellValue( institutionSummary.getFiid() );
            row.createCell( 1 ).setCellValue( institutionSummary.getUptime() );
            criticalFont( workbook, institutionSummary.getUptime(), row.getCell( 1 ) );
        }
        Row row = sheet.createRow( rowIdx );
        row.createCell( 0 ).setCellValue( summary.getSummaryNames()[ 0 ] );
        row.createCell( 1 ).setCellValue( summary.getUptime() );
        criticalFont( workbook, summary.getUptime(), row.getCell( 1 ) );
    }

    private void criticalFont( Workbook workbook, double value, Cell cell ) {
        Font font = workbook.createFont();
        if ( value > 0 && value < 69 ) {
            font.setBold( true );
            font.setColor( IndexedColors.RED.getIndex() );
        }
        if ( value > 69 && value < 79 ) {
            font.setBold( true );
            font.setColor( IndexedColors.ORANGE.getIndex() );
        }
        if ( value > 79 && value < 96 ) {
            font.setBold( true );
            font.setColor( IndexedColors.DARK_YELLOW.getIndex() );
        }
        if ( value > 96 && value <= 100 ) {
            font.setBold( true );
            font.setColor( IndexedColors.GREEN.getIndex() );
        }
        CellStyle criticalStyle = workbook.createCellStyle();
        criticalStyle.setFont( font );
        cell.setCellStyle( criticalStyle );
    }

    private void font( Workbook workbook, Sheet sheet, String... columnNames ) {
        Font headerFont = workbook.createFont();
        headerFont.setBold( true );
        headerFont.setColor( IndexedColors.BLUE.getIndex() );
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont( headerFont );
        // Row for Header
        Row headerRow = sheet.createRow( 0 );
        // Header
        for ( int col = 0; col < columnNames.length; col++ ) {
            Cell cell = headerRow.createCell( col );
            cell.setCellValue( columnNames[ col ] );
            cell.setCellStyle( headerCellStyle );
        }
    }

}
