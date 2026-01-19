import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FicheiroEspecialidade {
    private Especialidade[] listaEspecialidade;
    private int totalEspecialidade;

    public FicheiroEspecialidade() {
        listaEspecialidade = new Especialidade[100];
        totalEspecialidade = 0;
    }

    public void adicionarEspecialidade(Especialidade especialidade) {
        if(totalEspecialidade == listaEspecialidade.length) {
            expandirArray();
        }
        listaEspecialidade[totalEspecialidade++] = especialidade;
    }

    public void expandirArray() {
        Especialidade[] novo =  new Especialidade[listaEspecialidade.length * 2];
        for (int i = 0; i < totalEspecialidade; i++) {
            novo[i] = listaEspecialidade[i];
        }
        this.listaEspecialidade = novo;
    }

    public void carregarFicheiro(){
        File ficheiro = new File("especialidade.txt");
        if(!ficheiro.exists()) {
            System.out.println("Erro ao ler o ficheiro!");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while (ler.hasNext()) {
                String linha = ler.next();
                if(linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if(dados.length >= 2){
                    String sigla =  dados[0];
                    String nome =  dados[1];

                    Especialidade especialidade = new Especialidade(sigla, nome);
                    adicionarEspecialidade(especialidade);
                }
            }
            ler.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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

        System.out.println("Removido com sucesso!");
        return true;
    }

    public boolean ecisteEspecialidade(String sigla) {
        for(int i = 0; i < totalEspecialidade; i++) {
            if(listaEspecialidade[i].getSigla().equalsIgnoreCase(sigla)) {
                return true;
            }
        }
        return false;
    }

    public String procurarEspecialidade(String sigla) {
        for(int i = 0; i < totalEspecialidade; i++) {
            if(listaEspecialidade[i].getSigla().equalsIgnoreCase(sigla)) {
                return  listaEspecialidade[i].getNome();
            }
        }
        return null;
    }

    public boolean atualizarSintoma(String sigla, String novoNome){
        for(int i = 0; i < totalEspecialidade; i++) {
            if(listaEspecialidade[i].getNome().equalsIgnoreCase(sigla)) {
                listaEspecialidade[i].setNome(novoNome);
                return true;
            }
        }
        return false;
    }
}
