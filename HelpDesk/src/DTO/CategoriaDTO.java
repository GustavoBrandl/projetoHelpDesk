package DTO;

public class CategoriaDTO {
	private int id;
	private String nome;
	private int numeroTicket;
	private DepartamentoDTO departamento;
	
	public CategoriaDTO() {}
	public CategoriaDTO(int id, String nome, int numeroTicket, DepartamentoDTO departamento) {
		setId(id);
		setNome(nome);
		setNumeroTicket(numeroTicket);
		setDepartamento(departamento);
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
	public int getNumeroTicket() {
		return numeroTicket;
	}
	public void setNumeroTicket(int numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	public DepartamentoDTO getDepartamento() {
		return departamento;
	}
	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoriaDTO [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", numeroTicket=");
		builder.append(numeroTicket);
		builder.append(", departamento=");
		builder.append(departamento);
		builder.append("]");
		return builder.toString();
	}
	
	
}
