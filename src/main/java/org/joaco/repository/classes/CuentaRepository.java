package org.joaco.repository.classes;
import org.joaco.model.entities.CuentaEntity;
import org.joaco.model.entities.UsuarioEntity;
import org.joaco.model.enums.TipoCuenta;
import org.joaco.repository.ConnectionSQL;
import org.joaco.repository.I.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaRepository implements Repository<CuentaEntity> {

    protected static final CuentaRepository instance = new CuentaRepository();

    private CuentaRepository() {
    }

    public static CuentaRepository getInstance() {
        return instance;
    }

    @Override
    public void save(CuentaEntity cuentaEntity) throws SQLException {
        try(Connection connection = ConnectionSQL.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO cuentas (id_usuario,tipo,saldo) values (?,?,?)")
        )
        {
            preparedStatement.setInt(1,cuentaEntity.getId_usuario());
            preparedStatement.setString(2,cuentaEntity.getTipoCuenta().toString());
            preparedStatement.setDouble(3,cuentaEntity.getSaldo());
            preparedStatement.executeUpdate();
        }
    }

    private CuentaEntity resultToEntity(ResultSet resultSet) throws SQLException
    {
        return CuentaEntity.builder()
                .id(resultSet.getInt("id_cuenta"))
                .id_usuario(resultSet.getInt("id_usuario"))
                .tipoCuenta(TipoCuenta.valueOf(resultSet.getString("tipo")))
                .saldo(resultSet.getDouble("saldo"))
                .fechaCreacion(resultSet.getDate("fecha_creacion"))
                .build();
    }

    @Override
    public CuentaEntity searchById(int id) throws SQLException {
        try(Connection connection = ConnectionSQL.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("SELECT * from cuentas where id_cuenta = ?")
        )
        {
            preparedStatement.setInt(1,id);
           return resultToEntity(preparedStatement.executeQuery());
        }
    }

    @Override
    public List<CuentaEntity> searchAll() throws SQLException {
        List<CuentaEntity> cuentaEntityList = new ArrayList<>();
       try(Connection connection = ConnectionSQL.getConnection();
       PreparedStatement preparedStatement =
               connection.prepareStatement
                       ("SELECT * FROM cuentas")
       ){
          try (ResultSet resultSet = preparedStatement.executeQuery()){
              while (resultSet.next())
              {
                 cuentaEntityList.add(resultToEntity(resultSet));
              }
          }
       }
       return cuentaEntityList;
    }

    @Override
    public void update(CuentaEntity cuentaEntity) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                    ("UPDATE cuentas set id_usuario = ?, tipo = ?, saldo = ?")
        ){
            preparedStatement.setInt(1,cuentaEntity.getId_usuario());
            preparedStatement.setString(2,cuentaEntity.getTipoCuenta().toString());
            preparedStatement.setDouble(3,cuentaEntity.getSaldo());
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionSQL.getConnection()) {

            // Activar claves foráneas con PreparedStatement
            try (PreparedStatement pragmaStmt = connection.prepareStatement("PRAGMA foreign_keys = ON;")) {
                pragmaStmt.execute();
            }

            // Ejecutar DELETE con PreparedStatement
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("DELETE FROM cuentas WHERE id_cuenta = ?")) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }

}
