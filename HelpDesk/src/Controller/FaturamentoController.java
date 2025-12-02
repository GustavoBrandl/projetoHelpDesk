package Controller;

import BO.FaturamentoBO;
import DTO.FaturamentoDTO;
import java.time.YearMonth;

public class FaturamentoController {
    private final FaturamentoBO faturamentoBO;

    public FaturamentoController() {
        this.faturamentoBO = new FaturamentoBO();
    }

    public FaturamentoDTO gerarFaturamento(Integer organizacaoId, YearMonth mes) {
        return faturamentoBO.gerarFaturamento(organizacaoId, mes);
    }
}
