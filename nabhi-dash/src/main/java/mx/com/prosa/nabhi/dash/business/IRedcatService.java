package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.dash.model.redcat.RedcatSearchConditions;
import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;
import mx.com.prosa.nabhi.misc.exception.dash.RedcatException;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface IRedcatService {

    String forceCutForATD(RedcatSearchConditions conditions ) throws RedcatException;

    String forceCutForFIID( RedcatSearchConditions conditions ) throws RedcatException;

    List < Redcat > findRedcat(RedcatSearchConditions conditions ) throws RedcatException;

    ByteArrayOutputStream findExcelRedcat(RedcatSearchConditions conditions ) throws RedcatException;



}
