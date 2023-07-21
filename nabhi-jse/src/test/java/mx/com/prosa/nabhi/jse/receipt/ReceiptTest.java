package mx.com.prosa.nabhi.jse.receipt;

import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.receipt.Receipt;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptARM;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptScript;
import mx.com.prosa.nabhi.misc.receipt.ContextRCPT;
import mx.com.prosa.nabhi.misc.receipt.ReceiptBuilder;
import mx.com.prosa.nabhi.misc.receipt.ReceiptDefinitionLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import us.gonet.serializable.data.ISO;
import us.gonet.utils.DecodeISO8583;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { ContextRCPT.class } )
@ActiveProfiles( "dev" )
public class ReceiptTest {

    public static final Logger LOGGER = LoggerFactory.getLogger( ReceiptTest.class );

    @Autowired
    private ReceiptDefinitionLoader receiptDefinitionLoader;
    @Autowired
    private ReceiptBuilder receiptBuilder;

    @Test
    public void testALoad() {
        test( "/script/retiro.yml", true );
    }

    @Test
    public void testBLoad() {
        test( "/script/consulta.yml", true );
    }

    @Test
    public void testCLoad() {
        test( "/script/nip.yml", true );
    }

    @Test
    public void testDLoad() {
        test( "/script/stmt.yml", true );
    }

    @Test
    public void testELoad() {
        test( "/script/vta.yml", true );
    }

    @Test
    public void testFLoad() {
        test( "/script/retiro-journal.yml", false );
    }

    @Test
    public void testGLoad() {
        test( "/script/consulta-journal.yml", false );
    }

    @Test
    public void testHLoad() {
        test( "/script/nip-journal.yml", false );
    }

    @Test
    public void testILoad() {
        test( "/script/vta-journal.yml", false );
    }

    private void test( String file, boolean costumer ) {
        try {
            ReceiptARM receiptARM = template( file );
            ReceiptScript receipt = receiptARM.getScript();
            ATMRequestModel arm = receiptARM.getArm();
            DecodeISO8583 decodeISO8583 = new DecodeISO8583( receiptARM.getIsoMessage() );
            ISO iso = decodeISO8583.getIso();
            Receipt receipt1 = receiptBuilder.build( arm, iso, receipt, costumer,
                    ( terminalId, message ) -> LOGGER.info( String.format( "Escribiendo en Journal del ATM %s %s", terminalId, message ) ) );
            Assert.assertNotNull( receipt1 );
            LOGGER.debug( receipt1.getTicket() );
        } catch ( ReceiptException e ) {
            LOGGER.error( e.getMessage() );
        }

    }

    private ReceiptARM template( String file ) throws ReceiptException {
        return receiptDefinitionLoader.getTemplate( file );
    }

}
