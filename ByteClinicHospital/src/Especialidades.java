public class Especialidades {
    private String sigla;
    private String nome;

    public Especialidades(String sigla) {
        this.sigla = sigla.toUpperCase();
        this.nome = descobrirNomePelaSigla(this.sigla);
    }

    public Especialidades(String sigla, String nome) {
        this.sigla = sigla.toUpperCase();
        this.nome = nome;
    }

    private String descobrirNomePelaSigla(String sigla) {
        switch (sigla) {
            case "CARD": return "Cardiologia";
            case "ORTO": return "Ortopedia";
            case "PEDI": return "Pediatria";
            default: return sigla;
        }
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }
}