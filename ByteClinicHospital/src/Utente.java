import java.util.Arrays;

public class Utente {
    //Atributos: nome, sintomas (Array de Sintoma), pulseira (cor atribuída), medicoAtribuido (referência ou nome).
    private String nomeUtente;
    private Sintomas[] sintomas;
    private String pulseira;
    private Medico medico;
    private int horaEntrada;

    public Utente(String nomeUtente, Sintomas[] sintomas, String pulseira, Medico medico, int horaEntrada) {
        this.nomeUtente = nomeUtente;
        this.sintomas = sintomas;
        this.pulseira = pulseira;
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

    public String getPulseira() {
        return pulseira;
    }

    public void setPulseira(String pulseira) {
        this.pulseira = pulseira;
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
                "nomeUtente='" + nomeUtente + '\'' +
                ", sintomas=" + Arrays.toString(sintomas) +
                ", pulseira='" + pulseira + '\'' +
                ", medico=" + medico +
                ", horaEntrada=" + horaEntrada +
                '}';
    }
}
