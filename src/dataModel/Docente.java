package dataModel;

public class Docente {
    private String id;
    private String nome;
    private String cognome;

    public Docente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getId() { return id; }
}
