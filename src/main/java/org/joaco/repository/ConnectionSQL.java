package org.joaco.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionSQL {
    private static final DataSource datasource;
    private static final String url = "jdbc:sqlite:D:\\Programacion 3\\Guia 2.5 AdvanceJDBC\\src\\main\\resources\\banco.db";

    static
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10);// Maximo de conexiones
        config.setMinimumIdle(2); //minimo conexiones inactivas
        config.setIdleTimeout(30000);//tiempo antes de cerrar conexiones inactivas
        config.setConnectionTimeout(10000); //time out para obetener conexiones
        config.setLeakDetectionThreshold(5000); //Deteccion de fugas de conexiones

        datasource = new HikariDataSource(config);
    }
    public static Connection getConnection()throws SQLException
    {
        return datasource.getConnection();
    }


}
