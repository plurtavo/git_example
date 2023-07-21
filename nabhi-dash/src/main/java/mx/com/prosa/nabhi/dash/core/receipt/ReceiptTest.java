package mx.com.prosa.nabhi.dash.core.receipt;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.RCPTEntity;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.receipt.Entry;
import mx.com.prosa.nabhi.misc.model.receipt.Receipt;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptARM;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptScript;
import mx.com.prosa.nabhi.misc.receipt.ReceiptBuilder;
import mx.com.prosa.nabhi.misc.receipt.ReceiptDefinitionLoader;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.serializable.data.ISO;
import us.gonet.utils.DecodeISO8583;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class ReceiptTest {

    public static final Logger LOGGER = LoggerFactory.getLogger( ReceiptTest.class );
    private final ReceiptBuilder receiptBuilder;
    private final ModelMapper mapper;
    private final ReceiptDefinitionLoader receiptDefinitionLoader;
    private final Gson gson;

    @Autowired
    public ReceiptTest( ReceiptBuilder receiptBuilder, ModelMapper mapper, ReceiptDefinitionLoader receiptDefinitionLoader ) {
        this.receiptBuilder = receiptBuilder;
        this.mapper = mapper;
        this.receiptDefinitionLoader = receiptDefinitionLoader;
        gson = new GsonBuilder().create();
    }


    public Receipt createReceipt( TranCodes tranCode, Optional < RCPTEntity > or, boolean costumer ) throws ReceiptException {
        ReceiptARM yml = findType( tranCode, costumer );
        ReceiptScript rcpt = yml.getScript();
        ATMRequestModel arm = yml.getArm();
        ISO iso = new DecodeISO8583( yml.getIsoMessage() ).getIso();
        if ( or.isPresent() ) {
            rcpt = mapper.map( or.get(), ReceiptScript.class );
            @SuppressWarnings( "UnstableApiUsage" ) Type listType = new TypeToken < ArrayList < Entry > >() {
            }.getType();
            rcpt.setHeader( gson.fromJson( or.get().getHeader(), listType ) );
            rcpt.setBody( gson.fromJson( or.get().getBody(), listType ) );
            rcpt.setTrailer( gson.fromJson( or.get().getTrailer(), listType ) );
        } else {
            LOGGER.debug( "No se encuentra script para el recibo de prueba, se buscara uno por defecto" );
        }
        return receiptBuilder.build( arm, iso, rcpt, costumer
                , ( terminalId, message ) -> LOGGER.debug( String.format( "Simulación de escritura journal del ATM %s %s", terminalId, message ) ) );
    }

    private ReceiptARM findType( TranCodes tranCodes, boolean customer ) throws ReceiptException {
        String add = "";
        if ( !customer ) {
            add = "-journal";
        }
        switch ( tranCodes ) {
            case WITHDRAWAL:
                return template( "/script/retiro" + add + ".yml" );
            case BALANCE_INQUIRY:
                return template( "/script/consulta" + add + ".yml" );
            case GENERIC_SALE:
                return template( "/script/vta" + add + ".yml" );
            case STATEMENT_PRINT:
                return template( "/script/stmt" + add + ".yml" );
            case PIN_CHANGE:
                return template( "/script/nip" + add + ".yml" );
            default:
                throw new ReceiptException( CatalogError.RECEIPT_ERROR_1 );
        }
    }

    private ReceiptARM template( String file ) throws ReceiptException {
        try {
            return receiptDefinitionLoader.getTemplate( file );
        } catch ( ReceiptException e ) {
            throw new ReceiptException( e.getError(), String.format( "No se encuentra el archivo para hacer pruebas %s", file ) );
        }
    }
}
