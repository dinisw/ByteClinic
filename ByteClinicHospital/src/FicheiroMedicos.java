import java.io.*;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
/**
 * Classe responsável pela gestão da equipa médica da clínica.
 * Permite adicionar, remover, listar e persistir dados de médicos.
 * @author Henrique (Aluno 1).
 */
public class FicheiroMedicos {
    /**
     * Estrutura de dados (Array) que armazena os objetos do tipo Medico.
     * Funciona como a "base de dados" em memória RAM.
     * Tem uma capacidade fixa inicial, mas pode ser expandido se necessário pelo metodo expandirArray().
     */
    private Medico[] listaMedicos;
    /**
     * Contador que regista o número real de médicos inscritos no momento.
     * Serve também para indicar o índice da próxima posição livre no array.
     * Deve ser sempre menor ou igual ao tamanho do array.
     */
    private int totalMedicos;
    /**
     * Construtor que inicializa o array de médicos com uma capacidade padrão.
     * Define o contador de médicos a zero.
     */
    public FicheiroMedicos() {
        this.listaMedicos = new Medico[10];
        this.totalMedicos = 0;
    }
    /**
     * Carrega os dados dos médicos a partir de um ficheiro de texto.
     * Limpa a memória atual antes de carregar para evitar duplicados.
     * Ignora linhas mal formatadas ou vazias.
     * @param caminho O caminho do ficheiro a ler.
     */
    //region CARREGAR FICHEIRO
    public void carregarFicheiro(String caminho) {
        File ficheiro = new File(caminho);
        if (!ficheiro.exists()) {
            GestorLogs.registarErro("Ficheiro Médicos", "Ficheiro não existe.");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if (dados.length >= 6) {
                    String nomeMedico = dados[0];
                    int cedula  = Integer.parseInt(dados[1]);
                    String especialidade = dados[2];
                    int horaEntrada = Integer.parseInt(dados[3]);
                    int horaSaida = Integer.parseInt(dados[4]);
                    double salarioHora = parseDouble(dados[5].trim().replace(",", "."));
                    Medico medico = new Medico(nomeMedico,cedula, especialidade, horaEntrada, horaSaida, salarioHora);
                    adicionarMedico(medico);
                }
            }
            ler.close();
            System.out.println("Médicos carregados: " + totalMedicos);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar médicos" + e.getMessage());
            GestorLogs.registarErro("FicheiroMedicos", "Erro ao ler ficheiro: " + e.getMessage());

        }
    }
    //endregion
    /**
     * Guarda a lista completa de médicos num ficheiro de texto.
     * Este método sobrescreve o ficheiro existente para garantir que a cópia em disco é idêntica à memória RAM.
     * @param caminho O caminho ou nome do ficheiro ("medicos.txt").
     */
    //region GUARDAR FICHEIRO
    public void guardarFicheiroMedico(String caminho) {
        try {
            PrintWriter writer = new PrintWriter (caminho);
            for (int i = 0; i < totalMedicos; i++) {
                Medico medico = listaMedicos[i];
                writer.printf("%s;%d;%s;%d;%d;%.2f%n",
                        medico.getNomeMedico(),
                        medico.getCedulaProfissional(),
                        medico.getEspecialidade(),
                        medico.getHoraEntrada(),
                        medico.getHoraSaida(),
                        medico.getSalarioHora());
            }
            writer.close();
            GestorLogs.registarSucesso("Médico guardado em ficheiro");
        } catch (IOException ex) {
            System.out.println("Erro ao guardar ficheiro" + ex.getMessage());
            GestorLogs.registarErro("FicheiroMedicos", "Falha ao gravar médico" + ex.getMessage());
        }
    }
    //endregion
    /**
     * Adiciona um novo médico à memória e atualiza o ficheiro de texto.
     * Se o array estiver cheio, chama o método expandirArray() para expansão.
     * @param medico O objeto Medico a ser adicionado.
     */
    //region ADICIONAR MEDICO
    public void adicionarMedico(Medico medico) {
        if (totalMedicos == listaMedicos.length) {
            expandirArray();
        }
        listaMedicos[totalMedicos++] = medico;
    }
    //endregion
    /**
     * Redimensiona o array de médicos duplicando a sua capacidade atual.
     * Este método é chamado internamente quando o array atinge o seu limite máximo.
     */
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

    /**
     * Procura o Médico pela cédula profissional do próprio.
     * @param cedula Identificação do médico.
     * @return lista de médicos caso a cédula inserida seja igual aos registados. Caso o contrário devolve vazio.
     */
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

    /**
     * Devolve os médicos.
     * @return lista de médicos caso a mesma seja > 0. Caso contrário devolve vazio.
     */
    //region GET MEDICOS
    public Medico[] getMedicos() {
        if (listaMedicos.length > 0) return listaMedicos;
        else return null;
    }
    //endregion

    /**
     * Procura o Médico pela especialidade.
     * @param especialidade Especialidade do médico
     * @return especialidadeMedico caso seja encontrado.
     */
    //region PROCURAR MEDICO POR ESPECIALIDADE
    public Medico[] procurarMedicoPorEspecialidade(String especialidade) {
        // PASSO 1: Contar quantos médicos existem
        int contador = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                contador++;
            }
        }

        Medico[] especialidadeMedico = new Medico[contador];

        int indiceResultado = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getEspecialidade().equalsIgnoreCase(especialidade)) {
                especialidadeMedico[indiceResultado] = listaMedicos[i];
                indiceResultado++;
            }
        }
        return especialidadeMedico;
    }
    //endregion
    /**
     * Remove um médico da lista através do seu nome.
     * Realiza a reordenação do array para não deixar posições vazias.
     * Atualiza automaticamente o ficheiro de texto após a remoção.
     * @param cedula O número do médico que se pretende remover.
     * @return true se o médico foi encontrado e removido, false caso contrário.
     */
    //region REMOVER MEDICO
    public boolean removerMedico(int cedula) {
        int contador = -1;
        for (int i = 0; i < totalMedicos; i++) {
            if (listaMedicos[i].getCedulaProfissional() == cedula) {
                contador = i;
                break;
            }
        }
        if (contador == -1) {
            System.out.println("O médico não foi encontrado.");
            return false;
        }
        for (int i = contador; i < totalMedicos - 1; i++) {
            listaMedicos[i] = listaMedicos[i + 1];
        }
        listaMedicos[totalMedicos - 1] = null;
        totalMedicos--;
        guardarFicheiroMedico("medicos.txt");
        return true;
    }
    //endregion

    /**
     * Atualiza o médico em memória e também em ficheiro.
     * @param cedula            Nova cedula inserida pelo utilizador.
     * @param novoNomeMedico    Novo nome do médico inserido pelo utilizador.
     * @param novaEspecialidade Nova especialidade inserido pelo utilizador.
     * @param novaHoraEntrada   Nova hora de entrada inserido pelo utilizador.
     * @param novaHoraSaida     Nova hora saida inserido pelo utilizador.
     * @param novoSalarioHora   Novo salario por hora inserido pelo utilizador.
     */
    //region ATUALIZAR MEDICO
    public void atualizarMedico(int cedula, String novoNomeMedico, String novaEspecialidade, int novaHoraEntrada, int novaHoraSaida, double novoSalarioHora) {
        Medico medico = procurarMedicoPorCedula(cedula);
        if (medico != null) {
            medico.setNomeMedico(novoNomeMedico);
            medico.setEspecialidade(novaEspecialidade);
            medico.setHoraEntrada(novaHoraEntrada);
            medico.setHoraSaida(novaHoraSaida);
            medico.setSalarioHora(novoSalarioHora);
        }
    }
    //endregion\

    /**
     * Devolve a lista de médicos
     * @return A lista de médicos.
     */
    public  Medico[] getListaMedicos() {
        return listaMedicos;
    }

    /**
     * Devolve o número total de médicos.
     * @return O número de médicos.
     */
    public int getTotalMedicos() {
        return totalMedicos;
    }
}


