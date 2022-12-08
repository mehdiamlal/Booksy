export var coursesView = {
    data: function() {
        return {
            search: "",
            listaCorsi: []
        };
    },
    methods: {
        filterCourses() {
            var self = this;
            self.listaCorsi.forEach((corso) => {
                if(corso.nome.toLowerCase().includes(self.search.toLowerCase())) {
                    corso.show = true;
                } else {
                    corso.show = false;
                }
            });
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista Corsi</h1>
            <div class="form-group">
                <input type="text" class="form-control shadow" v-model="search" placeholder="Cerca corsi" @input="filterCourses">
            </div>
            <hr>
            <div class="row">
                <course-card v-for="corso in listaCorsi" :title="corso.nome" v-show="corso.show"></course-card>
            </div>
        </div>
    `,
    created: function() {
        var self = this;
        //chiamata HTTP per ottenere lista corsi attivi, che verranno aggiunti a lista corsi secondo lo schema
        // {
        //     nome: "nome corso",
        //     show: true
        // }
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/corsi",
            {action: "ottieniCorsiAttivi"},
            function(data) {
                data.forEach(function(c) {
                    self.listaCorsi.push({
                        nome: c.nome,
                        show: true
                    });
                });
            });
    }
};