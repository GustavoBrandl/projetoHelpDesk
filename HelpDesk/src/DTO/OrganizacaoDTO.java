package DTO;
import java.time.LocalDateTime;

public class OrganizacaoDTO {
	private Integer id;
	private String nome;
	private String dominio;
	private LocalDateTime dataCriacao;
	
	public OrganizacaoDTO() {}
	public OrganizacaoDTO(Integer id, String nome, String dominio, LocalDateTime dataCriacao) {
		setId(id);
		setNome(nome);
		setDominio(dominio);
		setDataCriacao(dataCriacao);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrganizacaoDTO [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", dominio=");
		builder.append(dominio);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append("]");
		return builder.toString();
	}
	
}



