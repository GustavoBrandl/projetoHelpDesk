package BO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import DAO.*;
import DTO.*;
import Util.PermissaoUtil;

public class TicketBO {
    private final TicketDAO ticketDAO = new TicketDAO();

    public boolean abrirChamado(String sobre, String descricao, Integer idCategoria, 
                               Integer idPrioridade, UsuarioDTO solicitante) {
        
        if (sobre == null || descricao == null || idCategoria == null || idPrioridade == null) {
            return false;
        }

        if (!PermissaoUtil.podeCriarTicket(solicitante)) {
            return false;
        }

        CategoriaDTO cat = new CategoriaDTO();
        cat.setId(idCategoria);

        PrioridadeDTO prioridade = new PrioridadeDTO();
        prioridade.setId(idPrioridade);

        StatusDTO aberto = new StatusDTO();
        aberto.setId(1);

        TicketDTO ticket = new TicketDTO();
        ticket.setSobre(sobre);
        ticket.setDescricao(descricao);
        ticket.setCategoria(cat);
        ticket.setSolicitante(solicitante);
        ticket.setPrioridade(prioridade);
        ticket.setStatus(aberto);

        return ticketDAO.inserir(ticket);
    }

    public boolean atenderChamado(Integer idTicket, UsuarioDTO tecnico) {
        if (!PermissaoUtil.podeAtenderTickets(tecnico)) {
            return false;
        }

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        StatusDTO emAtendimento = new StatusDTO();
        emAtendimento.setId(2);

        t.setStatus(emAtendimento);
        t.setAtendente(tecnico);

        return ticketDAO.alterar(t);
    }

    public boolean alterarCategoria(Integer idTicket, Integer idCategoria, UsuarioDTO editor) {
        if (!PermissaoUtil.podeGerenciarSistema(editor)) {
            return false;
        }

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        CategoriaDTO c = new CategoriaDTO();
        c.setId(idCategoria);

        t.setCategoria(c);

        return ticketDAO.alterar(t);
    }

    public boolean alterarPrioridade(Integer idTicket, Integer idPrioridade, UsuarioDTO editor) {
        if (!PermissaoUtil.podeGerenciarSistema(editor)) {
            return false;
        }

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        PrioridadeDTO p = new PrioridadeDTO();
        p.setId(idPrioridade);

        t.setPrioridade(p);

        return ticketDAO.alterar(t);
    }

    public boolean alterarStatus(Integer idTicket, Integer idStatus, UsuarioDTO editor) {
        if (!PermissaoUtil.podeGerenciarSistema(editor)) {
            return false;
        }

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        StatusDTO s = new StatusDTO();
        s.setId(idStatus);

        t.setStatus(s);

        return ticketDAO.alterar(t);
    }

    public boolean finalizarChamado(Integer idTicket, UsuarioDTO tecnico) {
        if (!PermissaoUtil.podeAtenderTickets(tecnico)) {
            return false;
        }

        LocalDateTime agora = LocalDateTime.now();

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        StatusDTO finalizado = new StatusDTO();
        finalizado.setId(3);

        t.setStatus(finalizado);
        t.setDataHoraFinalizacao(agora);

        TicketDTO chamado = buscarPorId(idTicket);
        double horas = ChronoUnit.MINUTES.between(chamado.getDataHoraAbertura(), agora) / 60.0;
        t.setTempoChamado(horas);

        boolean ok = ticketDAO.alterar(t);
        if (!ok) return false;

        CategoriaDAO catDAO = new CategoriaDAO();
        PrioridadeDAO priDAO = new PrioridadeDAO();

        catDAO.incrementarNumeroTickets(chamado.getCategoria().getId());
        priDAO.incrementarNumeroTickets(chamado.getPrioridade().getId());

        return true;
    }

    public boolean reabrirChamado(Integer idTicket, UsuarioDTO editor) {
        if (!PermissaoUtil.podeGerenciarSistema(editor)) {
            return false;
        }

        TicketDTO t = new TicketDTO();
        t.setId(idTicket);

        StatusDTO reaberto = new StatusDTO();
        reaberto.setId(4);

        t.setStatus(reaberto);
        t.setDataHoraFinalizacao(null);
        t.setTempoChamado(null);

        return ticketDAO.alterar(t);
    }

    public List<TicketDTO> listarTodos() {
        return ticketDAO.pesquisarTodos();
    }

    public TicketDTO buscarPorId(Integer id) {
        for (TicketDTO t : ticketDAO.pesquisarTodos()) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}