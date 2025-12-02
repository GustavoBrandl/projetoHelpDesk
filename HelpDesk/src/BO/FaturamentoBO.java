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

	/**
	 * Gera faturamento para uma organização em um determinado mês.
	 * Cálculo:
	 * - horasTrabalhadas = soma das horas de todos os tickets do mês
	 * - valorBase = min(horasTrabalhadas, horasContrato) * precoContrato / horasContrato
	 * - horasExcedentes = max(0, horasTrabalhadas - horasContrato)
	 * - valorExtra = horasExcedentes * precoExtra
	 * - valorTotal = valorBase + valorExtra
	 * 
	 * @param organizacaoId ID da organização
	 * @param mesFaturamento Mês para o qual gerar faturamento (YYYY-MM)
	 * @return FaturamentoDTO com valores calculados, ou null se não houver contrato ativo
	 */
	public FaturamentoDTO gerarFaturamento(Integer organizacaoId, YearMonth mesFaturamento) {
		if (organizacaoId == null || organizacaoId <= 0) {
			System.out.println("Erro: ID da organização inválido");
			return null;
		}
		if (mesFaturamento == null) {
			System.out.println("Erro: Mês de faturamento inválido");
			return null;
		}

		// Buscar contrato ativo para a organização no mês
		ContratoDTO contrato = contratoBO.pesquisarAtivoOrganizacao(organizacaoId, mesFaturamento);
		if (contrato == null) {
			System.out.println("Erro: Nenhum contrato ativo para a organização no mês: " + mesFaturamento);
			return null;
		}

		// Buscar todos os tickets da organização no mês
		List<TicketDTO> tickets = ticketDAO.pesquisarPorOrganizacaoMes(organizacaoId, mesFaturamento);
		
		// Calcular horas trabalhadas (soma de tempoChamado, que é Double em horas)
		double horasTrabalhadas = 0.0;
		if (tickets != null) {
			for (TicketDTO ticket : tickets) {
				Double tHoras = ticket.getTempoChamado();
				if (tHoras != null) {
					horasTrabalhadas += tHoras;
				}
			}
		}

		// Calcular valores
		Double horasContratoD = contrato.getHorasContrato() != null ? contrato.getHorasContrato().doubleValue() : 0.0;
		Double precoContrato = contrato.getPrecoContrato();
		Double precoExtra = contrato.getPrecoExtra();

		// Valor base = preço contrato × horas contrato (valor fixo do contrato)
		Double valorBase = precoContrato * horasContratoD;

		// Valor extra: horas excedentes * preço extra (apenas se houver horas além do contrato)
		double horasExcedentes = Math.max(0.0, horasTrabalhadas - horasContratoD);
		Double valorExtra = horasExcedentes * precoExtra;

		// Valor total
		Double valorTotal = valorBase + valorExtra;

		// Montar objeto de faturamento
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
