
public class Anexo {

    private int id;
    private String nome;
    private byte[] conteudo;
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
	public byte[] getConteudo() {
		return conteudo;
	}
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public void anexar(byte[] dados) {
        this.conteudo = dados;
    }
}