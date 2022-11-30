export var courseCard = {
    props: {
        title: String,
    },
    data: function() {
        return {
            listaDocenti: []
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <h3 class="text-center">{{title}}</h3>
                <p></p>
                <div class="text-center">
                    <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Lista Docenti</button>
                </div>
            </div>
        </div>
        
        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">Lista Docenti</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <h6 v-for="docente in listaDocenti">{{docente.nome}} {{docente.cognome}} | 
                <span class="text-muted">{{docente.email}}</span></h6>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
              </div>
            </div>
          </div>
        </div>
    `,
    beforeMount: function() {
        //chiamata http per ottenere la lista dicenti che insegnano il corso 'title'
        this.listaDocenti = [
            {
                "nome": "Marco",
                "cognome": "Alvaro",
                "email": "malvaro@gmail.com"
            },
            {
                "nome": "Marco",
                "cognome": "Alvaro",
                "email": "malvaro@gmail.com"
            },
            {
                "nome": "Marco",
                "cognome": "Alvaro",
                "email": "malvaro@gmail.com"
            }
        ];
    }
}