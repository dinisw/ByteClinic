import javax.xml.transform.Source;
import java.io.*;
import java.util.Formatter;
import java.io.PrintWriter;
import java.util.Scanner;

public class FicheirosSintomas {
    private Sintomas[] listaSintomas;
    private int totalSintomas;

    public FicheirosSintomas() {
        listaSintomas = new Sintomas[100];
        totalSintomas = 0;
    }

    //region ADICIONAR SINTOMAS
    public void adicionarSintoma(Sintomas sintoma) {
        if (totalSintomas == listaSintomas.length) {
            expandirArray();
        }
        listaSintomas[totalSintomas++] = sintoma;
    }
    //endregion

    //region EXPANDIR ARRAY
    public void expandirArray() {
        Sintomas[] expandido = new Sintomas[listaSintomas.length * 2];
        for (int i = 0; i < totalSintomas; i++) {
            expandido[i] = listaSintomas[i];
        }
        this.listaSintomas = expandido;
    }
    //endregion

    public void carregarSintomas(String caminho, String separador, FicheiroEspecialidade ficheiroEspecialidade) {
        File ficheiro = new File(caminho);
        if (!ficheiro.exists()) {
            System.out.println("Erro: Ficheiro " + caminho + " não encontrado.");
            return;
        }

        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(separador);

                if (dados.length >= 2) {
                    String nomeDoSintoma = dados[0].trim();

                    String textoNivel = dados[1].trim().toUpperCase();

                    NivelSintomas nivel;

                    try {
                        nivel = NivelSintomas.valueOf(textoNivel);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Aviso: Nível '" + textoNivel + "' inválido para " + nomeDoSintoma + ". Será definido como VERDE.");
                        nivel = NivelSintomas.VERDE;
                    }

                    Especialidade especialidadeEncontrada = null;

                    if (dados.length > 2) {
                        String codigoLido = dados[2].trim();

                        for (Especialidade especialidade : ficheiroEspecialidade.procurarEspecialidades()) {
                            if (especialidade.getSigla().equalsIgnoreCase(codigoLido)) {
                                especialidadeEncontrada = especialidade;
                                break;
                            }
                        }
                    }

                    Sintomas sintoma = new Sintomas(nomeDoSintoma, nivel, especialidadeEncontrada);
                    adicionarSintoma(sintoma);
                }
                String textoEsp = dados[2].trim().toUpperCase();
                Especialidades esp;
                if (textoEsp.equals("CARD")) esp = Especialidades.CARDIOLOGIA;
                else if (textoEsp.equals("PEDI")) esp = Especialidades.PEDIATRIA;
                else if (textoEsp.equals("ORTO")) esp = Especialidades.ORTOPEDIA;
            }
            Sintomas sintomas = new Sintomas(nome, nivel, esp);
            ler.close();
            System.out.println("Sintomas carregados: " + totalSintomas);

        } catch (FileNotFoundException e) {
            System.out.println("Erro crítico ao ler ficheiro: " + e.getMessage());
        }
    }
    //endregion

    public void guardarSintomas(String caminho, String separador) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(caminho, false)); // false = sobrescrever
            for(int i = 0; i < totalSintomas; i++) {
                if (listaSintomas[i] != null) {
                String codigoEsp = (listaSintomas[i].getEspecialidadesAssociadas() != null) ? listaSintomas[i].getEspecialidadesAssociadas().getSigla() : "NA";

                writer.println(listaSintomas[i].getNomeSintoma() + separador + listaSintomas[i].getNivelSintoma().name() + separador + codigoEsp);
                }
            }

            writer.close();
            System.out.println("Sintomas guardados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar sintomas" + e.getMessage());
        }
    }
    //endregion

    //region REMOVER SINTOMA
    public boolean removerSintoma(String sintoma) {
        int contador = -1;
        for (int i = 0; i < totalSintomas; i++) {
            if (listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) {
                contador = i;
                break;
            }
        }
        for ( int i = contador; i < totalSintomas - 1; i++) { //Volta para a esquerda para não haver espaços vazios.
            listaSintomas[i] = listaSintomas[i + 1];
        }
        listaSintomas[totalSintomas - 1] = null;
        totalSintomas--;
        GestorLogs.registarSucesso("Sintoma removido do sistema: " + sintoma);
        return true;
    }
    //endregion

    //region PROCURAR SINTOMA
    public Sintomas procurarSintoma(String sintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) return listaSintomas[i];
        }
        return null;
    }
    //endregion

}
