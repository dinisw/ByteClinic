public class Medico {
    private String nomeMedico;
    private String especialidade;
    private int horaEntrada;
    private int horaSaida;
    private double salarioHora;

    public Medico(String nomeMedico, String especialidade, int horaEntrada, int horaSaida, double salarioHora) {
        this.nomeMedico = nomeMedico;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.salarioHora = salarioHora;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
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

    public void setSalarioHora(double salarioHora) {
        this.salarioHora = salarioHora;
    }
}
