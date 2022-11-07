package dataModel;

public class Prenotazione {
    private String utente;
    private String corso;
    private String docente;
    private String data;
    private String fasciaOraria;

    private boolean attiva;

    public Prenotazione(String utente, String corso, String docente, String data, String fasciaOraria, boolean attiva) {
        this.utente = utente;
        this.corso = corso;
        this.docente = docente;
        this.data = data;
        this.fasciaOraria = fasciaOraria;
        this.attiva = attiva;
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

    public boolean isAttiva() {
        return attiva;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "utente='" + utente + '\'' +
                ", corso='" + corso + '\'' +
                ", docente='" + docente + '\'' +
                ", data='" + data + '\'' +
                ", fasciaOraria='" + fasciaOraria + '\'' +
                ", attiva=" + attiva +
                '}';
    }
}
