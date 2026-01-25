import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class FicheiroUtentes {
    private Utente[] paciente;
    private int totalPacientes;

    public FicheiroUtentes() {
        this.paciente = new Utente[100];
        this.totalPacientes = 0;
    }

    public void adicionaUtente(Utente utente) {
        if(totalPacientes == paciente.length) {
            expandirArray();
        }
        paciente[totalPacientes] = utente;
        totalPacientes++;
    }

    public boolean removerUtente(Utente utente) {
        if (utente == null) return false;

        int index = -1;
        for (int i = 0; i < totalPacientes; i++) {
            if (paciente[i] == utente) {
                index = i;
                break;
            }
        }

        if (index == -1) return false;

        for (int i = index; i < totalPacientes - 1; i++) {
            paciente[i] = paciente[i + 1];
        }
        paciente[totalPacientes - 1] = null;
        totalPacientes--;
        return true;
    }

    public void guardarFicheiro(String caminho, String separador) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(caminho, false));

            for (int i = 0; i < totalPacientes; i++) {
                if (paciente[i] != null) {
                    escritor.println(paciente[i].paraFicheiro(separador));
                }
            }
            escritor.close();
            System.out.println("Utentes guardados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar utentes: " + e.getMessage());
        }
    }

    public void carregarFicheiro(FicheiroMedicos ficheiroMedicos, FicheirosSintomas ficheirosSintomas, String caminho, String separador) {
        File ficheiro = new File(caminho);
        if (!ficheiro.exists()) return;

        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(separador);
                if (dados.length >= 6) {
                    String nome = dados[0];
                    NivelSintomas nivel = NivelSintomas.valueOf(dados[1]);
                    Especialidades especialidade = null;
                    if (!dados[2].equals("NA")) {
                        especialidade = Especialidades.getPorSigla(dados[2]);
                    }
                    int hora = Integer.parseInt(dados[3]);
                    int cedulaMedico = Integer.parseInt(dados[4]);
                    String stringSintomas = dados[5];

                    Sintomas[] arraySintomas = new Sintomas[20];
                    int countSintomas = 0;

                    if (!stringSintomas.equals("NA")) {
                        String[] nomesSintomas = stringSintomas.split(",");
                        for (String nomeSintoma : nomesSintomas) {
                            Sintomas sintomas = ficheirosSintomas.procurarSintoma(nomeSintoma);
                            if (sintomas != null) {
                                arraySintomas[countSintomas++] = sintomas;
                            }
                        }
                    }

                    Utente utente = new Utente(nome, arraySintomas, nivel, hora, especialidade);

                    if (cedulaMedico != -1) {
                        Medico medico = ficheiroMedicos.procurarMedicoPorCedula(cedulaMedico);
                        if (medico != null) {
                            utente.setMedico(medico);
                            utente.setEmAtendimento(true);
                            medico.iniciarAtendimento(utente);
                        }
                    }

                    adicionaUtente(utente);
                }
            }
            ler.close();
            System.out.println("Utentes carregados: " + totalPacientes);
        } catch (Exception e) {
            System.out.println("Erro ao carregar utentes: " + e.getMessage());
        }
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

    public Utente[] getPacientes() {
        return paciente;
    }

    public int getTotalDePacientes() {
        return totalPacientes;
    }
}
