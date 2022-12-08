export var adminCoursesView = {
    data: function() {
        return {
            search: "",
            listaCorsi: [],
            newCourse: "",
            notValidInput: false,
            errorMessage: "",
            addedSuccess: false
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
        },
        aggiungiCorso() {
            var self = this;
            self.newCourse = self.newCourse.replace(/\s/g, ''); //togli spazi da stringa
            self.addedSuccess = false;
            if(!self.controllaCorso()) {
                self.notValidInput = true;
            } else {
                self.notValidInput = false;

                //chiamata http post per aggiungere il corso
                $.post("http://localhost:8080/progetto_TWeb_war_exploded/corsi",
                    {
                        action: "aggiungiCorso",
                        corso: self.newCourse
                    });

                self.listaCorsi.push({
                    nome: self.newCourse,
                    attivo: true,
                    show: true
                });
                self.addedSuccess = true;
                self.newCourse = "";
            }
        },
        controllaCorso() {
            var self = this;
            var ris = true;
            if(self.newCourse.length === 0) {
                self.errorMessage = "Nome corso non valido"
                ris = false;
            } else {
                self.listaCorsi.forEach((corso) => {
                    if(corso.nome.toLowerCase() === self.newCourse.toLowerCase()) {
                        self.errorMessage = "Il corso " + self.newCourse + " è già esistente."
                        ris = false;
                    }
                });
            }
            
            return ris;
        },
        resetFlags() {
            var self = this;
            self.addedSuccess = false;
            self.notValidInput = false;
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista Corsi</h1>
            <div class="form-group">
                <input type="text" class="form-control shadow" v-model="search" placeholder="Cerca corsi" @input="filterCourses" @click="addedSuccess = false">
            </div>
            <hr>
            <div class="row">
                <admin-course-card v-for="corso in listaCorsi" :title="corso.nome" :active="corso.attivo" v-show="corso.show"></admin-course-card>
            </div>
            <button class="btn btn-primary shadow add-btn" data-bs-toggle="modal" data-bs-target="#aggiungiCorso" @click="resetFlags"><i class="fas fa-plus"></i></button>

            <div class="modal fade" id="aggiungiCorso" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="aggiungiCorsoLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="aggiungiCorsoLabel">Aggiungi un nuovo corso</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Nome del corso" v-model="newCourse" v-if="!notValidInput">
                        <input type="text" class="form-control is-invalid" placeholder="Nome del corso" v-model="newCourse" v-if="notValidInput"/>
                        <div class="invalid-feedback" v-if="notValidInput">{{errorMessage}}</div>
                        <div class="text-success" v-if="addedSuccess" style="margin-top: 1.5em">Il corso è stato aggiunto con successo.</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                        <button type="button" class="btn btn-primary" @click="aggiungiCorso">Salva</button>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    created: function() {
        var self = this;

        var self = this;
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/corsi",
            {action: "ottieniCorsi"},
            function(data) {
                data.forEach(function(c) {
                    self.listaCorsi.push({
                        nome: c.nome,
                        attivo: c.attivo,
                        show: true
                    });
                });
            });
    }
}