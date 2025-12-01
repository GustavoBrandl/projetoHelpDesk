package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ENUM.TipoUsuario;
import DTO.UsuarioDTO;
import DTO.OrganizacaoDTO;
import Conexao.Conexao;

public class UsuarioDAO {

    final String NOMEDATABELA = "usuario";

    public boolean inserir(UsuarioDTO usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (username, email, password, telefone, tipo, fk_organizacao_usuario) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getTelefone());
            ps.setInt(5, usuario.getTipo().getId());
            ps.setInt(6, usuario.getOrganizacao().getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ERRO NO INSERT DAO");
            return false;
        }
    }

    public boolean excluir(UsuarioDTO usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existe(UsuarioDTO usuario) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            rs.close();
            ps.close();
            conn.close();
            return existe;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UsuarioDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT u.id, u.username, u.email, u.telefone, u.ativo, u.tipo, u.dataCriacao, o.id, o.nome, o.dataCriacao " +
                         "FROM " + NOMEDATABELA + " u " +
                         "INNER JOIN organizacao o ON u.fk_organizacao_usuario = o.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<UsuarioDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<UsuarioDTO> montarLista(ResultSet rs) {
        List<UsuarioDTO> listObj = new ArrayList<>();
        try {
            while (rs.next()) {
                OrganizacaoDTO org = new OrganizacaoDTO();
                org.setId(rs.getInt(8));
                org.setNome(rs.getString(9));
                org.setDataCriacao(rs.getTimestamp(10).toLocalDateTime());;
                UsuarioDTO user = new UsuarioDTO();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setTelefone(rs.getString(4));
                user.setAtivo(rs.getBoolean(5));
                user.setTipo(TipoUsuario.fromId(rs.getInt(6)));
                user.setDataCriacao(rs.getTimestamp(7).toLocalDateTime());
               user.setOrganizacao(org);

                listObj.add(user);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean alterarUsuario(UsuarioDTO usuario) {
        try {
            Connection conn = Conexao.conectar();

            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (usuario.getUsername() != null) {
                sql.append("username = ?, ");
                parametros.add(usuario.getUsername());
            }

            if (usuario.getEmail() != null) {
                sql.append("email = ?, ");
                parametros.add(usuario.getEmail());
            }
            
            if (usuario.getTelefone() != null) {
            	sql.append("telefone = ?, ");
            	parametros.add(usuario.getTelefone());
            }
            
            if (usuario.getTipo() != null) {
            	sql.append("tipo = ?, ");
            	parametros.add(usuario.getTipo().getId());
            }
            
            if (usuario.getOrganizacao() != null && usuario.getOrganizacao().getId() != null) {
            	sql.append("fk_organizacao_usuario = ?, ");
            	parametros.add(usuario.getOrganizacao().getId());
            }
            
            if(parametros.isEmpty()) {
            	return false;
            }

            sql.delete(sql.length() - 2, sql.length());
            sql.append(" WHERE id = ?");
            parametros.add(usuario.getId());

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean ativarDesativar(int id) {
        try {
            Connection conn = Conexao.conectar();

            String sql = "UPDATE " + NOMEDATABELA + " SET ativo = NOT ativo WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
