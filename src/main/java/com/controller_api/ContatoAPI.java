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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    // Lista todos
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

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo(); // ex: /1
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            // GET /api/contatos → lista todos
//            List<Contato> lista = null;
//			try {
//				lista = service.listarTodos();
//			} catch (NamingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            response.setStatus(HttpServletResponse.SC_OK); // 200
//            out.print(new Gson().toJson(lista));
//        } else {
//            // GET /api/contatos/{id} → detalhe
//            try {
//                int id = Integer.parseInt(pathInfo.substring(1));
//                Contato c = service.buscarPorId(id);
//                if (c != null) {
//                    response.setStatus(HttpServletResponse.SC_OK); // 200
//                    out.print(new Gson().toJson(c));
//                } else {
//                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
//                    out.print("{\"erro\":\"Contato não encontrado\"}");
//                }
//            } catch (NumberFormatException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
//                out.print("{\"erro\":\"ID inválido\"}");
//            }
//        }
//        out.flush();
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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



    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
}
