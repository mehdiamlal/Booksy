export var courseCard = {
    props: {
        title: String,
    },
    data: function() {
        return {
            listaDocenti: [],
            modalID: "#" + this.title,
            modalLabel: this.title + "Label"
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <h3 class="text-center">{{title}}</h3>
                <p></p>
                <div class="text-center">
                    <button class="btn btn-primary btn-sm" data-bs-toggle="modal" :data-bs-target="modalID">Lista Docenti</button>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" :id="title" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="modalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" :id="modalLabel">Docenti che insegnano {{title}}</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <ul>
                            <li v-for="docente in listaDocenti">{{docente.nome}} {{docente.cognome}} | <span class="text-muted">{{docente.email}}</span></li>
                        </ul>   
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                        <router-link to="/book">
                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Prenota ripetizione</button>
                        </router-link>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    mounted() {
        var self = this;
        //chiamata http che ottiene la lista dei docenti di quel corso e li mette nella lista docenti
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/docenti",
            {
                action: "filtraDocentePerCorso",
                corso: self.title
            },
            function(data) {
                data.forEach(function(docente) {
                    self.listaDocenti.push({
                        nome: docente.nome,
                        cognome: docente.cognome,
                        email: docente.email
                    });
                });
            });
    }
}