package com.model;

public class Metricas {
	private int _totalContatos;
	private int _contatosComEmail;
	private int _contatosComTelefone;

	public Metricas(int totalContatos, int contatosComEmail, int contatosComTelefone) {
		this._totalContatos = totalContatos;
		this._contatosComEmail = contatosComEmail;
		this._contatosComTelefone = contatosComTelefone;
	}

	public int getTotalContatos() {
		return _totalContatos;
	}

	public int getContatosComEmail() {
		return _contatosComEmail;
	}

	public int getContatosComTelefone() {
		return _contatosComTelefone;
	}

}
