package BO;

import DTO.PrioridadeDTO;
import DAO.PrioridadeDAO;
import java.util.List;

public class PrioridadeBO {
    
	public boolean inserir(PrioridadeDTO prioridade){
		if (existe(prioridade) != true) {
		    PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
		    return prioridadesDAO.inserir(prioridade);
		} 
			return false;
	}
	
    public boolean alterar(PrioridadeDTO prioridade){
        PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
        return prioridadesDAO.alterar(prioridade);
    }

    public boolean excluir(PrioridadeDTO prioridade) {
    	PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
    	return prioridadesDAO.excluir(prioridade);
    }
    
    public boolean existe(PrioridadeDTO prioridade){
        PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
        return prioridadesDAO.existe(prioridade);
    }

    public List<PrioridadeDTO> pesquisarTodos(){
        PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
        return prioridadesDAO.pesquisarTodos();
    }
    
    public boolean criarPrioridade(String nome, int valor, boolean padrao) {

        PrioridadeDTO prioridade= new PrioridadeDTO();
        prioridade.setNome(nome);
        prioridade.setValor(valor);
        prioridade.setPadrao(padrao);

        return inserir(prioridade);
    }
    
    public void listarPrioridade() {
    	List<PrioridadeDTO> prioridades = pesquisarTodos();
    	
    	for (PrioridadeDTO prioridade : prioridades) {
    		System.out.println(prioridade);
    	}
    }
    
    public boolean alterarPrioridade(Integer id, String nome, Integer valor) {

        PrioridadeDTO prioridade = new PrioridadeDTO();
        prioridade.setId(id);

        if (nome != null) {
            prioridade.setNome(nome);
        }

        if (valor != null) {
            prioridade.setValor(valor);
        }

        PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
        return prioridadesDAO.alterarPrioridade(prioridade);
    }

    public boolean tornarPrioridadePadrao(int id) {
        PrioridadeDAO prioridadesDAO = new PrioridadeDAO();
        return prioridadesDAO.tornarPrioridadePadrao(id);
    }

}


