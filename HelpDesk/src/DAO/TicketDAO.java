package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import DTO.*;

public class TicketDAO {

    final String NOMEDATABELA = "ticket";

    public boolean inserir(TicketDTO ticket) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + 
                " (sobre, descricao, fk_prioridade_ticket, fk_status_ticket, fk_categoria_ticket, fk_solicitante_ticket) " +
                " VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ticket.getSobre());
            ps.setString(2, ticket.getDescricao());
            ps.setInt(3, ticket.getPrioridade().getId());
            ps.setInt(4, ticket.getStatus().getId());
            ps.setInt(5, ticket.getCategoria().getId());
            ps.setInt(6, ticket.getSolicitante().getId());

            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean alterar(TicketDTO ticket) {
        try {
            Connection conn = Conexao.conectar();
            

            Statement stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.close();
            
            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (ticket.getCategoria() != null) {
                sql.append("fk_categoria_ticket = ?, ");
                parametros.add(ticket.getCategoria().getId());
            }
            if (ticket.getPrioridade() != null) {
                sql.append("fk_prioridade_ticket = ?, ");
                parametros.add(ticket.getPrioridade().getId());
            }
            if (ticket.getStatus() != null) {
                sql.append("fk_status_ticket = ?, ");
                parametros.add(ticket.getStatus().getId());
            }
            if (ticket.getAtendente() != null) {
                sql.append("fk_atendente_ticket = ?, ");
                parametros.add(ticket.getAtendente().getId());
            }
            if (ticket.getDataHoraFinalizacao() != null) {
                sql.append("dataHoraFinalizacao = ?, ");
                parametros.add(Timestamp.valueOf(ticket.getDataHoraFinalizacao()));
            }
            if (ticket.getTempoChamado() != null) {
                sql.append("tempoChamado = ?, ");
                parametros.add(ticket.getTempoChamado());
            }

            if (parametros.isEmpty()) {
                conn.close();
                return false;
            }

            sql.delete(sql.length() - 2, sql.length());
            sql.append(" WHERE id = ?");
            parametros.add(ticket.getId());

            System.out.println("DEBUG TicketDAO.alterar: SQL=" + sql.toString());
            
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            int linhasAfetadas = ps.executeUpdate();
            System.out.println("DEBUG TicketDAO.alterar: Linhas afetadas=" + linhasAfetadas);
            
            ps.close();
            

            stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            stmt.close();
            
            conn.close();
            return true;

        } catch (com.mysql.jdbc.MysqlDataTruncation e) {
            System.out.println("ERRO TicketDAO.alterar: Data truncation - " + e.getMessage());
            System.out.println("DEBUG: Tentando sem data...");

            try {
                Connection conn2 = Conexao.conectar();
                StringBuilder sql2 = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
                List<Object> parametros2 = new ArrayList<>();

                if (ticket.getStatus() != null) {
                    sql2.append("fk_status_ticket = ?, ");
                    parametros2.add(ticket.getStatus().getId());
                }
                if (ticket.getCategoria() != null) {
                    sql2.append("fk_categoria_ticket = ?, ");
                    parametros2.add(ticket.getCategoria().getId());
                }
                if (ticket.getPrioridade() != null) {
                    sql2.append("fk_prioridade_ticket = ?, ");
                    parametros2.add(ticket.getPrioridade().getId());
                }
                if (ticket.getAtendente() != null) {
                    sql2.append("fk_atendente_ticket = ?, ");
                    parametros2.add(ticket.getAtendente().getId());
                }
                if (ticket.getTempoChamado() != null) {
                    sql2.append("tempoChamado = ?, ");
                    parametros2.add(ticket.getTempoChamado());
                }

                if (parametros2.isEmpty()) {
                    conn2.close();
                    return false;
                }

                sql2.delete(sql2.length() - 2, sql2.length());
                sql2.append(" WHERE id = ?");
                parametros2.add(ticket.getId());

                System.out.println("DEBUG TicketDAO.alterar (retry sem data): SQL=" + sql2.toString());

                PreparedStatement ps2 = conn2.prepareStatement(sql2.toString());
                for (int i = 0; i < parametros2.size(); i++) {
                    ps2.setObject(i + 1, parametros2.get(i));
                }

                int linhasAfetadas2 = ps2.executeUpdate();
                System.out.println("DEBUG TicketDAO.alterar (retry sem data): Linhas afetadas=" + linhasAfetadas2);

                ps2.close();
                conn2.close();
                return true;
            } catch (Exception ex2) {
                System.out.println("ERRO em TicketDAO.alterar (retry sem data): " + ex2.getMessage());
                ex2.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println("ERRO em TicketDAO.alterar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<TicketDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql =
                "SELECT t.id, t.sobre, t.descricao, t.dataHoraAbertura, t.dataHoraFinalizacao, t.tempoChamado, " +
                "p.id, p.nome, c.id, c.nome, s.id, s.nome, " +
                "sol.id, sol.username, at.id, at.username " +
                "FROM ticket t " +
                "INNER JOIN prioridade p ON p.id = t.fk_prioridade_ticket " +
                "INNER JOIN categoria c ON c.id = t.fk_categoria_ticket " +
                "INNER JOIN status s ON s.id = t.fk_status_ticket " +
                "INNER JOIN usuario sol ON sol.id = t.fk_solicitante_ticket " +
                "LEFT JOIN usuario at ON at.id = t.fk_atendente_ticket";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<TicketDTO> lista = montarLista(rs);

            rs.close();
            ps.close();
            conn.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<TicketDTO> montarLista(ResultSet rs) {
        List<TicketDTO> lista = new ArrayList<>();
        try {
            while (rs.next()) {

                PrioridadeDTO pri = new PrioridadeDTO();
                pri.setId(rs.getInt(7));
                pri.setNome(rs.getString(8));

                CategoriaDTO cat = new CategoriaDTO();
                cat.setId(rs.getInt(9));
                cat.setNome(rs.getString(10));

                StatusDTO st = new StatusDTO();
                st.setId(rs.getInt(11));
                st.setNome(rs.getString(12));

                UsuarioDTO solicitante = new UsuarioDTO();
                solicitante.setId(rs.getInt(13));
                solicitante.setUsername(rs.getString(14));

                UsuarioDTO atendente = null;
                int idAt = rs.getInt(15);
                if (idAt != 0) {
                    atendente = new UsuarioDTO();
                    atendente.setId(idAt);
                    atendente.setUsername(rs.getString(16));
                }

                TicketDTO t = new TicketDTO();
                t.setId(rs.getInt(1));
                t.setSobre(rs.getString(2));
                t.setDescricao(rs.getString(3));
                

                Timestamp abertura = rs.getTimestamp(4);
                if (abertura != null) {
                    try {
                        t.setDataHoraAbertura(abertura.toLocalDateTime());
                    } catch (Exception e) {
                        t.setDataHoraAbertura(java.time.LocalDateTime.now());
                    }
                }


                Timestamp fim = rs.getTimestamp(5);
                if (fim != null) {
                    try {
                        t.setDataHoraFinalizacao(fim.toLocalDateTime());
                    } catch (Exception e) {

                    }
                }

                t.setTempoChamado(rs.getDouble(6));
                t.setPrioridade(pri);
                t.setCategoria(cat);
                t.setStatus(st);
                t.setSolicitante(solicitante);
                t.setAtendente(atendente);

                lista.add(t);
            }

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<TicketDTO> pesquisarPorOrganizacaoMes(Integer organizacaoId, YearMonth mes) {
        try {
            Connection conn = Conexao.conectar();
            String mesStr = mes.toString();
            String sql = "SELECT t.id, t.sobre, t.descricao, t.dataHoraAbertura, t.dataHoraFinalizacao, t.tempoChamado, " +
                         "p.id, p.nome, c.id, c.nome, s.id, s.nome, " +
                         "sol.id, sol.username, at.id, at.username " +
                         "FROM ticket t " +
                         "INNER JOIN prioridade p ON p.id = t.fk_prioridade_ticket " +
                         "INNER JOIN categoria c ON c.id = t.fk_categoria_ticket " +
                         "INNER JOIN status s ON s.id = t.fk_status_ticket " +
                         "INNER JOIN usuario sol ON sol.id = t.fk_solicitante_ticket " +
                         "LEFT JOIN usuario at ON at.id = t.fk_atendente_ticket " +
                         "WHERE sol.fk_organizacao_usuario = ? " +
                         "AND DATE_FORMAT(t.dataHoraAbertura, '%Y-%m') = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, organizacaoId);
            ps.setString(2, mesStr);
            ResultSet rs = ps.executeQuery();
            List<TicketDTO> lista = montarLista(rs);

            rs.close();
            ps.close();
            conn.close();
            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}


