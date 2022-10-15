package dataModel;

public class Utente {

    private String username;
    private String password;
    private String ruolo;

    public Utente(String nomeAccount, String password, String ruolo) {
        this.username = nomeAccount;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRuolo() {
        return this.ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}