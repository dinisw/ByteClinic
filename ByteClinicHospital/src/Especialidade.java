public class Especialidade {
    private String sigla;
    private String nome;

    public Especialidade(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }
    public Especialidade() {}

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("Especialidade [Sigla: %s] | [Nome: %s]",
                sigla, nome);
    }
}
