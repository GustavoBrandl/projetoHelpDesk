package DTO;

public class FaturamentoDTO {
    private Integer id;
    private Integer contratoId;
    private Integer organizacaoId;
    private String mesFaturamento;
    private Double horasTrabalhadas;
    private Double valorBase;
    private Double valorExtra;
    private Double valorTotal;

    public FaturamentoDTO() {
    }

    public FaturamentoDTO(Integer organizacaoId, String mesFaturamento, Double horasTrabalhadas, 
                          Double valorBase, Double valorExtra, Double valorTotal) {
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

    public String getMesFaturamento() {
        return mesFaturamento;
    }

    public void setMesFaturamento(String mesFaturamento) {
        this.mesFaturamento = mesFaturamento;
    }

    public Double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(Double horasTrabalhadas) {
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
        return "FaturamentoDTO{" +
                "id=" + id +
                ", contratoId=" + contratoId +
                ", organizacaoId=" + organizacaoId +
                ", mesFaturamento='" + mesFaturamento + '\'' +
                ", horasTrabalhadas=" + horasTrabalhadas +
                ", valorBase=" + valorBase +
                ", valorExtra=" + valorExtra +
                ", valorTotal=" + valorTotal +
                '}';
    }
}


