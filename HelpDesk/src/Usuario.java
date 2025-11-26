import java.time.*;

public class Usuario {
	private int id;
	private String username;
	private String email;
	private String password;
	private boolean ativo = true;
	private LocalDateTime dataCriacao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		if (id > 0)
			this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String usename) {
		if (username.length() > 3)
			this.username = usename;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if (password.length() >= 8)
			this.password = password;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
}
