export var addTutorView = {
    data: function() {
        return {
            listaDocenti: []
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Aggiungi un docente</h1>
            <new-tutor-form :listaDocenti="listaDocenti"></new-tutor-form>
        </div>
    `,
    created() {
        var self = this;
        //chiamata http per ottenere i docenti attivi
        self.listaDocenti = ["mario@gmail.com"];
    }
};