package mx.com.prosa.nabhi.dash.core.impl;

import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService implements IDataBaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger( DataBaseService.class );

    @Override
    public < T, I > void create( JpaRepository < T, I > repository, I id, T value ) throws DataBaseException {
        Optional < T > optional = repository.findById( id );
        if ( optional.isPresent() ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( CatalogError.DATABASE_CREATE_ALREADY_EXIST.getMessage(), id ) );
            }
            throw new DataBaseException( CatalogError.DATABASE_CREATE_ALREADY_EXIST );
        } else {
            try {
                repository.save( value );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Registro con ID %s creado correctamente", id ) );
                }
            } catch ( EntityNotFoundException e ) {
                noFound();
            } catch ( DataIntegrityViolationException e ) {
                integrityDataAccess( id );
            } catch ( InvalidDataAccessApiUsageException e ) {
                invalidDataAccess();
            } catch ( DataAccessException e ) {
                dataAccess( e );
            }
        }
    }


    @Override
    public < T, I > void modify( JpaRepository < T, I > repository, I id, T value ) throws DataBaseException {
        Optional < T > optional = repository.findById( id );
        if ( optional.isPresent() ) {
            try {
                repository.save( value );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Registro con ID %s modificado correctamente", id ) );
                }
            } catch ( InvalidDataAccessApiUsageException | DataIntegrityViolationException e ) {
                modifyReference( id );
            } catch ( DataAccessException e ) {
                if ( LOGGER.isErrorEnabled() ) {
                    LOGGER.error( String.format( CatalogError.DATABASE_MODIFY_NO_REFERENCE.getMessage(), id ) );
                }
                throw new DataBaseException( CatalogError.DATABASE_MODIFY_NO_REFERENCE );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( CatalogError.DATABASE_MODIFY_NO_EXIST.getMessage(), id ) );
            }
            throw new DataBaseException( CatalogError.DATABASE_MODIFY_NO_EXIST );
        }
    }

    @Override
    public < T, I > void delete( JpaRepository < T, I > repository, I id ) throws DataBaseException {
        Optional < T > optional = repository.findById( id );
        if ( optional.isPresent() ) {
            try {
                repository.deleteById( id );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Registro con ID %s eliminado correctamente", id ) );
                }
            } catch ( InvalidDataAccessApiUsageException | DataIntegrityViolationException e ) {
                deleteReference( id );
            } catch ( DataAccessException e ) {
                if ( LOGGER.isErrorEnabled() ) {
                    LOGGER.error( String.format( CatalogError.DATABASE_DELETE_NO_REFERENCE.getMessage(), id ) );
                }
                throw new DataBaseException( CatalogError.DATABASE_DELETE_NO_REFERENCE );
            }
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( CatalogError.DATABASE_DELETE_NO_EXIST.getMessage(), id ) );
            }
            throw new DataBaseException( CatalogError.DATABASE_DELETE_NO_EXIST );
        }
    }

    @Override
    public < T, I > T findById( JpaRepository < T, I > repository, I id ) throws DataBaseException {
        Optional < T > optional = repository.findById( id );
        if ( optional.isPresent() ) {
            return optional.get();
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( String.format( CatalogError.DATABASE_FIND_NO_EXIST.getMessage(), id ) );
            }
            throw new DataBaseException( CatalogError.DATABASE_FIND_NO_EXIST );
        }
    }

    @Override
    public < T, I > List < T > findByAll( JpaRepository < T, I > repository ) throws DataBaseException {
        List < T > entities = repository.findAll();
        if ( entities.isEmpty() ) {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( CatalogError.FIND_ERROR_LIST_NOT_FOUND.getMessage() );
            }
            throw new DataBaseException( CatalogError.FIND_ERROR_LIST_NOT_FOUND );
        } else {
            return entities;
        }
    }

    private void dataAccess( DataAccessException e ) throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( e.getCause().getMessage() );
        }
        throw new DataBaseException( CatalogError.DATABASE_CREATE_NO_REFERENCE );
    }

    private void invalidDataAccess() throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( CatalogError.DATABASE_CREATE_NO_REFERENCE_2.getMessage() );
        }
        throw new DataBaseException( CatalogError.DATABASE_CREATE_NO_REFERENCE_2 );
    }

    private < I > void integrityDataAccess( I id ) throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( String.format( CatalogError.DATABASE_CREATE_UNIQUE_DUPLICATE.getMessage(), id ) );
        }
        throw new DataBaseException( CatalogError.DATABASE_CREATE_UNIQUE_DUPLICATE );
    }

    private void noFound() throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( CatalogError.DATABASE_CREATE_NO_REFERENCE.getMessage() );
        }
        throw new DataBaseException( CatalogError.DATABASE_CREATE_NO_REFERENCE );
    }

    private < I > void modifyReference( I id ) throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( String.format( CatalogError.DATABASE_MODIFY_NO_REFERENCE_2.getMessage(), id ) );
        }
        throw new DataBaseException( CatalogError.DATABASE_MODIFY_NO_REFERENCE_2 );
    }

    private < I > void deleteReference( I id ) throws DataBaseException {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( String.format( CatalogError.DATABASE_DELETE_NO_REFERENCE_2.getMessage(), id ) );
        }
        throw new DataBaseException( CatalogError.DATABASE_DELETE_NO_REFERENCE_2 );
    }

}
