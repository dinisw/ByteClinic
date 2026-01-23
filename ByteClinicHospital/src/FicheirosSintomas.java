import javax.xml.transform.Source;
import java.io.*;
import java.util.Formatter;
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

    //region CARREGAR SINTOMAS
    public void carregarSintomas(String caminho) {
        File ficheiro = new File(caminho);
        if (!ficheiro.exists()) {
            GestorLogs.registarErro("FicheiroSintomas", "Ficheiro sintomas.txt não encontrado. Será criado um novo.");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if (dados.length >= 2) {
                    String nome = dados[0].trim();
                    String textoNivel = dados[1].trim().toUpperCase();
                    NivelSintomas nivel;
                    if (textoNivel.equals("VERMELHA")) {
                        nivel = NivelSintomas.VERMELHO;
                    }
                    nivel = NivelSintomas.valueOf(textoNivel);
                }
                String textoEsp = dados[2].trim().toUpperCase();
                Especialidades esp;
                if (textoEsp.equals("CARD")) esp = Especialidades.CARDIOLOGIA;
                else if (textoEsp.equals("PEDI")) esp = Especialidades.PEDIATRIA;
                else if (textoEsp.equals("ORTO")) esp = Especialidades.ORTOPEDIA;
            }
            Sintomas sintomas = new Sintomas(nome, nivel, esp);
            ler.close();
            GestorLogs.registarSucesso("Sintomas carregados do ficheiro. Total: " + totalSintomas);
            System.out.println("Sintomas carregados: " + totalSintomas);
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar sintomas:" + e.getMessage());
            GestorLogs.registarErro("FicheirosSintomas", "Erro de leitura: " + e.getMessage());
        }
    }
    //endregion

    //region GUARDAR SINTOMA
    public void guardarSintomas(String caminho) {
        try {
            PrintWriter out = new PrintWriter(caminho);
            for (int i = 0; i < totalSintomas; i++) {
                Sintomas sintoma = listaSintomas[i];
                out.printf("%s;%s;%s%n",
                        sintoma.getNomeSintoma(),
                        sintoma.getNivelSintoma(),
                        sintoma.getEspecialidadesAssociadas());
            }
            out.close();
            GestorLogs.registarSucesso("Ficheiro sintomas.txt atualizado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar sintomas" + e.getMessage());
            GestorLogs.registarErro("FicheirosSintomas", "Erro escrita: " + e.getMessage());
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
        if (contador == -1) {
            return false;
        }
        for (int i = contador; i < totalSintomas - 1; i++) {
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

    //region ATUALIZAR SINTOMA
    public boolean atualizarSintoma(String nomeSintoma, NivelSintomas nivelSintomas) {
        Sintomas sintomas = procurarSintoma(nomeSintoma);
        if (sintomas != null) {
            sintomas.setNivelSintoma(nivelSintomas);
            GestorLogs.registarSucesso("Nível de urgência atuaizado para o sintoma:" + nomeSintoma);
            return true;
        }
        return false;
    }
    //endregion

    public Sintomas[] getSintomas() {
        if (listaSintomas.length > 0) return listaSintomas;
        else return null;
    }
}
