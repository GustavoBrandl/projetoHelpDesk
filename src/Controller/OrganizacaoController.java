package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class OrganizacaoController {
    private final OrganizacaoBO organizacaoBO;
    
    public OrganizacaoController() {
        this.organizacaoBO = new OrganizacaoBO();
    }
    
    public boolean criarOrganizacao(String nome, String dominio) {
        return organizacaoBO.criarOrganizacao(nome, dominio);
    }
    
    public boolean alterarOrganizacao(int id, String nome, String dominio) {
        return organizacaoBO.alterarOrganizacao(id, nome, dominio);
    }
    
    public List<OrganizacaoDTO> listarOrganizacoes() {
        return organizacaoBO.pesquisarTodos();
    }
    
    public boolean excluirOrganizacao(OrganizacaoDTO organizacao) {
        return organizacaoBO.excluir(organizacao);
    }
}