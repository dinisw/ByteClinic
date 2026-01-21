import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class GestorLogs {
    private static final  String FICHEIRO_LOGS = "logs.txt";
    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void registarErro(String local, String mensagemErro) {
        escreverLog("[ERRO] (" + local + "): " + mensagemErro);
    }

    public static void registarSucesso(String mensagem) {
        escreverLog("[INFO] " + mensagem);
    }

    private static void escreverLog(String texto) {
        try (FileWriter fileWriter = new FileWriter(FICHEIRO_LOGS, true);
        PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String tempo = LocalDateTime.now().format(DATA_HORA);
            printWriter.println(tempo + " - " + texto);
        } catch (IOException e) {
            System.out.println("Não foi possível escrever no ficheiro logs!");
        }
    }

    public static void lerLogs() {
        File ficheiro = new File(FICHEIRO_LOGS);
        if (!ficheiro.exists()) {
            System.out.println("Ainda não existem registos de logs.");
            return;
        }

        System.out.println("\n--- HISTÓRICO DE LOGS ---");
        try (Scanner scanner = new Scanner(ficheiro)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("[ERRO]")) {
                    System.out.println(System.out.println("\033[0;31m" + linha + "\033[0m");
                } else {
                    System.out.println(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler logs: " + e.getMessage());
        }
    }
}
