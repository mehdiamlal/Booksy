package dataModel;

public class Prenotazione {
    private String utente;
    private String corso;
    private String docente;
    private String data;
    private String fasciaOraria;

    public Prenotazione(String utente, String corso, String docente, String data, String fasciaOraria) {
        this.utente = utente;
        this.corso = corso;
        this.docente = docente;
        this.data = data;
        this.fasciaOraria = fasciaOraria;
    }

    public String getUtente() {
        return utente;
    }

    public String getCorso() {
        return corso;
    }

    public String getDocente() {
        return docente;
    }

    public String getData() {
        return data;
    }

    public String getFasciaOraria() {
        return fasciaOraria;
    }
}
