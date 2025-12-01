package Controller;

import BO.*;
import DTO.*;
import java.util.List;

public class DepartamentoController {
    private final DepartamentoBO departamentoBO;
    
    public DepartamentoController() {
        this.departamentoBO = new DepartamentoBO();
    }
    
    public boolean criarDepartamento(String nome) {
        return departamentoBO.criarDepartamento(nome);
    }
    
    public boolean alterarDepartamento(int id, String nome) {
        return departamentoBO.alterarDepartamento(id, nome);
    }
    
    public List<DepartamentoDTO> listarDepartamentos() {
        return departamentoBO.pesquisarTodos();
    }
    
    public boolean excluirDepartamento(DepartamentoDTO departamento) {
        return departamentoBO.excluir(departamento);
    }
}