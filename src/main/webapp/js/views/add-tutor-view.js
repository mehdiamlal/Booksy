export var addTutorView = {
    data: function() {
        return {
            listaDocenti: []
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Aggiungi un docente</h1>
            <new-tutor-form :listaDocenti="listaDocenti"></new-tutor-form>
        </div>
    `,
    created() {
        var self = this;
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "amministratore") {
            self.$router.push("/");
        }
        //chiamata http per ottenere i docenti attivi
        self.listaDocenti = [];
        var tmp;
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/docenti",{
            action: "ottieniDocenti"
        }, function(data) {
            if(data === "no_session") {
                localStorage.clear();
                alert("Sessione scaduta.");
                self.$router.push("/login");
            } else {
                data.forEach(function(docente) {
                    tmp = docente.email;
                    self.listaDocenti.push(tmp);
                });
            }
        });
        console.log(self.listaDocenti);
    }
};