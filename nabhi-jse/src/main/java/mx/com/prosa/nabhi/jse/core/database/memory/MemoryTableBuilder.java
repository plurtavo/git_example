package mx.com.prosa.nabhi.jse.core.database.memory;

import mx.com.prosa.nabhi.jse.core.database.memory.bin.BinarySearch;
import mx.com.prosa.nabhi.misc.domain.complete.entity.BINEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.BINRepository;
import mx.com.prosa.nabhi.misc.model.jdb.Prefix;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryTableBuilder {

    private final BinarySearch binarySearch;
    private final ModelMapper mapper;
    private final BINRepository binRepository;
    private static final Logger LOG = LoggerFactory.getLogger( MemoryTableBuilder.class );

    @Autowired
    public MemoryTableBuilder( BinarySearch binarySearch, ModelMapper mapper, BINRepository binRepository ) {
        this.binarySearch = binarySearch;
        this.mapper = mapper;
        this.binRepository = binRepository;
    }

    @PostConstruct
    public void buildBinTable() {
        Type listType = new TypeToken < List < Prefix > >() {
        }.getType();
        List < Prefix > prefixList;
        try {
            List < BINEntity > entities = binRepository.findAll( Sort.by( Sort.Direction.ASC, "BinLen", "Bin", "PanLen" ) );
            binarySearch.buildTable( mapper.map( entities, listType ) );
            if ( LOG.isInfoEnabled() ) {
                LOG.info( "List BIN retrieve" );
            }
        } catch ( ClassCastException e ) {
            prefixList = new ArrayList <>();
            binarySearch.buildTable( prefixList );
            binarySearch.getTable().setEmpty( true );
            LOG.error( "Cannot retrieve Bines list" );
        }
    }


    public BinarySearch getBinarySearch() {
        return binarySearch;
    }

}
