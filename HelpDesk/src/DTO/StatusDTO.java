package DTO;

public class StatusDTO {
	private int id;
	private String nome;
	private boolean padrao = false;
	private int numeroTicket;
	
	public StatusDTO() {}
	public StatusDTO(int id, String nome, boolean padrao, int numeroTicket) { 
		setId(id);
		setNome(nome);
		setPadrao(padrao);
		setNumeroTicket(numeroTicket);
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
	public boolean isPadrao() {
		return padrao;
	}
	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}
	public int getNumeroTicket() {
		return numeroTicket;
	}
	public void setNumeroTicket(int numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatusDTO [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", padrao=");
		builder.append(padrao);
		builder.append(", numeroTicket=");
		builder.append(numeroTicket);
		builder.append("]");
		return builder.toString();
	}
	
}


