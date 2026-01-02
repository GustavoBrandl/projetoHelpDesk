package BO;

import DTO.StatusDTO;
import DAO.StatusDAO;
import java.util.List;

public class StatusBO {
    
	public boolean inserir(StatusDTO status){
		if (existe(status) != true) {
		    StatusDAO statusDAO = new StatusDAO();
		    return statusDAO.inserir(status);
		} 
			return false;
	}
	
    public boolean alterar(StatusDTO status){
        StatusDAO statusDAO = new StatusDAO();
        return statusDAO.alterar(status);
    }

    public boolean excluir(StatusDTO status) {
    	StatusDAO statusDAO = new StatusDAO();
    	return statusDAO.excluir(status);
    }
    
    public boolean existe(StatusDTO status){
        StatusDAO statusDAO = new StatusDAO();
        return statusDAO.existe(status);
    }

    public List<StatusDTO> pesquisarTodos(){
        StatusDAO statusDAO = new StatusDAO();
        return statusDAO.pesquisarTodos();
    }
    
    public boolean criarStatus(String nome) {

        StatusDTO status = new StatusDTO();
        status.setNome(nome);

        return inserir(status);
    }

    public boolean alterarStatus(int id, String nome) {
    	StatusDTO status= new StatusDTO();
    	status.setId(id);
    	status.setNome(nome);
    	
    	return alterar(status);
    }
    
    public void listarStatus() {
    	List<StatusDTO> statuss = pesquisarTodos();
    	
    	for (StatusDTO status : statuss) {
    		System.out.println(status);
    	}
    }
    
    public boolean tornarStatusPadrao(int id) {
        StatusDAO statusDAO = new StatusDAO();
        return statusDAO.tornarStatusPadrao(id);
    }
}


