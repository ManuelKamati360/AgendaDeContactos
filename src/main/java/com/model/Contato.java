package com.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Contato {
	
	// Atributos...
	
	@SerializedName("id")
	private int _id;
	
	@SerializedName("nome")
	private String _nome;
	
	@SerializedName("dataNascimento")
	private Date _dataNascimento;
	
	@SerializedName("telefone")
	private String _telefone;
	
	@SerializedName("email")
	private String _email;
	
	@SerializedName("endereco")
	private String _endereco;
	
	@SerializedName("estado")
	private String _estado;
	
	@SerializedName("cidade")
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
	public Contato(int _id, String _nome, String _email, String _telefone, Date _dataNascimento, String _endereco, String _estado, String _cidade) {
		this._id = _id;
		this._nome = _nome;
		this._dataNascimento = _dataNascimento;
		this._telefone = _telefone;
		this._email = _email;
		this._endereco = _endereco;
		this._estado = _estado;
		this._cidade = _cidade;
	}
	
	public Contato(int _id, String _nome, String _email, String _telefone, String _endereco, String _estado, String _cidade) {
		this._nome = _nome;
		this._telefone = _telefone;
		this._email = _email;
		this._endereco = _endereco;
		this._estado = _estado;
		this._cidade = _cidade;
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
