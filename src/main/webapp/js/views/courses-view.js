export var coursesView = {
    data: function() {
        return {
            search: ""
        };
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista Corsi</h1>
            <div class="form-group">
                <input type="text" class="form-control shadow" v-model="search" placeholder="Cerca corsi" @input="showList">
            </div>
            <hr>
            <div class="row">
                <course-card v-for="corso in filteredList" :title="corso.title"></course-card>
            </div>
        </div>
    `,
    computed: {
      filteredList() {
          return this.listaCorsi.filter(corso => corso.title.toLowerCase().includes(this.search.toLowerCase()));
      }
    },
    beforeMount: function() {
        var self = this;
        //chiamata HTTP per ottenere lista corsi attivi
        self.listaCorsi = [{
                title: "Informatica"
            },
            {
                title: "Matematica"
            },
            {
                title: "Geometria"
            }];
        console.log(self.listaCorsi);
    }
};