export var coursesView = {
    data: function() {
        return {
            search: ""
        };
    },
    template: `
        <div class="container">
            <p></p>
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista Corsi</h1>
            <div class="form-group">
                <input type="text" class="form-control shadow" v-model="search" placeholder="Cerca corsi" @input="showList">
            </div>
            <hr>
            <div class="row">
                <course-card v-for="corso in filteredList" :title="corso"></course-card>
            </div>
        </div>
    `,
    computed: {
      filteredList() {
          return this.listaCorsi.filter(corso => corso.toLowerCase().includes(this.search.toLowerCase()));
      }
    },
    beforeMount: function() {
        var self = this;
        //chiamata HTTP per ottenere lista corsi attivi
        self.listaCorsi = ["Italiano", "Matematica", "Informatica", "Fisica", "Latino", "Scienze",
        "Sociologia", "Biologia", "Arte", "Storia", "Geografia", "Filosofia",
        "Psicologia", "Diritto", "Economia", "Inglese", "Francese", "Disegno Tecnico"];
        console.log(self.listaCorsi);
    }
};