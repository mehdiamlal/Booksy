export var studentHomeView = {
    data: function() {
        return {
            prenotImminenti: [],
            nomeStudente: localStorage.getItem("name")
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center">Benvenuto, {{nomeStudente}}. &#128075;</h1>
            <hr>
            <h3 class="text-center" style="margin: 1em">Le tue prenotazioni più imminenti</h3>
            <div class="row">
                <appointment-card v-for="prenotazione in prenotImminenti" 
                    :user="prenotazione.utente" 
                    :tutor="prenotazione.docente"
                    :course="prenotazione.corso"
                    :day="prenotazione.data"
                    :timeSlot="prenotazione.fasciaOraria"
                    :active="prenotazione.attiva"
                    :completed="prenotazione.effettuata"
                     v-if="prenotImminenti.length > 0"></appointment-card>
                <h5 class="text-center text-muted" v-else >Nessuna prenotazione imminente.</h5>
            </div>
            <hr>
            <div class="row text-center" style="margin-top: 2em">
                <div class="col"></div>
                <div class="col">
                    <router-link to="/book">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Prenota una ripetizione</button>
                    </router-link>
                    <router-link to="/active_bookings">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Le tue prenotazioni attive</button>
                    </router-link>
                    <router-link to="/bookings">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Storico prenotazioni</button>
                    </router-link>
                    <router-link to="/courses">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Lista corsi</button>
                    </router-link>
                </div>
                <div class="col"></div>
            </div>
        </div>
    `,
    created() {
        var self = this;
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "studente") {
            self.$router.push("/");
        }
        self.prenotImminenti = [];
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/prenotazioni", {
            action: "ottieniPrenotazioniUtenteImminenti",
            utente: "heymehdi"
        }, function(data) {
            if(data === "no_session") {
                localStorage.clear();
                alert("Sessione scaduta.");
                self.$router.push("/login");
            } else {
                data.forEach(function(prenotazione) {
                    var tmp = prenotazione;
                    self.prenotImminenti.push(tmp);
                    console.log(tmp);
                });
            }
        });
    }
}