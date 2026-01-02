package Controller;

import BO.*;
import DTO.*;
import java.time.YearMonth;
import java.util.List;

public class ContratoController {
    private final ContratoBO contratoBO;
    private final OrganizacaoBO organizacaoBO;
    
    public ContratoController() {
        this.contratoBO = new ContratoBO();
        this.organizacaoBO = new OrganizacaoBO();
    }
    
    public boolean inserir(ContratoDTO contrato) {
        return contratoBO.inserir(contrato);
    }
    
    public boolean alterar(ContratoDTO contrato) {
        return contratoBO.alterar(contrato);
    }
    
    public List<ContratoDTO> pesquisarTodos() {
        return contratoBO.pesquisarTodos();
    }
    
    public List<OrganizacaoDTO> pesquisarOrganizacoes() {
        return organizacaoBO.pesquisarTodos();
    }
    
    public boolean excluir(ContratoDTO contrato) {
        return contratoBO.excluir(contrato);
    }
    
    public ContratoDTO pesquisarAtivoOrganizacao(Integer organizacaoId, YearMonth mes) {
        return contratoBO.pesquisarAtivoOrganizacao(organizacaoId, mes);
    }
}

