import java.util.Arrays;
import java.util.Objects;

public class Utente {
    //Atributos: nome, sintomas (Array de Sintoma), pulseira (cor atribuída), medicoAtribuido (referência ou nome).
    private String nomeUtente;
    private Sintomas[] sintomas;
    private NivelSintomas nivelSintomas;
    private Medico medico;
    private int horaEntrada;
    private int tempoDeEspera;
    private int tempoDeConsulta;
    private boolean emConsulta;

    public Utente(String nomeUtente, Sintomas[] sintomas, NivelSintomas nivelSintomas, Medico medico, int horaEntrada, int tempoDeConsulta) {
        this.nomeUtente = nomeUtente;
        this.sintomas = sintomas;
        this.nivelSintomas = nivelSintomas;
        this.medico = medico;
        this.horaEntrada = horaEntrada;
        this.tempoDeConsulta = tempoDeConsulta;
        this.emConsulta = false;
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

    public int getTempoDeEspera() {
        return tempoDeEspera;
    }

    public void setTempoDeEspera(int tempoDeEspera) {
        this.tempoDeEspera = tempoDeEspera;
    }

    public int getTempoDeConsulta() {
        return tempoDeConsulta;
    }

    public void setTempoDeConsulta(int tempoDeConsulta) {
        this.tempoDeConsulta = tempoDeConsulta;
    }

    public boolean isEmConsulta() {
        return emConsulta;
    }

    public void setEmConsulta(boolean emConsulta) {
        this.emConsulta = emConsulta;
    }

    public void addHoraUtente(Utente utente){

        int tempoDeEspera = getTempoDeEspera();
        NivelSintomas nivelSintomas =  utente.nivelSintomas;
        if(utente.isEmConsulta()){
            if(Objects.equals(NivelSintomas.VERDE.getNivel(), nivelSintomas.getNivel())){
                if (tempoDeConsulta == 1) //ficheiroUtentes.deleteUtente()
                tempoDeConsulta++;

            }else if(Objects.equals(NivelSintomas.VERDE.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera < 3){
                tempoDeConsulta += 2;
            }
            if(Objects.equals(NivelSintomas.LARANJA.getNivel(), nivelSintomas.getNivel())){
                tempoDeConsulta ++;
            }else if(Objects.equals(NivelSintomas.VERMELHO.getNivel(), nivelSintomas.getNivel())){
                tempoDeEspera++;
            }
        }else{
            if(Objects.equals(NivelSintomas.VERDE.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera == 3){
                setNivelSintoma(NivelSintomas.LARANJA);
                tempoDeEspera = 0;
            }else if(Objects.equals(NivelSintomas.VERDE.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera < 3){
                tempoDeEspera++;
            }

            if(Objects.equals(NivelSintomas.LARANJA.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera == 3){
                setNivelSintoma(NivelSintomas.VERMELHO);
                tempoDeEspera = 0;
            }else if(Objects.equals(NivelSintomas.LARANJA.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera < 3){
                tempoDeEspera++;
            }

            if(Objects.equals(NivelSintomas.VERMELHO.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera == 2){
                //ficheiroUtentes.deleteUtente()

            }else if(Objects.equals(NivelSintomas.VERMELHO.getNivel(), nivelSintomas.getNivel()) && tempoDeEspera < 2){
                tempoDeEspera++;
            }
        }

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
