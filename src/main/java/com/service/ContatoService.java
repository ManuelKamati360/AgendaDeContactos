package com.service;

import com.dao.ContatoDAO;
import com.database.MySqlConnectionEE;
import com.model.Contato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

public class ContatoService {

    // Listar todos os contactos
    public List<Contato> listarTodos() throws NamingException {
        try (Connection conn = MySqlConnectionEE.getConnection()) {
            ContatoDAO dao = new ContatoDAO(conn);
            return dao.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Salvar contacto com validação
    public boolean inserir(Contato c) {
        if (c.getNome() == null || c.getNome().isEmpty() ||
            c.getTelefone() == null || c.getTelefone().isEmpty() ||
            c.getEmail() == null || c.getEmail().isEmpty()) {
            System.out.println("❌ Falha na validação: campos obrigatórios ausentes");
            return false;
        }

        try (Connection conn = MySqlConnectionEE.getConnection()) {
            ContatoDAO dao = new ContatoDAO(conn);
            return dao.inserir(c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 
    // Remover contacto
    public boolean remover(int id) {
        try (Connection conn = MySqlConnectionEE.getConnection()) {
            ContatoDAO dao = new ContatoDAO(conn);
            return dao.remover(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
       
    }

    // Atualizar contacto
    public boolean atualizar(Contato c) {
        try (Connection conn = MySqlConnectionEE.getConnection()) {
            ContatoDAO dao = new ContatoDAO(conn);
            return dao.atualizar(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // Buscar contacto por ID
    public Contato buscarPorId(int id) {
		try (Connection conn = MySqlConnectionEE.getConnection()) {
			ContatoDAO dao = new ContatoDAO(conn);
			return dao.buscarPorId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    
    // Este método, será usado para full-text search no futuro...
	public List<Contato> buscarPorTexto(String texto) {
		try (Connection conn = MySqlConnectionEE.getConnection()) {
			ContatoDAO dao = new ContatoDAO(conn);
			return dao.buscarPorTexto(texto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
