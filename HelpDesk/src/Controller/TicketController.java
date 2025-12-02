package Controller;

import BO.TicketBO;
import BO.CategoriaBO;
import BO.PrioridadeBO;
import DTO.TicketDTO;
import DTO.CategoriaDTO;
import DTO.PrioridadeDTO;
import DTO.StatusDTO;
import DTO.UsuarioDTO;
import Util.PermissaoUtil;
import java.util.ArrayList;
import java.util.List;

public class TicketController {
    private final TicketBO ticketBO;
    private final CategoriaBO categoriaBO;
    private final PrioridadeBO prioridadeBO;
    
    public TicketController() {
        this.ticketBO = new TicketBO();
        this.categoriaBO = new CategoriaBO();
        this.prioridadeBO = new PrioridadeBO();
    }
    
    public boolean criarTicket(String sobre, String descricao, 
                              Integer idCategoria, Integer idPrioridade, UsuarioDTO solicitante) {
        return ticketBO.abrirChamado(sobre, descricao, idCategoria, idPrioridade, solicitante);
    }
    
    public List<TicketDTO> pesquisarPorUsuario(UsuarioDTO usuario) {
        List<TicketDTO> todosTickets = ticketBO.listarTodos();
        
        if (usuario == null) return new ArrayList<>();
        
        if (PermissaoUtil.podeVerTodosTickets(usuario)) {
            return todosTickets;
        }
        
        if (PermissaoUtil.podeVerOrgTickets(usuario)) {
            List<TicketDTO> ticketsOrg = new ArrayList<>();
            for (TicketDTO ticket : todosTickets) {
                if (ticket.getSolicitante() != null && 
                    ticket.getSolicitante().getOrganizacao() != null &&
                    usuario.getOrganizacao() != null &&
                    ticket.getSolicitante().getOrganizacao().getId() == 
                    usuario.getOrganizacao().getId()) {
                    ticketsOrg.add(ticket);
                }
            }
            return ticketsOrg;
        }
        
        List<TicketDTO> meusTickets = new ArrayList<>();
        for (TicketDTO ticket : todosTickets) {
            if (ticket.getSolicitante() != null && 
                ticket.getSolicitante().getId() == usuario.getId()) {
                meusTickets.add(ticket);
            }
        }
        return meusTickets;
    }
    
    public List<TicketDTO> pesquisarTodos() {
        return ticketBO.listarTodos();
    }
    
    public TicketDTO pesquisarPorId(Integer id) {
        return ticketBO.buscarPorId(id);
    }
    
    public boolean atender(Integer idTicket, UsuarioDTO tecnico) {
        return ticketBO.atenderChamado(idTicket, tecnico);
    }
    
    public boolean alterarPrioridade(Integer idTicket, Integer idPrioridade, UsuarioDTO editor) {
        return ticketBO.alterarPrioridade(idTicket, idPrioridade, editor);
    }
    
    // Wrappers retained for Views compatibility
    public List<StatusDTO> pesquisarStatus() {
        return ticketBO.listarStatus();
    }

    public List<PrioridadeDTO> pesquisarPrioridades() {
        return prioridadeBO.pesquisarTodos();
    }
    
    public boolean finalizarTicket(Integer idTicket, UsuarioDTO tecnico) {
        return ticketBO.finalizarChamado(idTicket, tecnico, null);
    }
    
    public boolean finalizarTicket(Integer idTicket, UsuarioDTO tecnico, Double tempoEmHoras) {
        return ticketBO.finalizarChamado(idTicket, tecnico, tempoEmHoras);
    }
    
    public boolean reabrirTicket(Integer idTicket, UsuarioDTO editor) {
        return ticketBO.reabrirChamado(idTicket, editor);
    }
    
    public boolean alterarCategoriaTicket(Integer idTicket, Integer idCategoria, UsuarioDTO editor) {
        return ticketBO.alterarCategoria(idTicket, idCategoria, editor);
    }
    
    public boolean alterarStatusTicket(Integer idTicket, Integer idStatus, UsuarioDTO editor) {
        return ticketBO.alterarStatus(idTicket, idStatus, editor);
    }
}