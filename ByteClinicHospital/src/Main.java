import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    //region Propriedades Iniciais
    private static FicheiroMedicos ficheiroMedicos = new FicheiroMedicos();
    private static FicheiroEspecialidade ficheiroEspecialidade = new FicheiroEspecialidade();
    private static FicheirosSintomas ficheirosSintomas = new FicheirosSintomas();
    private static FicheiroUtentes ficheiroUtentes = new FicheiroUtentes();


    private static final String SEPARADOR = ";";
    private static final String ARQUIVO_MEDICOS = "medicos.txt";
    private static final String ARQUIVO_SINTOMAS = "sintomas.txt";
    private static final String ARQUIVO_ESPECIALIDADES = "especialidades.txt";
    private static final String ARQUIVO_UTENTES = "utentes.txt";

    public static final String PASSWORD = "admin123";
    //endregion

    //region Dia e Hora
    private static int hora = 0;
    private static int dia = 1;

    public static int getHora(){
        return hora;
    }
    //endregion

    //region Variáveis de Configuração
    public static int TEMPO_CONSULTA_VERDE = 1;
    public static int TEMPO_CONSULTA_LARANJA = 2;
    public static int TEMPO_CONSULTA_VERMELHO = 3;

    public static int TEMPO_AGRAVAMENTO_VERDE = 3;
    public static int TEMPO_AGRAVAMENTO_LARANJA = 3;
    public static int TEMPO_SAIDA_VERMELHO = 2;
    //endregion

    //region Design
    public static final String RESET = "\033[0m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String BLUE = "\033[0;34m";
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\033[0;33m";
    private static final int LARGURA = 84;

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    //endregion

    //region MAIN
    static void main(String[] args) {

        System.out.println("A carregar dados do sistema...");

        ficheirosSintomas.carregarSintomas(ARQUIVO_SINTOMAS, SEPARADOR);
        ficheiroEspecialidade.carregarFicheiro(ARQUIVO_ESPECIALIDADES, SEPARADOR);
        ficheiroMedicos.carregarFicheiro(ARQUIVO_MEDICOS, SEPARADOR);
        ficheiroUtentes.carregarFicheiro(ficheiroMedicos, ficheirosSintomas, ARQUIVO_UTENTES, SEPARADOR);

        Scanner ler = new Scanner(System.in);
        String opcao = "";
        do {
            exibirTitulo();
            exibirMenu();

            System.out.print("\n" + WHITE_BOLD + "Digite a opção desejada: " + RESET);
            opcao = ler.nextLine();

            switch (opcao) {
                case "1":
                    menuTriagem(ler);
                    break;
                case "2":
                    menuMedicos(ler);
                    break;
                case "3":
                    menuEspecialidades(ler);
                    break;
                case "4":
                    menuSintomas(ler);
                    break;
                case "5":
                    gerirHospital(ler);
                    break;
                case "6":
                    consultarEstatisticas(ler);
                    break;
                case "7":
                    configuracoes(ler);
                    break;
                case "0":
                    System.out.println("\n" + YELLOW + "A guardar dados e a encerrar..." + RESET);

                    ficheirosSintomas.guardarSintomas(ARQUIVO_SINTOMAS, SEPARADOR);
                    ficheiroEspecialidade.guardarFicheiro(ARQUIVO_ESPECIALIDADES, SEPARADOR);
                    ficheiroMedicos.guardarFicheiroMedico(ARQUIVO_MEDICOS, SEPARADOR);
                    ficheiroUtentes.guardarFicheiro(ARQUIVO_UTENTES, SEPARADOR);

                    System.out.println(GREEN + "Dados salvos. Até logo!" + RESET);
                    break;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }

        } while (!opcao.equals("0"));

    }
    //endregion

    //region MENU
    public static void exibirTitulo() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
        String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
        String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

        String titulo = "SISTEMA DE GESTÃO BYTECLINIC";
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        int paddingTitulo = (LARGURA - titulo.length()) / 2;
        String textoTitulo = String.format("%" + paddingTitulo + "s%s%-" + paddingTitulo + "s", "", titulo, "");

        if (textoTitulo.length() < LARGURA) textoTitulo += " ";

        String statusLabel = " Status: ";
        String statusValor = "ONLINE - " + hora + ":00h";
        String statusCompleto = statusLabel + statusValor;

        int espacoDisponivel = LARGURA - statusLabel.length() - statusValor.length() - dataHora.length() - 1;
        String paddingData = " ".repeat(Math.max(0, espacoDisponivel));

        System.out.println(CYAN_BOLD + bordaSuperior + RESET);
        System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + textoTitulo + CYAN_BOLD + "║" + RESET);
        System.out.println(CYAN_BOLD + bordaMeio + RESET);
        System.out.println(CYAN_BOLD + "║" + RESET + statusLabel + GREEN + statusValor + RESET + paddingData + BLUE + dataHora + " " + CYAN_BOLD + "║" + RESET);
        System.out.println(CYAN_BOLD + bordaInferior + RESET);
    }

    public static void exibirMenu() {

        String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
        String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

        String menuTitulo = "MENU PRINCIPAL";
        int paddingMenu = (LARGURA - menuTitulo.length()) / 2;
        String cabecalhoMenu = String.format("%" + paddingMenu + "s%s%-" + paddingMenu + "s", "", menuTitulo, "");
        if (cabecalhoMenu.length() < LARGURA) cabecalhoMenu += " ";

        System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + cabecalhoMenu + CYAN_BOLD + "║" + RESET);
        System.out.println(CYAN_BOLD + bordaMeio + RESET);

        String[] opcoes = {
                "1. Triagem",
                "2. Médicos",
                "3. Especialidades",
                "4. Sintomas",
                "5. Avançar Tempo",
                "6. Consultar estatísticas e logs",
                "7. Configurações",
                "0. Sair"
        };

        for (String opcao : opcoes) {
            System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao) + CYAN_BOLD + "║" + RESET);
        }
        System.out.println(CYAN_BOLD + bordaInferior + RESET);
    }
    //endregion

    //region TRIAGEM
    //region MENU TRIAGEM
    private static void menuTriagem(Scanner ler) {
        String opcao = "";

        do {
            System.out.println("\033[H\033[2J");
            System.out.flush();

            String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
            String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
            String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

            String titulo = "TRIAGEM & SALA DE ESPERA";
            int paddingTitulo = (LARGURA - titulo.length()) / 2;
            String textoTitulo = String.format("%" + paddingTitulo + "s%s%-" + paddingTitulo + "s", "", titulo, "");
            if (textoTitulo.length() < LARGURA) textoTitulo += " ";

            System.out.println(CYAN_BOLD + bordaSuperior + RESET);
            System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + textoTitulo + CYAN_BOLD + "║" + RESET);
            System.out.println(CYAN_BOLD + bordaMeio + RESET);

            String[] opcoes = {
                    "1. Realizar Nova Triagem (Admissão)",
                    "2. Ver Lista de Espera (Utentes)",
                    "0. Voltar ao Menu Principal"
            };

            for (String opcao2 : opcoes) {
                System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao2) + CYAN_BOLD + "║" + RESET);
            }
            System.out.println(CYAN_BOLD + bordaInferior + RESET);

            System.out.print("\n" + WHITE_BOLD + "Selecione uma opção: " + RESET);
            opcao = ler.nextLine().trim();

            switch (opcao) {
                case "1":
                    registarTriagem(ler);
                    break;
                case "2":
                    listarFilaEspera(ler);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }
        } while (!opcao.equals("0"));
    }
    //endregion

    //region TRIAGEM
    private static void registarTriagem(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- TRIAGEM (ADMISSÃO) ---" + RESET);
        try {
            System.out.print("Nome do Utente: ");
            String nome = ler.nextLine();

            Sintomas[] sintomasEscolhidos = new Sintomas[20];
            int qtdSelecionados = 0;

            String opcao = "";

            do {
                Sintomas[] sintomasDisponiveis = ficheirosSintomas.getSintomas();

                System.out.println("\n" + CYAN_BOLD + "--- SELECIONE OS SINTOMAS ---" + RESET);

                int count = 1;
                for (Sintomas sintoma : sintomasDisponiveis) {
                    if (sintoma != null) {
                        System.out.printf("%02d - %s\n", count, sintoma.getNomeSintoma());
                    }
                    count++;
                }

                if (qtdSelecionados > 0) {
                    System.out.println(YELLOW + "------------------------");
                    System.out.print("Atualmente selecionados: ");
                    for(int i=0; i<qtdSelecionados; i++) System.out.print(sintomasEscolhidos[i].getNomeSintoma() + "; ");
                    System.out.println(RESET);
                }

                System.out.println("------------------------");
                System.out.println(GREEN + "0 - [ + ADICIONAR NOVO SINTOMA AO SISTEMA + ]" + RESET);
                System.out.println("Digite os códigos separados por vírgula (ex: 1, 3).");
                System.out.print("Escolha (ou 'sair' para finalizar): ");

                opcao = ler.nextLine().trim();

                if (opcao.equalsIgnoreCase("sair")) {
                    break;
                }

                String[] partes = opcao.split("\\s*,\\s*");

                for (String parte : partes) {
                    if (parte.isEmpty()) continue;

                    if (parte.equals("0")) {
                        System.out.println(YELLOW + ">>> Redirecionando para criação de sintoma..." + RESET);
                        registarSintomas(ler);
                        System.out.println(YELLOW + ">>> Retornando à triagem de " + nome + "..." + RESET);
                        continue;
                    }

                    Sintomas sintomaEncontrado = null;

                    try {
                        int indice = Integer.parseInt(parte) - 1;
                        if (indice >= 0 && indice < sintomasDisponiveis.length && sintomasDisponiveis[indice] != null) {
                            sintomaEncontrado = sintomasDisponiveis[indice];
                        }
                    } catch (NumberFormatException e) {
                        for (Sintomas s : sintomasDisponiveis) {
                            if (s != null && s.getNomeSintoma().equalsIgnoreCase(parte)) {
                                sintomaEncontrado = s;
                                break;
                            }
                        }
                    }

                    if (sintomaEncontrado != null) {
                        boolean jaExiste = false;
                        for(int i = 0; i < qtdSelecionados; i++) {
                            if(sintomasEscolhidos[i].getNomeSintoma().equalsIgnoreCase(sintomaEncontrado.getNomeSintoma())){
                                jaExiste = true; break;
                            }
                        }

                        if (!jaExiste) {
                            sintomasEscolhidos[qtdSelecionados++] = sintomaEncontrado;
                            System.out.println(GREEN + "Adicionado: " + sintomaEncontrado.getNomeSintoma() + RESET);
                        } else {
                            System.out.println(YELLOW + "Já selecionado: " + sintomaEncontrado.getNomeSintoma() + RESET);
                        }
                    } else {
                        if(!parte.equals("0")) System.out.println(RED + "Não encontrado: " + parte + RESET);
                    }
                }

                if (qtdSelecionados > 0) {
                    System.out.println("\n[ENTER] para adicionar mais ou digite 'sair' para terminar a triagem.");
                }

            } while (true);

            if (qtdSelecionados > 0) {

                NivelSintomas nivelFinal = NivelSintomas.VERDE;
                for(int i=0; i<qtdSelecionados; i++) {
                    NivelSintomas n = sintomasEscolhidos[i].getNivelSintoma();
                    if(n == NivelSintomas.VERMELHA) { nivelFinal = NivelSintomas.VERMELHA; break; }
                    if(n == NivelSintomas.LARANJA) nivelFinal = NivelSintomas.LARANJA;
                }

                String especialidadeCalculada = calcularEspecialidadeAutomaticamente(sintomasEscolhidos, qtdSelecionados);
                if (especialidadeCalculada == null) {
                    System.out.println(YELLOW + "\nSistema incerto. Sugestão: 'CLINICA GERAL'." + RESET);
                    especialidadeCalculada = "GERAL";
                } else {
                    System.out.println(CYAN_BOLD + "\nDiagnóstico do Sistema: Encaminhar para " + especialidadeCalculada + RESET);
                }

                Utente novoUtente = new Utente(nome, sintomasEscolhidos, nivelFinal, hora, especialidadeCalculada);
                ficheiroUtentes.adicionaUtente(novoUtente);

                System.out.println(GREEN + "Triagem concluída! Utente na sala de espera." + RESET);

                atribuirPacientesAutomaticamente();

            } else {
                System.out.println(RED + "Nenhum sintoma selecionado. Triagem cancelada." + RESET);
            }

        } catch (Exception e) {
            System.out.println(RED + "Erro inesperado: " + e.getMessage() + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion
    //endregion

    //region MEDICOS
    //region MENU MEDICO
    private static void menuMedicos(Scanner ler) {
        String opcao = "";

        do {
            System.out.println("\033[H\033[2J");
            System.out.flush();

            String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
            String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
            String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

            String titulo = "GESTÃO DE MÉDICOS";
            int paddingTitulo = (LARGURA - titulo.length()) / 2;
            String textoTitulo = String.format("%" + paddingTitulo + "s%s%-" + paddingTitulo + "s", "", titulo, "");
            if (textoTitulo.length() < LARGURA) textoTitulo += " ";

            System.out.println(CYAN_BOLD + bordaSuperior + RESET);
            System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + textoTitulo + CYAN_BOLD + "║" + RESET);
            System.out.println(CYAN_BOLD + bordaMeio + RESET);

            String[] opcoes = {
                    "1. Registar Novo Médico",
                    "2. Listar Todos os Médicos (Status)",
                    "3. Pesquisar Médico (por Cédula)",
                    "4. Atualizar Dados de Médico",
                    "5. Remover Médico",
                    "6. Verificar Disponibilidade por Especialidade",
                    "0. Sair"
            };

            for (String opcao2 : opcoes) {
                System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao2) + CYAN_BOLD + "║" + RESET);
            }
            System.out.println(CYAN_BOLD + bordaInferior + RESET);

            System.out.print("\n" + WHITE_BOLD + "Selecione uma opção: " + RESET);
            opcao = ler.nextLine();

            switch (opcao) {
                case "1":
                    registarMedico(ler);
                    break;
                case "2":
                    listarMedicos(ler);
                    break;
                case "3":
                    pesquisarMedico(ler);
                    break;
                case "4":
                    atualizarMedico(ler);
                    break;
                case "5":
                    removerMedico(ler);
                    break;
                case "6":
                    listarPorEspecialidade(ler);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }
        } while (!opcao.equals("0"));
    }
    //endregion

    //region REGISTAR MEDICO
    private static void registarMedico(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- REGISTAR MÉDICO ---" + RESET);
        try {
            System.out.print("Nome: ");
            String nome = ler.nextLine();

            System.out.print("Cédula Profissional (Números): ");
            int cedula = Integer.parseInt(ler.nextLine());

            if (ficheiroMedicos.procurarMedicoPorCedula(cedula) != null) {
                System.out.println(RED + "Erro: Cédula já registada!" + RESET);
                pressionarEnter(ler);
                return;
            }

            // --- INÍCIO DA CORREÇÃO ---
            System.out.println("Especialidades disponíveis:");
            for (Especialidades esp : Especialidades.values()) {
                System.out.print(esp.getCodigo() + " ");
            }
            System.out.println();

            System.out.print("Especialidade (Sigla): ");
            String especialidadeStr = ler.nextLine().trim().toUpperCase();

            Especialidades especialidadeSelecionada = null;

            for (Especialidades esp : Especialidades.values()) {
                if (esp.getCodigo().equalsIgnoreCase(especialidadeStr)) {
                    especialidadeSelecionada = esp;
                    break;
                }
            }

            if (especialidadeSelecionada == null) {
                System.out.println(RED + "Erro: Especialidade não encontrada. Registo cancelado." + RESET);
                pressionarEnter(ler);
                return;
            }

            System.out.print("Hora Entrada (0-23): ");
            int horaEntrada = Integer.parseInt(ler.nextLine());
            System.out.print("Hora Saída (0-23): ");
            int horaSaida = Integer.parseInt(ler.nextLine());
            System.out.println("Salário Hora: ");
            int salario = Integer.parseInt(ler.nextLine());
            Medico medicos = new Medico(nome, cedula, especialidadeSelecionada, horaEntrada, horaSaida, salario);

            ficheiroMedicos.adicionarMedico(medicos);
            ficheiroMedicos.guardarFicheiroMedico(ARQUIVO_MEDICOS, ARQUIVO_MEDICOS);

            System.out.println(GREEN + "Médico registado com sucesso!" + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Erro: Introduza números válidos." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region LISTAR MEDICO
    private static void listarMedicos(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- EQUIPA MÉDICA ---" + RESET);
        Medico[] lista = ficheiroMedicos.getListaMedicos();
        int total = ficheiroMedicos.getTotalMedicos();
        int horaAtual = hora;

        if (total == 0) System.out.println(YELLOW + "Sem médicos registados." + RESET);
        else {
            System.out.printf("%-10s %-20s %-10s %-15s %s\n", "CÉDULA", "NOME", "ESP.", "HORÁRIO", "ESTADO");
            System.out.println("-------------------------------------------------------------------------");
            for (int i=0 ; i<total; i++) {
                Medico medico = lista[i];
                        String estadoStr;
                        if (medico.isOcupado()) {
                            estadoStr = RED + "OCUPADO" + RESET;
                        } else if (horaAtual >= medico.getHoraEntrada() && horaAtual < medico.getHoraSaida()) {
                            estadoStr = GREEN + "LIVRE" + RESET;
                        } else {
                            estadoStr = YELLOW + "AUSENTE" + RESET;
                        }

                        System.out.printf("%-10d %-20s %-10s %02dh-%02dh       %s\n",
                                medico.getCedulaProfissional(), medico.getNomeMedico(), medico.getEspecialidade(), medico.getHoraEntrada(), medico.getHoraSaida(), estadoStr);
            }
        pressionarEnter(ler);
        }
    }
    //endregion

    //region PESQUISAR MEDICO
    private static void pesquisarMedico(Scanner ler) {
        try {
            System.out.print("Cédula a pesquisar: ");
            int cedula = Integer.parseInt(ler.nextLine());
            Medico medico = ficheiroMedicos.procurarMedicoPorCedula(cedula);

            if (medico != null) {
                System.out.println(GREEN + "\nEncontrado: " + RESET);
                System.out.println(medico.toString());
                System.out.println("Pacientes atendidos hoje: " + medico.getTotalPacientesAtendidos());
            } else {
                System.out.println(RED + "Médico não encontrado." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Erro no input." + RESET);
        }
            pressionarEnter(ler);
    }
    //endregion

    //region ATUALIZAR MEDICO
    private static void atualizarMedico(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- ATUALIZAR MÉDICO ---" + RESET);

        int cedula = -1;
        try {
            System.out.print("Cédula do médico a editar: ");
            cedula = Integer.parseInt(ler.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED + "Cédula inválida." + RESET);
            pressionarEnter(ler);
            return;
        }

        if (ficheiroMedicos.procurarMedicoPorCedula(cedula) == null) {
            System.out.println(RED + "Médico não encontrado." + RESET);
            pressionarEnter(ler);
            return;
        }

        try {
            System.out.print("Novo Nome: ");
            String nome = ler.nextLine();

            System.out.print("Nova Especialidade (Sigla): ");
            String sigla = ler.nextLine().trim();

            Especialidades especialidadeSelecionada = null;

            for (Especialidades esp : Especialidades.values()) {
                if (esp.getCodigo().equalsIgnoreCase(sigla)) {
                    especialidadeSelecionada = esp;
                    break;
                }
            }

            if (especialidadeSelecionada == null) {
                System.out.println(RED + "Erro: Especialidade não existe. Atualização cancelada." + RESET);
                pressionarEnter(ler);
                return;
            }

            System.out.print("Nova Entrada (0-23): ");
            int entrada = Integer.parseInt(ler.nextLine());

            System.out.print("Nova Saída (0-23): ");
            int saida = Integer.parseInt(ler.nextLine());

            System.out.print("Novo Salário: ");
            int salario = Integer.parseInt(ler.nextLine());

            boolean sucesso = ficheiroMedicos.atualizarMedico(cedula, nome, especialidadeSelecionada, entrada, saida, salario, ARQUIVO_MEDICOS, SEPARADOR);

            if(sucesso) {
                System.out.println(GREEN + "Atualizado com sucesso" + RESET);
            } else {
                System.out.println(RED + "Erro ao atualizar." + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Erro: Introduza apenas números nos campos de horário/salário." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region REMOVER MEDICO
    private static void removerMedico(Scanner ler) {
        System.out.print("Cédula a remover: ");
        int cedula = Integer.parseInt(ler.nextLine());
        if (ficheiroMedicos.removerMedico(cedula)) {
            System.out.println(GREEN + "Removido com sucesso." + RESET);
        } else {
            System.out.println(RED + "Não encontrado." + RESET);
        }
    }
    //endregion

    //region LISTAR ESPECIALIDADE MEDICO
    private static void listarPorEspecialidade(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- LISTAR POR ESPECIALIDADE ---" + RESET);

        System.out.println("Siglas disponíveis: ");
        for (Especialidades e : Especialidades.values()) {
            System.out.print(e.getCodigo() + " ");
        }
        System.out.println();

        System.out.print("Qual a especialidade (Sigla)? ");
        String input = ler.nextLine().trim();

        Especialidades especialidadeSelecionada = null;

        for (Especialidades esp : Especialidades.values()) {
            if (esp.getCodigo().equalsIgnoreCase(input)) {
                especialidadeSelecionada = esp;
                break;
            }
        }

        if (especialidadeSelecionada == null) {
            System.out.println(RED + "Erro: Especialidade não encontrada." + RESET);
            pressionarEnter(ler);
            return;
        }

        Medico[] lista = ficheiroMedicos.procurarMedicoPorEspecialidade(especialidadeSelecionada);

        if (lista != null && lista.length > 0) {
            System.out.println("\nMédicos de " + especialidadeSelecionada.name() + ":");
            System.out.println("-------------------------------------------------");
            for (Medico medico : lista) {
                if (medico != null) {
                    System.out.println(medico);
                }
            }
        } else {
            System.out.println(YELLOW + "Nenhum médico encontrado com essa especialidade." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion
    //endregion

    //region ESPECIALIDADES
    //region MENU ESPECIALIDADE
    private static void menuEspecialidades(Scanner ler) {
        String opcao = "";

        do {
            System.out.println("\033[H\033[2J");
            System.out.flush();

            String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
            String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
            String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

            String titulo = "GESTÃO DE ESPECIALIDADES";
            int paddingTitulo = (LARGURA - titulo.length()) / 2;
            String textoTitulo = String.format("%" + paddingTitulo + "s%s%-" + paddingTitulo + "s", "", titulo, "");
            if (textoTitulo.length() < LARGURA) textoTitulo += " ";

            System.out.println(CYAN_BOLD + bordaSuperior + RESET);
            System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + textoTitulo + CYAN_BOLD + "║" + RESET);
            System.out.println(CYAN_BOLD + bordaMeio + RESET);

            String[] opcoes = {
                    "1. Registar Especialidade",
                    "2. Listar Especialidades",
                    "3. Pesquisar (Sigla)",
                    "4. Atualizar Nome",
                    "5. Remover Especialidade",
                    "0. Sair"
            };

            for (String opcao2 : opcoes) {
                System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao2) + CYAN_BOLD + "║" + RESET);
            }
            System.out.println(CYAN_BOLD + bordaInferior + RESET);

            System.out.print("\n" + WHITE_BOLD + "Selecione uma opção: " + RESET);
            opcao = ler.nextLine();

            switch (opcao) {
                case "1":
                    registarEspecialidade(ler);
                    break;
                case "2":
                    listarEspecialidade(ler);
                    break;
                case "3":
                    pesquisarEspecialidade(ler);
                    break;
                case "4":
                    atualizarEspecialidade(ler);
                    break;
                case "5":
                    removerEspecialidade(ler);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }
        } while (!opcao.equals("0"));
    }
    //endregion

    //region REGISTAR ESPECIALIDADE
    private static void registarEspecialidade(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- NOVA ESPECIALIDADE ---" + RESET);
        System.out.print("Sigla (ex: ORT, PED, CARD): ");
        String sigla = ler.nextLine().toUpperCase();

        if (ficheiroEspecialidade.existeEspecialidade(sigla)) {
            System.out.println(RED + "Erro: Essa sigla já existe!" + RESET);
        } else {
            System.out.print("Nome descritivo (ex: Ortopedia): ");
            String nome = ler.nextLine();
            Especialidade especialidade = new Especialidade(sigla, nome);
            ficheiroEspecialidade.adicionarEspecialidade(especialidade);
            System.out.println(GREEN + "Especialidade registada com sucesso!" + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region LISTAR ESPECIALIDADE
    private static void listarEspecialidade(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- LISTA DE ESPECIALIDADES ---" + RESET);
        Especialidade[] lista = ficheiroEspecialidade.getLista();
        int total = ficheiroEspecialidade.getTotal();

        if (total == 0) {
            System.out.println(YELLOW + "Nenhuma especialidade registada." + RESET);
        } else {
            System.out.printf("%-10s %-30s\n", "SIGLA", "DESCRIÇÃO");
            System.out.println("----------------------------------------");
            for (int i = 0; i < total; i++) {
                System.out.printf("%-10s %-30s\n", lista[i].getSigla(), lista[i].getNome());
            }
            pressionarEnter(ler);
        }
    }
    //endregion

    //region PESQUISAR ESPECIALIDADE
    private static void pesquisarEspecialidade(Scanner ler) {
        System.out.print("Qual a sigla a pesquisar? ");
        String sigla = ler.nextLine();
        Especialidade especialidade = ficheiroEspecialidade.procurarEspecialidade(sigla);

        if (especialidade != null) {
            System.out.println(GREEN + "\nEncontrado: " + RESET);
        } else {
            System.out.println(RED + "Não encontrada." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region ATUALIZAR ESPECIALIDADE
    private static void atualizarEspecialidade(Scanner ler) {
        System.out.print("Sigla da especialidade a editar: ");
        String sigla = ler.nextLine();

        if (ficheiroEspecialidade.existeEspecialidade(sigla)) {
            System.out.println(RED + "Especialidade não Existe." + RESET);
        } else {
            System.out.print("Novo Nome: ");
            String novoNome = ler.nextLine();
            ficheiroEspecialidade.atualizarEspecialidade(sigla, novoNome);
            System.out.println(GREEN + "Atualizado com sucesso!" + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region REMOVER ESPECIALIDADE
    private static void removerEspecialidade(Scanner ler) {
        System.out.print("Sigla da especialidade a remover: ");
        String sigla = ler.nextLine();
        if (ficheiroEspecialidade.removerEspecialidade(sigla)) {
            System.out.println(GREEN + "Removido com sucesso." + RESET);
        } else {
            System.out.println(RED + "Não encontrado." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region PESQUISAR ESPECIALIDADE
    private static void pesquisarSintoma(Scanner ler) {
        System.out.print("Nome do sintoma a pesquisar: ");
        String nome = ler.nextLine();
        Sintomas sintomas = ficheirosSintomas.procurarSintoma(nome);

        if (sintomas != null) {
            System.out.println(GREEN + "\nEncontrado: " + RESET);
            System.out.println("Sintoma: " + sintomas.getNomeSintoma());
            System.out.println("Nível: " + sintomas.getNivelSintoma().getNivel() + " (" + sintomas.getNivelSintoma().getCor() + ")");
            if (sintomas.getEspecialidadesAssociadas() != null) {
                System.out.println("Encaminhamento sugerido: " + sintomas.getEspecialidadesAssociadas().getNome());
            }
        } else {
            System.out.println(RED + "Sintoma não encontrado." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion

    //region REMOVER ESPECIALIDADE
    private static void removerSintoma(Scanner ler) {
        System.out.print("Nome do sintoma a remover: ");
        String nome = ler.nextLine();
        if (ficheirosSintomas.removerSintoma(nome)) {
            System.out.println(GREEN + "Sintoma removido com sucesso." + RESET);
        } else {
            System.out.println(RED + "Sintoma não encontrado." + RESET);
        }
        pressionarEnter(ler);
    }
    //endregion
    //endregion

    //region SINTOMAS
    //region MENU SINTOMAS
    private static void menuSintomas(Scanner ler) {
        String opcao = "";

        do {
            System.out.println("\033[H\033[2J");
            System.out.flush();

            String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
            String bordaMeio = "╠" + "═".repeat(LARGURA) + "╣";
            String bordaInferior = "╚" + "═".repeat(LARGURA) + "╝";

            String titulo = "GESTÃO DE SINTOMAS (TRIAGEM)";
            int paddingTitulo = (LARGURA - titulo.length()) / 2;
            String textoTitulo = String.format("%" + paddingTitulo + "s%s%-" + paddingTitulo + "s", "", titulo, "");
            if (textoTitulo.length() < LARGURA) textoTitulo += " ";

            System.out.println(CYAN_BOLD + bordaSuperior + RESET);
            System.out.println(CYAN_BOLD + "║" + WHITE_BOLD + textoTitulo + CYAN_BOLD + "║" + RESET);
            System.out.println(CYAN_BOLD + bordaMeio + RESET);

            String[] opcoes = {
                    "1. Registar Novo Sintoma",
                    "2. Listar Sintomas e Cores",
                    "3. Pesquisar Sintoma",
                    "4. Remover Sintoma",
                    "0. Sair"
            };

            for (String opcao2 : opcoes) {
                System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao2) + CYAN_BOLD + "║" + RESET);
            }
            System.out.println(CYAN_BOLD + bordaInferior + RESET);

            System.out.print("\n" + WHITE_BOLD + "Selecione uma opção: " + RESET);
            opcao = ler.nextLine();

            switch (opcao) {
                case "1":
                    registarSintomas(ler);
                    break;
                case "2":
                    listarSintomas(ler);
                    break;
                case "3":
                    pesquisarSintoma(ler);
                    break;
                case "4":
                    removerSintoma(ler);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }
        } while (!opcao.equals("0"));
    }
    //endregion

    //region REGISTAR SINTOMAS
    private static void registarSintomas(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- NOVO SINTOMA ---" + RESET);
        System.out.print("Nome do Sintoma (ex: Febre Alta): ");
        String nome = capitalize(ler.nextLine());

        if (ficheirosSintomas.procurarSintoma(nome) != null) {
            System.out.println(RED + "Erro: Esse Sintoma já existe!" + RESET);
            pressionarEnter(ler);
            return;
        } else {

            System.out.println("\nSelecione o Nível de Urgência (Cor da Pulseira):");
            NivelSintomas[] nivelSintomas = NivelSintomas.values();

            for (int i = 0; i < nivelSintomas.length; i++) {
                System.out.printf("%d - %s (%s)\n", (i + 1), nivelSintomas[i].getCor(), nivelSintomas[i].getNivel());
            }
            System.out.print("Opção: ");
            int opcao = -1;
            try {
                opcao = Integer.parseInt(ler.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            if (opcao < 1 || opcao > nivelSintomas.length) {
                System.out.println(RED + "Opção inválida." + RESET);
                pressionarEnter(ler);
                return;
            }
            NivelSintomas opcaoSelecionado = nivelSintomas[opcao -1];
            System.out.println("\nAssociar a qual a especialidade? (Sigla)");
            for (Especialidades especialidade : Especialidades.values()) {
                System.out.println(especialidade.getCodigo() + " ");
            }
            System.out.print("\nSigla: ");
            String siglaIntroduzida = ler.nextLine().toUpperCase();

            Especialidades especialidades = null;
            for (Especialidades especialidades2 : Especialidades.values()) {
                if (especialidades2.getCodigo().equals(siglaIntroduzida)) {
                    especialidades = especialidades2;
                    break;
                }
            }

            if (especialidades == null) {
                System.out.println(YELLOW + "Erro: Sigla não encontrada no sistema. Será registado sem especialidade automática." + RESET);
            }
            Sintomas sintomas = new Sintomas(nome,opcaoSelecionado,especialidades);
            ficheirosSintomas.adicionarSintoma(sintomas);

            System.out.println(GREEN + "Sintoma registado com sucesso!" + RESET);
            pressionarEnter(ler);
        }
    }
    //endregion

    //region LISTAR SINTOMAS
    private static void listarSintomas(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- LISTA DE SINTOMAS ---" + RESET);
        Sintomas[] lista = ficheirosSintomas.getSintomas();

        if (lista == null) {
            System.out.println(YELLOW + "Nenhuma sintomaregistado." + RESET);
        } else {
            System.out.printf("%-20s %-15s %-15s %-20s\n", "NOME", "COR", "URGÊNCIA", "ESPECIALIDADE");
            System.out.println("--------------------------------------------------------------------------");
            for (Sintomas sintomas : lista) {
                if (sintomas != null) {
                    String corTexto = RESET;
                    if (sintomas.getNivelSintoma() == NivelSintomas.VERMELHA) corTexto = RED;
                    else if (sintomas.getNivelSintoma() == NivelSintomas.LARANJA) corTexto = YELLOW;
                    else if (sintomas.getNivelSintoma() == NivelSintomas.VERDE) corTexto = GREEN;

                    String nomeEspecialidade = (sintomas.getEspecialidadesAssociadas() != null) ? sintomas.getEspecialidadesAssociadas().getNome() : "---";

                    System.out.printf("%-20s " + corTexto + "%-15s %-15s" + RESET + " %-20s\n",
                            sintomas.getNomeSintoma(),
                            sintomas.getNivelSintoma().getCor(),
                            sintomas.getNivelSintoma().getNivel(),
                            nomeEspecialidade);
                }
            }
        }
        pressionarEnter(ler);
    }
    //endregion
    //endregion

    //region Configurações e Estatísticas
    private static void gerirHospital(Scanner ler) {
        avancarUmaHora(ler);
    }

    private static void consultarEstatisticas(Scanner ler) {
        System.out.println("\n" + CYAN_BOLD + "--- ESTATÍSTICAS DO HOSPITAL (Dia " + dia + ") ---" + RESET);

        Medico[] medicos = ficheiroMedicos.getListaMedicos();
        Utente[] utentes = ficheiroUtentes.getPacientes();
        int totalUtentes = ficheiroUtentes.getTotalDePacientes();
        int totalMedicos = ficheiroMedicos.getTotalMedicos();

        System.out.println("\n" + WHITE_BOLD + "1. Média de Utentes Atendidos/Dia" + RESET);
        int totalAtendimentos = 0;
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i] != null) {
                totalAtendimentos += medicos[i].getTotalPacientesAtendidos();
            }
        }
        double media = (dia > 0) ? (double) totalAtendimentos / dia : 0;
        System.out.printf("Total Atendidos: %d | Média Diária: %.2f utentes/dia\n", totalAtendimentos, media);


        System.out.println("\n" + WHITE_BOLD + "2. Salários Diários Previstos" + RESET);
        System.out.printf("%-20s %-10s %-10s %s\n", "MÉDICO", "CARGA(H)", "€/HORA", "TOTAL/DIA");
        System.out.println("-------------------------------------------------------");

        for (int i = 0; i < totalMedicos; i++) {
            Medico m = medicos[i];
            if (m != null) {
                int horasTrabalho = m.getHoraSaida() - m.getHoraEntrada();
                if (horasTrabalho < 0) horasTrabalho += 24;

                double salarioDia = horasTrabalho * m.getSalarioHora();

                System.out.printf("%-20s %-10d %-10.2f %.2f €\n",
                        m.getNomeMedico(), horasTrabalho, m.getSalarioHora(), salarioDia);
            }
        }


        System.out.println("\n" + WHITE_BOLD + "3. Ocorrência de Sintomas" + RESET);
        Sintomas[] listaSintomas = ficheirosSintomas.getSintomas(); // Pega a lista base de sintomas

        if (listaSintomas != null) {
            for (Sintomas sRef : listaSintomas) {
                if (sRef != null) {
                    int contador = 0;
                    for (int u = 0; u < totalUtentes; u++) {
                        if (utentes[u] != null && utentes[u].getSintomas() != null) {
                            for (Sintomas sUtente : utentes[u].getSintomas()) {
                                if (sUtente != null && sUtente.getNomeSintoma().equalsIgnoreCase(sRef.getNomeSintoma())) {
                                    contador++;
                                }
                            }
                        }
                    }
                    if (contador > 0) {
                        System.out.printf("- %-20s: %d casos\n", sRef.getNomeSintoma(), contador);
                    }
                }
            }
        }

        System.out.println("\n" + WHITE_BOLD + "4. Top 3 Especialidades (%)" + RESET);

        Especialidade[] especialidades = ficheiroEspecialidade.getLista();
        int qtdEspec = ficheiroEspecialidade.getTotal();

        String[] nomesEsp = new String[qtdEspec];
        int[] contagens = new int[qtdEspec];

        for (int i = 0; i < qtdEspec; i++) {
            nomesEsp[i] = especialidades[i].getSigla();
            contagens[i] = 0;

            for (int u = 0; u < totalUtentes; u++) {
                if (utentes[u] != null &&
                        utentes[u].getEspecialidadeEncaminhada() != null &&
                        utentes[u].getEspecialidadeEncaminhada().equalsIgnoreCase(especialidades[i].getSigla())) {
                    contagens[i]++;
                }
            }
        }

        for (int i = 0; i < qtdEspec - 1; i++) {
            for (int j = 0; j < qtdEspec - 1 - i; j++) {
                if (contagens[j] < contagens[j + 1]) {
                    int tempC = contagens[j];
                    contagens[j] = contagens[j + 1];
                    contagens[j + 1] = tempC;
                    String tempN = nomesEsp[j];
                    nomesEsp[j] = nomesEsp[j + 1];
                    nomesEsp[j + 1] = tempN;
                }
            }
        }

        int top = Math.min(3, qtdEspec);
        for (int i = 0; i < top; i++) {
            double percentagem = (totalUtentes > 0) ? ((double) contagens[i] / totalUtentes) * 100 : 0;
            System.out.printf("%dº. %-10s: %d utentes (%.1f%%)\n", (i + 1), nomesEsp[i], contagens[i], percentagem);
        }

        pressionarEnter(ler);
    }

    private static void configuracoes(Scanner ler) {
        System.out.println("\n--- ÁREA DE CONFIGURAÇÕES (ADMIN) ---");

        System.out.print("Password: ");
        String pass = ler.nextLine().trim();
        if (!pass.equals(PASSWORD)) {
            System.out.println(RED + "Acesso Negado." + RESET);
            pressionarEnter(ler);
            return;
        }

        String opcao = "";
        do {
            System.out.println("\n" + CYAN_BOLD + "--- CONFIGURAÇÕES ATUAIS ---" + RESET);
            System.out.println("1. Tempos de Consulta (Atual: " + TEMPO_CONSULTA_VERDE + "/" + TEMPO_CONSULTA_LARANJA + "/" + TEMPO_CONSULTA_VERMELHO + ")");
            System.out.println("2. Tempos de Agravamento (Atual: " + TEMPO_AGRAVAMENTO_VERDE + "/" + TEMPO_AGRAVAMENTO_LARANJA + "/" + TEMPO_SAIDA_VERMELHO + ")");
            System.out.println("0. Voltar");

            System.out.print("Opção: ");
            opcao = ler.nextLine();

            try {
                switch (opcao) {
                    case "1":
                        System.out.print("Novo tempo Baixa (Verde): ");
                        TEMPO_CONSULTA_VERDE = Integer.parseInt(ler.nextLine());
                        System.out.print("Novo tempo Média (Laranja): ");
                        TEMPO_CONSULTA_LARANJA = Integer.parseInt(ler.nextLine());
                        System.out.print("Novo tempo Urgente (Vermelho): ");
                        TEMPO_CONSULTA_VERMELHO = Integer.parseInt(ler.nextLine());
                        System.out.println(GREEN + "Tempos atualizados!" + RESET);
                        break;
                    case "2":
                        System.out.print("Horas p/ agravar Verde->Laranja: ");
                        TEMPO_AGRAVAMENTO_VERDE = Integer.parseInt(ler.nextLine());
                        System.out.print("Horas p/ agravar Laranja->Vermelho: ");
                        TEMPO_AGRAVAMENTO_LARANJA = Integer.parseInt(ler.nextLine());
                        System.out.print("Horas p/ saída (Vermelho): ");
                        TEMPO_SAIDA_VERMELHO = Integer.parseInt(ler.nextLine());
                        System.out.println(GREEN + "Critérios atualizados!" + RESET);
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Erro: Insira apenas números inteiros." + RESET);
            }
        } while (!opcao.equals("0"));
    }

    private static void pressionarEnter(Scanner ler) {
        System.out.print("\nPressione " + WHITE_BOLD + "ENTER" + RESET + " para voltar ao menu...");
        ler.nextLine();
    }

    private static void listarFilaEspera(Scanner ler) {
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println(CYAN_BOLD + "--- LISTA DE ESPERA (TRIAGEM FEITA) ---" + RESET);

        Utente[] lista = ficheiroUtentes.getPacientes();
        int total = ficheiroUtentes.getTotalDePacientes();
        int naFila = 0;

        System.out.printf(WHITE_BOLD + "%-20s %-15s %-10s %-15s %s\n" + RESET,
                "NOME", "URGÊNCIA", "ESPERA", "ESPECIALIDADE", "STATUS");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < total; i++) {
            Utente u = lista[i];

            if (u != null && !u.isEmAtendimento()) {
                naFila++;

                String corUrgencia = RESET;
                if (u.getNivelSintoma() == NivelSintomas.VERMELHA) corUrgencia = RED;
                else if (u.getNivelSintoma() == NivelSintomas.LARANJA) corUrgencia = YELLOW;
                else if (u.getNivelSintoma() == NivelSintomas.VERDE) corUrgencia = GREEN;

                String nomeEspecialidade = (u.getEspecialidadeEncaminhada() != null) ? u.getEspecialidadeEncaminhada() : "---";

                System.out.printf("%-20s " + corUrgencia + "%-15s" + RESET + " %-10s %-15s %s\n",
                        u.getNomeUtente(),
                        u.getNivelSintoma().getCor(),
                        u.getTempoEsperaAtual() + "h",
                        nomeEspecialidade,
                        "AGUARDA VAGA");
            }
        }

        if (naFila == 0) {
            System.out.println(GREEN + "\nExcelente! A sala de espera está vazia." + RESET);
        } else {
            System.out.println("\nTotal em espera: " + naFila);
        }

        pressionarEnter(ler);
    }

    private static String calcularEspecialidadeAutomaticamente(Sintomas[] sintomasEscolhidos, int qtd) {
        if (qtd == 0) return "Geral";

        NivelSintomas urgenciaMaxima = NivelSintomas.VERDE;

        for (int i = 0; i < qtd; i++) {
            NivelSintomas nivelAtual = sintomasEscolhidos[i].getNivelSintoma();

            if (nivelAtual == NivelSintomas.VERMELHA) {
                urgenciaMaxima = NivelSintomas.VERMELHA;
                break;
            }
            if (nivelAtual == NivelSintomas.LARANJA && urgenciaMaxima == NivelSintomas.VERDE) {
                urgenciaMaxima = NivelSintomas.LARANJA;
            }
        }

        String[] nomesEncontrados = new String[qtd];
        int[] contadores = new int[qtd];
        int totalNomes = 0;

        for (int i = 0; i < qtd; i++) {
            if (sintomasEscolhidos[i].getNivelSintoma() == urgenciaMaxima) {

                Especialidades especialidadeEnum = sintomasEscolhidos[i].getEspecialidadesAssociadas();
                if (especialidadeEnum == null) continue;

                String nomeEspecialidade = especialidadeEnum.name();


                boolean encontrado = false;
                for (int k = 0; k < totalNomes; k++) {
                    if (nomesEncontrados[k].equals(nomeEspecialidade)) {
                        contadores[k]++;
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    nomesEncontrados[totalNomes] = nomeEspecialidade;
                    contadores[totalNomes] = 1;
                    totalNomes++;
                }
            }
        }

        String maiorQtdDeSintomasIguais = null;
        int maiorContagem = -1;

        for (int i = 0; i < totalNomes; i++) {
            if (contadores[i] > maiorContagem) {
                maiorContagem = contadores[i];
                maiorQtdDeSintomasIguais = nomesEncontrados[i];
            }
        }

        return maiorQtdDeSintomasIguais;
    }

    private static void avancarUmaHora(Scanner ler) {

        if (hora == 24) {
            hora = 0;
            dia++;
            System.out.println(YELLOW + ">>> UM NOVO DIA COMEÇOU (Dia " + dia + ") <<<" + RESET);
        } else {
            hora++;
        }

        System.out.println(YELLOW + "\n>>> AVANÇANDO TEMPO... (Nova Hora: " + hora + ":00)" + RESET);
        String notificacoes = "";


        Medico[] medicos = ficheiroMedicos.getListaMedicos();
        for (int i = 0; i < ficheiroMedicos.getTotalMedicos(); i++) {
            if (medicos[i] != null) {
                String logMedico = medicos[i].processarHora(hora);
                if (!logMedico.isEmpty()) notificacoes += logMedico;
            }
        }

        Utente[] utentes = ficheiroUtentes.getPacientes();
        int totalUtentes = ficheiroUtentes.getTotalDePacientes();

        for (int i = 0; i < totalUtentes; i++) {
            Utente utente = utentes[i];

            if (utente != null && !utente.isEmAtendimento()) {
                utente.incrementarEspera();
                int espera = utente.getTempoEsperaAtual();
                NivelSintomas nivel = utente.getNivelSintoma();

                if (nivel == NivelSintomas.VERDE && espera >= TEMPO_AGRAVAMENTO_VERDE) {
                    utente.setNivelSintoma(NivelSintomas.LARANJA);
                    utente.resetarTempoEspera();
                    notificacoes += " ! ATENÇÃO: Utente " + utente.getNomeUtente() + " agravou para LARANJA (Espera excessiva).\n";
                }

            else if (nivel == NivelSintomas.LARANJA && espera >= TEMPO_AGRAVAMENTO_LARANJA) {
                    utente.setNivelSintoma(NivelSintomas.VERMELHA);
                    utente.resetarTempoEspera();
                    notificacoes += " !!! PERIGO: Utente " + utente.getNomeUtente() + " agravou para VERMELHO.\n";
                }

            else if (nivel == NivelSintomas.VERMELHA && espera >= TEMPO_SAIDA_VERMELHO) {
                    notificacoes += " X UTENTE PERDIDO: " + utente.getNomeUtente() + " abandonou/transferido por falta de atendimento.\n";

                    if (ficheiroUtentes.removerUtente(utente)) {
                        i--;
                        totalUtentes--;
                    }
                }
            }
        }

        String logAtribuicao = atribuirPacientesAutomaticamente();

        if (!logAtribuicao.isEmpty()) {
            System.out.println(CYAN_BOLD + "--- NOVAS ATRIBUIÇÕES ---" + RESET);
            System.out.println(logAtribuicao);
        }

        if (!notificacoes.isEmpty()) {
            System.out.println(CYAN_BOLD + "--- REGISTO DE OCORRÊNCIAS ---" + RESET);
            System.out.println(notificacoes);
        } else {
            System.out.println("Nenhuma alteração crítica registada nesta hora.");
        }

        pressionarEnter(ler);
    }

    private static String atribuirPacientesAutomaticamente() {
        StringBuilder log = new StringBuilder();
        Medico[] medicos = ficheiroMedicos.getListaMedicos();
        Utente[] listaEspera = ficheiroUtentes.getPacientes();

        for (int i = 0; i < ficheiroMedicos.getTotalMedicos(); i++) {
            Medico medico = medicos[i];

            if (medico != null && medico.isDisponivel(hora) && !medico.isEmDescanso()) {

                Utente candidato = null;

                for (int j = 0; j < ficheiroUtentes.getTotalDePacientes(); j++) {
                    Utente utente = listaEspera[j];

                    if (utente != null && !utente.isEmAtendimento() && utente.getEspecialidadeEncaminhada() != null && medico.getEspecialidade() != null && utente.getEspecialidadeEncaminhada().equalsIgnoreCase(medico.getEspecialidade().getCodigo())) {
                        if (candidato == null) {
                            candidato = utente;
                        }
                        else {
                            int pesoAtual = getNivelDeUrgencia(utente.getNivelSintoma());
                            int pesoCandidato = getNivelDeUrgencia(candidato.getNivelSintoma());

                            if (pesoAtual > pesoCandidato) {
                                candidato = utente;
                            }
                            else if (pesoAtual == pesoCandidato) {
                                if (utente.getHoraEntrada() < candidato.getHoraEntrada()) {
                                    candidato = utente;
                                }
                            }
                        }
                    }
                }

                if (candidato != null) {
                    medico.iniciarAtendimento(candidato);
                    candidato.setMedico(medico);
                    candidato.setEmAtendimento(true);

                    log.append(GREEN + " -> SUCESSO: " + candidato.getNomeUtente()
                            + " (" + candidato.getNivelSintoma() + ") encaminhado para Dr. "
                            + medico.getNomeMedico() + "\n" + RESET);
                }
            }
        }
        return log.toString();
    }

    private static int getNivelDeUrgencia(NivelSintomas nivel) {
        if (nivel == null) return 0;

        switch (nivel.name()) {
            case "VERMELHO": return 3;
            case "LARANJA": return 2;
            case "VERDE": return 1;
            default: return 0;
        }
    }
    //endregion

}