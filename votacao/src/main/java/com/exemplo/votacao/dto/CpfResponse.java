package com.exemplo.votacao.dto;

public class CpfResponse {
    private int status;
    private String cpf;
    private String nome;
    private String nascimento;
    private String mae;
    private String genero;
    private String situacaoInscricao;
    private String situacao;
    private String situacaoDigito;
    private String situacaoMotivo;
    private int situacaoAnoObito;
    private String situacaoComprovante;
    private int pacoteUsado;
    private double saldo;
    private String consultaID;
    private double delay;

    // Getters e Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

