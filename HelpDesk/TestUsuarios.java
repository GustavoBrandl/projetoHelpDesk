import DAO.UsuarioDAO;
import DTO.UsuarioDTO;
import java.util.List;

public class TestUsuarios {
    public static void main(String[] args) {
        System.out.println("Testando busca de usuários...");
        UsuarioDAO dao = new UsuarioDAO();
        List<UsuarioDTO> usuarios = dao.pesquisarTodos();
        
        System.out.println("Total de usuários encontrados: " + usuarios.size());
        
        if (usuarios.isEmpty()) {
            System.out.println("AVISO: Nenhum usuário encontrado no banco!");
            System.out.println("Verifique se a tabela 'usuario' existe e tem dados.");
        } else {
            for (UsuarioDTO user : usuarios) {
                System.out.println("- " + user.getUsername() + " (" + user.getEmail() + ")");
            }
        }
    }
}
