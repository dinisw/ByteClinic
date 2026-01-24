public class Medico {
    private String nomeMedico;
    private int cedulaProfissional;
    private Especialidades especialidade;
    private int horaEntrada;
    private int horaSaida;
    private int salarioHora;
    private boolean ocupado;
    private int horasConsecutivasTrabalhadas;
    private int tempoAteFimDescanso;
    private int tempoRestanteConsulta;
    private Utente utenteAtual;
    private int totalPacientesAtendidos;

    public Medico(String nomeMedico,int cedulaProfissional, Especialidades especialidade, int horaEntrada, int horaSaida, int salarioHora) {
        this.nomeMedico = nomeMedico;
        this.cedulaProfissional = cedulaProfissional;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.salarioHora = salarioHora;
        this.ocupado = false;
        this.totalPacientesAtendidos = 0;
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
        return (horaAtual >= horaEntrada && horaAtual < horaSaida) && !ocupado;
    }

    public void iniciarAtendimento(Utente utente) {
        this.ocupado = true;
        this.utenteAtual = utente;
        this.horasConsecutivasTrabalhadas++;

        switch (utente.getNivelSintoma()) {
            case VERDE: this.tempoRestanteConsulta = 1; break;
            case LARANJA: this.tempoRestanteConsulta = 2; break;
            case VERMELHA: this.tempoRestanteConsulta = 3; break;
        }
    }

    public String processarHora(int horaDoDia) {
        String status = "";

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

    public Especialidades getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidades especialidade) {
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

    public void setSalarioHora(int salarioHora) {
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

    @Override
    public String toString() {
        String estadoStr;
        if (isOcupado()) {
            estadoStr = "OCUPADO";
        } else if (Main.getHora() >= getHoraEntrada() && Main.getHora() < getHoraSaida()) {
            estadoStr = "LIVRE";
        } else {
            estadoStr = "AUSENTE";
        }
        return String.format("Dr(a). %s [Cédula: %d] | %s | %dh-%dh | Status: %s",
                nomeMedico, cedulaProfissional, especialidade, horaEntrada, horaSaida, estadoStr);
    }
}
