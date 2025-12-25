package com.controller_api;

import com.dao.ContatoDAO;
import com.model.Contato;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testarDAO")
public class TestarDAO extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ContatoDAO dao = new ContatoDAO();
        List<Contato> lista = dao.listarTodos();

        out.println("<h2>Lista de Contactos</h2>");
        out.println("<ul>");
        for (Contato c : lista) {
            out.println("<li>" 
            		+ c.getNome() + " - " 
            		+ c.getEmail() + " - " 
            		+ c.getTelefone() + "</li>");
        }
        out.println("</ul>");
    }
}

