export var bookingForm = {
    data: function() {
        return {
            listaCorsi: [],
            listaDocenti: [],
            listaFasceOrarie: [],
            selectedCorso: "",
            selectedDocente: "",
            selectedData: "",
            formattedData: "",
            selectedFascia: "",
            slotAvailable: true,
            showDocenteSelector: false,
            showDateSelector: false,
            showFasciaSelector: false,
            showWarning: false,
            addedSuccess: false
        }
    },
    methods: {
        getDocenti() {
            var self = this;
            self.addedSuccess = false;
            /*chiamata http per ottenere i docenti che insegnano il corso self.selectedCorso
            i dati vanno aggiunti a self.listaDocenti*/
            $.get("http://localhost:8080/progetto_TWeb_war_exploded/docenti", {
                action: "filtraDocentePerCorso",
                corso: self.selectedCorso
            }, function(data) {
                self.listaDocenti = data;
            });
            self.showDocenteSelector = true;
        },
        getFasceOrarie() {
            var self = this;
            self.slotAvailable = true;
            self.formattedData = self.selectedData.substring(8, 10) + "/" +
                                 self.selectedData.substring(5, 7) + "/" +
                                 self.selectedData.substring(0, 4);

            /* chiamata http per ottenere fasce orarie per il docente selezionato e la data
            selezionata, i risultati vanno aggiunti a self.listaFasceOrarie
            */
            $.get("http://localhost:8080/progetto_TWeb_war_exploded/slot-disponibili", {
                action: "ottieniSlotDisponibili",
                dataInizio: self.formattedData,
                dataFine: self.formattedData
            }, function(data) {
                self.listaFasceOrarie = data[self.selectedDocente.email][self.formattedData];
                if(self.listaFasceOrarie.length === 0) {
                    self.slotAvailable = false;
                } else {
                    self.showFasciaSelector = true;
                }
            });
        },
        prenotaRipetizione() {
            var self = this;
            self.showWarning = false;
            self.addedSuccess = false;
            if(self.selectedCorso !== "" && self.selectedDocente !== "" && self.formattedData !== "" && self.selectedFascia != "") {
                $.post("http://localhost:8080/progetto_TWeb_war_exploded/prenotazioni", {
                    action: "aggiungiPrenotazione",
                    username: localStorage.username,
                    idCorso: self.selectedCorso,
                    emailDocente: self.selectedDocente.email,
                    data: self.formattedData,
                    fasciaOraria: self.selectedFascia
                }, function(data) {
                    if(data === "no_session") {
                        localStorage.clear();
                        self.$router.push("/login");
                    }
                });
                self.addedSuccess = true;
                self.selectedCorso = "";
                self.selectedDocente = "";
                self.formattedData = "";
                self.selectedData = "";
                self.selectedFascia = "";
                self.showDocenteSelector = false;
                self.showDateSelector = false;
                self.showFasciaSelector = false;

            } else {
                self.showWarning = true;
            }

        }
    },
    template: `
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-6 offset-md-3 border p-4 shadow bg-light">
                    <form action="">
                        <div class="row g-3">
                            <div class="col-12">
                                <label for="selezionaCorso" class="text-secondary">Corso</label>
                                <select id="selezionaCorso" class="form-select" name="corso" v-model="selectedCorso" @change="getDocenti">
                                    <option v-for="corso in listaCorsi" :value="corso">{{corso}}</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label for="selezionaDocente" class="text-secondary">Docente</label>
                                <select id="selezionaDocente" class="form-select" name="docente" v-model="selectedDocente" v-if="showDocenteSelector" @change="showDateSelector = !showDateSelector">
                                    <option v-for="docente in listaDocenti" :value="docente">{{docente.nome}} {{docente.cognome}} | {{docente.email}}</option>
                                </select>
                                <select class="form-select" disabled v-else></select>
                            </div>
                            <div class="col-12">
                                <label for="data" class="text-secondary">Data</label>
                                <input v-model="selectedData" type="date" class="form-control" name="data" id="data" @change="getFasceOrarie" v-if="showDateSelector">
                                <input type="date" class="form-control" v-else disabled>
                            </div>
                            <div class="col-12">
                                <label for="selezionaSlot" class="text-secondary">Fascia Oraria</label>
                                <select id="selezionaSlot" class="form-select" name="fasciaOraria" v-model="selectedFascia" v-if="showFasciaSelector">
                                    <option v-for="fascia in listaFasceOrarie">{{fascia}}</option>
                                </select>
                                <select class="form-select" disabled v-else></select>
                                <div v-if="!slotAvailable" id="noSlotAvailable" class="text-secondary text-danger text-center">
                                    <p></p>
                                    Nessuna fascia oraria disponibile in data selezionata.
                                </div>
                                <div v-if="showWarning" class="text-secondary text-danger text-center">
                                    <p></p>
                                    Attenzione! Compilare tutti i campi.
                                </div>
                                <div v-if="addedSuccess" class="text-secondary text-success text-center">
                                    <p></p>
                                    Prenotazione aggiunta con successo.
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="col-12 mt-5 text-center">
                            <router-link class="navbar-brand" to="/home">
                                <button type="button" class="btn btn-outline-secondary me-2">Annulla</button>
                            </router-link>                        
                        <button type="submit" class="btn btn-primary" @click="prenotaRipetizione">Prenota</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    created() {
        var self = this;
        self.listaCorsi = [];
        /*HTTP request per ottenere lista corsi
        i dati li aggiungiamo a self.listaCorsi*/
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/corsi", {
            action: "ottieniCorsiAttivi"
        }, function(data) {
            if(data === "no_session") {
                localStorage.clear();
                self.$router.push("/login");
            } else {
                data.forEach(function (corso) {
                    self.listaCorsi.push(corso.nome);
                });
            }
        });
        console.log(self.listaCorsi);
    }
}