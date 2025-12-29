package com.controller_api;

import com.service.ContatoService;
import com.model.Contato;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

@WebServlet("/api/contatos/*")
public class ContatoAPI extends HttpServlet {
    private ContatoService service = new ContatoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // ex: /1
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (pathInfo == null || pathInfo.equals("/")) {
            // GET /api/contatos → lista todos
            List<Contato> lista = null;
			try {
				lista = service.listarTodos();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            response.setStatus(HttpServletResponse.SC_OK); // 200
            out.print(new Gson().toJson(lista));
        } else {
            // GET /api/contatos/{id} → detalhe
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Contato c = service.buscarPorId(id);
                if (c != null) {
                    response.setStatus(HttpServletResponse.SC_OK); // 200
                    out.print(new Gson().toJson(c));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                    out.print("{\"erro\":\"Contato não encontrado\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                out.print("{\"erro\":\"ID inválido\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Contato c = new Contato();
        c.setNome(request.getParameter("nome"));
        c.setTelefone(request.getParameter("telefone"));

        // Data de Nascimento
        String dataStr = request.getParameter("dataNascimento");
        Date data = null;
        if (dataStr != null && !dataStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                data = sdf.parse(dataStr);
            } catch (ParseException e) {
                System.err.println("❌ Erro ao converter data: " + dataStr);
            }
        }
        c.setDataNascimento(data);

        c.setEmail(request.getParameter("email"));   
        c.setEndereco(request.getParameter("endereco"));
        c.setEstado(request.getParameter("estado"));
		c.setCidade(request.getParameter("cidade"));

        boolean sucesso = service.salvar(c);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (sucesso) {
            response.setStatus(HttpServletResponse.SC_CREATED); // 201
            response.getWriter().print("{\"sucesso\": true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().print("{\"erro\":\"Dados inválidos\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // ex: /1
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Contato c = new Contato();
            int id = Integer.parseInt(pathInfo.substring(1));
            c.setId(id);
            c.setNome(request.getParameter("nome"));            
            c.setTelefone(request.getParameter("telefone"));
            
            // Data de Nascimento
            String dataStr = request.getParameter("dataNascimento");
            Date data = null;
            try {
                if (dataStr != null && !dataStr.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    data = sdf.parse(dataStr);
                }
            } catch (ParseException e) {
                System.err.println("❌ Erro ao converter data: " + dataStr);
            }
            c.setDataNascimento(data);

            c.setEndereco(request.getParameter("endereco"));
            c.setEstado(request.getParameter("estado"));
			c.setCidade(request.getParameter("cidade"));
            c.setEmail(request.getParameter("email"));

            boolean sucesso = service.atualizar(c);

            if (sucesso) {
                response.setStatus(HttpServletResponse.SC_OK); // 200
                response.getWriter().print("{\"sucesso\": true}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                response.getWriter().print("{\"erro\":\"Contato não encontrado\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().print("{\"erro\":\"ID inválido\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // ex: /1
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
//        		Contato c = new Contato();
//        		c.setId(Integer.parseInt(request.getParameter("id")));
            boolean sucesso = service.remover(id);

            if (sucesso) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                response.getWriter().print("{\"erro\":\"Contato não encontrado\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().print("{\"erro\":\"ID inválido\"}");
        }
    }
}
