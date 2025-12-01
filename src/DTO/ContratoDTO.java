package DTO;

public class ContratoDTO {
	private int id;
	private Double horasContrato;
	private Double precoContrato;
	private Double precoExtra;
	private OrganizacaoDTO organizacao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getHorasContrato() {
		return horasContrato;
	}
	public void setHorasContrato(Double horasContrato) {
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
	public OrganizacaoDTO getOrganizacao() {
		return organizacao;
	}
	public void setOrganizacao(OrganizacaoDTO organizacao) {
		this.organizacao = organizacao;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContratoDTO [id=");
		builder.append(id);
		builder.append(", horasContrato=");
		builder.append(horasContrato);
		builder.append(", precoContrato=");
		builder.append(precoContrato);
		builder.append(", precoExtra=");
		builder.append(precoExtra);
		builder.append(", organizacao=");
		builder.append(organizacao);
		builder.append("]");
		return builder.toString();
	}
	
}
