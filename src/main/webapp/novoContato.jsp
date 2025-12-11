<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="model.Contacto"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novo contato</title>
</head>
<body>
    <h1>Novo contato</h1>
    <form action="InserirContato" method="post">
        Nome: <br><input type="text" name="nome" size="40" /><br/>
        Telefone: <br><input type="text" name="telefone" size="40"  /><br/>
        Email: <br><input type="text" name="email" size="40" /><br/>
        Endereco: <br><input type="text" name="endereco" size="40" /><br/>
        Data de nascimento: <br><input type="date" name="dataNascimento" size="40" /><br/>
        Cidade: <br><input type="text" name="cidade" size="40" /><br/>
        Estado: <br><input type="text" name="estado" size="40" /><br/>
        <br><input type="submit" value="Adicionar" size="40" />
        <input type="submit" value="Limpar" size="10"/>
    </form>	
</body>
</html>