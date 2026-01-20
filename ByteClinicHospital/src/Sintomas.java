public class Sintomas {
    private String nomeSintoma;
    private NivelSintoma nivelSintoma;
    private Especialidades especialidadesAssociadas;

    public Sintomas(String nomeSintoma, NivelSintoma nivelSintoma, Especialidades especialidadesAssociadas) {
        this.nomeSintoma = nomeSintoma;
        this.nivelSintoma = nivelSintoma;
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    public Sintomas(String nome, String nivelSintoma, String[] especialidades) {
    }

    public String getNomeSintoma() {
        return nomeSintoma;
    }

    public void setNomeSintoma(String nomeSintoma) {
        this.nomeSintoma = nomeSintoma;
    }

    public NivelSintoma getNivelSintoma() {
        return nivelSintoma;
    }

    public void setNivelSintoma(NivelSintoma nivelSintoma) {
        this.nivelSintoma = nivelSintoma;
    }

    public Especialidades getEspecialidadesAssociadas() {
        return especialidadesAssociadas;
    }

    public void setEspecialidadesAssociadas(Especialidades especialidadesAssociadas) {
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    @Override
    public String toString() {
        return "Sintomas{" +
                "nomeSintoma='" + nomeSintoma + '\'' +
                ", cor='" + nivelSintoma + '\'' +
                ", especialidadesAssociadas='" + especialidadesAssociadas + '\'' +
                '}';
    }

    public String paraFicheiro() {
        String linha = nomeSintoma + ";" + nivelSintoma;
        if (especialidadesAssociadas != null) {
            linha += ";" + especialidadesAssociadas;
        }
        return linha;
    }
}
