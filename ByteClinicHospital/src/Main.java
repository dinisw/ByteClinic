import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



void main() {
    Medico[] medicos = FicheiroMedicos.getMedicos(); //Ver como carregar os médicos pra cá
    Medico[] sintomas = FicheirosSintomas.getMedicos(); //Ver como carregar os sintomas pra cá
    Medico[] especialidades = FicheiroMedicos.getMedicos(); //Ver como especialidades os médicos pra cá
    Scanner ler = new Scanner(System.in);
    exibirTitulo();
    exibirMenu();

    System.out.print("\n" + WHITE_BOLD + "Digite a opção desejada: " + RESET);
    String opcao = ler.nextLine();
    do{

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

public static final String PASSWORD = "admin123";

public static void exibirTitulo() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    
    String bordaSuperior = "╔" + "═".repeat(LARGURA) + "╗";
    String bordaMeio     = "╠" + "═".repeat(LARGURA) + "╣";
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

    String bordaMeio     = "╠" + "═".repeat(LARGURA) + "╣";
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

private static void menuMedicos(Scanner ler) {
    System.out.println("\n--- Módulo de Médicos ---");
    System.out.println("Aqui você poderá cadastrar, listar e editar médicos.");
    pressionarEnter(ler);
}

private static void menuEspecialidades(Scanner ler) {
    System.out.println("\n--- Módulo de Especialidades ---");
    pressionarEnter(ler);
}

private static void menuSintomas(Scanner ler) {
    System.out.println("\n--- Módulo de Sintomas ---");
    pressionarEnter(ler);
}

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

        if(!tentativaDeAcesso.equals(PASSWORD) && !tentativaDeAcesso.equals("0")){
            System.out.println("Senha incorreta, tente novamente...");
            continue;
        }
        pressionarEnter(ler);
    }while (!tentativaDeAcesso.equals("0"));
}

private static void pressionarEnter(Scanner ler) {
    System.out.print("\nPressione " + WHITE_BOLD + "ENTER" + RESET + " para voltar ao menu...");
    ler.nextLine();
}