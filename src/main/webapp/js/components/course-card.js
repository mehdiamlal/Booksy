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
    methods: {
        displayList() {
            var self = this;
            self.listaDocenti.forEach((value) => {
                console.log(value);
            });
            
        }      
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <h3 class="text-center">{{title}}</h3>
                <p></p>
                <div class="text-center">
                    <button class="btn btn-primary btn-sm" @click="displayList" data-bs-toggle="modal" :data-bs-target="modalID">Lista Docenti</button>
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
                        {{listaDocenti[0]}}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                        <button type="button" class="btn btn-primary">Prenota ripetizione</button>
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