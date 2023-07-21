package mx.com.prosa.nabhi.esq.bussines;

import mx.com.prosa.nabhi.esq.model.Response;
import mx.com.prosa.nabhi.esq.model.XFSMessage;


public interface IESQService {

    Response xfs( XFSMessage xfsMessage );
}
