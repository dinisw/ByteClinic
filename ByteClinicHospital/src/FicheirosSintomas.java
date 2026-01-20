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

    public void adicionarSintoma(Sintomas sintoma) {
        if(totalSintomas == listaSintomas.length) {
            expandirArray();
        }
        listaSintomas[totalSintomas++] = sintoma;
    }

    public void expandirArray() {
        Sintomas[] expandido = new Sintomas[listaSintomas.length * 2];
        for (int i = 0; i < totalSintomas; i++) {
            expandido[i] = listaSintomas[i];
        }
        this.listaSintomas = expandido;
    }

    public void carregarSintomas() {
        File ficheiro = new File("sintomas.txt");
        if(!ficheiro.exists()) {
            System.out.println("Erro ao carregar sintomas");
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while(ler.hasNext()) {
                String linha = ler.nextLine();
                if(linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if(dados.length >= 2) {
                    String nome = dados[0];
                    String cor = dados[1];

                    int qtdEspecialidades = dados.length - 2;
                    String[] especialidades =  new String[qtdEspecialidades];
                    for(int i = 0; i < qtdEspecialidades; i++) {
                        especialidades[i] = dados[i+2].trim();
                    }

                    Sintomas sintoma = new Sintomas(nome, cor, especialidades);
                    adicionarSintoma(sintoma);
                }
            }
            ler.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar sintomas" + e.getMessage());
        }
    }

    public void guardarSintomas() {
        try {
            Formatter out = new Formatter(new FileWriter("sintomas.txt"));
            for(int i = 0; i < totalSintomas; i++) {
                Sintomas sintoma = listaSintomas[i];
                out.format("%s%n", listaSintomas[i].paraFicheiro());
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar sintomas" + e.getMessage());        }
    }

    public boolean removerSintoma(String sintoma) {
        int contador = -1;
        for(int i = 0; i < totalSintomas; i++) {
            if(listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) {
                contador = i;
                break;
            }
        }
        for ( int i = 0; i < totalSintomas - 1; i++) { //Volta para a esquerda para não haver espaços vazios.
            listaSintomas[i] = listaSintomas[i + 1];
        }
        listaSintomas[totalSintomas - 1] = null;
        totalSintomas--;
        return true;
    }

    public Sintomas[] getSintomas(){
        if(listaSintomas.length > 0) return listaSintomas;
        else return null;
    }

    public Sintomas procurarSintoma(String sintoma) {
        for(int i = 0; i < totalSintomas; i++) {
            if(listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) return  listaSintomas[i];
        }
        return null;
    }

    public boolean atualuzarSintoma(String nomeSintoma, String corPulseira) {
        Sintomas sintomas = procurarSintoma(nomeSintoma);
        if(sintomas != null) {
            sintomas.setCor(corPulseira);
            return true;
        }
        return false;
    }
}
