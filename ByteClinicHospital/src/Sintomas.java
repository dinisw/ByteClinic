public class Sintomas {
    private String nomeSintoma;
    private NivelSintomas nivelSintomas;
    private Especialidades especialidadesAssociadas;

    public Sintomas(String nomeSintoma, NivelSintomas nivelSintomas, Especialidades especialidadesAssociadas) {
        this.nomeSintoma = nomeSintoma;
        this.nivelSintomas = nivelSintomas;
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    public Sintomas(String nome, String nivelSintoma, String[] especialidades) {
    }

    public Sintomas() {
    }

    public String getNomeSintoma() {
        return nomeSintoma;
    }

    public void setNomeSintoma(String nomeSintoma) {
        this.nomeSintoma = nomeSintoma;
    }

    public NivelSintomas getNivelSintoma() {
        return nivelSintomas;
    }

    public void setNivelSintoma(NivelSintomas nivelSintomas) {
        this.nivelSintomas = nivelSintomas;
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
                ", cor='" + nivelSintomas + '\'' +
                ", especialidadesAssociadas='" + especialidadesAssociadas + '\'' +
                '}';
    }

    public String paraFicheiro() {
        String linha = nomeSintoma + ";" + nivelSintomas.name();
        if (especialidadesAssociadas != null) {
            linha += ";" + especialidadesAssociadas.getCodigo();
        } else {
            linha += ";NA";
        }
        return linha;
    }
}
