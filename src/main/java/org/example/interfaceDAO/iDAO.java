package org.example.interfaceDAO;
import org.example.model.dto.Artist;

import java.sql.SQLException;
import java.util.List;

public interface iDAO<T,K> {
    List<T> findAll() throws SQLException;
    T findById (K id) throws SQLException;
    T save(T entity) throws SQLException ;

    void delete(T entity) throws SQLException ;

}
