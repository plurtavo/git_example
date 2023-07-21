package mx.com.prosa.nabhi.jse.core.i8583.receipt;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.misc.domain.complete.entity.RCPTEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.RCPTRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.receipt.Entry;
import mx.com.prosa.nabhi.misc.model.receipt.Receipt;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptARM;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptScript;
import mx.com.prosa.nabhi.misc.receipt.IReceipt;
import mx.com.prosa.nabhi.misc.receipt.ReceiptBuilder;
import mx.com.prosa.nabhi.misc.receipt.ReceiptDefinitionLoader;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.serializable.data.ISO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class ReceiptHandler implements IReceipt {

    public static final Logger LOGGER = LoggerFactory.getLogger( ReceiptHandler.class );
    private final ReceiptBuilder receiptBuilder;
    private final RCPTRepository rcptRepository;
    private final ModelMapper mapper;
    private final JournalOperation journalOperation;
    private final ReceiptDefinitionLoader receiptDefinitionLoader;
    private final Gson gson;

    @Autowired
    public ReceiptHandler( ReceiptBuilder receiptBuilder, RCPTRepository rcptRepository, ModelMapper mapper, JournalOperation journalOperation, ReceiptDefinitionLoader receiptDefinitionLoader ) {
        this.receiptBuilder = receiptBuilder;
        this.rcptRepository = rcptRepository;
        this.mapper = mapper;
        this.journalOperation = journalOperation;
        this.receiptDefinitionLoader = receiptDefinitionLoader;
        gson = new GsonBuilder().create();
    }

    @Override
    public Receipt createReceipt( ATMRequestModel ar, ISO i, boolean costumer ) throws ReceiptException {
        ReceiptScript rcpt;
        TranCodes tranCode = TranCodes.getValue( i.getDataElements().get( 2 ).getContentField().substring( 0, 2 ) );
        String key = "RCPT" + "-" + ar.getTermFiid() + "-" + tranCode.getValue() + "-" +
                ( costumer ? "CLIENTE" : "JOURNAL" );
        Optional < RCPTEntity > or = rcptRepository.findByName( key );
        if ( or.isPresent() ) {
            rcpt = mapper.map( or.get(), ReceiptScript.class );
            @SuppressWarnings( "UnstableApiUsage" ) Type listType = new TypeToken < ArrayList < Entry > >() {
            }.getType();
            rcpt.setHeader( gson.fromJson( or.get().getHeader(), listType ) );
            rcpt.setBody( gson.fromJson( or.get().getBody(), listType ) );
            rcpt.setTrailer( gson.fromJson( or.get().getTrailer(), listType ) );
        } else {
            LOGGER.debug( "No se encuentra script para el recibo, se buscara uno por defecto" );
            rcpt = findType( tranCode, costumer );
        }
        return receiptBuilder.build( ar, i, rcpt, costumer, journalOperation );
    }

    private ReceiptScript findType( TranCodes tranCodes, boolean customer ) throws ReceiptException {
        String add = "";
        if ( !customer ) {
            add = "-journal";
        }
        switch ( tranCodes ) {
            case WITHDRAWAL:
                return template( "/script/retiro" + add + ".yml" ).getScript();
            case BALANCE_INQUIRY:
                return template( "/script/consulta" + add + ".yml" ).getScript();
            case GENERIC_SALE:
                return template( "/script/nip" + add + ".yml" ).getScript();
            case STATEMENT_PRINT:
                return template( "/script/stmt" + add + ".yml" ).getScript();
            case PIN_CHANGE:
                return template( "/script/vta" + add + ".yml" ).getScript();
            default:
                throw new ReceiptException( CatalogError.RECEIPT_ERROR_1 );
        }
    }

    private ReceiptARM template( String file ) throws ReceiptException {
        try {
            return receiptDefinitionLoader.getTemplate( file );
        } catch ( ReceiptException e ) {
            throw new ReceiptException( e.getError(), String.format( "No se encuentra el archivo %s", file ) );
        }
    }
}
