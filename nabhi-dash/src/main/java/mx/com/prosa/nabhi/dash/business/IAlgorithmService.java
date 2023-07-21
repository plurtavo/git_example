package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.exception.dash.AlgorithmException;

import java.util.List;

public interface IAlgorithmService {

    String create( DispensedAlgorithm algorithm ) throws AlgorithmException;

    String update( DispensedAlgorithm algorithm ) throws AlgorithmException;

    String delete( DispensedAlgorithm algorithm ) throws AlgorithmException;

    DispensedAlgorithm findByName( String name ) throws AlgorithmException;

    List < DispensedAlgorithm > findAll() throws AlgorithmException;

    List < String > findAllOnlyNames() throws AlgorithmException;

    List < String > findAllOnlyNames( String fiid ) throws AlgorithmException;

    String updateGroup( String groupName, String algorithm ) throws AlgorithmException;

}
