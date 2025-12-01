package BO;

import DTO.OrganizacaoDTO;
import DTO.ContratoDTO;
import DAO.ContratoDAO;
import java.util.List;

public class ContratoBO {
    
	public boolean inserir(ContratoDTO contrato){
	    ContratoDAO contratosDAO = new ContratoDAO();
	    return contratosDAO.inserir(contrato);
	}
	
    public boolean excluir(ContratoDTO contrato) {
    	ContratoDAO contratosDAO = new ContratoDAO();
    	return contratosDAO.excluir(contrato);
    }

    public List<ContratoDTO> pesquisarTodos(){
        ContratoDAO contratosDAO = new ContratoDAO();
        return contratosDAO.pesquisarTodos();
    }
    
    public boolean criarContrato(Double horasContrato, Double precoContrato, Double precoExtra, Integer idOrganizacao) {

        OrganizacaoDTO organizacao = new OrganizacaoDTO();
        organizacao.setId(idOrganizacao);

        ContratoDTO contrato = new ContratoDTO();
        contrato.setHorasContrato(horasContrato);
        contrato.setPrecoContrato(precoContrato);
        contrato.setPrecoExtra(precoExtra);
        contrato.setOrganizacao(organizacao);

        return inserir(contrato);
    }
    
    public boolean alterarContrato(Integer id, Double horasContrato, Double precoContrato, Double precoExtra, Integer idOrganizacao) {

        ContratoDTO contrato= new ContratoDTO();
        contrato.setId(id);

        if (horasContrato != null) {
            contrato.setHorasContrato(horasContrato);
        }
        
        if(precoContrato != null) {
        	contrato.setPrecoContrato(precoContrato);
        }
        
        if(precoExtra != null) {
        	contrato.setPrecoExtra(precoExtra);
        }

        if (idOrganizacao != null) {
            OrganizacaoDTO org = new OrganizacaoDTO();
            org.setId(idOrganizacao);
            contrato.setOrganizacao(org);
        }

        ContratoDAO contratosDAO = new ContratoDAO();
        return contratosDAO.alterarCategoria(contrato);
    }
    
    public void listarContrato(Integer id) {
    	List<ContratoDTO> contratos = pesquisarTodos();
    	
    	if (id == null) {
	    	for (ContratoDTO contrato : contratos) {
	    		System.out.println(contrato);
	    	}
    	} else {
    		for (ContratoDTO contrato : contratos) {
    			if(contrato.getOrganizacao().getId() == id) {
    				System.out.println(contrato);
    			}
    		}
    	}
    }

}
