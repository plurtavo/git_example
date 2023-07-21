package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.IPrinterService;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDBasic;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrinterService implements IPrinterService {

    private static final Logger LOG = LoggerFactory.getLogger( PrinterService.class );
    private final ATMSearch atmSearch;

    @Autowired
    public PrinterService( ATMSearch atmSearch ) {
        this.atmSearch = atmSearch;
    }

    @Override
    public Ticket printingTicket( Generic generic ) throws ATMException {
        Ticket printingTicket = new Ticket();
        ATDBasic atd = atmSearch.searchBasic( generic.getTermId() );
        printingTicket.setBodyTicket( atd.getReceipt() );
        printingTicket.setCode( "00" );
        if ( printingTicket.getBodyTicket().startsWith( "ERROR" ) ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( String.format( "El ticket fue generado con error para %s ", generic.getTermId() ) );
            }
            throw new ATMException( CatalogError.RECEIPT_ERROR_1 );
        }
        return printingTicket;
    }
}
