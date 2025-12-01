package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class ContratoController {
    private final ContratoBO contratoBO;
    private final OrganizacaoBO organizacaoBO;
    
    public ContratoController() {
        this.contratoBO = new ContratoBO();
        this.organizacaoBO = new OrganizacaoBO();
    }
    
    public boolean criarContrato(Double horasContrato, Double precoContrato, 
                                 Double precoExtra, Integer idOrganizacao) {
        return contratoBO.criarContrato(horasContrato, precoContrato, precoExtra, idOrganizacao);
    }
    
    public boolean alterarContrato(Integer id, Double horasContrato, Double precoContrato, 
                                   Double precoExtra, Integer idOrganizacao) {
        return contratoBO.alterarContrato(id, horasContrato, precoContrato, precoExtra, idOrganizacao);
    }
    
    public List<ContratoDTO> listarContratos() {
        return contratoBO.pesquisarTodos();
    }
    
    public List<OrganizacaoDTO> listarOrganizacoes() {
        return organizacaoBO.pesquisarTodos();
    }
    
    public boolean excluirContrato(ContratoDTO contrato) {
        return contratoBO.excluir(contrato);
    }
}