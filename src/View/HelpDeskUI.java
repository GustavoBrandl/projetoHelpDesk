package View;

import javax.swing.*;
import java.awt.*;
import Controller.HelpDeskController;

public class HelpDeskUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private CadastroPanel cadastroPanel;
    private TicketsPanel ticketsPanel;
    private CategoriasPanel categoriasPanel;
    private PrioridadesPanel prioridadesPanel;
    private DepartamentoView departamentoView;
    private UsuarioGerenciamentoPanel usuarioGerenciamentoPanel;
    private HelpDeskController controller;
    private MenuPrincipal menuPrincipal;
    
    public HelpDeskUI() {
        controller = HelpDeskController.getInstance();
        
        setTitle("Sistema Help Desk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        cadastroPanel = new CadastroPanel(this);
        ticketsPanel = new TicketsPanel(this);
        categoriasPanel = new CategoriasPanel(this);
        prioridadesPanel = new PrioridadesPanel(this);
        departamentoView = new DepartamentoView(this);
        usuarioGerenciamentoPanel = new UsuarioGerenciamentoPanel(this);

        mainPanel.add(loginPanel, "login");
        mainPanel.add(cadastroPanel, "cadastro");
        mainPanel.add(ticketsPanel, "tickets");
        mainPanel.add(categoriasPanel, "categorias");
        mainPanel.add(prioridadesPanel, "prioridades");
        mainPanel.add(departamentoView, "departamentos");
        mainPanel.add(usuarioGerenciamentoPanel, "usuarios");

        add(mainPanel);
        setVisible(true);

        mostrarLogin();
    }

    public HelpDeskController getController() {
        return controller;
    }

    public void atualizarMenu() {
        if (menuPrincipal != null) {
            setJMenuBar(null);
        }
        menuPrincipal = new MenuPrincipal(controller, this);
        setJMenuBar(menuPrincipal);
        revalidate();
        repaint();
    }

    public void usuarioLogado() {
        atualizarMenu();
        mostrarTickets();
    }

    public void mostrarLogin() {
        if (menuPrincipal != null) {
            setJMenuBar(null);
            menuPrincipal = null;
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HelpDeskUI());
    }
}