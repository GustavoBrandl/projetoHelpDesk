package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BO.UsuarioBO;

public class CadastroPanel extends JPanel {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField telefoneField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;
    private UsuarioBO usuarioBO;

    public CadastroPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        this.usuarioBO = new UsuarioBO();
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("CRIAR NOVA CONTA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Usuário:");
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Senha:");
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel telefoneLabel = new JLabel("Telefone:");
        add(telefoneLabel, gbc);
        gbc.gridx = 1;
        telefoneField = new JTextField(20);
        add(telefoneField, gbc);

        // Botão Cadastrar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        cadastrarButton = new JButton("CADASTRAR");
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastrarButton.setBackground(new Color(76, 175, 80));
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setFocusPainted(false);
        add(cadastrarButton, gbc);

        // Botão Voltar
        gbc.gridy = 6;
        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 14));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        add(voltarButton, gbc);

        // Ações dos botões
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                mainFrame.mostrarLogin();
            }
        });
    }

    private void fazerCadastro() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String telefone = telefoneField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Criar usuário tipo CLIENTE (tipo 3)
            if (usuarioBO.criarUsuario(username, email, password, telefone, 3, 1)) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!\nVocê será redirecionado para login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                mainFrame.mostrarLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao realizar cadastro. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        telefoneField.setText("");
    }
}
