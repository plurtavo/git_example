package mx.com.prosa.nabhi.jse.core.srh;

import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.jse.core.database.memory.bin.BinarySearch;
import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.SurchargeIdentity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.SurchargeRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.model.jdb.Surcharge;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.srh.RequestSurcharge;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SurchargeFinder {

    private static final Logger LOG = LoggerFactory.getLogger( SurchargeFinder.class );
    private final BinarySearch binarySearch;
    private final ATMSearch atmSearch;
    private final SurchargeRepository surchargeRepository;
    private final ModelMapper mapper;

    @Autowired
    public SurchargeFinder( BinarySearch binarySearch, ATMSearch atmSearch, SurchargeRepository surchargeRepository, ModelMapper mapper ) {
        this.binarySearch = binarySearch;
        this.atmSearch = atmSearch;
        this.surchargeRepository = surchargeRepository;
        this.mapper = mapper;
    }

    public Surcharge getSurcharge( RequestSurcharge surcharge ) throws TransactionException, ATMException {
        String pan = surcharge.getTrack().substring( 0, surcharge.getTrack().indexOf( '=' ) );
        String fiid = binarySearch.search( pan.length(), 11, pan );
        ATDTransactional atd = atmSearch.searchTransactional( surcharge.getTermId() );
        SurchargeIdentity identity = new SurchargeIdentity( atd.getIdf().getFiid(), fiid, surcharge.getTransactionCode() );
        Optional < SurchargeEntity > os = surchargeRepository.findById( identity );
        if ( os.isPresent() ) {
            return mapper.map( os.get(), Surcharge.class );
        } else {
            identity = new SurchargeIdentity( atd.getIdf().getFiid(), "****", surcharge.getTransactionCode() );
            os = surchargeRepository.findById( identity );
            if ( os.isPresent() ) {
                return mapper.map( os.get(), Surcharge.class );
            }
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Comisión no encontrada %s", identity.getTranCode() ) );
            }
            throw new TransactionException( CatalogError.SURCHARGE_NOT_FOUND );
        }
    }
}
