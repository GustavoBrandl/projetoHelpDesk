package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Controller.HelpDeskController;
import DTO.StatusDTO;
import DTO.UsuarioDTO;
import Util.PermissaoUtil;

public class StatusPanel extends JPanel {
    private JTable tabela;
    private DefaultTableModel modelo;
    private HelpDeskUI mainFrame;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;

    public StatusPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(240,240,240));

        JPanel topo = new JPanel();
        topo.setBackground(new Color(0,150,136));
        JLabel title = new JLabel("GERENCIAR STATUS");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        topo.add(title);
        add(topo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Descrição"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(25);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        novoButton = new JButton("NOVO");
        editarButton = new JButton("EDITAR");
        excluirButton = new JButton("EXCLUIR");
        JButton voltar = new JButton("VOLTAR");
        botoes.add(novoButton);
        botoes.add(editarButton);
        botoes.add(excluirButton);
        botoes.add(voltar);
        add(botoes, BorderLayout.SOUTH);

        voltar.addActionListener(e -> mainFrame.mostrarTickets());
        novoButton.addActionListener(e -> criarNovo());
        editarButton.addActionListener(e -> editarSelecionado());

        carregarStatus();
        atualizarBotoes();
    }

    private void carregarStatus() {
        modelo.setRowCount(0);
        try {
            HelpDeskController controller = mainFrame.getController();
            UsuarioDTO usuario = mainFrame.getUsuarioLogado();
            if (usuario == null || !PermissaoUtil.podeGerenciarSistema(usuario)) {
                modelo.addRow(new Object[]{"", "Acesso não autorizado", ""});
                return;
            }

            var statusController = controller.getStatusController();
            List<StatusDTO> lista = statusController == null ? null : statusController.listarStatus();

            if (lista != null && !lista.isEmpty()) {
                for (StatusDTO s : lista) {
                    modelo.addRow(new Object[]{s.getId(), s.getNome()});
                }
            } else {
                modelo.addRow(new Object[]{"", "Nenhum status encontrado", ""});
            }
        } catch (Exception ex) {
            modelo.setRowCount(0);
            modelo.addRow(new Object[]{"", "Erro ao carregar status", ""});
            ex.printStackTrace();
        }
    }

    private void atualizarBotoes() {
        UsuarioDTO usuario = mainFrame.getUsuarioLogado();
        boolean pode = PermissaoUtil.podeGerenciarSistema(usuario);
        novoButton.setEnabled(pode);
        editarButton.setEnabled(pode && tabela.getSelectedRow() >= 0);
        excluirButton.setEnabled(pode && tabela.getSelectedRow() >= 0);
    }

    private Integer getIdSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Object o = modelo.getValueAt(linha, 0);
            if (o instanceof Integer) return (Integer) o;
            try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return null; }
        }
        return null;
    }

    private void criarNovo() {
        String nome = JOptionPane.showInputDialog(this, "Nome do status:");
        if (nome == null || nome.trim().isEmpty()) return;
        try {
            var controller = mainFrame.getController();
            boolean ok = controller.getStatusController().criarStatus(nome.trim());
            if (ok) carregarStatus();
            else JOptionPane.showMessageDialog(this, "Erro ao criar status.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void editarSelecionado() {
        Integer id = getIdSelecionado();
        if (id == null) { JOptionPane.showMessageDialog(this, "Selecione um status."); return; }
        String novo = JOptionPane.showInputDialog(this, "Novo nome:");
        if (novo == null || novo.trim().isEmpty()) return;
        try {
            var controller = mainFrame.getController();
            boolean ok = controller.getStatusController().alterarStatus(id, novo.trim());
            if (ok) carregarStatus();
            else JOptionPane.showMessageDialog(this, "Erro ao editar status.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }
    public void atualizar() {
        carregarStatus();
        atualizarBotoes();
    }
}
