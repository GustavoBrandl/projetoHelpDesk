package DTO;

public class DepartamentoDTO {
	private int id;
	private String nome;
	
	public DepartamentoDTO() {}
	public DepartamentoDTO(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DepartamentoDTO [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append("]");
		return builder.toString();
	}
	
}
