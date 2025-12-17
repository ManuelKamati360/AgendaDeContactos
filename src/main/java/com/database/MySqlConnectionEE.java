package com.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MySqlConnectionEE {

    public static Connection getConnection() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/agendaDB");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            System.err.println("Erro ao obter conexão no Java EE: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
