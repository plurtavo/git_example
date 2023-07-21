package mx.com.prosa.nabhi.dash.redcat.report;

import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DATReportBuilder {

    private static final char FS = '|';

    public ByteArrayOutputStream build( List< Redcat > redcatList ) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append( buildHeader() );
        for ( Redcat redcat: redcatList ){
            builder.append( buildReport( redcat ) );
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write( builder.toString().getBytes(StandardCharsets.UTF_8) );
        return outputStream;
    }

    private String buildReport(  Redcat redcat ){
        DecimalFormat decimalFormat = new DecimalFormat( "$#,###,##0.00" );
        DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yy HH:mm" );
        StringBuilder builder = new StringBuilder();
        builder.append(leftAppend( redcat.getFiid(), 5 ) );
        builder.append(leftAppend( redcat.getTermId(), 13) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout1() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout2() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout3() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout4() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout5() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getCout6() ), 20) );

        builder.append(leftAppend( decimalFormat.format(redcat.getTotalOutUSD() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getTlfUSD() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDifferenceUSD() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getTotalOutMXN() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getTlfMXN() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDifferenceMXN() ), 20) );

        builder.append(leftAppend( decimalFormat.format(redcat.getEnd1() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getEnd2() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getEnd3() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getEnd4() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getEnd5() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getEnd6() ), 20) );

        builder.append(leftAppend( decimalFormat.format(redcat.getRemanenteUSD() ), 20) );
        builder.append(leftAppend( decimalFormat.format(redcat.getRemanenteMXN() ), 20) );

        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination1() ), 10) );
        builder.append(leftAppend( redcat.getCurrency1(), 7) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination2() ), 10) );
        builder.append(leftAppend( redcat.getCurrency2(), 7) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination3() ), 10) );
        builder.append(leftAppend( redcat.getCurrency3(), 7) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination4() ), 10) );
        builder.append(leftAppend( redcat.getCurrency4(), 7) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination5() ), 10) );
        builder.append(leftAppend( redcat.getCurrency5(), 7) );
        builder.append(leftAppend( decimalFormat.format(redcat.getDenomination6() ), 10) );
        builder.append(leftAppend( redcat.getCurrency6(), 7) );

        builder.append(leftAppend( dateFormat.format( redcat.getStartDate() ), 16) );
        builder.append(leftAppend( dateFormat.format( redcat.getEndDate() ), 16) );

        builder.append(leftAppend( redcat.getCoutType(), 10) );
        builder.append("\n");
        return  builder.toString();
    }

    private String buildHeader(){
        StringBuilder builder = new StringBuilder();
        builder.append(leftAppend( "FIID", 5 ) );
        builder.append(leftAppend( "CODIGO ATM", 13) );
        builder.append(leftAppend( "COUT1", 20) );
        builder.append(leftAppend( "COUT2", 20) );
        builder.append(leftAppend( "COUT3", 20) );
        builder.append(leftAppend( "COUT4", 20) );
        builder.append(leftAppend( "COUT5", 20) );
        builder.append(leftAppend( "COUT6", 20) );

        builder.append(leftAppend( "TOTALOUT DOLARES", 20) );
        builder.append(leftAppend( "TLF DOLARES", 20) );
        builder.append(leftAppend( "DIFERENCIA DOLARES", 20) );
        builder.append(leftAppend( "TOTALOUT MON LOCL", 20) );
        builder.append(leftAppend( "TLF MONEDA LOCAL", 20) );
        builder.append(leftAppend( "DIFEREN MON LOCAL", 20) );

        builder.append(leftAppend( "END1", 20) );
        builder.append(leftAppend( "END2", 20) );
        builder.append(leftAppend( "END3", 20) );
        builder.append(leftAppend( "END4", 20) );
        builder.append(leftAppend( "END5", 20) );
        builder.append(leftAppend( "END6", 20) );

        builder.append(leftAppend( "REMANENTE DOLARES", 20) );
        builder.append(leftAppend( "REMANENTE LOCAL", 20) );

        builder.append(leftAppend( "DENOM_C1", 10) );
        builder.append(leftAppend( "CDE_C1", 7) );
        builder.append(leftAppend( "DENOM_C2", 10) );
        builder.append(leftAppend( "CDE_C2", 7) );
        builder.append(leftAppend( "DENOM_C3", 10) );
        builder.append(leftAppend( "CDE_C3", 7) );
        builder.append(leftAppend( "DENOM_C4", 10) );
        builder.append(leftAppend( "CDE_C4", 7) );
        builder.append(leftAppend( "DENOM_C5", 10) );
        builder.append(leftAppend( "CDE_C5", 7) );
        builder.append(leftAppend( "DENOM_C6", 10) );
        builder.append(leftAppend( "CDE_C6", 7) );

        builder.append(leftAppend( "FECHA_INICIAL", 16) );
        builder.append(leftAppend( "FECHA_FINAL", 16) );

        builder.append(leftAppend( "TIPO_CORTE", 10) );
        builder.append("\n");
        return builder.toString();
    }

    private String leftAppend( String data, int size ){
        StringBuilder builder = new StringBuilder();
        int spaces;
        if ( data != null ){
            spaces = size - data.length();
            for ( int i=0; i<spaces; i++ ){
                builder.append(" ");
            }
            builder.append(data);
        }else {
            spaces = size;
            for ( int i=0; i<spaces; i++ ){
                builder.append(" ");
            }
        }
        builder.append(FS);
        return builder.toString();
    }

}
