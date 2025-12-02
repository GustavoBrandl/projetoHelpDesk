package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.YearMonth;
import java.util.List;

import Controller.HelpDeskController;
import DTO.FaturamentoDTO;
import DTO.OrganizacaoDTO;

public class FaturamentoPanel extends JPanel {
    private HelpDeskUI mainFrame;
    private JComboBox<OrganizacaoDTO> orgCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JButton gerarButton;
    private JTextArea resultadoArea;

    public FaturamentoPanel(HelpDeskUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(250,250,250));

        JPanel top = new JPanel(new GridBagLayout());
        top.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        top.add(new JLabel("Organização:"), c);

        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        orgCombo = new JComboBox<>();
        top.add(orgCombo, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        top.add(new JLabel("Mês:"), c);
        c.gridx = 1;
        monthCombo = new JComboBox<>();
        for (int m = 1; m <= 12; m++) monthCombo.addItem(String.format("%02d", m));
        top.add(monthCombo, c);

        c.gridx = 0; c.gridy = 2; 
        top.add(new JLabel("Ano:"), c);
        c.gridx = 1;
        yearCombo = new JComboBox<>();
        int yearNow = YearMonth.now().getYear();
        for (int y = yearNow; y >= yearNow-5; y--) yearCombo.addItem(y);
        top.add(yearCombo, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        gerarButton = new JButton("Gerar Faturamento");
        top.add(gerarButton, c);

        add(top, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        gerarButton.addActionListener((ActionEvent e) -> gerarFaturamento());

        carregarOrganizacoes();
    }

    private void carregarOrganizacoes() {
        orgCombo.removeAllItems();
        try {
            HelpDeskController hc = mainFrame.getController();
            List<OrganizacaoDTO> orgs = hc.getOrganizacaoController().pesquisarTodos();
            if (orgs != null) {
                for (OrganizacaoDTO o : orgs) orgCombo.addItem(o);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void gerarFaturamento() {
        OrganizacaoDTO sel = (OrganizacaoDTO) orgCombo.getSelectedItem();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma organização.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int month = Integer.parseInt((String) monthCombo.getSelectedItem());
        int year = (Integer) yearCombo.getSelectedItem();
        YearMonth ym = YearMonth.of(year, month);

        try {
            var hc = mainFrame.getController();
            var faturamentoController = hc.getFaturamentoController();
            FaturamentoDTO dto = faturamentoController.gerarFaturamento(sel.getId(), ym);
            if (dto == null) {
                resultadoArea.setText("Nenhum faturamento gerado (contrato ausente ou erro).\n");
                return;
            }

            StringBuilder out = new StringBuilder();
            out.append("FATURAMENTO - ").append(sel.getNome()).append(" - ").append(ym).append('\n');
            out.append("Horas trabalhadas: ").append(dto.getHorasTrabalhadas()).append('\n');
            out.append("Horas contratadas: ").append(hc.getContratoController().pesquisarAtivoOrganizacao(sel.getId(), ym)!=null ? hc.getContratoController().pesquisarAtivoOrganizacao(sel.getId(), ym).getHorasContrato() : "N/A").append('\n');
            out.append("Valor base: R$ ").append(String.format("%.2f", dto.getValorBase())).append('\n');
            out.append("Valor extra: R$ ").append(String.format("%.2f", dto.getValorExtra())).append('\n');
            out.append("Valor total: R$ ").append(String.format("%.2f", dto.getValorTotal())).append('\n');

            resultadoArea.setText(out.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar faturamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizar() {
        carregarOrganizacoes();
    }
}
