package ENUM;

public enum TipoUsuario {
    USUARIO(1),
    GERENTE(2),
    TECNICO(3),
    ADMIN(4);

    private final int id;

    TipoUsuario(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isMaiorOuIgual(TipoUsuario outro) {
        return this.id >= outro.id;
    }

    public static TipoUsuario fromId(int id) {
        for (TipoUsuario t : TipoUsuario.values()) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("TipoUsuario inválido: " + id);
    }
}


