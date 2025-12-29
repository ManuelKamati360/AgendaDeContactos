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
	
	// Construtor...
	public ContatoDAO(Connection conn) {
		this.conn = conn;
	}
	
	// Listar todos os contactos
	public List<Contato> listarTodos() throws NamingException { 
		
		List<Contato> contatosList = new ArrayList<>(); 
		
		try { 
 
			String sqlQuery = "SELECT id, nome, dataNascimento, telefone, email, endereco, estado, cidade FROM contato LIMIT 10";

			try (
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
	
	// Inserir contacto
	public boolean inserir(Contato c) {
	    String sqlQuery = "INSERT INTO contato (nome, dataNascimento, telefone, email, endereco, estado, cidade) "+"VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
	        stmt.setString(1, c.getNome());
	        stmt.setDate(2, c.getDataNascimento() != null ? new java.sql.Date(c.getDataNascimento().getTime()) : null);
	        stmt.setString(3, c.getTelefone());
	        stmt.setString(4, c.getEmail());
	        stmt.setString(5, c.getEndereco());
	        stmt.setString(6, c.getEstado());
	        stmt.setString(7, c.getCidade());

	        int rows = stmt.executeUpdate();
	        System.out.println("✅ Inserção realizada. Linhas afetadas: " + rows);
	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao inserir contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	// Atualizar contacto
	public boolean atualizar(Contato c) {
	    String sqlQuery = "UPDATE contato SET nome = ?, dataNascimento = ?, telefone = ?, email = ?, endereco = ?, estado = ?, cidade = ? WHERE id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
	        stmt.setString(1, c.getNome());
	        stmt.setDate(2, c.getDataNascimento() != null ? new java.sql.Date(c.getDataNascimento().getTime()) : null);
	        stmt.setString(3, c.getTelefone());
	        stmt.setString(4, c.getEmail());
	        stmt.setString(5, c.getEndereco());
	        stmt.setString(6, c.getEstado());
	        stmt.setString(7, c.getCidade());
	        stmt.setInt(8, c.getId());
	        
	        // Log de atualização
	        System.out.println("📦 Atualizando contato:");
	        System.out.println("🆔 ID: " + c.getId());
	        System.out.println("👤 Nome: " + c.getNome());
	        System.out.println("📅 Data: " + c.getDataNascimento());
	        System.out.println("📞 Telefone: " + c.getTelefone());
	        System.out.println("📧 Email: " + c.getEmail());
	        System.out.println("🏠 Endereço: " + c.getEndereco());
	        System.out.println("🌍 Estado: " + c.getEstado());
	        System.out.println("🏙️ Cidade: " + c.getCidade());

	        
	        // Executa a atualização
	        int rows = stmt.executeUpdate();
	        System.out.println("✅ Atualização realizada. Linhas afetadas: " + rows);
	        
	        // Log detalhado
	        System.out.println("📦 Atualizando contato com ID: " + c.getId());
	        System.out.println("🧪 SQL: " + sqlQuery);
	        System.out.println("🧪 Nome: " + c.getNome());
	        System.out.println("🧪 ID: " + c.getId());

	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao atualizar contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// Remover contacto
	public boolean remover(int id) {
	    String sqlQuery = "DELETE FROM contato WHERE id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
	        stmt.setInt(1, id);

	        int rows = stmt.executeUpdate();
	        System.out.println("✅ Remoção realizada. Linhas afetadas: " + rows);
	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao remover contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

}
