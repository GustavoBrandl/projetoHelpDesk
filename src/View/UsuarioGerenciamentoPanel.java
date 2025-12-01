package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.UsuarioDTO;
import DTO.OrganizacaoDTO;
import Controller.HelpDeskController;
import Util.PermissaoUtil;
import ENUM.TipoUsuario;

public class UsuarioGerenciamentoPanel extends JPanel {
    private JTable usuariosTable;
    private DefaultTableModel tableModel;
    private JButton novoButton;
    private JButton editarButton;
    private JButton ativarButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;

    public UsuarioGerenciamentoPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR USUÁRIOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Usuário", "Email", "Tipo", "Organização", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usuariosTable = new JTable(tableModel);
        usuariosTable.setFont(new Font("Arial", Font.PLAIN, 11));
        usuariosTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novoButton = new JButton("NOVO USUÁRIO");
        novoButton.setFont(new Font("Arial", Font.BOLD, 12));
        novoButton.setBackground(new Color(76, 175, 80));
        novoButton.setForeground(Color.WHITE);
        novoButton.setFocusPainted(false);
        bottomPanel.add(novoButton);

        editarButton = new JButton("EDITAR");
        editarButton.setFont(new Font("Arial", Font.BOLD, 12));
        editarButton.setBackground(new Color(33, 150, 243));
        editarButton.setForeground(Color.WHITE);
        editarButton.setFocusPainted(false);
        bottomPanel.add(editarButton);

        ativarButton = new JButton("ATIVAR/DESATIVAR");
        ativarButton.setFont(new Font("Arial", Font.BOLD, 12));
        ativarButton.setBackground(new Color(255, 152, 0));
        ativarButton.setForeground(Color.WHITE);
        ativarButton.setFocusPainted(false);
        bottomPanel.add(ativarButton);

        voltarButton = new JButton("VOLTAR");
        voltarButton.setFont(new Font("Arial", Font.BOLD, 12));
        voltarButton.setBackground(new Color(158, 158, 158));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFocusPainted(false);
        bottomPanel.add(voltarButton);

        add(bottomPanel, BorderLayout.SOUTH);

        novoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoUsuario();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuarioSelecionado();
            }
        });

        ativarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ativarDesativarUsuario();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarUsuarios();
        atualizarBotoes();
    }

    private void carregarUsuarios() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null) {
                tableModel.addRow(new Object[]{"", "Usuário não logado", "", "", "", ""});
                return;
            }
            
            var usuarioController = controller.getUsuarioController();
            List<UsuarioDTO> usuarios = usuarioController.listarTodosUsuarios();
            
            if (usuarios != null && !usuarios.isEmpty()) {
                for (UsuarioDTO usuario : usuarios) {
                    if (PermissaoUtil.podeGerenciarUsuarios(usuarioLogado, usuario)) {
                        String tipoDesc = PermissaoUtil.getDescricaoTipo(usuario.getTipo());
                        String orgNome = usuario.getOrganizacao() != null ? 
                            usuario.getOrganizacao().getNome() : "N/A";
                        String status = usuario.isAtivo() ? "Ativo" : "Inativo";
                        
                        tableModel.addRow(new Object[]{
                            usuario.getId(),
                            usuario.getUsername(),
                            usuario.getEmail(),
                            tipoDesc,
                            orgNome,
                            status
                        });
                    }
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhum usuário encontrado", "", "", "", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar usuários: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        boolean podeGerenciar = false;
        if (usuarioLogado != null) {
            // ADMIN e TECNICO podem gerenciar usuários
            podeGerenciar = usuarioLogado.getTipo() == TipoUsuario.ADMIN || 
                           usuarioLogado.getTipo() == TipoUsuario.TECNICO;
        }
        
        int linhaSelecionada = usuariosTable.getSelectedRow();
        
        novoButton.setEnabled(podeGerenciar);
        editarButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
        ativarButton.setEnabled(podeGerenciar && linhaSelecionada >= 0);
    }

    private Integer getUsuarioIdSelecionado() {
        int linha = usuariosTable.getSelectedRow();
        if (linha >= 0) {
            Object idObj = tableModel.getValueAt(linha, 0);
            if (idObj instanceof Integer) return (Integer) idObj;
            if (idObj instanceof String) {
                try {
                    return Integer.parseInt((String) idObj);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private UsuarioDTO getUsuarioSelecionado() {
        Integer usuarioId = getUsuarioIdSelecionado();
        if (usuarioId == null) return null;
        
        try {
            var controller = mainFrame.getController();
            var usuarioController = controller.getUsuarioController();
            List<UsuarioDTO> usuarios = usuarioController.listarTodosUsuarios();
            
            for (UsuarioDTO usuario : usuarios) {
                if (usuario.getId() == usuarioId) {
                    return usuario;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void criarNovoUsuario() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (usuarioLogado == null || 
            !(usuarioLogado.getTipo() == TipoUsuario.ADMIN || 
              usuarioLogado.getTipo() == TipoUsuario.TECNICO)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para criar usuários.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField telefoneField = new JTextField();
        JComboBox<TipoUsuario> comboTipo = new JComboBox<>(TipoUsuario.values());
        JComboBox<OrganizacaoDTO> comboOrganizacao = new JComboBox<>();
        
        var organizacaoController = controller.getOrganizacaoController();
        List<OrganizacaoDTO> organizacoes = organizacaoController.listarOrganizacoes();
        
        if (organizacoes != null && !organizacoes.isEmpty()) {
            for (OrganizacaoDTO org : organizacoes) {
                comboOrganizacao.addItem(org);
            }
        }
        
        panel.add(new JLabel("Usuário:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(passwordField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Organização:"));
        panel.add(comboOrganizacao);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Novo Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String telefone = telefoneField.getText().trim();
            TipoUsuario tipo = (TipoUsuario) comboTipo.getSelectedItem();
            OrganizacaoDTO organizacao = (OrganizacaoDTO) comboOrganizacao.getSelectedItem();
            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Preencha todos os campos obrigatórios.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (organizacao == null) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione uma organização.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var usuarioController = controller.getUsuarioController();
                boolean sucesso = usuarioController.cadastrarUsuario(
                    username, email, password, telefone, tipo.getId(), organizacao.getId()
                );
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuário criado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao criar usuário. Email já existe?", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void editarUsuarioSelecionado() {
        UsuarioDTO usuario = getUsuarioSelecionado();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um usuário para editar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarUsuarios(usuarioLogado, usuario)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para editar este usuário.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Funcionalidade de edição em desenvolvimento.", 
            "Em desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ativarDesativarUsuario() {
        UsuarioDTO usuario = getUsuarioSelecionado();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um usuário para ativar/desativar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (!PermissaoUtil.podeGerenciarUsuarios(usuarioLogado, usuario)) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para alterar este usuário.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String acao = usuario.isAtivo() ? "desativar" : "ativar";
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja " + acao + " o usuário " + usuario.getUsername() + "?",
            "Confirmar " + (usuario.isAtivo() ? "Desativação" : "Ativação"),
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var usuarioController = controller.getUsuarioController();
                boolean sucesso = usuarioController.ativarDesativarUsuario(usuario.getId());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuário " + (usuario.isAtivo() ? "desativado" : "ativado") + " com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao alterar status do usuário.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}