package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.ICapabilitiesService;
import mx.com.prosa.nabhi.jse.core.Capabilities;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ImageEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.ImageRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.jdb.BankStyle;
import mx.com.prosa.nabhi.misc.model.jdb.Image;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDScreen;
import mx.com.prosa.nabhi.misc.model.jse.Publicity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.ScreenCapabilities;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.nabhi.misc.model.jdb.blob.ButtonMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class CapabilitiesService implements ICapabilitiesService {

    private static final Logger LOG = LoggerFactory.getLogger( CapabilitiesService.class );
    private final ATMSearch atmSearch;
    private final ImageRepository imageRepository;
    private final ModelMapper mapper;
    private final Capabilities capabilities;

    @Autowired
    public CapabilitiesService( ATMSearch atmSearch, ImageRepository imageRepository, ModelMapper mapper, Capabilities capabilities ) {
        this.atmSearch = atmSearch;
        this.imageRepository = imageRepository;
        this.mapper = mapper;
        this.capabilities = capabilities;
    }

    @Override
    public List < ScreenCapabilities > getButtons( Generic generic ) throws ATMException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "El cajero %s solicita capabilities", generic.getTermId() ) );
        }
        ATDScreen atd;
        ScreenGroup screenGroup;
        atd = atmSearch.searchScreen( generic.getTermId() );
        screenGroup = capabilities.updateCapabilities( atd.getScreenGroup(), atd.getTerminalId() );
        List < ButtonMapping > buttonService = screenGroup.getButtonsAllowed().getButtons();
        List < ScreenCapabilities > screenCapabilities = new ArrayList <>();
        for ( ButtonMapping b : buttonService ) {
            ScreenCapabilities model = new ScreenCapabilities();
            model.setActiveFDKs( b.getBitmap() );
            model.setScreen( b.getScreenComponent() );
            screenCapabilities.add( model );
        }
        return screenCapabilities;
    }

    @Override
    public Publicity getPublicity( Generic generic ) throws ATMException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "El cajero %s solicita publicidad", generic.getTermId() ) );
        }
        ATDScreen atd = atmSearch.searchScreen( generic.getTermId() );
        Publicity publicity = new Publicity();
        publicity.setScreenType( atd.getScreenType() );
        publicity.setNamePublicity( atd.getScreenGroup().getPublicityName() );
        BankStyle style = new BankStyle();
        style.setDashboard( atd.getScreenGroup().getBodyStyle() );
        style.setButtons( atd.getScreenGroup().getButtonsStyle() );
        style.setBackgroundImage( atd.getScreenGroup().getBackGround() );
        publicity.setBankStyle( style );
        return publicity;
    }

    @Override
    public ScreenGroup getAllScreen( Generic generic ) throws ATMException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "El cajero %s solicita pantalla", generic.getTermId() ) );
        }
        return atmSearch.searchScreen( generic.getTermId() ).getScreenGroup();
    }

    @Override
    public Image findImage( String id, String category ) throws ATMException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Se solicita imagen %s con categoría %s", id, category ) );
        }
        Optional < ImageEntity > oe = imageRepository.findByNameAndCategory( id, category );
        if ( oe.isPresent() ) {
            return mapper.map( oe.get(), Image.class );
        } else {
            throw new ATMException( CatalogError.ATM_NO_IMAGE );
        }
    }

}
