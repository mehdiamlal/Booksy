package dataModel;

public class Utente {

    private String username;
    private String nome;
    private String cognome;
    private String ruolo;

    public Utente(String username, String nome, String cognome, String ruolo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}