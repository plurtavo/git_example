package mx.com.prosa.nabhi.dash.business.impl;

import mx.com.prosa.nabhi.dash.business.INodeService;
import mx.com.prosa.nabhi.dash.business.ISockService;
import mx.com.prosa.nabhi.dash.core.IDataBaseService;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.node.repository.NodeTCPRepository;
import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import mx.com.prosa.nabhi.misc.exception.db.IDFException;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.jdb.NodeTCP;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService implements INodeService {

    private final NodeTCPRepository nodeTCPRepository;
    private final ModelMapper mapper;
    private final IDataBaseService dataBaseService;
    private final ISockService sockService;
    private static final Logger LOGGER = LoggerFactory.getLogger( NodeService.class );
    private static final String TABLE = "NODE";
    private static final String ERROR = "para el modulo " + TABLE;
    private static final Class < NodeTCPEntity > domainClass = NodeTCPEntity.class;
    private static final Class < NodeTCP > dtoClass = NodeTCP.class;
    private final Type listType = new TypeToken < List < NodeTCP > >() {
    }.getType();

    @Autowired
    public NodeService( NodeTCPRepository nodeTCPRepository, ModelMapper mapper, IDataBaseService dataBaseService, ISockService sockService ) {
        this.nodeTCPRepository = nodeTCPRepository;
        this.mapper = mapper;
        this.dataBaseService = dataBaseService;
        this.sockService = sockService;
    }

    @Override
    public String create( NodeTCP value ) throws DataBaseException {
        String str = "";
        try {
            NodeTCPEntity entity = mapper.map( value, domainClass );
            dataBaseService.create( nodeTCPRepository, entity.getNodeName(), entity );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
        str = doExecute( value, "create", str );
        return "Node Creado correctamente[ " + str + " ]";

    }

    @Override
    public String modify( NodeTCP value ) throws DataBaseException {
        NodeTCPEntity entity = mapper.map( value, domainClass );
        try {
            dataBaseService.modify( nodeTCPRepository, entity.getNodeName(), entity );
            return "Node Modificado correctamente, considerar desplegar nuevamente las instancias de los servicios";
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public String delete( NodeTCP value ) throws DataBaseException {
        NodeTCPEntity entity = mapper.map( value, domainClass );
        String str = "";
        try {
            dataBaseService.delete( nodeTCPRepository, entity.getNodeName() );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
        str = doExecute( value, "delete", str );
        return "Node eliminado correctamente[ " + str + " ]";
    }

    @Override
    public NodeTCP findById( String name ) throws DataBaseException {
        try {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Buscando Node con llave %s ", name ) );
            }
            NodeTCPEntity entity = dataBaseService.findById( nodeTCPRepository, name );
            return mapper.map( entity, dtoClass );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < NodeTCP > findAll() throws DataBaseException, IDFException {
        try {
            List < NodeTCPEntity > entities;
            entities = dataBaseService.findByAll( nodeTCPRepository );
            return mapper.map( entities, listType );
        } catch ( DataBaseException e ) {
            throw new DataBaseException( e.getError(), ERROR );
        }
    }

    @Override
    public List < String > findAllOnlyNames() throws DataBaseException, IDFException {
        List < NodeTCP > nodes = findAll();
        List < String > names = new ArrayList <>();
        for ( NodeTCP node : nodes ) {
            names.add( node.getNodeType() + ":" + node.getNodeName() );
        }
        return names;
    }

    private String doExecute( NodeTCP value, String op, String str ) {
        try {
            if ( value.getNodeType().equals( "HISO" ) ) {
                str += "ISO: ";
                str += sockService.sock( "ISO", op, value.getNodeName() );
            } else {
                str += "JKE: ";
                str += sockService.sock( "JKE", op, value.getNodeName() );
            }
        } catch ( SocketException e ) {
            str += "No se creo en el servicio, se debe desplegar, causa " + e.getError().getMessage();
        }
        return str;
    }
}
