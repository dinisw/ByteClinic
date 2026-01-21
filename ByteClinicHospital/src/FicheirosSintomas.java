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
            return;
        }
        try {
            Scanner ler = new Scanner(ficheiro);
            while(ler.hasNextLine()) {
                String linha = ler.nextLine();
                if(linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if(dados.length >= 2) {
                    String nome = dados[0];

                    NivelSintomas nivelSintomas = NivelSintomas.VERDE;
                    try {
                        nivelSintomas = NivelSintomas.valueOf(dados[1]);
                    } catch (Exception e) {}

                    Especialidades especialidade = null;
                    if (dados.length > 2 && !dados[2].equals("NA")) {
                        for (Especialidades especialidades2 : Especialidades.values()) {
                            if (especialidades2.getCodigo().equalsIgnoreCase(dados[2])) {
                                especialidade = especialidades2;
                                break;
                            }
                        }
                    }
                    Sintomas sintoma = new Sintomas(nome, nivelSintomas, especialidade);
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

    public Sintomas procurarSintoma(String sintoma) {
        for(int i = 0; i < totalSintomas; i++) {
            if(listaSintomas[i].getNomeSintoma().equalsIgnoreCase(sintoma)) return  listaSintomas[i];
        }
        return null;
    }

    public boolean atualizarSintoma(String nomeSintoma, NivelSintomas nivelSintomas) {
        Sintomas sintomas = procurarSintoma(nomeSintoma);
        if(sintomas != null) {
            sintomas.setNivelSintoma(nivelSintomas);
            return true;
        }
        return false;
    }

    public Sintomas[] getSintomas() {return listaSintomas; }
}
