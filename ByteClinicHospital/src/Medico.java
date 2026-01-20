public class Medico {
    private String nomeMedico;
    private int cedulaProfissional;
    private String especialidade;
    private int horaEntrada;
    private int horaSaida;
    private int salarioHora;
    private boolean ocupado;
    private int totalPacientesAtendidos;

    public Medico(String nomeMedico,int cedulaProfissional, String especialidade, int horaEntrada, int horaSaida, int salarioHora) {
        this.nomeMedico = nomeMedico;
        this.cedulaProfissional = cedulaProfissional;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.salarioHora = salarioHora;
        this.ocupado = false;
        this.totalPacientesAtendidos = 0;
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
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
        String estado = ocupado ? "OCUPADO" : "LIVRE";
        return String.format("Dr(a). %s [Cédula: %d] | %s | %dh-%dh | Status: %s",
                nomeMedico, cedulaProfissional, especialidade, horaEntrada, horaSaida, estado);
    }
}
