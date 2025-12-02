package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class PrioridadeController {
    private final PrioridadeBO prioridadeBO;
    
    public PrioridadeController() {
        this.prioridadeBO = new PrioridadeBO();
    }
    
    public boolean criarPrioridade(String nome, int valor, boolean padrao) {
        return prioridadeBO.criarPrioridade(nome, valor, padrao);
    }
    
    public boolean alterarPrioridade(Integer id, String nome, Integer valor) {
        return prioridadeBO.alterarPrioridade(id, nome, valor);
    }
    
    public List<PrioridadeDTO> listarPrioridades() {
        return prioridadeBO.pesquisarTodos();
    }
    
    public boolean tornarPadrao(int id) {
        return prioridadeBO.tornarPrioridadePadrao(id);
    }
    
    public boolean excluirPrioridade(PrioridadeDTO prioridade) {
        return prioridadeBO.excluir(prioridade);
    }
}