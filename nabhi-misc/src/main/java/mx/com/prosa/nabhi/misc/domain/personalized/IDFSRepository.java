package mx.com.prosa.nabhi.misc.domain.personalized;

import mx.com.prosa.nabhi.misc.domain.personalized.repository.IDFSurchargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IDFSRepository {

    private IDFSurchargeRepository idfSurchargeRepository;

    public IDFSurchargeRepository getIdfSurchargeRepository() {
        return idfSurchargeRepository;
    }

    @Autowired
    public void setIdfSurchargeRepository( IDFSurchargeRepository idfSurchargeRepository ) {
        this.idfSurchargeRepository = idfSurchargeRepository;
    }

}
