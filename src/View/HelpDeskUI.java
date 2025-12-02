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
        mainPanel.add(new CategoriasPanel(this), "categorias");
        mainPanel.add(new PrioridadesPanel(this), "prioridades");
        mainPanel.add(new DepartamentoView(this), "departamentos");
        mainPanel.add(new UsuarioGerenciamentoPanel(this), "usuarios");
        mainPanel.add(new StatusPanel(this), "status");

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
        cardLayout.show(mainPanel, "tickets");
    }

    public void mostrarCategorias() {
        cardLayout.show(mainPanel, "categorias");
    }

    public void mostrarPrioridades() {
        cardLayout.show(mainPanel, "prioridades");
    }

    public void mostrarDepartamentos() {
        cardLayout.show(mainPanel, "departamentos");
    }

    public void mostrarUsuarios() {
        cardLayout.show(mainPanel, "usuarios");
    }
    
    public void mostrarStatus() {
        cardLayout.show(mainPanel, "status");
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
