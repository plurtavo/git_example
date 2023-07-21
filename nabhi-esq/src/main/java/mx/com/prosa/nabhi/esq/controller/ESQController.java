package mx.com.prosa.nabhi.esq.controller;

import mx.com.prosa.nabhi.esq.bussines.IESQService;
import mx.com.prosa.nabhi.esq.model.Response;
import mx.com.prosa.nabhi.esq.model.XFSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/reporteMensajeATmXfs" )
public class ESQController {

    private final IESQService iesqService;

    @Autowired
    public ESQController( IESQService iesqService ) {
        this.iesqService = iesqService;
    }
   
    @ResponseBody
    @PostMapping("/xfs")
    public Response xfs( @RequestBody XFSMessage xfsMessage ){
         return iesqService.xfs( xfsMessage );
    }
}
