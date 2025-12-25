package com.service;

import com.dao.ContatoDAO;
import com.model.Contato;
import java.util.List;

public class ContatoService {
    private ContatoDAO dao = new ContatoDAO();

    // Listar todos os contactos
    public List<Contato> listarTodos() {
    	
        return dao.listarTodos();
    }

    // Salvar contacto com validação
    public boolean salvar(Contato c) {
        if (c.getEmail() == null || !c.getEmail().contains("@")) {
            return false; // regra de negócio simples
        }
        
        return dao.inserir(c);
    }

    // Remover contacto
    public boolean remover(int id) {
    	
        return dao.remover(id);
    }

    // Atualizar contacto
    public boolean atualizar(Contato c) {
    	
        return dao.atualizar(c);
    }
}
