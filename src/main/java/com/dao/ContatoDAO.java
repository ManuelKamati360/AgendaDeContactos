package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.model.Contato;

public class ContatoDAO {
	
	private Connection conn;
	
	// Sobrecarga do construtor...
	public ContatoDAO(Connection conn) {
		this.conn = conn;
	}
	public ContatoDAO() {
		// Construtor vazio
	}
	
	public List<Contato> listarTodos() throws NamingException { 
		
		List<Contato> contatosList = new ArrayList<>(); 
		
		try { 
			InitialContext ctx = new InitialContext(); 
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/agendaDB"); 
			String sqlQuery = "SELECT id, nome, dataNascimento, telefone, email, endereco, estado, cidade FROM contato LIMIT 10";

			try (Connection conn = ds.getConnection(); 
					PreparedStatement stmt = conn.prepareStatement(sqlQuery);
					ResultSet rs = stmt.executeQuery()) { 
				
						while (rs.next()) { 					
							Contato contato = new Contato(); 
							contato.setId(rs.getInt("id"));
							contato.setNome(rs.getString("nome"));
							contato.setEmail(rs.getString("email"));
							contato.setTelefone(rs.getString("telefone"));
							contato.setDataNascimento(rs.getDate("dataNascimento"));
							contato.setEndereco(rs.getString("endereco"));
							contato.setEstado(rs.getString("estado"));
							contato.setCidade(rs.getString("cidade"));
							
							contatosList.add(contato); 
						} 
						
						System.out.println("✅ Contatos carregados: " + contatosList.size());
				} 
			
			} catch (SQLException e) { 
				System.err.println("❌ Erro ao listar contatos: " + e.getMessage());
				e.printStackTrace(); 
			} 
		
		return contatosList; 
	}

	public boolean inserir(Contato c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean remover(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean atualizar(Contato c) {
		// TODO Auto-generated method stub
		return false;
	}
}
