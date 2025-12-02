package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DTO.OrganizacaoDTO;
import ENUM.TipoUsuario;

public class OrganizacaoGerenciamentoPanel extends JPanel {
    private JTable organizacoesTable;
    private DefaultTableModel tableModel;
    private JButton novoButton;
    private JButton editarButton;
    private JButton deletarButton;
    private JButton voltarButton;
    private HelpDeskUI mainFrame;
    private JButton contratoButton;

    public OrganizacaoGerenciamentoPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 150, 136));
        JLabel titleLabel = new JLabel("GERENCIAR ORGANIZAÇÕES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Dominio"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        organizacoesTable = new JTable(tableModel);
        organizacoesTable.setFont(new Font("Arial", Font.PLAIN, 11));
        organizacoesTable.setRowHeight(25);
        organizacoesTable.getSelectionModel().addListSelectionListener(e -> atualizarBotoes());
        JScrollPane scrollPane = new JScrollPane(organizacoesTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        novoButton = new JButton("NOVA ORGANIZAÇÃO");
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

        deletarButton = new JButton("DELETAR");
        deletarButton.setFont(new Font("Arial", Font.BOLD, 12));
        deletarButton.setBackground(new Color(244, 67, 54));
        deletarButton.setForeground(Color.WHITE);
        deletarButton.setFocusPainted(false);
        bottomPanel.add(deletarButton);
        
        contratoButton = new JButton("CONTRATO");
        contratoButton.setFont(new Font("Arial", Font.BOLD, 12));
        contratoButton.setBackground(new Color(255, 193, 7));
        contratoButton.setForeground(Color.WHITE);
        contratoButton.setFocusPainted(false);
        bottomPanel.add(contratoButton);

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
                criarNovaOrganizacao();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarOrganizacaoSelecionada();
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarOrganizacaoSelecionada();
            }
        });

        contratoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogContrato();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostrarTickets();
            }
        });

        carregarOrganizacoes();
        atualizarBotoes();
    }

    private void carregarOrganizacoes() {
        tableModel.setRowCount(0);
        try {
            var controller = mainFrame.getController();
            var usuarioLogado = controller.getUsuarioLogado();
            
            if (usuarioLogado == null) {
                tableModel.addRow(new Object[]{"", "Usuário não logado", ""});
                return;
            }
            
            // Apenas ADMIN pode ver organizações
            if (usuarioLogado.getTipo() != TipoUsuario.ADMIN) {
                tableModel.addRow(new Object[]{"", "Acesso negado", ""});
                return;
            }
            
            var organizacaoController = controller.getOrganizacaoController();
            List<OrganizacaoDTO> organizacoes = organizacaoController.pesquisarTodos();
            
            if (organizacoes != null && !organizacoes.isEmpty()) {
                for (OrganizacaoDTO org : organizacoes) {
                    tableModel.addRow(new Object[]{
                        org.getId(),
                        org.getNome(),
                        org.getDominio() != null ? org.getDominio() : "N/A"
                    });
                }
            } else {
                tableModel.addRow(new Object[]{"", "Nenhuma organização encontrada", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar organizações: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        boolean ehAdmin = usuarioLogado != null && usuarioLogado.getTipo() == TipoUsuario.ADMIN;
        int linhaSelecionada = organizacoesTable.getSelectedRow();
        
        novoButton.setEnabled(ehAdmin);
        editarButton.setEnabled(ehAdmin && linhaSelecionada >= 0);
        deletarButton.setEnabled(ehAdmin && linhaSelecionada >= 0);
        contratoButton.setEnabled(ehAdmin && linhaSelecionada >= 0);
    }

    private Integer getOrganizacaoIdSelecionada() {
        int linha = organizacoesTable.getSelectedRow();
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

    private OrganizacaoDTO getOrganizacaoSelecionada() {
        Integer orgId = getOrganizacaoIdSelecionada();
        if (orgId == null) return null;
        
        try {
            var controller = mainFrame.getController();
            var organizacaoController = controller.getOrganizacaoController();
            List<OrganizacaoDTO> organizacoes = organizacaoController.listarOrganizacoes();
            
            for (OrganizacaoDTO org : organizacoes) {
                if (org.getId() != null && org.getId().equals(orgId)) {
                    return org;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void editarOrganizacaoSelecionada() {
        OrganizacaoDTO organizacao = getOrganizacaoSelecionada();
        if (organizacao == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma organização para editar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.ADMIN) {
            JOptionPane.showMessageDialog(this, 
                "Apenas administradores podem editar organizações.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomeField = new JTextField(organizacao.getNome());
        JTextField dominioField = new JTextField(organizacao.getDominio() != null ? organizacao.getDominio() : "");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Dominio:"));
        panel.add(dominioField);

        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Editar Organização", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String dominio = dominioField.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Preencha o nome da organização.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                var organizacaoController = controller.getOrganizacaoController();
                boolean sucesso = organizacaoController.alterar(organizacao.getId(), nome, dominio);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Organização atualizada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarOrganizacoes();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao atualizar organização.", 
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

    private void deletarOrganizacaoSelecionada() {
        OrganizacaoDTO organizacao = getOrganizacaoSelecionada();
        if (organizacao == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma organização para deletar.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.ADMIN) {
            JOptionPane.showMessageDialog(this, 
                "Apenas administradores podem deletar organizações.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja deletar a organização '" + organizacao.getNome() + "'?", 
            "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                var organizacaoController = controller.getOrganizacaoController();
                boolean sucesso = organizacaoController.excluir(organizacao);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Organização deletada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarOrganizacoes();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao deletar organização.", 
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

    private void criarNovaOrganizacao() {
        var controller = mainFrame.getController();
        var usuarioLogado = controller.getUsuarioLogado();
        
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.ADMIN) {
            JOptionPane.showMessageDialog(this, 
                "Apenas administradores podem criar organizações.", 
                "Permissão Negada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nomeField = new JTextField();
        JTextField dominioField = new JTextField();
        
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Dominio:"));
        panel.add(dominioField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Nova Organização", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String dominio = dominioField.getText().trim();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Preencha o nome da organização.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                var organizacaoController = controller.getOrganizacaoController();
                boolean sucesso = organizacaoController.inserir(nome, dominio);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Organização criada com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarOrganizacoes();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao criar organização.", 
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

    private void abrirDialogContrato() {
        OrganizacaoDTO organizacao = getOrganizacaoSelecionada();
        if (organizacao == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma organização.", 
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ContratoDialog dialog = new ContratoDialog(SwingUtilities.getWindowAncestor(this), organizacao);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        carregarOrganizacoes();
    }

    public void atualizar() {
        carregarOrganizacoes();
        atualizarBotoes();
    }
}
