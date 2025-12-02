package BO;

import DTO.DepartamentoDTO;
import DAO.DepartamentoDAO;
import java.util.List;

public class DepartamentoBO {
    
	private DepartamentoDAO dao = new DepartamentoDAO();

	public boolean inserir(DepartamentoDTO departamento) {
		if (!existe(departamento)) {
		    return dao.inserir(departamento);
		} 
		return false;
	}
	
    public boolean alterar(DepartamentoDTO departamento) {
        return dao.alterar(departamento);
    }

    public boolean excluir(DepartamentoDTO departamento) {
    	return dao.excluir(departamento);
    }
    
    public boolean existe(DepartamentoDTO departamento) {
        return dao.existe(departamento);
    }

    public List<DepartamentoDTO> pesquisarTodos() {
        return dao.pesquisarTodos();
    }
}
