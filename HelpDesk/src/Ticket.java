import java.time.*;

public class Ticket {
	private int id;
	private String sobre;
	private String descricao;
	private LocalDateTime dataHoraAbertura;
	private LocalDateTime dataHoraFinalizacao;
	private double tempoChamado;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSobre() {
		return sobre;
	}
	public void setSobre(String sobre) {
		this.sobre = sobre;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDateTime getDataHoraAbertura() {
		return dataHoraAbertura;
	}
	public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}
	public LocalDateTime getDataHoraFinalizacao() {
		return dataHoraFinalizacao;
	}
	public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
		this.dataHoraFinalizacao = dataHoraFinalizacao;
	}
	public double getTempoChamado() {
		return tempoChamado;
	}
	public void setTempoChamado(double tempoChamado) {
		this.tempoChamado = tempoChamado;
	}
	
}
