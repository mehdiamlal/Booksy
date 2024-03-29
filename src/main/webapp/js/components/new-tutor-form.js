export var newTutorForm = {
    props: {
        listaDocenti: Array
    },
    data: function() {
        return {
            listaDocentiData: this.listaDocenti,
            nome: "",
            nomeMissing: false,
            cognome: "",
            cognomeMissing: false,
            email: "",
            emailMissing: false,
            emailNotValid: false,
            docenteExists: false,
            addedSuccess: false
        }
    },
    methods: {
        add_tutor_event: function() {
            var self = this;
            self.addedSuccess = false;
            self.docenteExists = false;
            if(self.valid_input() && !(self.docente_esistente())) {
                //http post per aggiungere il docente al db
                $.post("http://localhost:8080/progetto_TWeb_war_exploded/docenti",
                    {
                        action: "aggiungiDocente",
                        nome: self.nome,
                        cognome: self.cognome,
                        email: self.email
                    }, function(data) {
                        if(data === "no_session") {
                            localStorage.clear();
                            self.$router.push("/login");
                        }
                    });
                self.addedSuccess = true;
                self.docenteExists = false;
                self.listaDocentiData.push(self.email);
            }
        },
        valid_email: function(email) {
            return String(email)
                  .toLowerCase()
                  .match(
                    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                  );
        },
        valid_input: function() {
            var self = this;
            var res = true;
            if(self.nome != "" && self.nomeMissing == true) {
                self.nomeMissing = false;
            }

            if(self.cognome != "" && self.cognomeMissing == true) {
                self.cognomeMissing = false;
            }

            if(self.email != "" && self.emailMissing == true) {
                self.emailMissing = false;
            }

            if(self.email != "" && !self.valid_email(self.email)) {
                self.emailNotValid = true;
                res = false;
            } else {
                self.emailNotValid = false;
            }

            if(self.nome == "") {
                self.nomeMissing = true;
                res = false;
            }

            if(self.cognome == "") {
                self.cognomeMissing = true;
                res = false;
            }

            if(self.email == "") {
                self.emailMissing = true;
                self.emailNotValid = false;
                res = false;
            }

            return res;
        },
        docente_esistente: function() {
            var self = this;
            var res = false;
            if(self.listaDocentiData.includes(self.email)) {
                self.docenteExists = true;
                res = true;
            } else {
                self.docenteExists = false;
            }
            return res;
        }
    },
    template: `
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-6 offset-md-3 border p-4 shadow bg-light">
                    <form action="">
                        <div class="row g-3">
                            <div class="col-12">
                                    <!-- nome docente -->
                                    <div class="form-outline mb-4" v-if="!nomeMissing">
                                        <label class="text-secondary" for="nome">First Name</label>
                                        <input v-model="nome" type="text" id="nome" name="nome" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-else>
                                        <label class="text-secondary" for="nome">First Name</label>
                                        <input v-model="nome" type="text" id="nome" name="nome" class="form-control is-invalid"/>
                                        <div id="invalidNomeFeedback" class="invalid-feedback">This field cannot be empty.</div>
                                    </div>

                                    <!-- cognome docente -->
                                    <div class="form-outline mb-4" v-if="!cognomeMissing">
                                        <label class="text-secondary" for="cognome">Last Name</label>
                                        <input v-model="cognome" type="text" id="cognome" name="cognome" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-else>
                                        <label class="text-secondary" for="cognome">Last Name</label>
                                        <input v-model="cognome" type="text" id="cognome" name="cognome" class="form-control is-invalid"/>
                                        <div id="invalidCognomeFeedback" class="invalid-feedback">This field cannot be empty.</div>
                                    </div>

                                    <!-- email docente -->
                                    <div class="form-outline mb-4" v-if="!emailMissing && !emailNotValid">
                                        <label class="text-secondary" for="email">Email</label>
                                        <input v-model="email" type="email" id="email" name="email" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-if="emailMissing">
                                        <label class="text-secondary" for="cognome">Email</label>
                                        <input v-model="email" type="email" id="email" name="email" class="form-control is-invalid"/>
                                        <div id="invalidEmailFeedback" class="invalid-feedback">This field cannot be empty.</div>
                                    </div>
                                    <div class="form-outline mb-4" v-if="emailNotValid">
                                        <label class="text-secondary" for="cognome">Email</label>
                                        <input v-model="email" type="email" id="email" name="email" class="form-control is-invalid"/>
                                        <div id="invalidEmailFeedback" class="invalid-feedback">The email is not valid.</div>
                                    </div>

                                    <!-- warning docente duplicato -->
                                    <div v-if="docenteExists" class="text-secondary text-danger text-center">
                                        <p></p>
                                        Warning! A tutor with the same email already exists.
                                        <p></p>
                                    </div>

                                    <!-- messaggio docente aggiunto con successo -->
                                    <div v-if="addedSuccess" class="text-secondary text-success text-center">
                                        <p></p>
                                        Tutor added successfully.
                                        <p></p>
                                    </div>
                                
                                    <!-- Submit button -->
                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary btn-block mb-4" @click="add_tutor_event" style="width: 10em">Add Tutor</button>
                                    </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `
}