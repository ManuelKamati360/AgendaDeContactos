package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
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
 
			String sqlQuery = "{CALL sp_ListarContatos()}";

			try (
					CallableStatement callableStatement = conn.prepareCall(sqlQuery);
					ResultSet resultSet = callableStatement.executeQuery()) { 
				
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
						
						// Debug: Log de contatos carregados do banco de dados...
						System.out.println("✅ Contatos carregados com sucesso!: " + contatosList.size());
				} 
			
			} catch (SQLException e) { 
				System.err.println("❌ Erro ao listar contatos: " + e.getMessage());
				e.printStackTrace(); 
			} 
		
		return contatosList; 
	}
	
	
	// Buscar contacto por ID
	public Contato buscarPorId(int id) {
		
	    String sqlQuery = "{CALL sp_BuscarContatoPorID(?)}";
	    
	    try (CallableStatement callableStatement = conn.prepareCall(sqlQuery)) {
	    	
	    		callableStatement.setInt(1, id);
	        
	        ResultSet resultSet = callableStatement.executeQuery();
	        
	        Contato contato = new Contato();
	        
	        if (resultSet.next()) {	            
	            contato.setId(resultSet.getInt("id"));
	            contato.setNome(resultSet.getString("nome"));
	            contato.setDataNascimento(resultSet.getDate("dataNascimento"));
	            contato.setTelefone(resultSet.getString("telefone"));
	            contato.setEmail(resultSet.getString("email"));
	            contato.setEndereco(resultSet.getString("endereco"));
	            contato.setEstado(resultSet.getString("estado"));
	            contato.setCidade(resultSet.getString("cidade"));
	            return contato;
	        }
	        
			// Debug: Log de contatos carregados do banco de dados...
			System.out.println("✅ Contato carregado com sucesso!: " + contato);
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	// Buscar contacto que contenha um texto específico em um de seus campos (nome, email, telefone, cidade, estado morada)...
	public List<Contato> buscarPorTexto(String texto) {
		
	    List<Contato> contatos = new ArrayList<>();
	    
	    String sqlQuery = "{CALL sp_BuscarContatos(?, ?, ?, ?, ?, ?)}"; 
	    
	    try (CallableStatement callableStatement = conn.prepareCall(sqlQuery)) {
	        
	    		String likeTerm = "%" + texto + "%";
	        callableStatement.setString(1, likeTerm);
	        callableStatement.setString(2, likeTerm);
	        callableStatement.setString(3, likeTerm);
	        callableStatement.setString(4, likeTerm);
	        callableStatement.setString(5, likeTerm);
	        callableStatement.setString(6, likeTerm);
	        
	        ResultSet resultSet = callableStatement.executeQuery();
	        
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
	    String sqlQuery = "{CALL sp_InserirContato(?, ?, ?, ?, ?, ?, ?)}";

	    try (CallableStatement callableStatement = conn.prepareCall(sqlQuery)) {
	        callableStatement.setString(1, contato.getNome());
	        callableStatement.setString(2, contato.getEmail());
	        callableStatement.setString(3, contato.getTelefone());
	        callableStatement.setString(4, contato.getEndereco());
	        callableStatement.setString(5, contato.getCidade());
	        callableStatement.setString(6, contato.getEstado());
	        callableStatement.setDate(7, contato.getDataNascimento() != null ? new java.sql.Date(contato.getDataNascimento().getTime()) : null);

	        int rows = callableStatement.executeUpdate();
	        
	        // Log de inserção de dados...
	        System.out.println("✅ Inserção realizada. Linhas afetadas: " + rows);
	        System.out.println("👤 Nome: " + contato.getNome());
	        System.out.println("📅 Data: " + contato.getDataNascimento());
	        System.out.println("📞 Telefone: " + contato.getTelefone());
	        System.out.println("📧 Email: " + contato.getEmail());
	        System.out.println("🏠 Endereço: " + contato.getEndereco());
	        System.out.println("🌍 Estado: " + contato.getEstado());
	        System.out.println("🏙️ Cidade: " + contato.getCidade());
	        return rows > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao inserir contato: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	// Atualizar contacto
	public boolean atualizar(Contato contato) {
	    String sqlQuery = "{CALL sp_AtualizarContato(?, ?, ?, ?, ?, ?, ?, ?)}";

	    try (CallableStatement callableStatement = conn.prepareCall(sqlQuery)) {
	        callableStatement.setString(1, contato.getNome());
	        callableStatement.setString(2, contato.getEmail());
	        callableStatement.setString(3, contato.getTelefone());
	        callableStatement.setString(4, contato.getEndereco());
	        callableStatement.setString(5, contato.getCidade());
	        callableStatement.setString(6, contato.getEstado());
	        callableStatement.setDate(7, contato.getDataNascimento() != null ? new java.sql.Date(contato.getDataNascimento().getTime()) : null);
	        callableStatement.setInt(8, contato.getId());
	        
	        // Debug: Log de atualização de dados...
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
	        int rows = callableStatement.executeUpdate();
	        
	        // Debug: Log de sucesso na atualização...
	        System.out.println("✅ Atualização realizada. Linhas afetadas: " + rows);

	        return rows > 0;
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao atualizar contato: " + e.getMessage());
	        System.out.println("🆔 ID: " + contato.getId());
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// Remover contacto
	public boolean remover(int id) {
	    String sqlQuery = "{CALL sp_DeletarContato(?)}";

	    try (CallableStatement callableStatement = conn.prepareCall(sqlQuery)) {
	        callableStatement.setInt(1, id); 

	        int rows = callableStatement.executeUpdate();
	        
	        // Debug: Log de remoção de dados...
	        System.out.println("📦 Removendo contato:");
	        System.out.println("🆔 ID: " + id);
	        System.out.println("✅ Remoção realizada com sucesso! Linhas afetadas: " + rows);
	        
	        return rows > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao remover contato: " + e.getMessage());
	        System.out.println("🆔 ID: " + id);
	        e.printStackTrace();
	        return false;
	    }
	}

}
