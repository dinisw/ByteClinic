public enum NivelSintomas {
    VERDE("Baixo", "Verde"),
    LARANJA("Medio", "Laranja"),
    VERMELHA("Urgente", "Vermelha");

    private final String nivel;
    private final String cor;

    NivelSintomas(String nivel, String cor) {
        this.nivel = nivel;
        this.cor = cor;
    }

    public String getNivel() {
        return nivel;
    }

    public String getCor() {
        return cor;
    }
}
