package View;

import javax.swing.*;
import java.awt.*;

public class HelpDeskUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private CadastroPanel cadastroPanel;
    private TicketsPanel ticketsPanel;
    private CategoriasPanel categoriasPanel;
    private PrioridadesPanel prioridadesPanel;

    public HelpDeskUI() {
        setTitle("Sistema Help Desk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inicializar painéis
        loginPanel = new LoginPanel(this);
        cadastroPanel = new CadastroPanel(this);
        ticketsPanel = new TicketsPanel(this);
        categoriasPanel = new CategoriasPanel(this);
        prioridadesPanel = new PrioridadesPanel(this);

        // Adicionar painéis ao mainPanel
        mainPanel.add(loginPanel, "login");
        mainPanel.add(cadastroPanel, "cadastro");
        mainPanel.add(ticketsPanel, "tickets");
        mainPanel.add(categoriasPanel, "categorias");
        mainPanel.add(prioridadesPanel, "prioridades");

        add(mainPanel);
        setVisible(true);

        // Mostrar tela de login
        mostrarLogin();
    }

    public void mostrarLogin() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HelpDeskUI());
    }
}
