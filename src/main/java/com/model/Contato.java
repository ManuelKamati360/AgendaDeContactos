package com.model;

import java.util.Date;

public class Contato {
	
	// Atributos...
	private int _id;
	private String _nome;
	private Date _dataNascimento;
	private String _telefone;
	private String _email;
	private String _endereco;
	private String _estado;
	private String _cidade;
	
	// Getters e Setters...
	public int getId() { return _id; }
	public void setId(int _id) { this._id = _id; }
	public String getNome() { return _nome;	}
	public void setNome(String _nome) {	this._nome = _nome;	}
	public Date getDataNascimento() { return _dataNascimento; }
	public void setDataNascimento(Date _dataNascimento) { this._dataNascimento = _dataNascimento; }
	public String getTelefone() { return _telefone;	}
	public void setTelefone(String _telefone) {	this._telefone = _telefone;	}
	public String getEmail() { return _email; }
	public void setEmail(String _email) { this._email = _email;	}
	public String getEndereco() { return _endereco;	}
	public void setEndereco(String _endereco) {	this._endereco = _endereco;	}
	public String getEstado() {	return _estado;	}
	public void setEstado(String _estado) {	this._estado = _estado;	}
	public String getCidade() {	return _cidade;	}
	public void setCidade(String _cidade) { this._cidade = _cidade;	}
	
	// Sobrecargas do Construtor...
	public Contato(String nome, Date dataNascimento, String telefone, String email, String endereco, String estado, String cidade) {
		this._nome = nome;
		this._dataNascimento = dataNascimento;
		this._telefone = telefone;
		this._email = email;
		this._endereco = endereco;
		this._estado = estado;
		this._cidade = cidade;
	}
	
	public Contato(int id, String nome, String telefone, String email) {
		this._id = id;
		this._nome = nome;
		this._telefone = telefone;
		this._email = email;
	}
	
	public Contato(String nome, String telefone) {
		this._nome = nome;
		this._telefone = telefone;
	}
	
	public Contato() {
		this._nome = "";
		this._dataNascimento = null;
		this._telefone = "";
		this._email = "";
		this._endereco = "";
		this._estado = "";
		this._cidade = "";
	}

}
