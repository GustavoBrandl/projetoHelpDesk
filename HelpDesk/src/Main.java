
import java.util.ArrayList;
import java.util.List;

import BO.*;
import DTO.*;

public class Main {

	public static void main(String[] args) {
		
		DepartamentoBO departamentoBO = new DepartamentoBO();
		/*
		departamentoBO.alterarDepartamento(1, "Suporte Tecnico");
		departamentoBO.listarDepartamento();
		*/
		
		CategoriaBO categoriaBO = new CategoriaBO();
		/*
		if (categoriaBO.criarCategoria("Compra", 2)) {
	        System.out.println("Categoria criada!");
	    } else {
	        System.out.println("Erro ao criar categoria.");
	    }
	    categoriaBO.alterarCategoria(1, "Impressoras", 2); 
		categoriaBO.listarCategoria(null);
	    */
		
		PrioridadeBO prioridadeBO = new PrioridadeBO();
		/*
		prioridadeBO.criarPrioridade("Baixa", 10, false)
		prioridadeBO.alterarPrioridade(1, "Baixa", null);
		prioridadeBO.listarPrioridade();
		*/
		
		StatusBO statusBO = new StatusBO();
		/*
		statusBO.tornarStatusPadrao(1);
		statusBO.listarStatus();
		*/
		
		OrganizacaoBO organizacaoBO = new OrganizacaoBO();
		/*
		organizacaoBO.criarOrganizacao("Teste", "teste.com.br");
		organizacaoBO.alterarOrganizacao(1, "Teste", null);
		organizacaoBO.listarOrganizacao();
		*/
		
		UsuarioBO usuarioBO = new UsuarioBO();
		/*
		usuarioBO.criarUsuario("Admin", "Admin@teste.com.br", "123456", "47999141339", 1, 1); 
		usuarioBO.alterarUsuario(1, "Nome", null, null, null, 1);
		usuarioBO.ativarDesativar(1);
		usuarioBO.listarUsuario(null);
		 */
		
		ContratoBO contratoBO = new ContratoBO();
		/*
		contratoBO.criarContrato(5.0, 100.0, 200.0, 1);
		contratoBO.listarContrato(null);
		*/
		
		
	}

}
