package org.joaco.repository.I;

import org.joaco.model.entities.UsuarioEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    public void save(T t)throws SQLException;
    public T searchById(int id)throws SQLException;
    public List<T> searchAll()throws SQLException;
    public void update(T t)throws SQLException;
    public void delete(int id)throws SQLException;



}
