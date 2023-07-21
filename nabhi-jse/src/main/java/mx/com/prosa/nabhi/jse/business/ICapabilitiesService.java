package mx.com.prosa.nabhi.jse.business;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.jdb.Image;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jse.Publicity;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.response.ScreenCapabilities;

import java.util.List;

public interface ICapabilitiesService {

    List < ScreenCapabilities > getButtons( Generic generic ) throws ATMException;

    Publicity getPublicity( Generic generic ) throws ATMException;

    ScreenGroup getAllScreen( Generic generic ) throws ATMException;

    Image findImage( String id, String category ) throws ATMException;

}
