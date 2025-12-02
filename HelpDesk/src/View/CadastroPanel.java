package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.OrganizacaoDTO;

public class CadastroPanel extends JPanel {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField telefoneField;
    private JComboBox<OrganizacaoDTO> comboOrganizacao;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;

    public CadastroPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("CRIAR NOVA CONTA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Usuário:");
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Senha:");
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel telefoneLabel = new JLabel("Telefone:");
        add(telefoneLabel, gbc);
        gbc.gridx = 1;
        telefoneField = new JTextField(20);
        add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel organizacaoLabel = new JLabel("Organização:");
        add(organizacaoLabel, gbc);
        gbc.gridx = 1;
        comboOrganizacao = new JComboBox<>();
        carregarOrganizacoes();
        add(comboOrganizacao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        cadastrarButton = new JButton("CADASTRAR");
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastrarButton.setBackground(new Color(76, 175, 80));
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setFocusPainted(false);
        add(cadastrarButton, gbc);

        gbc.gridy = 7;
        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 14));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        add(voltarButton, gbc);

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

    private void carregarOrganizacoes() {
        try {
            var controller = mainFrame.getController();
            var organizacaoController = controller.getOrganizacaoController();
            List<OrganizacaoDTO> organizacoes = organizacaoController.pesquisarTodos();
            
            if (organizacoes != null && !organizacoes.isEmpty()) {
                for (OrganizacaoDTO org : organizacoes) {
                    comboOrganizacao.addItem(org);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fazerCadastro() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String telefone = telefoneField.getText().trim();
        OrganizacaoDTO organizacao = (OrganizacaoDTO) comboOrganizacao.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (organizacao == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma organização.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            var controller = mainFrame.getController();
            var usuarioController = controller.getUsuarioController();

            // Cadastrar como USUARIO (tipo 1)
            boolean sucesso = usuarioController.cadastrarUsuario(
                username, email, password, telefone, 1, organizacao.getId()
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Cadastro realizado!\n\n" +
                    "Usuário: " + username + "\n" +
                    "Email: " + email + "\n" +
                    "Organização: " + organizacao.getNome() + "\n\n" +
                    "Use este email para fazer login.", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                mainFrame.mostrarLogin();
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Erro ao cadastrar.\nEmail já existe?", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Erro: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limparCampos() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        telefoneField.setText("");
    }
}