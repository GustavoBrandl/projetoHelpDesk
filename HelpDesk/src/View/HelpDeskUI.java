package View;

import javax.swing.*;
import java.awt.*;
import Controller.HelpDeskController;
import DTO.UsuarioDTO;

public class HelpDeskUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private HelpDeskController controller;
    private UsuarioDTO usuarioLogado;
    
    public HelpDeskUI() {
        controller = HelpDeskController.getInstance();
        
        setTitle("Sistema Help Desk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "login");
        mainPanel.add(new CadastroPanel(this), "cadastro");
        mainPanel.add(new TicketsPanel(this), "tickets");
        mainPanel.add(new AtendimentoPanel(this), "atendimento");
        mainPanel.add(new ChamadosFinalizadosPanel(this), "finalizados");
        mainPanel.add(new CategoriasPanel(this), "categorias");
        mainPanel.add(new PrioridadesPanel(this), "prioridades");
        mainPanel.add(new DepartamentoView(this), "departamentos");
        mainPanel.add(new UsuarioGerenciamentoPanel(this), "usuarios");
        mainPanel.add(new OrganizacaoGerenciamentoPanel(this), "organizacoes");
        mainPanel.add(new StatusPanel(this), "status");
        mainPanel.add(new FaturamentoPanel(this), "faturamento");

        add(mainPanel);
        
        mostrarLogin();
        setVisible(true);
    }

    public HelpDeskController getController() {
        return controller;
    }

    public void usuarioLogado() {
        setJMenuBar(null);
        JMenuBar menuBar = new MenuPrincipal(controller, this);
        setJMenuBar(menuBar);
        revalidate();
        repaint();
        cardLayout.show(mainPanel, "tickets");
    }

    public void mostrarLogin() {
        setJMenuBar(null);
        revalidate();
        repaint();
        cardLayout.show(mainPanel, "login");
    }

    public void mostrarCadastro() {
        cardLayout.show(mainPanel, "cadastro");
    }

    public void mostrarTickets() {
        TicketsPanel panel = (TicketsPanel) mainPanel.getComponent(2);
        if (panel != null && panel instanceof TicketsPanel) {
            ((TicketsPanel) panel).atualizar();
        }
        cardLayout.show(mainPanel, "tickets");
    }

    public void mostrarCategorias() {
        CategoriasPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof CategoriasPanel) {
                panel = (CategoriasPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "categorias");
    }

    public void mostrarPrioridades() {
        PrioridadesPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof PrioridadesPanel) {
                panel = (PrioridadesPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "prioridades");
    }

    public void mostrarDepartamentos() {
        DepartamentoView panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof DepartamentoView) {
                panel = (DepartamentoView) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "departamentos");
    }

    public void mostrarUsuarios() {
        UsuarioGerenciamentoPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof UsuarioGerenciamentoPanel) {
                panel = (UsuarioGerenciamentoPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "usuarios");
    }

    public void mostrarOrganizacoes() {
        OrganizacaoGerenciamentoPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof OrganizacaoGerenciamentoPanel) {
                panel = (OrganizacaoGerenciamentoPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "organizacoes");
    }
    
    public void mostrarStatus() {
        StatusPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof StatusPanel) {
                panel = (StatusPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "status");
    }
    
    public void mostrarFaturamento() {
        FaturamentoPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof FaturamentoPanel) {
                panel = (FaturamentoPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "faturamento");
    }
    
    public void mostrarAtendimento() {
        AtendimentoPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof AtendimentoPanel) {
                panel = (AtendimentoPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "atendimento");
    }
    
    public void mostrarFinalizados() {
        ChamadosFinalizadosPanel panel = null;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (mainPanel.getComponent(i) instanceof ChamadosFinalizadosPanel) {
                panel = (ChamadosFinalizadosPanel) mainPanel.getComponent(i);
                break;
            }
        }
        if (panel != null) {
            panel.atualizar();
        }
        cardLayout.show(mainPanel, "finalizados");
    }

    public void setUsuarioLogado(UsuarioDTO usuario) {
        this.usuarioLogado = usuario;
    }

    public UsuarioDTO getUsuarioLogado() {
        return this.usuarioLogado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HelpDeskUI());
    }
}
