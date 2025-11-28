package BO;

import DTO.OrganizacaoDTO;
import DAO.OrganizacaoDAO;
import java.util.List;

public class OrganizacaoBO {
    
	public boolean inserir(OrganizacaoDTO organizacao){
		if (existe(organizacao) != true) {
		    OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
		    return organizacoesDAO.inserir(organizacao);
		} 
			return false;
	}
	
    public boolean alterar(OrganizacaoDTO organizacao){
        OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
        return organizacoesDAO.alterar(organizacao);
    }

    public boolean excluir(OrganizacaoDTO organizacao) {
    	OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
    	return organizacoesDAO.excluir(organizacao);
    }
    
    public boolean existe(OrganizacaoDTO organizacao){
        OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
        return organizacoesDAO.existe(organizacao);
    }

    public List<OrganizacaoDTO> pesquisarTodos(){
        OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
        return organizacoesDAO.pesquisarTodos();
    }
    
    public boolean criarOrganizacao(String nome, String dominio) {

        OrganizacaoDTO organizacao = new OrganizacaoDTO();
        organizacao.setNome(nome);
        organizacao.setDominio(dominio);

        return inserir(organizacao);
    }

    public boolean alterarOrganizacao(int id, String nome, String dominio) {
    	OrganizacaoDTO organizacao = new OrganizacaoDTO();
    	OrganizacaoDAO organizacoesDAO = new OrganizacaoDAO();
    	
    	organizacao.setId(id);
    	if (nome != null) {
    		organizacao.setNome(nome);
    	}
    	if (dominio != null) {
    		organizacao.setDominio(dominio);
    	}
    	
    	return organizacoesDAO.alterarOrganizacao(organizacao);
    }
    
    public void listarOrganizacao() {
    	List<OrganizacaoDTO> organizacoes= pesquisarTodos();
    	
    	for (OrganizacaoDTO organizacao : organizacoes) {
    		System.out.println(organizacao);
    	}
    }
}
