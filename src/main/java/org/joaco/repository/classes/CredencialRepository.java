package org.joaco.repository.classes;
import org.joaco.model.entities.CredencialEntity;
import org.joaco.model.enums.Permiso;
import org.joaco.repository.ConnectionSQL;
import org.joaco.repository.I.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CredencialRepository implements Repository<CredencialEntity> {

    protected static final CredencialRepository instance = new CredencialRepository();

    public CredencialRepository() {
    }

    public CredencialRepository getInstance() {
        return instance;
    }

    @Override
    public void save(CredencialEntity credencialEntity) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement
                     ("INSERT INTO credenciales (id_usuario,username,password,permiso) values (?,?,?,?)")
        ){
            preparedStatement.setInt(1,credencialEntity.getId_usuario());
            preparedStatement.setString(2,credencialEntity.getUsuario());
            preparedStatement.setString(3,credencialEntity.getPassword());
            preparedStatement.setString(4,credencialEntity.getPermiso().toString());
            preparedStatement.executeUpdate();
        }
    }

    private CredencialEntity resultToEntity(ResultSet resultSet) throws SQLException
    {
        return CredencialEntity.builder()
                .id(resultSet.getInt("id_credencial"))
                .id_usuario(resultSet.getInt("id_usuario"))
                .usuario(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .permiso(Permiso.valueOf(resultSet.getString("permiso")))
                .build();
    }

    @Override
    public CredencialEntity searchById(int id) throws SQLException {
        try(Connection connection = ConnectionSQL.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("SELECT * FROM credenciales where id_credencial = ?")
        )
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultToEntity(resultSet);
        }
    }

    @Override
    public List<CredencialEntity> searchAll() throws SQLException {
        List<CredencialEntity> credencialEntities = new ArrayList<>();
        try(Connection connection = ConnectionSQL.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("SELECT * FROM credenciales")
        ){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next())
                {
                  credencialEntities.add(resultToEntity(resultSet));
                }
            }
        }
        return credencialEntities;
    }

    @Override
    public void update(CredencialEntity credencialEntity) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("UPDATE credenciales set username = ? , password = ?, permiso = ?")
        ){
            preparedStatement.setString(1,credencialEntity.getUsuario());
            preparedStatement.setString(2,credencialEntity.getPassword());
            preparedStatement.setString(3,credencialEntity.getPermiso().toString());
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection()){

            try (PreparedStatement pragmaStmt = connection.prepareStatement("PRAGMA foreign_keys = ON;")) {
                pragmaStmt.execute();
            }
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement
                                 ("DELETE FROM credenciales WHERE id_credencial = ?")
            ){
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();
            }
        }
    }
}
