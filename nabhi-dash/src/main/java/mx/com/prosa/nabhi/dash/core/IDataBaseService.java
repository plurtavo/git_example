package mx.com.prosa.nabhi.dash.core;

import mx.com.prosa.nabhi.misc.exception.db.DataBaseException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDataBaseService {

    < T, I > void create( JpaRepository < T, I > repository, I id, T value ) throws DataBaseException;

    < T, I > void modify( JpaRepository < T, I > repository, I id, T value ) throws DataBaseException;

    < T, I > void delete( JpaRepository < T, I > repository, I id ) throws DataBaseException;

    < T, I > T findById( JpaRepository < T, I > repository, I id ) throws DataBaseException;

    < T, I > List < T > findByAll( JpaRepository < T, I > repository ) throws DataBaseException;
}
