import java.util.Arrays;

public class Utente {
    //Atributos: nome, sintomas (Array de Sintoma), pulseira (cor atribuída), medicoAtribuido (referência ou nome).
    private String nomeUtente;
    private Sintomas[] sintomas;
    private NivelSintomas nivelSintomas;
    private Medico medico;
    private int horaEntrada;

    public Utente(String nomeUtente, Sintomas[] sintomas, NivelSintomas nivelSintomas, Medico medico, int horaEntrada) {
        this.nomeUtente = nomeUtente;
        this.sintomas = sintomas;
        this.nivelSintomas = nivelSintomas;
        this.medico = medico;
        this.horaEntrada = horaEntrada;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public Sintomas[] getSintomas() {
        return sintomas;
    }

    public void setSintomas(Sintomas[] sintomas) {
        this.sintomas = sintomas;
    }

    public NivelSintomas getNivelSintoma() {
        return nivelSintomas;
    }

    public void setNivelSintoma(NivelSintomas nivelSintomas) {
        this.nivelSintomas = nivelSintomas;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "Nome: '" + nomeUtente +
                "\nSintomas: " + Arrays.toString(sintomas) +
                "\nCor da pulseira: '" + nivelSintomas.getCor() +
                "\nCor da pulseira: '" + nivelSintomas.getCor() +
                "\nNível do sintoma : '" + nivelSintomas.getNivel() +
                "\nMédico: " + medico +
                "\nHora de entrada: " + horaEntrada +
                '}';
    }
}
