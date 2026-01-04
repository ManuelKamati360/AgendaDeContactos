package com.controller_api;

import com.model.Contato;
import com.dao.ContatoDAO;
import com.database.MySqlConnectionEE;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/api/diagnostico")
public class DiagnosticoAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {

            try (Connection conn = MySqlConnectionEE.getConnection()) {
                System.out.println("✅ Conexão com banco estabelecida.");

                ContatoDAO dao = new ContatoDAO(conn);
                List<Contato> lista = dao.listarTodos();

                System.out.println("🔍 Total de contatos encontrados: " + lista.size());
                for (Contato c : lista) {
                    System.out.println("→ " + c.getId() + " | " + c.getNome() + " | " + c.getEmail());
                }

                out.print("{\"total\": " + lista.size() + "}");
            }

        } catch (NamingException e) {
            System.err.println("❌ Erro JNDI: " + e.getMessage());
            out.print("{\"erro\": \"Falha ao acessar JNDI\"}");
        } catch (Exception e) {
            System.err.println("❌ Erro geral: " + e.getMessage());
            e.printStackTrace();
            out.print("{\"erro\": \"Falha geral na conexão ou leitura\"}");
        }

        out.flush();
    }
}
