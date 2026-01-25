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
                    Especialidade especialidade2 = new Especialidade(dados[0], dados[1]);
                    adicionarEspecialidade(especialidade2);
                }
            }
            System.out.println("Especialidades carregadas: " + totalEspecialidade);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o ficheiro: " + e.getMessage());
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
            System.out.println("Erro ao guardar ficheiro" + e.getMessage());
        }
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
        if(contador == -1) return false;

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
    //endregion

    public boolean atualizarEspecialidade(String sigla, String novoNome){
        Especialidade especialidade = procurarEspecialidade(sigla);
        if (especialidade != null) {
            especialidade.setNome(novoNome);
            return true;
        }
        return false;
    }
    public Especialidade[] getLista() { return listaEspecialidade; }
    public int getTotal() { return totalEspecialidade; }
}
