package mx.com.prosa.nabhi.misc.receipt;

import mx.com.prosa.nabhi.misc.constants.ProductConstants;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.receipt.*;
import mx.com.prosa.nabhi.misc.util.IJournalOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.FromAccount;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.serializable.data.ISO;
import us.gonet.token.Token;
import us.gonet.utils.STMTDecoder;
import us.gonet.utils.TokenDecoder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class ReceiptBuilder {

    private static final int MAX_LINE = 41;
    private static final int MAX_TEXT = 18;
    private static final int MAX_VARIABLE = 22;
    public static final Logger LOGGER = LoggerFactory.getLogger( ReceiptBuilder.class );

    public Receipt build( ATMRequestModel arm, ISO iso, ReceiptScript template, boolean costumer, IJournalOperation journalOperation ) {
        String header = buildElements( arm, iso, template.getHeader(), costumer, journalOperation );
        String body = buildElements( arm, iso, template.getBody(), costumer, journalOperation );
        String trailer = buildElements( arm, iso, template.getTrailer(), costumer, journalOperation );
        Receipt receipt = new Receipt();
        receipt.setHeader( header );
        receipt.setBody( body );
        receipt.setTrailer( trailer );
        return receipt;
    }


    private String buildElements( ATMRequestModel arm, ISO iso, List < Entry > template, boolean costumer, IJournalOperation journalOperation ) {
        StringBuilder lines = new StringBuilder();
        if ( template != null ) {
            for ( Entry entry : template ) {
                String finalLine;
                String line1 = entry.getText();
                String line2;
                ReceiptVariable variable = entry.getVariable();
                if ( variable != ReceiptVariable.EMPTY ) {
                    Optional < String > optional = getRequestedValue( arm, iso, variable );
                    line2 = optional.orElse( "" );
                } else {
                    line2 = "";
                }
                if ( variable == ReceiptVariable.STMT ) {
                    String[] stmts = line2.split( "," );
                    for ( String stmt : stmts ) {
                        finalLine = formatLine( line1, stmt, entry.getMarginText(), entry.getMarginVariable() );
                        lines.append( finalLine );
                        lines.append( "\n" );
                        printJournal( journalOperation, costumer, arm.getTermId(), finalLine );
                    }
                } else {
                    finalLine = formatLine( line1, line2, entry.getMarginText(), entry.getMarginVariable() );
                    lines.append( finalLine );
                    lines.append( "\n" );
                    printJournal( journalOperation, costumer, arm.getTermId(), finalLine );
                }
            }
        }
        return lines.toString();
    }

    private String formatLine( String l1, String l2, ReceiptMargin marginLine1, ReceiptMargin marginLine2 ) {
        char[] line = new char[ MAX_LINE ];
        for ( int i = 0; i < MAX_LINE; i++ ) {
            line[ i ] = ' ';
        }
        int maxText = l2.isEmpty() ? MAX_TEXT + MAX_VARIABLE : MAX_TEXT;
        char[] line1 = align( l1, marginLine1, maxText );
        int maxVariable = l1.isEmpty() ? MAX_TEXT + MAX_VARIABLE : MAX_VARIABLE;
        char[] line2 = align( l2, marginLine2, maxVariable );
        if ( !l1.isEmpty() && !l2.isEmpty() ) {
            System.arraycopy( line1, 0, line, 0, maxText );
            System.arraycopy( line2, 0, line, maxText + 1, maxVariable );
        } else if ( !l1.isEmpty() ) {
            System.arraycopy( line1, 0, line, 0, maxText );
        } else {
            System.arraycopy( line2, 0, line, 0, maxVariable );
        }
        return new String( line );
    }

    private char[] align( String line, ReceiptMargin margin, int max ) {
        char[] lineAlign = new char[ max ];
        try {
            char[] oldText = line.toCharArray();
            for ( int i = 0; i < max; i++ ) {
                lineAlign[ i ] = ' ';
            }
            int lineLength = line.length();
            if ( margin == ReceiptMargin.LEFT ) {
                System.arraycopy( oldText, 0, lineAlign, 0, lineLength );
            } else if ( margin == ReceiptMargin.RIGHT ) {
                int lineLengthTemp = lineLength;
                for ( int i = max - 1; i > ( max - lineLength - 1 ); i-- ) {
                    lineAlign[ i ] = oldText[ lineLengthTemp - 1 ];
                    lineLengthTemp--;
                }
            } else {
                int inter = ( max - lineLength ) / 2;
                System.arraycopy( oldText, 0, lineAlign, inter, lineLength );
            }
            return lineAlign;
        } catch ( ArrayIndexOutOfBoundsException e ) {
            LOGGER.error( String.format( "Error al procesar la linea %s", line ) );
            return lineAlign;
        }
    }

    private Optional < String > getRequestedValue( ATMRequestModel arm, ISO iso, ReceiptVariable variable ) {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yy" );
        DateFormat dateAndTimeFormat = new SimpleDateFormat( "dd/MM/yy HH:mm" );
        DateFormat timeFormat = new SimpleDateFormat( "HH:mm" );
        DecimalFormat decimalFormat = new DecimalFormat( "$#,###,##0.00" );
        switch ( variable ) {
            case DATE:
                return Optional.of( dateFormat.format( now ) );
            case DATE_TIME:
                return Optional.of( dateAndTimeFormat.format( now ) );
            case TIME:
                return Optional.of( timeFormat.format( now ) );
            case AT:
                return Optional.of( arm.getTermId().replace( " ", "" ) );
            case TC:
                return Optional.of( TranCodes.getAlphaValue( iso.getDataElements().get( 2 ).getContentField().substring( 0, 2 ) ) );
            case PAN:
                return Optional.of( scramblerPan( iso ) );
            case AC:
                return Optional.of( iso.getDataElements().get( 37 ).getContentField() );
            case FC:
                return Optional.of( FromAccount.getAlphaValue( iso.getDataElements().get( 2 ).getContentField().substring( 2, 4 ) ) );
            case OB:
                return Optional.of( decimalFormat.format( oldBalance( iso ) ) );
            case AMT:
                return Optional.of( decimalFormat.format( amount( iso ) ) );
            case SRH:
                return Optional.of( decimalFormat.format( surchargeWithoutIva( iso ) ) );
            case IVA:
                return Optional.of( decimalFormat.format( ivaSurcharge( iso ) ) );
            case TT:
                return Optional.of( decimalFormat.format( surcharge( iso ) + amount( iso ) ) );
            case CB:
                return Optional.of( decimalFormat.format( balance( iso ) ) );
            case AID:
                return aid( arm );
            case ARQC:
                return arqc( arm );
            case ARPC:
                return arpc( arm );
            case PHN:
                return phone( arm );
            case COMP:
                return company( arm );
            case STMT:
                return Optional.of( lastMoves( iso ) );
            case OWNER:
                return owner( arm );
            case LOC:
                return location( arm );
            case SEC:
                return sequence( iso );
            case ATC:
                return scramblerAccount( iso );
            case PEM:
                return posEntryMode( arm );
            case COD:
                return Optional.of( discretionary( iso ) );
            case TCAP:
                return terminalCapabilities( arm );
            case PROD:
                return product( iso );
            default:
                return Optional.empty();
        }
    }

    private void printJournal( IJournalOperation journalOperation, boolean customer, String terminalId, String finalLine ) {
        if ( !customer ) {
            journalOperation.write( terminalId, finalLine );
        }
    }

    private String scramblerPan( ISO iso ) {
        String str = pan( iso );
        int indexOf = str.indexOf( '=' );
        str = str.substring( 0, indexOf );
        String format = "%-" + ( str.length() - 4 ) + "s%s";
        String scramblerPan = String.format( format, str.substring( 0, 6 ), str.substring( str.length() - 4 ) );
        return scramblerPan.replace( " ", "X" );
    }

    private String pan( ISO iso ) {
        return iso.getDataElements().get( 34 ).getContentField();
    }

    private String discretionary( ISO iso ) {
        String track2 = pan( iso );
        int index = track2.indexOf( '=' );
        String discretionary = track2.substring( index );
        return discretionary.substring( 5, 8 );
    }

    private Optional < String > scramblerAccount( ISO iso ) {
        Optional < String > optional = account( iso );
        String str;
        if ( optional.isPresent() ) {
            str = optional.get();
        } else {
            return optional;
        }
        String formatAccount = "%-" + ( str.length() - 3 ) + "s%s";
        String scramblerAccount = String.format( formatAccount, "", str.substring( str.length() - 3 ) );
        return Optional.of( scramblerAccount.replace( " ", "x" ) );
    }

    private Optional < String > account( ISO iso ) {
        if ( iso.getDataElements().get( 101 ).isBit() && iso.getDataElements().get( 101 ).getContentField() != null ) {
            return Optional.of( iso.getDataElements().get( 101 ).getContentField() );
        }
        return Optional.empty();
    }

    private float surcharge( ISO iso ) {
        if ( iso.getDataElements().get( 125 ).isBit() && iso.getDataElements().get( 125 ).getContentField() != null ) {
            TokenDecoder tokenDecoder = new TokenDecoder( iso.getDataElements().get( 125 ).getContentField().getBytes() );
            Token token = tokenDecoder.getTokens().getToken( "25" );
            if ( token != null ) {
                return Float.parseFloat( token.getToken().get( 1 ).getContentField() ) / 100;
            } else {
                return 0.00f;
            }
        } else {
            return 0.00f;
        }
    }

    private float surchargeWithoutIva( ISO iso ) {
        float surcharge = surcharge( iso );
        if ( surcharge > 0.00 ) {
            float iva = ( 16 * surcharge ) / 100;
            return surcharge - iva;
        } else {
            return surcharge;
        }
    }

    private float ivaSurcharge( ISO iso ) {
        float surcharge = surcharge( iso );
        if ( surcharge > 0.00 ) {
            return ( 16 * surcharge ) / 100;
        } else {
            return surcharge;
        }
    }

    private float oldBalance( ISO iso ) {
        float surcharge = surcharge( iso );
        float balance = balance( iso );
        float amount = amount( iso );
        return balance + amount + surcharge;
    }

    private float balance( ISO iso ) {
        return Float.parseFloat( iso.getDataElements().get( 43 ).getContentField().substring( 13, 25 ) ) / 100;
    }

    private float amount( ISO iso ) {
        return Float.parseFloat( iso.getDataElements().get( 3 ).getContentField() ) / 100;
    }

    String lastMoves( ISO iso ) {
        StringBuilder stmt = new StringBuilder();
        List < String > last = new STMTDecoder().decode( iso.getDataElements().get( 124 ).getContentField() );
        for ( String s : last ) {
            stmt.append( s )
                    .append( "," );
        }
        return stmt.toString();
    }

    private Optional < String > aid( ATMRequestModel arm ) {
        if ( arm.getEmv() != null && arm.getEmv().get( "9F06" ) != null ) {
            return Optional.of( arm.getEmv().get( "9F06" ) );
        }
        return Optional.empty();
    }

    private Optional < String > arqc( ATMRequestModel arm ) {
        if ( arm.getEmv() != null && arm.getEmv().get( "9F26" ) != null ) {
            return Optional.of( arm.getEmv().get( "9F26" ) );
        }
        return Optional.empty();
    }


    private Optional < String > arpc( ATMRequestModel arm ) {
        if ( arm.getEmv() != null && arm.getEmv().get( "9F27" ) != null ) {
            return Optional.of( arm.getEmv().get( "9F27" ) );
        }
        return Optional.empty();
    }


    private Optional < String > phone( ATMRequestModel arm ) {
        if ( arm.getPhoneNumber() != null ) {
            return Optional.of( arm.getPhoneNumber() );
        }
        return Optional.empty();
    }

    private Optional < String > company( ATMRequestModel arm ) {
        if ( arm.getCompany() != null ) {
            return Optional.of( arm.getCompany() );
        }
        return Optional.empty();
    }

    private Optional < String > owner( ATMRequestModel arm ) {
        if ( arm.getTermOwnerName() != null ) {
            return Optional.of( arm.getTermOwnerName() );
        }
        return Optional.empty();
    }

    private Optional < String > location( ATMRequestModel arm ) {
        if ( arm.getTermCity() != null && arm.getTermState() != null ) {
            return Optional.of( arm.getTermCity() + " " + arm.getTermState() );
        }
        return Optional.empty();
    }

    private Optional < String > sequence( ISO iso ) {
        if ( iso.getDataElements().get( 36 ).isBit() ) {
            return Optional.of( iso.getDataElements().get( 36 ).getContentField() );
        }
        return Optional.empty();
    }

    private Optional < String > posEntryMode( ATMRequestModel arm ) {
        if ( arm.getEntryMode() != null ) {
            return Optional.of( arm.getEntryMode() );
        }
        return Optional.empty();
    }

    private Optional < String > terminalCapabilities( ATMRequestModel arm ) {
        Optional < String > optional = posEntryMode( arm );
        return optional.map( s -> s.substring( 1, 2 ) );
    }

    private Optional < String > product( ISO iso ) {
        String prefix = pan( iso ).substring( 0, 1 );
        return Optional.of( ProductConstants.getByBin( prefix ).name() );
    }
}