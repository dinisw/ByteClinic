public class FicheiroUtentes {
    private Utente[] paciente;
    private int totalPacientes;

    public FicheiroUtentes() {
        Utente[] paciente = new Utente[100];
        totalPacientes = 0;
    }
    public void adicionaUtente(Utente utente) {
        if(totalPacientes == paciente.length) {
            expandirArray();
        }
        paciente[totalPacientes] = utente;
        totalPacientes++;
    }

    public void expandirArray() {
        Utente[] novo = new Utente[paciente.length * 2];
        for(int i = 0; i < totalPacientes; i++) {
            novo[i] = paciente[i];
        }
        this.paciente = novo;
        System.out.println("Array expandido com sucesso para: " + paciente.length);
    }

    public Utente procurarUtente(String nomeUtente) {
        int contador = 0;
        for(int i = 0; i < totalPacientes; i++) {
            if(paciente[i].getNomeUtente().equalsIgnoreCase(nomeUtente)) return paciente[i];
        }
        return null;
    }
}
