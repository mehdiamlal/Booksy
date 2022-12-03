export var bookingForm = {
    data: function() {
        return {
            listaCorsi: [],
            listaDocenti: [],
            listaFasceOrarie: [],
            selectedCorso: "",
            selectedDocente: "",
            selectedData: "",
            selectedFascia: "",
            fact: "",
            slotAvailable: true
        }
    },
    methods: {
        getDocenti() {
            var self = this;
            
            /*chiamata http per ottenere i docenti che insegnano il corso self.selectedCorso
            i dati vanno aggiunti a self.listaDocenti*/
        },
        getFasceOrarie() {
            var self = this;

            /* chiamata http per ottenere fasce orarie per il docente selezionato e la data
            selezionata, i risultati vanno aggiunti a self.listaFasceOrarie
            */
            
            if(self.listaFasceOrarie.length == 0) {
                self.slotAvailable = false;
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
                                    <option v-for="corso in listaCorsi">{{corso}}</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label for="selezionaDocente" class="text-secondary">Docente</label>
                                <select id="selezionaDocente" class="form-select" name="docente" v-model="selectedDocente">
                                    <option v-for="d in listaDocenti">{{d}}</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label for="data" class="text-secondary">Data</label>
                                <input v-model="selectedData" type="date" class="form-control" name="data" id="data" @change="getFasceOrarie">
                            </div>
                            <div class="col-12">
                                <label for="selezionaSlot" class="text-secondary">Fascia Oraria</label>
                                <select id="selezionaSlot" class="form-select" name="fasciaOraria" v-model="selectedFascia">
                                    <option v-for="fascia in listaFasceOrarie">{{fascia}}</option>
                                </select>
                                <div v-if="!slotAvailable" id="noSlotAvailable" class="text-secondary text-danger text-center">
                                    <p></p>
                                    Nessuna fascia oraria disponibile in data selezionata.
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="col-12 mt-5 text-center">
                            <router-link class="navbar-brand" to="/home">
                                <button type="button" class="btn btn-outline-secondary me-2">Annulla</button>
                            </router-link>                        
                        <button type="submit" class="btn btn-primary">Prenota</button>
                    </div>
                </div>
            </div>
        </div>
    `,
    beforeMount() {
        var self = this;

        /*HTTP request per ottenere lista corsi
        i dati li aggiungiamo a self.listaCorsi*/

        
    }
}