package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.CategoriaDTO;
import DTO.DepartamentoDTO;
import Conexao.Conexao;

public class CategoriaDAO {

    final String NOMEDATABELA = "categoria";

    public boolean inserir(CategoriaDTO categoria) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOMEDATABELA + " (nome, fk_departamento_categoria) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categoria.getNome());
            ps.setInt(2, categoria.getDepartamento().getId());
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

    public boolean excluir(CategoriaDTO categoria) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoria.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existe(CategoriaDTO categoria) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categoria.getNome());
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

    public List<CategoriaDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT c.id, c.nome, c.numeroTickets, d.id, d.nome " +
                         "FROM " + NOMEDATABELA + " c " +
                         "INNER JOIN departamento d ON c.fk_departamento_categoria = d.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<CategoriaDTO> lista = montarLista(rs);
            rs.close();
            ps.close();
            conn.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<CategoriaDTO> montarLista(ResultSet rs) {
        List<CategoriaDTO> listObj = new ArrayList<>();
        try {
            while (rs.next()) {
                DepartamentoDTO dep = new DepartamentoDTO();
                dep.setId(rs.getInt(4));
                dep.setNome(rs.getString(5));

                CategoriaDTO cat = new CategoriaDTO();
                cat.setId(rs.getInt(1));
                cat.setNome(rs.getString(2));
                cat.setNumeroTicket(rs.getInt(3));
                cat.setDepartamento(dep);

                listObj.add(cat);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean alterarCategoria(CategoriaDTO categoria) {
        try {
            Connection conn = Conexao.conectar();

            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (categoria.getNome() != null) {
                sql.append("nome = ?, ");
                parametros.add(categoria.getNome());
            }

            if (categoria.getDepartamento() != null) {
                sql.append("fk_departamento_categoria = ?, ");
                parametros.add(categoria.getDepartamento().getId());
            }
            
            if(parametros.isEmpty()) {
            	return false;
            }

            sql.delete(sql.length() - 2, sql.length());
            sql.append(" WHERE id = ?");
            parametros.add(categoria.getId());

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
    
    public boolean incrementarNumeroTickets(Integer idCategoria) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE categoria SET numeroTickets = numeroTickets + 1 WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idCategoria);
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


