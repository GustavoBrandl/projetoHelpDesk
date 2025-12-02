import java.time.YearMonth;

public class Faturamento {
    private Integer id;
    private Integer contratoId;
    private Integer organizacaoId;
    private YearMonth mesFaturamento;
    private Integer horasTrabalhadas;
    private Double valorBase;
    private Double valorExtra;
    private Double valorTotal;

    public Faturamento() {
    }

    public Faturamento(Integer contratoId, Integer organizacaoId, YearMonth mesFaturamento, Integer horasTrabalhadas, Double valorBase, Double valorExtra, Double valorTotal) {
        this.contratoId = contratoId;
        this.organizacaoId = organizacaoId;
        this.mesFaturamento = mesFaturamento;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorBase = valorBase;
        this.valorExtra = valorExtra;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContratoId() {
        return contratoId;
    }

    public void setContratoId(Integer contratoId) {
        this.contratoId = contratoId;
    }

    public Integer getOrganizacaoId() {
        return organizacaoId;
    }

    public void setOrganizacaoId(Integer organizacaoId) {
        this.organizacaoId = organizacaoId;
    }

    public YearMonth getMesFaturamento() {
        return mesFaturamento;
    }

    public void setMesFaturamento(YearMonth mesFaturamento) {
        this.mesFaturamento = mesFaturamento;
    }

    public Integer getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(Integer horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Double getValorExtra() {
        return valorExtra;
    }

    public void setValorExtra(Double valorExtra) {
        this.valorExtra = valorExtra;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Faturamento{" +
                "id=" + id +
                ", contratoId=" + contratoId +
                ", organizacaoId=" + organizacaoId +
                ", mesFaturamento=" + mesFaturamento +
                ", horasTrabalhadas=" + horasTrabalhadas +
                ", valorBase=" + valorBase +
                ", valorExtra=" + valorExtra +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
