package BO;

import java.time.YearMonth;
import java.util.List;

import DAO.ContratoDAO;
import DTO.ContratoDTO;

public class ContratoBO {
    
	private ContratoDAO dao = new ContratoDAO();

	public boolean inserir(ContratoDTO contrato) {
		// Validações
		if (contrato.getOrganizacaoId() == null || contrato.getOrganizacaoId() <= 0) {
			System.out.println("Erro: Organização inválida");
			return false;
		}
		if (contrato.getHorasContrato() == null || contrato.getHorasContrato() <= 0) {
			System.out.println("Erro: Horas de contrato inválidas");
			return false;
		}
		if (contrato.getPrecoContrato() == null || contrato.getPrecoContrato() <= 0) {
			System.out.println("Erro: Preço de contrato inválido");
			return false;
		}
		if (contrato.getPrecoExtra() == null || contrato.getPrecoExtra() < 0) {
			System.out.println("Erro: Preço extra inválido");
			return false;
		}
		if (contrato.getDataVigencia() == null) {
			System.out.println("Erro: Data de vigência inválida");
			return false;
		}
		return dao.inserir(contrato);
	}
	
	public boolean alterar(ContratoDTO contrato) {
		if (contrato.getId() == null || contrato.getId() <= 0) {
			System.out.println("Erro: ID do contrato inválido");
			return false;
		}
		return dao.alterarContrato(contrato);
	}
	
	public boolean excluir(ContratoDTO contrato) {
		if (contrato.getId() == null || contrato.getId() <= 0) {
			System.out.println("Erro: ID do contrato inválido");
			return false;
		}
		return dao.excluir(contrato);
	}

	public ContratoDTO pesquisarPorId(Integer id) {
		if (id == null || id <= 0) {
			System.out.println("Erro: ID inválido");
			return null;
		}
		return dao.pesquisarPorId(id);
	}

	public List<ContratoDTO> pesquisarPorOrganizacao(Integer organizacaoId) {
		if (organizacaoId == null || organizacaoId <= 0) {
			System.out.println("Erro: ID da organização inválido");
			return List.of();
		}
		return dao.pesquisarPorOrganizacao(organizacaoId);
	}

	public List<ContratoDTO> pesquisarTodos() {
		return dao.pesquisarTodos();
	}

	public List<ContratoDTO> pesquisarAtivos() {
		return dao.pesquisarAtivos();
	}

	public ContratoDTO pesquisarAtivoOrganizacao(Integer organizacaoId, YearMonth mes) {
		if (organizacaoId == null || organizacaoId <= 0) {
			System.out.println("Erro: ID da organização inválido");
			return null;
		}
		if (mes == null) {
			System.out.println("Erro: Mês inválido");
			return null;
		}
		return dao.pesquisarAtivoOrganizacao(organizacaoId, mes);
	}
}
