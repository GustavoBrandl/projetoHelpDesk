package View;

import javax.swing.*;
import java.awt.*;
import Controller.HelpDeskController;

public class HelpDeskUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private HelpDeskController controller;
    
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

        add(mainPanel);
        
        mostrarLogin();
        setVisible(true);
    }

    public HelpDeskController getController() {
        return controller;
    }

    public void usuarioLogado() {
        System.out.println("=== usuarioLogado() CHAMADO ===");
        System.out.println("Controller: " + controller);
        System.out.println("Usuário logado: " + controller.getUsuarioLogado());
        
        setJMenuBar(null);
        
        JMenuBar menuBar = new MenuPrincipal(controller, this);
        setJMenuBar(menuBar);
        
        revalidate();
        repaint();
        
        cardLayout.show(mainPanel, "tickets");
        
        System.out.println("✅ Menu configurado!");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 INICIANDO APLICAÇÃO...");
            new HelpDeskUI();
        });
    }
}