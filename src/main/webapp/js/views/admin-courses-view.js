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
            self.newCourse = self.newCourse.trim();
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
                    }, function(data) {
                        if(data === "no_session") {
                            localStorage.clear();
                            self.$router.push("/login");
                        }
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
                self.errorMessage = "Invalid course name"
                ris = false;
            } else {
                self.listaCorsi.forEach((corso) => {
                    if(corso.nome.toLowerCase() === self.newCourse.toLowerCase()) {
                        self.errorMessage = "The course " + self.newCourse + " already exists."
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
            <h1 class="text-center" style="margin-bottom: 1.5em">Course List</h1>
            <div class="form-group">
                <input type="text" class="form-control shadow" v-model="search" placeholder="Search course" @input="filterCourses" @click="addedSuccess = false">
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
                        <h1 class="modal-title fs-5" id="aggiungiCorsoLabel">Add a new course</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Course name" v-model="newCourse" v-if="!notValidInput">
                        <input type="text" class="form-control is-invalid" placeholder="Course name" v-model="newCourse" v-if="notValidInput"/>
                        <div class="invalid-feedback" v-if="notValidInput">{{errorMessage}}</div>
                        <div class="text-success" v-if="addedSuccess" style="margin-top: 1.5em">Course was added successfully.</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" @click="aggiungiCorso">Save</button>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    created: function() {
        var self = this;
        document.title = "(Admin) Course List | Booksy";
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "amministratore") {
            self.$router.push("/");
        }
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/corsi",
            {action: "ottieniCorsi"},
            function(data) {
                if(data === "no_session") {
                    localStorage.clear();
                    self.$router.push("/login");
                } else {
                    data.forEach(function (c) {
                        self.listaCorsi.push({
                            nome: c.nome,
                            attivo: c.attivo,
                            show: true
                        });
                    });
                }
            });
    }
}