package BO;

import DTO.DepartamentoDTO;
import DTO.CategoriaDTO;
import DAO.CategoriaDAO;
import java.util.List;

public class CategoriaBO {
    
	private CategoriaDAO dao = new CategoriaDAO();

	public boolean inserir(CategoriaDTO categoria) {
		if (!existe(categoria)) {
		    return dao.inserir(categoria);
		} 
		return false;
	}
	
    public boolean excluir(CategoriaDTO categoria) {
    	return dao.excluir(categoria);
    }
    
    public boolean existe(CategoriaDTO categoria) {
        return dao.existe(categoria);
    }

    public List<CategoriaDTO> pesquisarTodos() {
        return dao.pesquisarTodos();
    }
	
	public boolean alterar(CategoriaDTO categoria) {
		return dao.alterarCategoria(categoria);
	}
}


