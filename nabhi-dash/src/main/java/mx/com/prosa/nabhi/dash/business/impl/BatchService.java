package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.batch.YAMLLoader;
import mx.com.prosa.nabhi.dash.batch.model.Schema;
import mx.com.prosa.nabhi.dash.business.*;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.acv.FileException;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.model.jdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BatchService implements IBatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger( BatchService.class );

    private final YAMLLoader yamlLoader;
    private final IIDFService idfService;
    private final IATDService atdService;
    private final IAPCService apcService;
    private final IPrefixService prefixService;
    private final ISurchargeService surchargeService;

    @Autowired
    public BatchService( YAMLLoader yamlLoader, IIDFService idfService, IATDService atdService, IAPCService apcService, IPrefixService prefixService, ISurchargeService surchargeService ) {
        this.yamlLoader = yamlLoader;
        this.idfService = idfService;
        this.atdService = atdService;
        this.apcService = apcService;
        this.prefixService = prefixService;
        this.surchargeService = surchargeService;
    }

    @Override
    public String batch( MultipartFile file ) throws FileException {
        try {
            String data = new String( file.getBytes() );
            Schema schema = yamlLoader.getSchema( data );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Inicia operación batch para %s de las entidades %s", schema.getOperation(), schema.getModule() ) );
            }
            switch ( schema.getModule() ) {
                case INSTITUCIONES:
                    List < IDF > idfs = yamlLoader.getInstitutions( data );
                    return doExecuteInstitutions( schema, idfs );
                case BINES:
                    List < Prefix > prefixes = yamlLoader.getPrefixes( data );
                    return doExecutePrefix( schema, prefixes );
                case CAJEROS:
                    List < ATD > atds = yamlLoader.getATMS( data );
                    return doExecuteAtms( schema, atds );
                case APC:
                    List < APC > apcs = yamlLoader.getAcquirerProfiles( data );
                    return doExecuteAcquirerProfiles( schema, apcs );
                case COMISIONES:
                    List < Surcharge > surcharges = yamlLoader.geSurcharges( data );
                    return doExecuteSurcharges( schema, surcharges );
                default:
                    throw new FileException( CatalogError.INVALID_MODULE );
            }
        } catch ( IOException e ) {
            throw new FileException( CatalogError.FILE_NO_READ );
        }
    }

    @Override
    public Resource loadAsResource( String filename ) throws FileException {
        Resource resource = new ClassPathResource( filename );
        if ( resource.exists() ) {
            return resource;
        } else {
            throw new FileException( CatalogError.FILE_NOT_FOUND, filename );
        }
    }

    private String doExecuteInstitutions( Schema schema, List < IDF > idfs ) {
        StringBuilder builder = new StringBuilder();
        for ( IDF idf : idfs ) {
            builder.append( String.format( "Institución %s ", idf.getFiid() ) );
            try {
                if ( schema.getOperation() == Schema.Operation.ALTA ) {
                    idfService.create( idf );
                    builder.append( "Creada correctamente [IDF]" );
                } else if ( schema.getOperation() == Schema.Operation.BAJA ) {
                    idfService.delete( idf );
                    builder.append( "Actualizada correctamente [IDF]" );
                } else if ( schema.getOperation() == Schema.Operation.CAMBIO ) {
                    idfService.modify( idf );
                    builder.append( "Eliminada correctamente [IDF]" );
                }
                builder.append( "\n" );
            } catch ( DataBaseException | IDFException e ) {
                builder.append( e.getMessage() );
            }
        }
        return builder.toString();
    }

    private String doExecuteAtms( Schema schema, List < ATD > atds ) {
        StringBuilder builder = new StringBuilder();
        for ( ATD atd : atds ) {
            builder.append( String.format( "Cajero %s ", atd.getTerminalId() ) );
            try {
                if ( schema.getOperation() == Schema.Operation.ALTA ) {
                    atdService.create( atd );
                    builder.append( "Creado correctamente [ATD]" );
                } else if ( schema.getOperation() == Schema.Operation.BAJA ) {
                    atdService.delete( atd );
                    builder.append( "Actualizado correctamente [ATD]" );
                } else if ( schema.getOperation() == Schema.Operation.CAMBIO ) {
                    atdService.modify( atd );
                    builder.append( "Eliminado correctamente [ATD]" );
                }
                builder.append( "\n" );
            } catch ( DataBaseException | IDFException e ) {
                builder.append( e.getMessage() );
            }
        }
        return builder.toString();
    }

    private String doExecutePrefix( Schema schema, List < Prefix > prefixes ) {
        StringBuilder builder = new StringBuilder();
        for ( Prefix prefix : prefixes ) {
            builder.append( String.format( "BIN %s ", prefix.getBin() ) );
            try {
                if ( schema.getOperation() == Schema.Operation.ALTA ) {
                    prefixService.create( prefix );
                    builder.append( "Creado correctamente [BIN]" );
                } else if ( schema.getOperation() == Schema.Operation.BAJA ) {
                    prefixService.delete( prefix );
                    builder.append( "Actualizado correctamente [BIN]" );
                } else if ( schema.getOperation() == Schema.Operation.CAMBIO ) {
                    prefixService.modify( prefix );
                    builder.append( "Eliminado correctamente [BIN]" );
                }
                builder.append( "\n" );
            } catch ( DataBaseException | IDFException e ) {
                builder.append( e.getMessage() );
            }
        }
        return builder.toString();
    }


    private String doExecuteAcquirerProfiles( Schema schema, List < APC > apcs ) {
        StringBuilder builder = new StringBuilder();
        for ( APC apc : apcs ) {
            builder.append( String.format( "APC %s %s %s ", apc.getFormAcct(), apc.getToAcct(), apc.getTranCode() ) );
            try {
                if ( schema.getOperation() == Schema.Operation.ALTA ) {
                    apcService.create( apc );
                    builder.append( "Creado correctamente [APC]" );
                } else if ( schema.getOperation() == Schema.Operation.BAJA ) {
                    apcService.delete( apc );
                    builder.append( "Actualizado correctamente [APC]" );
                } else if ( schema.getOperation() == Schema.Operation.CAMBIO ) {
                    apcService.modify( apc );
                    builder.append( "Eliminado correctamente [APC]" );
                }
                builder.append( "\n" );
            } catch ( DataBaseException | IDFException e ) {
                builder.append( e.getMessage() );
            }
        }
        return builder.toString();
    }

    private String doExecuteSurcharges( Schema schema, List < Surcharge > surcharges ) {
        StringBuilder builder = new StringBuilder();
        for ( Surcharge surcharge : surcharges ) {
            builder.append( String.format( "Comisión %s %s %s", surcharge.getSurchargeId().getFiidAcquirer(), surcharge.getSurchargeId().getFiidIssuing(), surcharge.getSurchargeId().getTranCode() ) );
            try {
                if ( schema.getOperation() == Schema.Operation.ALTA ) {
                    surchargeService.create( surcharge );
                    builder.append( "Creada correctamente [SURCHARGE]" );
                } else if ( schema.getOperation() == Schema.Operation.BAJA ) {
                    surchargeService.delete( surcharge );
                    builder.append( "Actualizada correctamente [SURCHARGE]" );
                } else if ( schema.getOperation() == Schema.Operation.CAMBIO ) {
                    surchargeService.modify( surcharge );
                    builder.append( "Eliminada correctamente [SURCHARGE]" );
                }
                builder.append( "\n" );
            } catch ( DataBaseException | IDFException e ) {
                builder.append( e.getMessage() );
            }
        }
        return builder.toString();
    }


}
