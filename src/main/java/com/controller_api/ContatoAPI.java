package com.controller_api;

import com.service.ContatoService;
import com.model.Contato;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
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

    // GET /api/contatos ou /api/contatos/{id} ou /api/contatos?search=xxx
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    		// Estas linhas de código, servem para liberação parcial de CORS para requisições do frontend rodando em servidor diferente...
	    	response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
	    	response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	    	response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");


    	
        String pathInfo = request.getPathInfo(); // ex: /1
        String search = request.getParameter("search"); // ex: ?search=manuel

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Caso 1: GET /api/contatos → lista todos
                if (search != null && !search.isEmpty()) {
                    // Caso 2: GET /api/contatos?search=xxx → busca por termo
                    List<Contato> resultados = service.buscarPorTexto(search);
                    if (!resultados.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print(new Gson().toJson(resultados));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"erro\":\"Nenhum contato encontrado\"}");
                    }
                } else {
                    // Adicionando todos os dados na lista e serializando todos em um arquivo JSON...
	                	try {
	                	    List<Contato> lista = service.listarTodos();
	                	    response.setStatus(HttpServletResponse.SC_OK);
	                	    out.print(new Gson().toJson(lista));
	                	} catch (NamingException e) {
	                	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                	    out.print("{\"erro\":\"Erro ao listar contatos\"}");
	                	    e.printStackTrace();
	                	}
                }
            } else {
                // Caso 3: GET /api/contatos/{id} → detalhe
                String idStr = pathInfo.replaceAll("/", "");
                int id = Integer.parseInt(idStr);
                Contato c = service.buscarPorId(id);

                if (c != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print(new Gson().toJson(c));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"erro\":\"Contato não encontrado\"}");
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"erro\":\"ID inválido\"}");
        }

        out.flush();
    }

    // POST /api/contatos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    		// Estas linhas de código, servem para liberação parcial de CORS para requisições do frontend rodando em servidor diferente...
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    	
        Contato c = new Gson().fromJson(request.getReader(), Contato.class);
        boolean sucesso = service.inserir(c);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (sucesso) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().print("{\"sucesso\": true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"erro\":\"Falha ao criar contato\"}");
        }
    }

    // PUT /api/contatos/{id}
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    		// Estas linhas de código, servem para liberação parcial de CORS para requisições do frontend rodando em servidor diferente...
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        Contato c = new Gson().fromJson(request.getReader(), Contato.class);
        c.setId(id);

        boolean sucesso = service.atualizar(c);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (sucesso) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print("{\"sucesso\": true}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print("{\"erro\":\"Contato não encontrado\"}");
        }
    }


	// DELETE /api/contatos/{id}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
		// Estas linhas de código, servem para liberação parcial de CORS para requisições do frontend rodando em servidor diferente...
		response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    	
    		String pathInfo = request.getPathInfo(); // ex: /1 para deletar o contato com ID 1
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            
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
    
    // OPTIONS /api/contatos/*
    // Este método, servem para liberação completa de CORS para requisições do frontend rodando em servidor diferente...
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
