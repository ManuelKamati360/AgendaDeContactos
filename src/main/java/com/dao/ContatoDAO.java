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
 
			String sqlQuery = "SELECT id, nome, dataNascimento, telefone, email, endereco, estado, cidade FROM contato";

			try (
					PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
					ResultSet resultSet = preparedStatement.executeQuery()) { 
				
						while (resultSet.next()) { 					
							Contato contato = new Contato(); 
							contato.setId(resultSet.getInt("id"));
							contato.setNome(resultSet.getString("nome"));
							contato.setEmail(resultSet.getString("email"));
							contato.setTelefone(resultSet.getString("telefone"));
							contato.setDataNascimento(resultSet.getDate("dataNascimento"));
							contato.setEndereco(resultSet.getString("endereco"));
							contato.setEstado(resultSet.getString("estado"));
							contato.setCidade(resultSet.getString("cidade"));
							
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
	
	
	// Buscar contacto por ID
	public Contato buscarPorId(int id) {
	    String sql = "SELECT * FROM contato WHERE id = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Contato contato = new Contato();
	            contato.setId(rs.getInt("id"));
	            contato.setNome(rs.getString("nome"));
	            contato.setDataNascimento(rs.getDate("dataNascimento"));
	            contato.setTelefone(rs.getString("telefone"));
	            contato.setEmail(rs.getString("email"));
	            contato.setEndereco(rs.getString("endereco"));
	            contato.setEstado(rs.getString("estado"));
	            contato.setCidade(rs.getString("cidade"));
	            return contato;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	// Buscar contacto que contenha um texto específico em um de seus campos (full-text search)...
	public List<Contato> buscarPorTexto(String texto) {
	    List<Contato> contatos = new ArrayList<>();
	    String sql = 
	    		"SELECT * FROM contato WHERE nome LIKE ? OR email LIKE ? OR telefone LIKE ? OR endereco LIKE ? OR cidade LIKE ? OR estado LIKE ?";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
	        
	    		String likeTerm = "%" + texto + "%";
	        preparedStatement.setString(1, likeTerm);
	        preparedStatement.setString(2, likeTerm);
	        preparedStatement.setString(3, likeTerm);
	        preparedStatement.setString(4, likeTerm);
	        preparedStatement.setString(5, likeTerm);
	        preparedStatement.setString(6, likeTerm);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            Contato contato = new Contato();
	            contato.setId(resultSet.getInt("id"));
	            contato.setNome(resultSet.getString("nome"));
	            contato.setDataNascimento(resultSet.getDate("dataNascimento"));
	            contato.setTelefone(resultSet.getString("telefone"));
	            contato.setEmail(resultSet.getString("email"));
	            contato.setEndereco(resultSet.getString("endereco"));
	            contato.setEstado(resultSet.getString("estado"));
	            contato.setCidade(resultSet.getString("cidade"));
	            contatos.add(contato);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return contatos;
	}

	
	// Inserir contacto
	public boolean inserir(Contato contato) {
	    String sqlQuery = "INSERT INTO contato (nome, dataNascimento, telefone, email, endereco, estado, cidade) "+"VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
	        preparedStatement.setString(1, contato.getNome());
	        preparedStatement.setDate(2, contato.getDataNascimento() != null ? new java.sql.Date(contato.getDataNascimento().getTime()) : null);
	        preparedStatement.setString(3, contato.getTelefone());
	        preparedStatement.setString(4, contato.getEmail());
	        preparedStatement.setString(5, contato.getEndereco());
	        preparedStatement.setString(6, contato.getEstado());
	        preparedStatement.setString(7, contato.getCidade());

	        int rows = preparedStatement.executeUpdate();
	        System.out.println("✅ Inserção realizada. Linhas afetadas: " + rows);
	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao inserir contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	// Atualizar contacto
	public boolean atualizar(Contato contato) {
	    String sqlQuery = "UPDATE contato SET nome = ?, dataNascimento = ?, telefone = ?, email = ?, endereco = ?, estado = ?, cidade = ? WHERE id = ?";

	    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
	        preparedStatement.setString(1, contato.getNome());
	        preparedStatement.setDate(2, contato.getDataNascimento() != null ? new java.sql.Date(contato.getDataNascimento().getTime()) : null);
	        preparedStatement.setString(3, contato.getTelefone());
	        preparedStatement.setString(4, contato.getEmail());
	        preparedStatement.setString(5, contato.getEndereco());
	        preparedStatement.setString(6, contato.getEstado());
	        preparedStatement.setString(7, contato.getCidade());
	        preparedStatement.setInt(8, contato.getId());
	        
	        // Log de atualização de dados...
	        System.out.println("📦 Atualizando contato:");
	        System.out.println("🆔 ID: " + contato.getId());
	        System.out.println("👤 Nome: " + contato.getNome());
	        System.out.println("📅 Data: " + contato.getDataNascimento());
	        System.out.println("📞 Telefone: " + contato.getTelefone());
	        System.out.println("📧 Email: " + contato.getEmail());
	        System.out.println("🏠 Endereço: " + contato.getEndereco());
	        System.out.println("🌍 Estado: " + contato.getEstado());
	        System.out.println("🏙️ Cidade: " + contato.getCidade());

	        
	        // Executa a atualização
	        int rows = preparedStatement.executeUpdate();
	        System.out.println("✅ Atualização realizada. Linhas afetadas: " + rows);

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

	    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
	        preparedStatement.setInt(1, id);

	        int rows = preparedStatement.executeUpdate();
	        
	        // Log de remoção de dados...
	        System.out.println("📦 Removendo contato:");
	        System.out.println("🆔 ID: " + id);
//	        System.out.println("👤 Nome: " + contato.getNome());
//	        System.out.println("📅 Data: " + contato.getDataNascimento());
//	        System.out.println("📞 Telefone: " + contato.getTelefone());
//	        System.out.println("📧 Email: " + contato.getEmail());
//	        System.out.println("🏠 Endereço: " + contato.getEndereco());
//	        System.out.println("🌍 Estado: " + contato.getEstado());
//	        System.out.println("🏙️ Cidade: " + contato.getCidade());
	        System.out.println("✅ Remoção realizada com sucesso! Linhas afetadas: " + rows);
	        
	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao remover contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

}
