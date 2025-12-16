package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySqlConnection{
	
	protected static String dbUrl = "jdbc:mysql://loclahost:3306/agenda_contatos";
	protected static String dbUser = "root";
	protected static String dbPassword = "Oliveira@2025";		
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (SQLException exception) {
			System.err.println(
						"Erro ao conectar-se com o Banco de Dados!"
						+ exception.getMessage()
					);
			exception.printStackTrace();
			return null;
		}
	}
	
	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Conexão encerrada com sucesso!");
			} catch (SQLException exception) {
				System.err.println("Erro ao desconectar-se com o Banco de Dados!");
				exception.printStackTrace();
			}
		}
	} 

}
