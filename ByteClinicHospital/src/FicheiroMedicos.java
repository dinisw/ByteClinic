import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class FicheiroMedicos {
    private Medico[] listaMedicos;
    private int totalMedicos;

    public FicheiroMedicos() {
        this.listaMedicos = new Medico[10];
        this.totalMedicos = 3;
    }

    public void carregarFicheiro(String medicos) {
        File ficheiro = new File("medicos.txt");
        if (!ficheiro.exists()) {
            System.out.println("O ficheiro" + medicos + "não existe!");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if (dados.length >= 4) {
                    String nomeMedico = dados[0];
                    String especialidade = dados[1];
                    int horaEntrada = Integer.parseInt(dados[2]);
                    int horaSaida = Integer.parseInt(dados[3]);
                    double salarioHora = Double.parseDouble(dados[4]);
                    Medico medico = new Medico(nomeMedico, especialidade, horaEntrada, horaSaida, salarioHora);
                    adicionarMedico(medico);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar o ficheiro" + e.getMessage());
        }
    }

    public void adicionarMedico(Medico medico) {
        if (totalMedicos == listaMedicos.length) {
            expandirArray();
        }
        listaMedicos[totalMedicos] = medico;
        totalMedicos++;
    }

    public void expandirArray() {
        Medico[] expandir = new Medico[listaMedicos.length * 2];
        for (int i = 0; i < totalMedicos; i++) {
            expandir[i] = listaMedicos[i];
        }
        this.listaMedicos = expandir;
        System.out.println("Array redimensionado para: " + listaMedicos.length);
    }

    public Medico procurarMedicoPorNome(String medicos) {
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getNomeMedico().equalsIgnoreCase(medicos)) return listaMedicos[i];
        }
        return null;
    }

    public Medico[] procurarMedicoPorEspecialidade(String especialidade) {
        int contador = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                contador++;
            }
        }
        Medico[] especialidadeMedico = new Medico[contador];
        int index = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                especialidadeMedico[index++] = listaMedicos[i]; //???
            }
        }
        return especialidadeMedico;
    }

    public boolean removerMedico(String medicos) {
        int contador = -1;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getNomeMedico().equalsIgnoreCase(medicos)) {
                contador = i;
                break;
            }
        }
        for (int i = 0; i < totalMedicos - 1; i++) {
            listaMedicos[i] = listaMedicos[i + 1];
        }
        listaMedicos[totalMedicos - 1] = null;
        totalMedicos--;
        return true;
    }

    public void guardaFicheiroMedico(String ficheiro) {
        try {
            Formatter out = new Formatter(new File("medicos.txt"));
            for (int i = 0; i < totalMedicos; i++) {
                Medico medico = listaMedicos[i];
                out.format("%s;%s;%d;%d;%.2f%n",
                        medico.getNomeMedico(),
                        medico.getEspecialidade(),
                        medico.getHoraEntrada(),
                        medico.getHoraSaida(),
                        medico.getSalarioHora());
            }
            out.close();
            System.out.println("Ficheiro guardado com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar o ficheiro" + e.getMessage());
        }
    }
}
