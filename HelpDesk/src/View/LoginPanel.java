package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.HelpDeskController;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cadastroButton;
    private HelpDeskUI mainFrame;

    public LoginPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SISTEMA HELP DESK");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Usuário:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginButton = new JButton("ENTRAR");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 150, 136));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        add(loginButton, gbc);

        gbc.gridy = 4;
        cadastroButton = new JButton("CRIAR CONTA");
        cadastroButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastroButton.setBackground(new Color(33, 150, 243));
        cadastroButton.setForeground(Color.WHITE);
        cadastroButton.setFocusPainted(false);
        add(cadastroButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarCadastro();
            }
        });
        
        System.out.println("✅ LoginPanel criado!");
    }

    private void fazerLogin() {
        System.out.println("🔐 Tentando login...");
        
        String email = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            var controller = mainFrame.getController();
            var usuarioController = controller.getUsuarioController();
            var usuarioLogado = usuarioController.autenticar(email, password);
            
            System.out.println("🔍 Resultado autenticação: " + (usuarioLogado != null ? "SUCESSO" : "FALHA"));
            
            if (usuarioLogado != null) {
                System.out.println("✅ Usuário autenticado: " + usuarioLogado.getUsername());
                System.out.println("📊 Tipo do usuário: " + usuarioLogado.getTipo());
                System.out.println("🔢 ID do tipo: " + usuarioLogado.getTipo().getId());
                
                controller.setUsuarioLogado(usuarioLogado);
                mainFrame.setUsuarioLogado(usuarioLogado);

                
                JOptionPane.showMessageDialog(this, 
                    "Login realizado com sucesso!\nBem-vindo, " + usuarioLogado.getUsername(), 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                
                System.out.println("🔄 Chamando mainFrame.usuarioLogado()...");
                mainFrame.usuarioLogado();
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Email ou senha incorretos!\nOu usuário inativo.", 
                    "Falha no Login", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.out.println("❌ ERRO no login: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        usernameField.setText("");
        passwordField.setText("");
    }
}