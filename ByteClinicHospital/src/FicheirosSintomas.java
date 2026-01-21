import javax.xml.transform.Source;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        if(totalSintomas == listaSintomas.length) {
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
    public void carregarSintomas() {
        File ficheiro = new File("sintomas.txt");
        if(!ficheiro.exists()) {
            GestorLogs.registarErro("FicheiroSintomas", "Ficheiro sintomas.txt não encontrado. Será criado um novo.");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while(ler.hasNextLine()) {
                String linha = ler.nextLine();
                if(linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if(dados.length >= 2) {
                    try {
                        String nome = dados[0];
                        NivelSintomas nivelSintomas = NivelSintomas.valueOf(dados[1]);
                        Especialidades especialidade = null;
                        if (dados.length > 2 && !dados[2].equals("NA")) {
                            for (Especialidades especialidades2 : Especialidades.values()) {
                                if (especialidades2.getCodigo().equalsIgnoreCase(dados[2])) {
                                    especialidade = especialidades2;
                                    break;
                                }
                            }
                        }
                        adicionarSintoma(new Sintomas(nome, nivelSintomas, especialidade));
                    } catch (IllegalArgumentException e) {
                        GestorLogs.registarErro("FicheirosSintomas", "Dados corrompidos na linha: " + linha);
                    }
                }
            }
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
    public void guardarSintomas() {
        try {
            Formatter out = new Formatter(new FileWriter("sintomas.txt"));
            for(int i = 0; i < totalSintomas; i++) {
                out.format("%s%n", listaSintomas[i].paraFicheiro());
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
        for(int i = 0; i < totalSintomas; i++) {
            if(listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) {
                contador = i;
                break;
            }
        }
        if (contador == -1) {
            return false;
        }
        for ( int i = contador; i < totalSintomas - 1; i++) {
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
        for(int i = 0; i < totalSintomas; i++) {
            if(listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) return  listaSintomas[i];
        }
        return null;
    }
    //endregion

    //region ATUALIZAR SINTOMA
    public boolean atualizarSintoma(String nomeSintoma, NivelSintomas nivelSintomas) {
        Sintomas sintomas = procurarSintoma(nomeSintoma);
        if(sintomas != null) {
            sintomas.setNivelSintoma(nivelSintomas);
            GestorLogs.registarSucesso("Nível de urgência atuaizado para o sintoma:" + nomeSintoma);
            return true;
        }
        return false;
    }
    //endregion

    public Sintomas[] getSintomas() {
        return listaSintomas;
    }
}
