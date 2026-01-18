public class Sintomas {
    private String nomeSintoma;
    private String cor;
    private String especialidadesAssociadas;

    public Sintomas(String nomeSintoma, String cor, String especialidadesAssociadas) {
        this.nomeSintoma = nomeSintoma;
        this.cor = cor;
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    public String getNomeSintoma() {
        return nomeSintoma;
    }

    public void setNomeSintoma(String nomeSintoma) {
        this.nomeSintoma = nomeSintoma;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getEspecialidadesAssociadas() {
        return especialidadesAssociadas;
    }

    public void setEspecialidadesAssociadas(String especialidadesAssociadas) {
        this.especialidadesAssociadas = especialidadesAssociadas;
    }

    @Override
    public String toString() {
        return "Sintomas{" +
                "nomeSintoma='" + nomeSintoma + '\'' +
                ", cor='" + cor + '\'' +
                ", especialidadesAssociadas='" + especialidadesAssociadas + '\'' +
                '}';
    }
}
