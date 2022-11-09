package dataModel;

public class Corso {
    String nome;
    boolean attivo;

    public Corso(String nome, boolean attivo) {
        this.nome = nome;
        this.attivo = attivo;
    }

    public String getNome() {
        return this.nome;
    }


    public boolean getStatus() {
        return this.attivo;
    }

    @Override
    public String toString() {
        return "Corso{" +
                "nome='" + nome + '\'' +
                ", attivo=" + attivo +
                '}';
    }
}