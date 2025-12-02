package Controller;

import DTO.UsuarioDTO;

public class HelpDeskController {
    private static HelpDeskController instance;
    private UsuarioController usuarioController;
    private TicketController ticketController;
    private CategoriaController categoriaController;
    private PrioridadeController prioridadeController;
    private StatusController statusController;
    private DepartamentoController departamentoController;
    private ContratoController contratoController;
    private OrganizacaoController organizacaoController;
    private UsuarioDTO usuarioLogado;
    
    private HelpDeskController() {
        this.usuarioController = new UsuarioController();
        this.ticketController = new TicketController();
        this.categoriaController = new CategoriaController();
        this.prioridadeController = new PrioridadeController();
        this.statusController = new StatusController();
        this.departamentoController = new DepartamentoController();
        this.contratoController = new ContratoController();
        this.organizacaoController = new OrganizacaoController();
    }
    
    public static HelpDeskController getInstance() {
        if (instance == null) {
            instance = new HelpDeskController();
        }
        return instance;
    }
    
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }
    
    public TicketController getTicketController() {
        return ticketController;
    }
    
    public CategoriaController getCategoriaController() {
        return categoriaController;
    }
    
    public PrioridadeController getPrioridadeController() {
        return prioridadeController;
    }
    
    public StatusController getStatusController() {
        return statusController;
    }
    
    public DepartamentoController getDepartamentoController() {
        return departamentoController;
    }
    
    public ContratoController getContratoController() {
        return contratoController;
    }
    
    public OrganizacaoController getOrganizacaoController() {
        return organizacaoController;
    }
    
    public void setUsuarioLogado(UsuarioDTO usuario) {
        this.usuarioLogado = usuario;
    }
    
    public UsuarioDTO getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }
    
    public void logout() {
        this.usuarioLogado = null;
    }
}