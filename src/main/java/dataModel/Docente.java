package dataModel;

public class Docente {
    private String email;
    private String nome;
    private String cognome;
    private boolean attivo = true;

    public Docente(String email, String nome, String cognome, boolean attivo) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.attivo = attivo;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public boolean isAttivo() {
        return attivo;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}