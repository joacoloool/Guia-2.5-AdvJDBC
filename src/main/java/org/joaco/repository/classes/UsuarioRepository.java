package org.joaco.repository.classes;
import org.joaco.model.entities.UsuarioEntity;
import org.joaco.repository.ConnectionSQL;
import org.joaco.repository.I.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements Repository<UsuarioEntity> {


    private static final UsuarioRepository instance = new UsuarioRepository();

    private UsuarioRepository() {
    }

    public static UsuarioRepository getInstance() {
        return instance;
    }

    @Override
    public void save(UsuarioEntity usuarioEntity) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "INSERT INTO usuarios(nombre,apellido,dni,email,fecha_creacion) values(?,?,?,?,?) ")
        ) {
            preparedStatement.setString(1, usuarioEntity.getNombre());
            preparedStatement.setString(2, usuarioEntity.getApellido());
            preparedStatement.setString(3, usuarioEntity.getDni());
            preparedStatement.setString(4, usuarioEntity.getEmail());
            preparedStatement.setDate(5, usuarioEntity.getFechaCreacion());
            preparedStatement.executeUpdate();
        }
    }

    private UsuarioEntity resultToEntity(ResultSet resultSet) throws SQLException {
        return UsuarioEntity
                .builder()
                .id(resultSet.getInt("id_usuario"))
                .nombre(resultSet.getString("nombre"))
                .apellido(resultSet.getString("apellido"))
                .email(resultSet.getString("email"))
                .dni(resultSet.getString("dni"))
                .fechaCreacion(resultSet.getDate("fecha_creacion"))
                .build();
    }

    @Override
    public UsuarioEntity searchById(int id) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement
                             ("SELECT * from usuarios where id_usuario = ?")
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultToEntity(resultSet);
        }
    }


    @Override
    public List<UsuarioEntity> searchAll() throws SQLException {
        List<UsuarioEntity> usuariosList = new ArrayList<>();
        try (Connection connection = ConnectionSQL.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement
                             ("SELECT * FROM usuarios")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    usuariosList.add(resultToEntity(resultSet));
                }
            }
        }
        return usuariosList;
    }

    @Override
    public void update(UsuarioEntity usuarioEntity) throws SQLException {
         try (Connection connection = ConnectionSQL.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement
                         ("update usuarios set nombre = ?, apellido = ?, dni = ?, email = ? ")
         ){
            preparedStatement.setString(1,usuarioEntity.getNombre());
            preparedStatement.setString(2,usuarioEntity.getApellido());
            preparedStatement.setString(3,usuarioEntity.getDni());
            preparedStatement.setString(4,usuarioEntity.getEmail());
            preparedStatement.executeUpdate();
         }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection()) {

            // Activar claves foráneas con PreparedStatement
            try (PreparedStatement pragmaStmt = connection.prepareStatement("PRAGMA foreign_keys = ON;")) {
                pragmaStmt.execute();
            }
            // Ejecutar el DELETE
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?")) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }

}
