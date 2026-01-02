package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class StatusController {
    private final StatusBO statusBO;
    
    public StatusController() {
        this.statusBO = new StatusBO();
    }
    
    public boolean criarStatus(String nome) {
        return statusBO.criarStatus(nome);
    }
    
    public boolean alterarStatus(int id, String nome) {
        return statusBO.alterarStatus(id, nome);
    }
    
    public List<StatusDTO> listarStatus() {
        return statusBO.pesquisarTodos();
    }
    
    public boolean tornarPadrao(int id) {
        return statusBO.tornarStatusPadrao(id);
    }
    
    public boolean excluirStatus(StatusDTO status) {
        return statusBO.excluir(status);
    }
}

