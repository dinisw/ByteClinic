public class Sintomas {
    private String nomeSintoma;
    private NivelSintomas nivelSintomas;
    private Especialidade especialidadesAssociadas;

    public Sintomas(String nomeSintoma, NivelSintomas nivelSintomas, Especialidade especialidadesAssociadas) {
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

    public Especialidade getEspecialidadesAssociadas() {
        return especialidadesAssociadas;
    }

    public void setEspecialidadesAssociadas(Especialidade especialidadesAssociadas) {
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    @Override
    public String toString() {
        return "Sintomas{" +
                "nomeSintoma: '" + nomeSintoma + '\'' +
                ", cor: '" + nivelSintomas + '\'' +
                ", especialidadesAssociadas: '" + especialidadesAssociadas + '\'' +
                '}';
    }

    public String paraFicheiro(String separador) {
        String linha = nomeSintoma + separador + nivelSintomas.name();
        if (especialidadesAssociadas != null) {
            linha += separador + especialidadesAssociadas.getSigla();
        } else {
            linha += ";NA";
        }
        return linha;
    }
}
