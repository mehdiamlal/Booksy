package dataModel;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class DAO {
    String url;
    String user;
    String pw;

    public DAO(String url, String user, String pw) {
        this.url = url;
        this.user = user;
        this.pw = pw;
    }

    //metodo di utility per generare la date odierna in formato dd/MM/yyyy
    //NB: le lettere dei mesi devono essere in maiuscolo per evitare errori
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    public void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodi per la gestione delle operazioni sulla tabella utente
    public void aggiungiUtente(String username, String password, String nome, String cognome, String ruolo) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "INSERT INTO utente(username, password, nome, cognome, ruolo, dataCreazione)" +
                        "VALUES(?,?,?,?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, username.toLowerCase());
            st.setString(2, password);
            st.setString(3, nome);
            st.setString(4, cognome);
            st.setString(5, ruolo);
            st.setString(6, getDate());

            st.executeUpdate();

            System.out.println("Utente aggiunto con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviUtente(String username) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");


            String sql = "UPDATE utente SET attivo = 0, dataCancellazione = ? WHERE username = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, username);

            st.execute();

            System.out.println("Utente rimosso con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Utente> ottieniElencoUtenti() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Utente> elencoUtenti = new ArrayList<>();

        try {
            /* Stabiliamo la connessione col database */
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            /* Creiamo lo statement SQL da eseguire */
            st = conn.createStatement();

            /* Creiamo un ResultSet per contenere il risultato della query
             * e un ArrayList per averlo in una struttura dati più approcciabile */
            ResultSet rs = st.executeQuery("SELECT * FROM utente WHERE attivo = 1");

            /* Iteriamo sul ResultSet ottenuto dalla query, aggiungendo
            ogni elemento all'ArrayList contentente utenti */
            while(rs.next()) {
                Utente u = new Utente(rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("ruolo"));
                elencoUtenti.add(u);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoUtenti;
    }

    public Utente autenticaUtente(String username, String password) {
        //se il metodo ritorna un null, vuol dire che l'autenticazione è fallita
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Utente res = null;
        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM utente WHERE username = ? AND password = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);

            rs = st.executeQuery();

            if(rs.next()) {  //se la query ha restituito un risultato...
                res = new Utente(rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("ruolo"));
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return res;
    }

    //Metodi per gestione delle operazioni sulla tabella corso
    public void aggiungiCorso(String nome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO corso (nome) VALUES (?);";

            st = conn.prepareStatement(sql);
            st.setString(1, nome);

            st.executeUpdate();

            System.out.println("Corso aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void disattivaCorso(String nome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            conn.setAutoCommit(false);
            String sql1 = "UPDATE corso SET attivo = 0 WHERE nome = ?"; //disattiva il corso
            //elimina (logicamente) le prenotazioni attive per il corso disattivato
            String sql2 = "UPDATE prenotazione SET attiva = 0, dataCancellazione = ? WHERE corso = ? AND attiva = 1";

            st = conn.prepareStatement(sql1);
            st.setString(1, nome);
            st.executeUpdate();

            st = conn.prepareStatement(sql2);
            st.setString(1, getDate());
            st.setString(2, nome);
            st.executeUpdate();
            conn.commit();
            System.out.println("Corso disattivato con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(conn != null) {
                try {
                    System.out.println("Rollback della transazione!");
                    conn.rollback();
                } catch (SQLException ex2) {  //la chiamo così per non creare ambiguità con l'eccezione e
                    System.out.println(ex2.getMessage());
                }
            }
        } finally {
            try {
                if(conn != null && st != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void attivaCorso(String corso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE corso SET attivo = 1 WHERE nome = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, corso);

            st.executeUpdate();
            System.out.println("Corso attivato con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void toggleCorso(String corso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT attivo FROM corso WHERE nome = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, corso);
            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                if(rs.getString("attivo").equals("1")) {
                    this.disattivaCorso(corso);
                } else {
                    this.attivaCorso(corso);
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Corso> ottieniElencoCorsi() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM corso");
            while(rs.next()) {
                Corso c = new Corso(rs.getString("nome"), rs.getString("attivo").equals("1"));
                elencoCorsi.add(c);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoCorsi;
    }

    //Metodi per la gestione delle operazioni sulla tabella docente
    public void aggiungiDocente(String email, String password, String nome, String cognome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO docente (email, nome, cognome, dataCreazione) VALUES (?,?,?,?);";

            st = conn.prepareStatement(sql);
            st.setString(1, email.toLowerCase());
            st.setString(2, nome);
            st.setString(3, cognome);
            st.setString(4, getDate());

            st.executeUpdate();

            System.out.println("Docente aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null) {conn.close();}
                if(st != null) {st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void rimuoviInsegnamentiDocente(String email) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "DELETE FROM insegnamento " +
                        "WHERE docente = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, email);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviDocente(String email) {

        Connection conn = null;
        PreparedStatement st = null;

        rimuoviInsegnamentiDocente(email);

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE docente " +
                        "SET attivo = 0, dataCancellazione = ? " +
                        "WHERE email = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, email);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Docente> ottieniElencoDocenti() {

        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Docente> elencoDocenti = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM docente WHERE attivo = 1";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                elencoDocenti.add(new Docente(rs.getString("email"),
                        rs.getString("nome"),
                        rs.getString("cognome")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoDocenti;
    }

    //Metodi per la gestione delle operazioni sulla tabella insegnamento
    public void aggiungiInsegnamento(String docente, String corso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO insegnamento (docente, corso) VALUES (?,?);";

            st = conn.prepareStatement(sql);
            st.setString(1, docente);
            st.setString(2, corso);

            st.executeUpdate();

            System.out.println("Insegnamento aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviInsegnamento(String emailDocente, String nomeCorso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "DELETE FROM Corso " +
                         "WHERE docente = ? AND corso = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, emailDocente);
            st.setString(2, nomeCorso);

            st.executeUpdate();

            System.out.println("Insegnamento rimosso con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Metodi per la gestione delle operazioni sulla tabella prenotazione
    public void aggiungiPrenotazione(String username, String idCorso, String emailDocente, String data, String fasciaOraria) {

        //si presuppone che ciascuna prenotazione abbia un flag "attiva"
        //che viene settato a 1 di default quando viene creata
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO prenotazione (utente, corso, docente, data, fasciaOraria) VALUES (?, ?, ?, ?, ?);";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, idCorso);
            st.setString(3, emailDocente);
            st.setString(4, data);
            st.setString(5, fasciaOraria);

            st.executeUpdate();

            System.out.println("Prenotazione aggiunta con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviPrenotazione(String emailDocente, String data, String fasciaOraria) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE prenotazione SET attiva = 0, dataCancellazione = ? WHERE docente = ? AND data = ? AND fasciaOraria = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, emailDocente);
            st.setString(3, data);
            st.setString(4, fasciaOraria);

            st.executeUpdate();

            System.out.println("Prenotazione eliminata con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null && st != null) {
                    conn.close();
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Prenotazione> ottieniElencoPrenotazioni() {
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM prenotazione WHERE attiva = 1";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                elencoPrenotazioni.add(new Prenotazione(rs.getString("utente"),
                        rs.getString("corso"),
                        rs.getString("docente"),
                        rs.getString("data"),
                        rs.getString("fasciaOraria")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoPrenotazioni;
    }
}