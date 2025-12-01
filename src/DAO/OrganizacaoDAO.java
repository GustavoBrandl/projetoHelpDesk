package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.OrganizacaoDTO;
import Conexao.Conexao;

public class OrganizacaoDAO {

	final String NOMEDATABELA = "organizacao";
	
	public boolean inserir(OrganizacaoDTO organizacao) {
	    try {
	        Connection conn = Conexao.conectar();
	        System.out.println("DEBUG: Conexão OK? " + (conn != null));
	        
	        String sql = "INSERT INTO " + NOMEDATABELA + " (nome, dominio) VALUES (?, ?)";
	        System.out.println("DEBUG: SQL: " + sql);
	        
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, organizacao.getNome());
	        ps.setString(2, organizacao.getDominio());
	        
	        System.out.println("DEBUG: Nome: " + organizacao.getNome());
	        System.out.println("DEBUG: Dominio: " + organizacao.getDominio());
	        
	        int rows = ps.executeUpdate();
	        System.out.println("DEBUG: Linhas afetadas: " + rows);
	        
	        ps.close();
	        conn.close();
	        return rows > 0;
	        
	    } catch (Exception e){
	        System.out.println("DEBUG: ERRO NO INSERT:");
	        e.printStackTrace();
	        return false;
	    }
	}
    public boolean alterar(OrganizacaoDTO organizacao) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "UPDATE " + NOMEDATABELA + " SET nome = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, organizacao.getNome());
            ps.setInt(2, organizacao.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (Exception e) {
        	 e.printStackTrace();
             return false;
        }
    }
    public boolean excluir(OrganizacaoDTO organizacao) {
    	try{
    		Connection conn = Conexao.conectar();
    		String sql = "DELETE FROM " + NOMEDATABELA + " WHERE id = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, organizacao.getId());
    		ps.executeUpdate();
    		ps.close();
    		conn.close();
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
    }
    public boolean existe(OrganizacaoDTO organizacao) {
    	try {
    		Connection conn = Conexao.conectar();
    		String sql = "SELECT * FROM " + NOMEDATABELA + " WHERE nome = (?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, organizacao.getNome());
    		ResultSet rs = ps.executeQuery();
    		boolean existe = rs.next();
   			ps.close();
    		rs.close();
    		conn.close();
    		return existe;
    	}catch(Exception e){
    		return false;
    	}
    }
    public List<OrganizacaoDTO> pesquisarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOMEDATABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<OrganizacaoDTO> listObj = montarLista(rs);
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<OrganizacaoDTO> montarLista(ResultSet rs) {
        List<OrganizacaoDTO> listObj = new ArrayList<OrganizacaoDTO>();
        try {
            while (rs.next()) {
                OrganizacaoDTO obj = new OrganizacaoDTO();
                obj.setId(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDominio(rs.getString(3));
                obj.setDataCriacao(rs.getTimestamp(4).toLocalDateTime());;
                listObj.add(obj);
            }
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean alterarOrganizacao(OrganizacaoDTO organizacao) {
        try {
            Connection conn = Conexao.conectar();

            StringBuilder sql = new StringBuilder("UPDATE " + NOMEDATABELA + " SET ");
            List<Object> parametros = new ArrayList<>();

            if (organizacao.getNome() != null) {
                sql.append("nome = ?, ");
                parametros.add(organizacao.getNome());
            }

            if (organizacao.getDominio() != null) {
            	sql.append("dominio = ?, ");
            	parametros.add(organizacao.getDominio());
            }
            sql.delete(sql.length() - 2, sql.length());
            
            sql.append(" WHERE id = ?");
            parametros.add(organizacao.getId());

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
}
