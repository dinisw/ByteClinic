import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public class FicheiroMedicos {
    private Medico[] listaMedicos;
    private int totalMedicos;

    public FicheiroMedicos() {
        this.listaMedicos = new Medico[10];
        this.totalMedicos = 0;
    }
    //region CARREGAR FICHEIRO
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
                if (dados.length == 6) {
                    String nomeMedico = dados[0];
                    int cedula  = Integer.parseInt(dados[1]);
                    String especialidade = dados[2];
                    int horaEntrada = Integer.parseInt(dados[3]);
                    int horaSaida = Integer.parseInt(dados[4]);
                    int salarioHora = Integer.parseInt(dados[5]);
                    Medico medico = new Medico(nomeMedico,cedula, especialidade, horaEntrada, horaSaida, salarioHora);
                    adicionarMedico(medico);
                }
            }
            System.out.println("Médicos carregados: " + totalMedicos);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar o ficheiro" + e.getMessage());
        }
    }
    //endregion

    //region GUARDAR FICHEIRO
    public void guardarFicheiroMedico(Medico medico, String caminho) {
        try {
            FileWriter ficheiro = new FileWriter(caminho, true);
            PrintWriter writer = new PrintWriter(ficheiro);

            File f = new File("medicos.txt");
            if (f.length() > 0) {}
            writer.printf("%s;%d;%s;%d;%d;%.2f%n",
                    medico.getNomeMedico(),
                    medico.getCedulaProfissional(),
                    medico.getEspecialidade(),
                    medico.getHoraEntrada(),
                    medico.getHoraSaida(),
                    medico.getSalarioHora());
            writer.close();
            System.out.println("Ficheiro guardado com sucesso.");
        } catch (IOException ex) {
            System.out.println("Erro ao guardar ficheiro" + ex.getMessage());
        }
    }
    //endregion

    //region ADICIONAR MEDICO
    public void adicionarMedico(Medico medico) {
        if (totalMedicos == listaMedicos.length) {
            expandirArray();
        }
        listaMedicos[totalMedicos++] = medico;
    }
    //endregion

    //region EXPANDIR ARRAY
    public void expandirArray() {
        Medico[] expandir = new Medico[listaMedicos.length * 2];
        for (int i = 0; i < totalMedicos; i++) {
            expandir[i] = listaMedicos[i];
        }
        this.listaMedicos = expandir;
        System.out.println("Array redimensionado para: " + listaMedicos.length);
    }
    //endregion

    //region CEDULA
    public Medico procurarMedicoPorCedula(int cedula) {
        for ( int i = 0; i < totalMedicos; i++) {
            if ( listaMedicos[i].getCedulaProfissional() == cedula) {
                return listaMedicos[i];
            }
        }
        return null;
    }
    //endregion

    //region GET MEDICOS
    public Medico[] getMedicos() {
        if (listaMedicos.length > 0) return listaMedicos;
        else return null;
    }
    //endregion

    //region PROCURAR MEDICO POR ESPECIALIDADE
    public Medico[] procurarMedicoPorEspecialidade(String especialidade) {
        int contador = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                contador++;
            }
        }
        if (contador == 0) return null;
        Medico[] especialidadeMedico = new Medico[contador];
        int index = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                especialidadeMedico[index++] = listaMedicos[i]; //???
            }
        }
        return especialidadeMedico;
    }
    //endregion

    //region REMOVER MEDICO
    public boolean removerMedico(int cedula) {
        int contador = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getCedulaProfissional() == cedula) {
                contador = i;
                break;
            }
        }
        for (int i = 0; i < totalMedicos - 1; i++) {
            listaMedicos[totalMedicos - 1] = null;
            totalMedicos--;
        }
        return true;
    }
    //endregion
    //region ATUALIZAR MEDICO
    public boolean atualizarMedico(int cedula, String novoNomeMedico, String novaEspecialidade, int novaHoraEntrada, int novaHoraSaida, int novoSalarioHora) {
        Medico medico = procurarMedicoPorCedula(cedula);
        if (medico != null) {
            medico.setNomeMedico(novoNomeMedico);
            medico.setEspecialidade(novaEspecialidade);
            medico.setHoraEntrada(novaHoraEntrada);
            medico.setHoraSaida(novaHoraSaida);
            medico.setSalarioHora(novoSalarioHora);
            return true;
        }
        return false;
    }
    //endregion\

    public  Medico[] getListaMedicos() {
        return listaMedicos;
    }
    public int getTotalMedicos() {
        return totalMedicos;
    }
}
