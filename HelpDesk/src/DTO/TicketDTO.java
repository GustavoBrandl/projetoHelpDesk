package DTO;

import java.time.LocalDateTime;

public class TicketDTO {
	private Integer id;
	private String sobre;
	private String descricao;
	private LocalDateTime dataHoraAbertura;
	private LocalDateTime dataHoraFinalizacao;
	private Double tempoChamado;
	private PrioridadeDTO prioridade;
	private StatusDTO status;
	private CategoriaDTO categoria;
	private UsuarioDTO solicitante;
	private UsuarioDTO atendente;
	
	public TicketDTO() {}
	public TicketDTO(Integer id, String sobre, String descrcicao, LocalDateTime dataHoraAbertura, LocalDateTime dataHoraFinalizacao, Double tempoChamado, PrioridadeDTO prioridade, StatusDTO status, CategoriaDTO categoria, UsuarioDTO solicitante, UsuarioDTO atendente) {
		setId(id);
		setSobre(sobre);
		setDescricao(descrcicao);
		setDataHoraAbertura(dataHoraAbertura);
		setDataHoraFinalizacao(dataHoraFinalizacao);
		setTempoChamado(tempoChamado);
		setPrioridade(prioridade);
		setStatus(status);
		setCategoria(categoria);
		setSolicitante(solicitante);
		setAtendente(atendente);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSobre() {
		return sobre;
	}
	public void setSobre(String sobre) {
		this.sobre = sobre;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDateTime getDataHoraAbertura() {
		return dataHoraAbertura;
	}
	public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}
	public LocalDateTime getDataHoraFinalizacao() {
		return dataHoraFinalizacao;
	}
	public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
		this.dataHoraFinalizacao = dataHoraFinalizacao;
	}
	public Double getTempoChamado() {
		return tempoChamado;
	}
	public void setTempoChamado(Double tempoChamado) {
		this.tempoChamado = tempoChamado;
	}
	public PrioridadeDTO getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(PrioridadeDTO prioridade) {
		this.prioridade = prioridade;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public CategoriaDTO getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}
	public UsuarioDTO getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(UsuarioDTO solicitante) {
		this.solicitante = solicitante;
	}
	public UsuarioDTO getAtendente() {
		return atendente;
	}
	public void setAtendente(UsuarioDTO atendente) {
		this.atendente = atendente;
	}
	
	// Alias para compatibilidade
	public UsuarioDTO getTecnico() {
		return atendente;
	}
	public void setTecnico(UsuarioDTO tecnico) {
		this.atendente = tecnico;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataHoraAbertura;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TicketDTO [id=");
		builder.append(id);
		builder.append(", sobre=");
		builder.append(sobre);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(", dataHoraAbertura=");
		builder.append(dataHoraAbertura);
		builder.append(", dataHoraFinalizacao=");
		builder.append(dataHoraFinalizacao);
		builder.append(", tempoChamado=");
		builder.append(tempoChamado);
		builder.append(", prioridade=");
		builder.append(prioridade);
		builder.append(", status=");
		builder.append(status);
		builder.append(", categoria=");
		builder.append(categoria);
		builder.append(", solicitante=");
		builder.append(solicitante);
		builder.append(", atendente=");
		builder.append(atendente);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
