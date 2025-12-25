package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.model.Contato;
import com.model.Contato;

public class ContatoDAO {
	public List<Contato> listarTodos() { 
		
		List<Contato> contatosList = new ArrayList<>(); 
		
		try { 
			InitialContext ctx = new InitialContext(); 
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/agendaDB"); 
			
			try (Connection conn = ds.getConnection(); 
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contato;");
					ResultSet rs = stmt.executeQuery()) { 
				while (rs.next()) { 
					Contato contato = new Contato( 
							rs.getInt("id"), 
							rs.getString("nome"), 
							rs.getString("email"), 
							rs.getString("telefone")
							); 
					contatosList.add(contato); 
					} 
				} 
			
			} catch (Exception e) { 
				System.err.println("Erro ao listar contatos: " + e.getMessage());
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
