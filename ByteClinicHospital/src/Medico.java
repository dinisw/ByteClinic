public class Medico {
    private String nomeMedico;
    private int cedulaProfissional;
    private Especialidade especialidade;
    private int horaEntrada;
    private int horaSaida;
    private Double salarioHora;
    private boolean ocupado;
    private int horasConsecutivasTrabalhadas;
    private int tempoAteFimDescanso;
    private int tempoRestanteConsulta;
    private Utente utenteAtual;
    private int totalPacientesAtendidos;
    private int tempoDeAtendimento;
    private int horasConsecutivasDeAtendimento;

    public Medico(String nomeMedico,int cedulaProfissional, Especialidade especialidade, int horaEntrada, int horaSaida, Double salarioHora) {
        this.nomeMedico = nomeMedico;
        this.cedulaProfissional = cedulaProfissional;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.salarioHora = salarioHora;
        this.ocupado = false;
        this.totalPacientesAtendidos = 0;
        this.tempoDeAtendimento = 0;
    }

    public Medico() {
    }

    public void inicioAtendimento() {
        this.ocupado = true;
        this.totalPacientesAtendidos++;
    }

    public void terminarAtendimento() {
        this.ocupado = false;
    }

    public boolean isDisponivel(int horaAtual) {
        return (horaAtual >= horaEntrada && horaAtual < horaSaida) && !ocupado && !isEmDescanso();
    }

    public void iniciarAtendimento(Utente utente) {
        this.ocupado = true;
        this.utenteAtual = utente;
        this.horasConsecutivasTrabalhadas++;

        if (utente.getNivelSintoma() != null) {
            switch (utente.getNivelSintoma()) {
                case VERDE:
                    this.tempoRestanteConsulta = 1;
                    break;
                case LARANJA:
                    this.tempoRestanteConsulta = 2;
                    break;
                case VERMELHA:
                    this.tempoRestanteConsulta = 3;
                    break;
                default:
                    this.tempoRestanteConsulta = 1;
            }
        }
    }

    public String processarHora(int horaDoDia) {
        String status = "";

        if (horaDoDia == horaEntrada) {
            status += " -> Dr. " + nomeMedico + " iniciou o turno.\n";
        }

        if (tempoAteFimDescanso > 0) {
            tempoAteFimDescanso--;
            if (tempoAteFimDescanso == 0) {
                horasConsecutivasTrabalhadas = 0; // Reset ao cansaço
                status = " -> Dr. " + nomeMedico + " voltou do descanso.\n";
            }
            return status;
        }

        if (ocupado && tempoRestanteConsulta > 0) {
            tempoRestanteConsulta--;
            horasConsecutivasTrabalhadas++;

            if (tempoRestanteConsulta == 0) {
                ocupado = false;
                totalPacientesAtendidos++;
                status = " -> Dr. " + nomeMedico + " terminou consulta com " + utenteAtual.getNomeUtente() + ".\n";
                utenteAtual = null;

                if (horasConsecutivasTrabalhadas >= 5) {
                    tempoAteFimDescanso = 1;
                    status += " -> Dr. " + nomeMedico + " entrou em pausa forçada (Cansaço).\n";
                }
            }
        }

        if (horaDoDia == horaSaida && !ocupado) {
            status += " -> Dr. " + nomeMedico + " terminou o turno.\n";
        }

        return status;
    }


    public boolean isEmDescanso() {
        return tempoAteFimDescanso > 0;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public int getCedulaProfissional() {
        return cedulaProfissional;
    }

    public void setCedulaProfissional(int cedulaProfissional) {
        this.cedulaProfissional = cedulaProfissional;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(int horaSaida) {
        this.horaSaida = horaSaida;
    }

    public double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(Double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public int getTotalPacientesAtendidos() {
        return totalPacientesAtendidos;
    }

    public void setTotalPacientesAtendidos(int totalPacientesAtendidos) {
        this.totalPacientesAtendidos = totalPacientesAtendidos;
    }

    public void getDescanso(){
        //if (tempoDeAtendimento )
    }

    public void addHoraMedico(){
        horasConsecutivasDeAtendimento++;
        if(horasConsecutivasDeAtendimento == 5){
            ocupado = true;
        }
        if(horasConsecutivasDeAtendimento == 6){
            ocupado = false;
            horasConsecutivasDeAtendimento = 0;
        }
    }



    @Override
    public String toString() {
        String estadoStr;
        if (isEmDescanso()) {
            estadoStr = "PAUSA";
        } else if (isOcupado()) {
            estadoStr = "OCUPADO";
        } else if (Main.getHora() >= getHoraEntrada() && Main.getHora() < getHoraSaida()) {
            estadoStr = "LIVRE";
        } else {
            estadoStr = "AUSENTE";
        }
        return String.format("Dr(a). %s [Cédula: %d] | %s | %dh-%dh | Status: %s",
                nomeMedico, cedulaProfissional, especialidade, horaEntrada, horaSaida, estadoStr);
    }

    public Utente getUtenteAtual() {
        return utenteAtual;
    }
}
