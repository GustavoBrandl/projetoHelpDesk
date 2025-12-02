package BO;

import DTO.DepartamentoDTO;
import DAO.DepartamentoDAO;
import java.util.List;

public class DepartamentoBO {
    
	public boolean inserir(DepartamentoDTO departamento){
		if (existe(departamento) != true) {
		    DepartamentoDAO departamentosDAO = new DepartamentoDAO();
		    return departamentosDAO.inserir(departamento);
		} 
			return false;
	}
	
    public boolean alterar(DepartamentoDTO departamento){
        DepartamentoDAO departamentosDAO = new DepartamentoDAO();
        return departamentosDAO.alterar(departamento);
    }

    public boolean excluir(DepartamentoDTO departamento) {
    	DepartamentoDAO departamentosDAO = new DepartamentoDAO();
    	return departamentosDAO.excluir(departamento);
    }
    
    public boolean existe(DepartamentoDTO departamento){
        DepartamentoDAO departamentosDAO = new DepartamentoDAO();
        return departamentosDAO.existe(departamento);
    }

    public List<DepartamentoDTO> pesquisarTodos(){
        DepartamentoDAO departamentosDAO = new DepartamentoDAO();
        return departamentosDAO.pesquisarTodos();
    }
    
    public boolean criarDepartamento(String nome) {

        DepartamentoDTO departamento = new DepartamentoDTO();
        departamento.setNome(nome);

        return inserir(departamento);
    }

    public boolean alterarDepartamento(int id, String nome) {
    	DepartamentoDTO departamento = new DepartamentoDTO();
    	departamento.setId(id);
    	departamento.setNome(nome);
    	
    	return alterar(departamento);
    }
    
    public void listarDepartamento() {
    	List<DepartamentoDTO> departamentos = pesquisarTodos();
    	
    	for (DepartamentoDTO departamento : departamentos) {
    		System.out.println(departamento);
    	}
    }
}
