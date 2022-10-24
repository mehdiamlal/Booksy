package dataModel;

public class Corso {
    String nome;

    public Corso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return "Corso{" +
                "nome='" + nome + '\'' +
                '}';
    }
}