import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static FicheiroMedicos ficheiroMedicos = new FicheiroMedicos();
    private static FicheiroEspecialidade ficheiroEspecialidade = new FicheiroEspecialidade();
    private static FicheirosSintomas ficheirosSintomas = new FicheirosSintomas();
    private static FicheiroUtentes ficheiroUtentes = new FicheiroUtentes();

    private static int hora = 0;

    //Design
    public static final String RESET = "\033[0m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String BLUE = "\033[0;34m";
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\033[0;33m";
    private static final int LARGURA = 84;
    //endregion

    public static void main(String[] args) {
        //Medico[] medicos = FicheiroMedicos.getMedicos(); //Ver como carregar os médicos pra cá
        //Medico[] sintomas = FicheirosSintomas.getMedicos(); //Ver como carregar os sintomas pra cá
        //Medico[] especialidades = FicheiroMedicos.getMedicos(); //Ver como especialidades os médicos pra cá
        Scanner ler = new Scanner(System.in);
        exibirTitulo();
        exibirMenu();

        System.out.print("\n" + WHITE_BOLD + "Digite a opção desejada: " + RESET);
        String opcao = ler.nextLine();
        do {

            switch (opcao) {
                case "1":
                    menuMedicos(ler);
                    break;
                case "2":
                    menuEspecialidades(ler);
                    break;
                case "3":
                    menuSintomas(ler);
                    break;
                case "4":
                    gerirHospital(ler);
                    break;
                case "5":
                    consultarEstatisticas(ler);
                    break;
                case "6":
                    configuracoes(ler);
                    break;
                case "0":
                    System.out.println("\n" + YELLOW + "Encerrando o sistema... Até logo!" + RESET);
                    break;
                default:
                    System.out.println("\n" + RED + "Opção inválida! Tente novamente." + RESET);
                    pressionarEnter(ler);
            }

        } while (!opcao.equals("0"));

    }

    public static final String PASSWORD = "admin123";

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
        String statusValor = "ONLINE";
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
                "1. Médicos",
                "2. Especialidades",
                "3. Sintomas",
                "4. Gerir o funcionamento do hospital",
                "5. Consultar estatísticas e logs",
                "6. Configurações",
                "0. Sair"
        };

        for (String opcao : opcoes) {
            System.out.println(CYAN_BOLD + "║ " + RESET + String.format("%-" + (LARGURA - 1) + "s", opcao) + CYAN_BOLD + "║" + RESET);
        }
        System.out.println(CYAN_BOLD + bordaInferior + RESET);
    }

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
        ficheiroMedicos.carregarFicheiro("medicos.txt");
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
            System.out.print("Especialidade (Sigla): ");
            String especialidade = ler.nextLine();
            System.out.print("Hora Entrada (0-23): ");
            int horaEntrada = Integer.parseInt(ler.nextLine());
            System.out.print("Hora Saída (0-23): ");
            int horaSaida = Integer.parseInt(ler.nextLine());
            System.out.println("Salário Hora: ");
            int salario = Integer.parseInt(ler.nextLine());
            ler.nextLine();
            Medico medicos = new Medico(nome,cedula,especialidade,horaEntrada,horaSaida, salario);
            ficheiroMedicos.adicionarMedico(medicos);
            ficheiroMedicos.guardarFicheiroMedico(medicos, "medicos.txt");
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
        ficheiroMedicos.carregarFicheiro("medicos.txt");
        Medico[] lista = ficheiroMedicos.getListaMedicos();
        int total = ficheiroMedicos.getTotalMedicos();
        int horaAtual = LocalDateTime.now().getHour();

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
        System.out.print("Cédula do médico a editar: ");
        int cedula = Integer.parseInt(ler.nextLine());

        if (ficheiroMedicos.procurarMedicoPorCedula(cedula) == null) {
            System.out.println(RED + "Não Existe." + RESET);
            return;
        }
        System.out.print("Novo Nome: "); String nome = ler.nextLine();
        System.out.print("Nova Especialidade: "); String especialidade = ler.nextLine();
        System.out.print("Nova Entrada: "); int entrada = Integer.parseInt(ler.nextLine());
        System.out.print("Nova Saída: "); int saida= Integer.parseInt(ler.nextLine());
        System.out.print("Novo Salário: "); int salario = Integer.parseInt(ler.nextLine());

        ficheiroMedicos.atualizarMedico(cedula,nome,especialidade,entrada,saida,salario);
        System.out.println(GREEN + "Atualizado com sucesso" + RESET);
    }
    //endregion

    //region REMOVER MEDICO
    private static void removerMedico(Scanner ler) {
        ficheiroMedicos.carregarFicheiro("medicos.txt");
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
        System.out.print("Qual a especialidade? ");
        String especialidade = ler.nextLine();

        Medico[] lista = ficheiroMedicos.procurarMedicoPorEspecialidade(especialidade);

        if (lista != null) {
            for (Medico medico : lista) {
                System.out.println(medico);
            }
        } else {
            System.out.println(RED + "Nenhum médico com essa especialidade." + RESET);
        }
        ler.nextLine();
    }
    //endregion


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
        String nome = ler.nextLine().toUpperCase();

        if (ficheirosSintomas.procurarSintoma(nome) != null) {
            System.out.println(RED + "Erro: Esse Sintoma já existe!" + RESET);
            pressionarEnter(ler);
            return;
        } else {

            System.out.println("\nSelecione o Nível de Urgência (Cor da Pulseira):");
            NivelSintoma[] nivelSintomas = NivelSintoma.values();

            for (int i = 0; i < nivelSintomas.length; i++) {
                System.out.printf("%d - %s (%s)", (i + 1), nivelSintomas[i].getCor(), nivelSintomas[i].getNivel());
            }
            System.out.println("Opção: ");
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
            NivelSintoma opcaoSelecionado = nivelSintomas[opcao -1];
            System.out.println("\nAssociar a qual a especialidade? (Sigla)");
            for (Especialidades especialidade : Especialidades.values()) {
                System.out.println(especialidade.getCodigo() + " ");
            }
            System.out.println("\nSigla: ");
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
            ficheirosSintomas.adicionarSintoma(novoSintoma);

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
                    if (sintomas.getNivelSintoma() == NivelSintoma.VERMELHO) corTexto = RED;
                    else if (sintomas.getNivelSintoma() == NivelSintoma.LARANJA) corTexto = YELLOW;
                    else if (sintomas.getNivelSintoma() == NivelSintoma.VERDE) corTexto = GREEN;

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

    private static void gerirHospital(Scanner ler) {
        System.out.println("\n--- Gestão do Hospital ---");
        pressionarEnter(ler);
    }

    private static void consultarEstatisticas(Scanner ler) {
        System.out.println("\n--- Estatísticas e Logs ---");
        pressionarEnter(ler);
    }

    private static void configuracoes(Scanner ler) {
        System.out.println("\n--- Configurações (0 para voltar ao menu anterior) ---");
        String tentativaDeAcesso = "";
        do {
            System.out.print("Digite a password para acessar esse menu: ");
            tentativaDeAcesso = ler.nextLine().trim().toLowerCase();

            if (!tentativaDeAcesso.equals(PASSWORD) && !tentativaDeAcesso.equals("0")) {
                System.out.println("Senha incorreta, tente novamente...");
                continue;
            }
            pressionarEnter(ler);
        } while (!tentativaDeAcesso.equals("0"));
    }

    private static void pressionarEnter(Scanner ler) {
        System.out.print("\nPressione " + WHITE_BOLD + "ENTER" + RESET + " para voltar ao menu...");
        ler.nextLine();
    }
}