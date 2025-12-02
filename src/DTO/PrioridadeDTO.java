	package DTO;
	
	public class PrioridadeDTO {
		private int id;
		private String nome;
		private Integer valor;
		private boolean padrao = false;
		private int numeroTickets;
		
		public PrioridadeDTO() {}
		public PrioridadeDTO(int id, String nome, Integer valor, boolean padrao, int numeroTickets) {
			setId(id);
			setNome(nome);
			setValor(valor);
			setPadrao(padrao);
			setNumeroTickets(numeroTickets);
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
		public Integer getValor() {
			return valor;
		}
		public void setValor(Integer valor) {
			this.valor = valor;
		}
		public boolean isPadrao() {
			return padrao;
		}
		public void setPadrao(boolean padrao) {
			this.padrao = padrao;
		}
		public int getNumeroTickets() {
			return numeroTickets;
		}
		public void setNumeroTickets(int numeroTickets) {
			this.numeroTickets = numeroTickets;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PrioridadeDTO [id=");
			builder.append(id);
			builder.append(", nome=");
			builder.append(nome);
			builder.append(", valor=");
			builder.append(valor);
			builder.append(", padrao=");
			builder.append(padrao);
			builder.append(", numeroTickets=");
			builder.append(numeroTickets);
			builder.append("]");
			return builder.toString();
		}
		
		
	}
