package DTO;

import java.time.YearMonth;

public class ContratoDTO {
	private Integer id;
	private Integer organizacaoId;
	private Integer horasContrato;
	private Double precoContrato;
	private Double precoExtra;
	private YearMonth dataVigencia;
	private Boolean ativo;

	public ContratoDTO() {
	}

	public ContratoDTO(Integer organizacaoId, Integer horasContrato, Double precoContrato, Double precoExtra, YearMonth dataVigencia, Boolean ativo) {
		this.organizacaoId = organizacaoId;
		this.horasContrato = horasContrato;
		this.precoContrato = precoContrato;
		this.precoExtra = precoExtra;
		this.dataVigencia = dataVigencia;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrganizacaoId() {
		return organizacaoId;
	}

	public void setOrganizacaoId(Integer organizacaoId) {
		this.organizacaoId = organizacaoId;
	}

	public Integer getHorasContrato() {
		return horasContrato;
	}

	public void setHorasContrato(Integer horasContrato) {
		this.horasContrato = horasContrato;
	}

	public Double getPrecoContrato() {
		return precoContrato;
	}

	public void setPrecoContrato(Double precoContrato) {
		this.precoContrato = precoContrato;
	}

	public Double getPrecoExtra() {
		return precoExtra;
	}

	public void setPrecoExtra(Double precoExtra) {
		this.precoExtra = precoExtra;
	}

	public YearMonth getDataVigencia() {
		return dataVigencia;
	}

	public void setDataVigencia(YearMonth dataVigencia) {
		this.dataVigencia = dataVigencia;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "ContratoDTO{" +
				"id=" + id +
				", organizacaoId=" + organizacaoId +
				", horasContrato=" + horasContrato +
				", precoContrato=" + precoContrato +
				", precoExtra=" + precoExtra +
				", dataVigencia=" + dataVigencia +
				", ativo=" + ativo +
				'}';
	}
}
