export var adminBookingListView = {
    data: function () {
        return {
            searchDate: "", 
            listaPrenotazioni: [],
            nessunaPrenotazione: false
        }
    },
    methods: {
        filterBookings() {
            var self = this;
            var formattedDate = 
            self.searchDate.substring(8, 10) + "/" +
            self.searchDate.substring(5, 7) + "/" +
            self.searchDate.substring(0, 4);
            var contatorePrenotazioni = self.listaPrenotazioni.length;

            self.listaPrenotazioni.forEach((prenotazione) => {
                if(prenotazione.data.includes(formattedDate)) {
                    prenotazione.show = true;
                } else {
                    prenotazione.show = false;
                    contatorePrenotazioni--;
                }
            });

            self.nessunaPrenotazione = contatorePrenotazioni === 0;
        },
        clearFilter() {
            var self = this;
            self.searchDate = "";
            self.listaPrenotazioni.forEach((prenotazione) => {
                prenotazione.show = true;
            });
            self.nessunaPrenotazione = self.listaPrenotazioni.length === 0;
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista prenotazioni</h1>
            <div class="form-group row">
                <div class="col"></div>
                <div class="col-6 text-center">
                    <label for="dateFilter" class="form-label text-muted">Filtra per data:</label>
                    <div class="input-group">
                        <input type="date" class="form-control" v-model="searchDate" id="dateFilter" @change="filterBookings">
                        <button class="btn btn-primary" type="button" @click="clearFilter"><i class="far fa-times"></i></button>
                    </div>
                </div>
                <div class="col"></div>
            </div>
            <hr>
            <div class="row" style="margin-bottom: 7em">
                <admin-booking-card v-for="prenotazione in listaPrenotazioni"
                :user="prenotazione.utente"
                :tutor="prenotazione.docente"
                :course="prenotazione.corso"
                :day="prenotazione.data"
                :timeSlot="prenotazione.fasciaOraria"
                :active="prenotazione.attiva"
                :completed="prenotazione.effettuata" v-show="prenotazione.show"></admin-booking-card>
            </div>
            <h5 class="text-center text-muted" v-if="nessunaPrenotazione" style="margin-top: 2em">Nessuna prenotazione trovata.</h5>
        </div>
    `,
    created: function() {
        var self = this;
        document.title = "(Admin) Storico prenotazioni | Booksy";
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "amministratore") {
            self.$router.push("/");
        }
        self.listaPrenotazioni = [];
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/prenotazioni",
            {
                action: "ottieniTuttePrenotazioni"
            },
            function(data) {
                if(data === "no_session") {
                    localStorage.clear();
                    self.$router.push("/login");
                } else {
                    data.forEach(function (prenotazione) {
                        var tmp = prenotazione;
                        tmp["show"] = true;
                        self.listaPrenotazioni.push(tmp);
                        console.log(tmp);
                    });
                }
            });
    }
}