export var addTutorView = {
    data: function() {
        return {
            listaDocenti: []
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Add a Tutor</h1>
            <new-tutor-form :listaDocenti="listaDocenti"></new-tutor-form>
        </div>
    `,
    created() {
        var self = this;
        document.title = "(Admin) Add Tutor | Booksy";
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