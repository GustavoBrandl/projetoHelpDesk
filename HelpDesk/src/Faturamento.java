import java.time.*;

public class Faturamento {
	private int id;
	private int mes;
	private double totalHoras;
	private double precoPago;
	private int numeroTickets;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public double getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}
	public double getPrecoPago() {
		return precoPago;
	}
	public void setPrecoPago(double precoPago) {
		this.precoPago = precoPago;
	}
	public int getNumeroTickets() {
		return numeroTickets;
	}
	public void setNumeroTickets(int numeroTickets) {
		this.numeroTickets = numeroTickets;
	}
	
	
}
