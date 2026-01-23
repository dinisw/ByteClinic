import java.util.Arrays;
import java.util.Objects;

public class Utente {
    //Atributos: nome, sintomas (Array de Sintoma), pulseira (cor atribuída), medicoAtribuido (referência ou nome).
    private String nomeUtente;
    private Sintomas[] sintomas;
    private NivelSintomas nivelSintomas;
    private Medico medico;
    private int horaEntrada;
    private int tempoDeEsperaAtual;
    private boolean emAtendimento;
    private String especialidadeEncaminhada;

    public Utente(String nomeUtente, Sintomas[] sintomas, NivelSintomas nivelSintomas, int horaEntrada, String especialidadeEncaminhada) {
        this.nomeUtente = nomeUtente;
        this.sintomas = sintomas;
        this.nivelSintomas = nivelSintomas;
        this.especialidadeEncaminhada = especialidadeEncaminhada;
        this.horaEntrada = horaEntrada;
        this.medico = null;
        this.emAtendimento = false;
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

    public String getEspecialidadeEncaminhada() {
        return especialidadeEncaminhada;
    }

    public void setEspecialidadeEncaminhada(String especialidadeEncaminhada) {
        this.especialidadeEncaminhada = especialidadeEncaminhada;
    }

    public void incrementarEspera() {
        if (!emAtendimento) {
            this.tempoDeEsperaAtual++;
        }
    }

    public boolean isEmAtendimento() {
        return emAtendimento;
    }

    public void setEmAtendimento(boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
        // Se entrou em atendimento, reseta espera
        if(emAtendimento) this.tempoDeEsperaAtual = 0;
    }

    public int getTempoEsperaAtual() {
        return tempoDeEsperaAtual;
    }

    public void resetarTempoEspera() {
        this.tempoDeEsperaAtual = 0;
    }

    public String paraFicheiro(String separador) {

        StringBuilder sbSintomas = new StringBuilder();
        if (getSintomas() != null) {
            for (int i = 0; i < getSintomas().length; i++) {
                if (getSintomas()[i] != null) {
                    if (sbSintomas.length() > 0) sbSintomas.append(separador);
                    sbSintomas.append(getSintomas()[i].getNomeSintoma());
                }
            }
        }
        String strSintomas = sbSintomas.toString();
        if (strSintomas.isEmpty()) strSintomas = "NA";

        int cedulaMedico = (medico != null) ? medico.getCedulaProfissional() : -1;

        String esp = (getEspecialidadeEncaminhada() != null) ? getEspecialidadeEncaminhada() : "NA";

        return String.format("%s;%s;%s;%d;%d;%s",
                getNomeUtente(),
                getNivelSintoma().getCor(),
                esp,
                getHoraEntrada(),
                cedulaMedico,
                strSintomas);
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
