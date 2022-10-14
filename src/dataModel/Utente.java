package dataModel;

public class Utente {

    private String nomeAccount;
    private String password;
    private String ruolo;

    public Utente(String nomeAccount, String password, String ruolo) {
        this.nomeAccount = nomeAccount;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getNomeAccount() {
        return this.nomeAccount;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRuolo() {
        return this.ruolo;
    }
}