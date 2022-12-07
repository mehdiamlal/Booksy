export var adminCourseCard = {
    props: {
        title: String,
        active: Boolean
    },
    data: function() {
        return {
            listaDocenti: [],
            listaDocentiDaAggiungere: [],
            modalIDHash1: "#" + this.title + "1",
            modalID1: this.title + "1",
            modalLabel1: this.title + "Label",
            modalIDHash2: "#" + this.title + "2",
            modalID2: this.title + "2",
            modalLabel2: this.title + "Label2",
            modalIDHash3: "#" + this.title + "3",
            modalID3: this.title + "3",
            modalLabel3: this.title + "Label3",
            activeData: this.active
        }
    },
    methods: {
        displayList() {
            var self = this;
            self.listaDocenti.forEach((value) => {
                console.log(value);
            });
            
        },
        getDocentiDisponibili() {
            var self = this;
            //chiamata http get per ottenere i docenti (attivi) che non insegnano il corso
        },
        aggiungiDocente() {
            var self = this;
            //chiamata http post per aggiungere il docente selezionato nella select
        },
        disattivaCorso() {
            var self = this;
            //chiamata http per disattivare il corso da DB
            self.activeData = false;
        },
        attivaCorso() {
            var self = this;
            //chiamata http per attivare il corso da DB
            self.activeData = true;
        },
        aggiungiDocente() {
            var self = this;
            //chiamata http per rimuovere il docente selezionato dallínsegnamento del corso
        }

    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <div class="dropdown open text-end">
                    <a href="#!" class="px-2" id="triggerId1" data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">
                        <i class="fa fa-ellipsis-v" style="color: #5E17EB !important;"></i>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="triggerId1">
                        <a class="dropdown-item text-danger" @click="disattivaCorso" v-if="activeData"><i class="fas fa-times-circle"></i> Disattiva</a>
                        <a class="dropdown-item text-success" @click="attivaCorso" v-else><i class="fas fa-check-circle"></i> Attiva</a>
                    </div>
                </div>
                <h3 class="text-center">{{title}}</h3>
                <p></p>
                <div class="d-grid gap-2 col-6 mx-auto">
                    <button class="btn btn-primary" type="button" @click="displayList" data-bs-toggle="modal" :data-bs-target="modalIDHash1">Lista Docenti</button>
                    <button class="btn btn-primary" type="button" @click="getDocentiDisponibili" data-bs-toggle="modal" :data-bs-target="modalIDHash2">Aggiungi Docente</button>
                    <button class="btn btn-primary" type="button" data-bs-toggle="modal" :data-bs-target="modalIDHash3">Rimuovi Docente</button>
                </div>
                <span class="text-success fw-bold text-end" v-if="activeData">Attivo</span>
                <span class="text-danger fw-bold text-end" v-else>Non attivo</span>
            </div>
            <!-- Lista Docenti Modal -->
            <div class="modal fade" :id="modalID1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="modalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" :id="modalLabel1">Docenti che insegnano {{title}}</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        {{listaDocenti[0]}}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    </div>
                    </div>
                </div>
            </div>

            <!-- Aggiungi Docente Modal -->
            <div class="modal fade" :id="modalID2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="modalLabel2" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" :id="modalLabel2">Aggiungi un docente per {{title}}</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <select class="form-select" aria-label="Default select example">
                            <option v-for="docente in listaDocentiDaAggiungere" :value=docente.email>{{docente.nome}} {{docente.cognome}} | {{docente.email}}</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                        <button type="button" class="btn btn-primary" @click="aggiungiDocente">Salva</button>
                    </div>
                    </div>
                </div>
            </div>
            <!-- Rimuovi Docente Modal -->
            <div class="modal fade" :id="modalID3" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="modalLabel3" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" :id="modalLabel3">Rimuovi un docente per {{title}}</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <select class="form-select" aria-label="Default select example">
                            <option v-for="docente in listaDocentiDaAggiungere" :value=docente.email>{{docente.nome}} {{docente.cognome}} | {{docente.email}}</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                        <button type="button" class="btn btn-primary" @click="rimuoviDocente">Salva</button>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    mounted() {
        var self = this;
        //chiamata http che ottiene la lista dei docenti di quel corso e li mette nella lista docenti
        $.get("https://catfact.ninja/fact", function(data) {
            self.listaDocenti.push(data.fact)
            console.log("data fetched correctly");
        });
    }
}