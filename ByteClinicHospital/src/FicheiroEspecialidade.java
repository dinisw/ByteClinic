import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class FicheiroEspecialidade {
    private Especialidade[] listaEspecialidade;
    private int totalEspecialidade;

    public FicheiroEspecialidade() {
        this.listaEspecialidade = new Especialidade[100];
        this.totalEspecialidade = 0;
    }

    //region ADICIONAR ESPECIALIDADE
    public void adicionarEspecialidade(Especialidade especialidade) {
        if(totalEspecialidade == listaEspecialidade.length) {
            expandirArray();
        }
        listaEspecialidade[totalEspecialidade++] = especialidade;
    }
    //endregion

    //region EXPANDIR ARRAY
    public void expandirArray() {
        Especialidade[] novo =  new Especialidade[listaEspecialidade.length * 2];
        for (int i = 0; i < totalEspecialidade; i++) {
            novo[i] = listaEspecialidade[i];
        }
        this.listaEspecialidade = novo;
        System.out.println("Array redimensionado para: " + listaEspecialidade.length);
    }
    //endregion

    //region CARREGAR FICHEIRO
    public void carregarFicheiro(String caminho, String separador){
        File ficheiro = new File(caminho);
        if(!ficheiro.exists()) {
            System.out.println("O ficheiro " + caminho + " não existe!");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNextLine()) {
                String linha = ler.nextLine();
                if(linha.trim().isEmpty()) continue;
                String[] dados = linha.split(separador);
                if(dados.length >= 2){
                    String sigla = dados[0].trim();
                    String nome = dados[1].trim();

                    Especialidade especialidade2 = new Especialidade(sigla, nome);
                    adicionarEspecialidade(especialidade2);
                }
            }
            ler.close();
            System.out.println("Especialidades carregados: " + totalEspecialidade);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            GestorLogs.registarErro("FicheiroEspecialidade", "Erro leitura: " + e.getMessage());
        }
    }
    //endregion

    //region GUARDAR FICHEIRO
    public void guardarFicheiro(String caminho, String separador) {
        try {
            PrintWriter out = new PrintWriter (caminho);
            for (int i = 0; i < totalEspecialidade; i++) {
                out.println(listaEspecialidade[i].getSigla() + separador + listaEspecialidade[i].getNome());
            }
            out.close();
            System.out.println("Ficheiro guardado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar ficheiro:" + e.getMessage());
            GestorLogs.registarErro("FicheiroEspecialidade", "Erro escrita: " + e.getMessage());        }
    }
    //endregion

    //region REMOVER ESPECIALIDADE
    public boolean removerEspecialidade(String sigla){
        int contador = -1;
        for (int i = 0; i < totalEspecialidade; i++) {
            if(listaEspecialidade[i].getSigla().equalsIgnoreCase(sigla)) {
                contador = i;
                break;
            }
        }
        if(contador == -1){
            System.out.println("A especialidade não foi encontrado.");
            return false;
        }

        for(int i = contador; i < totalEspecialidade; i++) {
            listaEspecialidade[i] = listaEspecialidade[i+1];
        }
        listaEspecialidade[totalEspecialidade -1] = null;
        totalEspecialidade--;

        return true;
    }
    //endregion

    //region EXISTE ESPECIALIDADE
    public boolean existeEspecialidade(String sigla) {
       return procurarEspecialidade(sigla) != null;
    }
    //endregion

    //region PROCURAR ESPECIALIDADE
    public Especialidade procurarEspecialidade(String sigla) {
        for(int i = 0; i < totalEspecialidade; i++) {
            if(listaEspecialidade[i].getSigla().equals(sigla)) {
                return listaEspecialidade[i];
            }
        }
        return null;
    }

    public Especialidade[] procurarEspecialidades() {
        Especialidade[] resultado = new Especialidade[totalEspecialidade];
        for (int i = 0; i < totalEspecialidade; i++) {
            resultado[i] = listaEspecialidade[i];
        }
        return resultado;
    }


   // public Especialidade[] procurarEspecialidades() {
   //     Especialidade[] especialidades = new Especialidade[listaEspecialidade.length];
   //     int i = 0;
   //     for (var especialidade : listaEspecialidade) {
   //         if (especialidade != null) {
   //             especialidades[i] = especialidade;
   //             i++;
   //         }
   //     }
   //     return especialidades;
   // }
    //endregion

    public void atualizarEspecialidade(String sigla, String novoNome){
        Especialidade especialidade = procurarEspecialidade(sigla);
        if (especialidade != null) {
            especialidade.setNome(novoNome);
            guardarFicheiro("especialidades.txt");
        }
    }
    public Especialidade[] getLista() { return listaEspecialidade; }
    public int getTotal() { return totalEspecialidade; }
}
