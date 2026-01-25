public enum Especialidades {
    CARDIOLOGIA("CARD", "Cardiologia"),
    ORTOPEDIA("ORTO", "Ortopedia"),
    PEDIATRIA("PEDI", "Pediatria");

    private final String codigo;
    private final String nome;

    Especialidades(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
    public static Especialidades getPorSigla(String sigla) {
        for (Especialidades especialidades : values()) {
            if (especialidades.getCodigo().equalsIgnoreCase(sigla.trim())) {
                return especialidades;
            }
        }
        return null;
    }
}
