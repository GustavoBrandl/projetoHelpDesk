package BO;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import DAO.TicketDAO;
import DTO.ContratoDTO;
import DTO.FaturamentoDTO;
import DTO.TicketDTO;

public class FaturamentoBO {

	private TicketDAO ticketDAO = new TicketDAO();
	private ContratoBO contratoBO = new ContratoBO();

	
	public FaturamentoDTO gerarFaturamento(Integer organizacaoId, YearMonth mesFaturamento) {
		if (organizacaoId == null || organizacaoId <= 0) {
			System.out.println("Erro: ID da organização inválido");
			return null;
		}
		if (mesFaturamento == null) {
			System.out.println("Erro: Mês de faturamento inválido");
			return null;
		}


		ContratoDTO contrato = contratoBO.pesquisarAtivoOrganizacao(organizacaoId, mesFaturamento);
		if (contrato == null) {
			System.out.println("Erro: Nenhum contrato ativo para a organização no mês: " + mesFaturamento);
			return null;
		}


		List<TicketDTO> tickets = ticketDAO.pesquisarPorOrganizacaoMes(organizacaoId, mesFaturamento);
		

		double horasTrabalhadas = 0.0;
		if (tickets != null) {
			for (TicketDTO ticket : tickets) {
				Double tHoras = ticket.getTempoChamado();
				if (tHoras != null) {
					horasTrabalhadas += tHoras;
				}
			}
		}


		Double horasContratoD = contrato.getHorasContrato() != null ? contrato.getHorasContrato().doubleValue() : 0.0;
		Double precoContrato = contrato.getPrecoContrato();
		Double precoExtra = contrato.getPrecoExtra();


		Double valorBase = precoContrato * horasContratoD;


		double horasExcedentes = Math.max(0.0, horasTrabalhadas - horasContratoD);
		Double valorExtra = horasExcedentes * precoExtra;


		Double valorTotal = valorBase + valorExtra;


		FaturamentoDTO faturamento = new FaturamentoDTO();
		faturamento.setContratoId(contrato.getId());
		faturamento.setOrganizacaoId(organizacaoId);
		faturamento.setMesFaturamento(mesFaturamento.toString());
		faturamento.setHorasTrabalhadas(horasTrabalhadas);
		faturamento.setValorBase(valorBase);
		faturamento.setValorExtra(valorExtra);
		faturamento.setValorTotal(valorTotal);

		return faturamento;
	}
}


