package dataModel;

public class Corso {
    String nome;
    boolean attivo = true;

    public Corso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void cambiaStatoCorso() {
        attivo = !attivo;
    }

    @Override
    public String toString() {
        return "Corso{" +
                "nome='" + nome + '\'' +
                '}';
    }
}