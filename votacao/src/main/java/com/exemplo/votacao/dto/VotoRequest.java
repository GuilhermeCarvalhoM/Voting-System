package com.exemplo.votacao.dto;

import lombok.Data;

@Data
public class VotoRequest {
    private Long sessaoId;
    private Long associadoId;
    private Boolean voto;
	public Long getSessaoId() {
		return sessaoId;
	}
	public void setSessaoId(Long sessaoId) {
		this.sessaoId = sessaoId;
	}
	public Long getAssociadoId() {
		return associadoId;
	}
	public void setAssociadoId(Long associadoId) {
		this.associadoId = associadoId;
	}
	public Boolean getVoto() {
		return voto;
	}
	public void setVoto(Boolean voto) {
		this.voto = voto;
	}
    
    
}
