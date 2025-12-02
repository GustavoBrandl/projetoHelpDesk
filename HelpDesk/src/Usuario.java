import java.time.LocalDateTime;
import ENUM.TipoUsuario;

public class Usuario {
	private int id;
	private String username;
	private String email;
	private String password;
	private String telefone;
	private boolean ativo = true;
	private TipoUsuario tipo;
	private LocalDateTime dataCriacao;
	private Organizacao organizacao;
	
	public Usuario() {}
	public Usuario(int id, String username, String email, String password, boolean ativo, TipoUsuario tipo, LocalDateTime dataCriacao, Organizacao organizacao) {
		setId(id);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setAtivo(ativo);
		setTipo(tipo);
		setDataCriacao(dataCriacao);
		setOrganizacao(organizacao);
	}
	
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
	public void setUsername(String username) {
		this.username = username;
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
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	public Organizacao getOrganizacao() {
		return organizacao;
	}
	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", telefone=");
		builder.append(telefone);
		builder.append(", ativo=");
		builder.append(ativo);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", dataCriacao=");
		builder.append(dataCriacao);
		builder.append(", organizacao=");
		builder.append(organizacao);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
