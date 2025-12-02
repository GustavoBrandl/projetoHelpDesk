package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class OrganizacaoController {
    private final OrganizacaoBO organizacaoBO;
    
    public OrganizacaoController() {
        this.organizacaoBO = new OrganizacaoBO();
    }
    
    public boolean inserir(OrganizacaoDTO organizacao) {
        return organizacaoBO.inserir(organizacao);
    }

    // Compatibilidade: versão antiga usada pelas Views
    public boolean criarOrganizacao(String nome, String dominio) {
        OrganizacaoDTO dto = new OrganizacaoDTO();
        dto.setNome(nome);
        dto.setDominio(dominio);
        return organizacaoBO.inserir(dto);
    }
    
    public boolean alterar(OrganizacaoDTO organizacao) {
        return organizacaoBO.alterar(organizacao);
    }

    // Compatibilidade
    public boolean alterarOrganizacao(int id, String nome, String dominio) {
        OrganizacaoDTO dto = new OrganizacaoDTO();
        dto.setId(id);
        dto.setNome(nome);
        dto.setDominio(dominio);
        return organizacaoBO.alterar(dto);
    }
    
    public List<OrganizacaoDTO> pesquisarTodos() {
        return organizacaoBO.pesquisarTodos();
    }

    // Compatibilidade
    public List<OrganizacaoDTO> listarOrganizacoes() {
        return pesquisarTodos();
    }
    
    public boolean excluir(OrganizacaoDTO organizacao) {
        return organizacaoBO.excluir(organizacao);
    }

    // Backwards-compatible convenience methods used by Views
    public boolean inserir(String nome, String dominio) {
        OrganizacaoDTO o = new OrganizacaoDTO();
        o.setNome(nome);
        o.setDominio(dominio);
        return inserir(o);
    }

    public boolean alterar(int id, String nome, String dominio) {
        OrganizacaoDTO o = new OrganizacaoDTO();
        o.setId(id);
        o.setNome(nome);
        o.setDominio(dominio);
        return alterar(o);
    }
}