package BO;

import DTO.OrganizacaoDTO;
import DAO.OrganizacaoDAO;
import java.util.List;

public class OrganizacaoBO {
    
	private OrganizacaoDAO dao = new OrganizacaoDAO();

	public boolean inserir(OrganizacaoDTO organizacao) {
		if (!existe(organizacao)) {
		    return dao.inserir(organizacao);
		} 
		return false;
	}
	
    public boolean alterar(OrganizacaoDTO organizacao) {
        return dao.alterar(organizacao);
    }

    public boolean excluir(OrganizacaoDTO organizacao) {
    	return dao.excluir(organizacao);
    }
    
    public boolean existe(OrganizacaoDTO organizacao) {
        return dao.existe(organizacao);
    }

    public List<OrganizacaoDTO> pesquisarTodos() {
        return dao.pesquisarTodos();
    }

    // Compatibilidade: criar organização por nome/dominio (usado em algumas Views)
    public boolean inserir(String nome, String dominio) {
        OrganizacaoDTO dto = new OrganizacaoDTO();
        dto.setNome(nome);
        dto.setDominio(dominio);
        return inserir(dto);
    }
}
