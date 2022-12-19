export var loginForm = {
    data: function() {
        return {
            username: "",
            usernameMissing: false,
            password: "",
            passwordMissing: false,
            userNotFound: false
        }
    },
    methods: {
        login_event: function() {
            var self = this;
            if(self.valid_input()) {
                $.get("http://localhost:8080/progetto_TWeb_war_exploded/autentica", {
                    action: "autenticaUtente",
                    username: self.username,
                    password: self.password
                }, function(data) {
                    if(data === null) {
                        self.userNotFound = true;
                    } else {
                        self.userNotFound = false;
                        localStorage.setItem("username", self.username);
                        localStorage.setItem("password", self.password);
                        localStorage.setItem("role", data["ruolo"]);
                    }
                });

                if(localStorage.getItem("role") === "studente") {
                    this.$router.push("/home");
                    console.log("going home");
                } else if(localStorage.getItem("role") === "amministratore") {
                    this.$router.push("/admin");
                    console.log("going to admin");
                }

            }
        },
        valid_input: function() {
            var self = this;
            var res = true;
            if(self.username != "" && self.usernameMissing == true) {
                self.usernameMissing = false;
            }

            if(self.password != "" && self.passwordMissing == true) {
                self.passwordMissing = false;
            }

            if(self.username == "") {
                self.usernameMissing = true;
                res = false;
            }

            if(self.password == "") {
                self.passwordMissing = true;
                res = false;
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
                                    <div class="text-center">
                                        <img src="./img/favicon.png" alt="logo Booksy" width="60" height="60" style="margin-bottom: 2em">
                                    </div>
                                    <!-- username input -->
                                    <div class="form-outline mb-4" v-if="!usernameMissing">
                                        <label class="text-secondary" for="username">Username</label>
                                        <input v-model="username" type="text" id="username" name="username" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-else>
                                        <label class="text-secondary" for="username">Username</label>
                                        <input v-model="username" type="text" id="username" name="username" class="form-control is-invalid"/>
                                        <div id="invalidUsernameFeedback" class="invalid-feedback">Il campo username non può essere vuoto.</div>
                                    </div>
                                
                                    <!-- Password input -->
                                    <div class="form-outline mb-4" v-if="!passwordMissing">
                                        <label class="text-secondary" for="password">Password</label>
                                        <input v-model="password" type="password" id="password" name="password" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-else>
                                        <label class="text-secondary" for="password">Password</label>
                                        <input v-model="password" type="password" id="password" name="password" class="form-control is-invalid"/>
                                        <div id="invalidUsernameFeedback" class="invalid-feedback">Il campo password non può essere vuoto.</div>
                                    </div>
                                    
                                    <div v-if="userNotFound" class="text-secondary text-danger text-center">
                                        <p></p>
                                        Spiacenti, il tuo username o password non sono corretti. Ricontrollali.
                                        <p></p>
                                    </div>
                                
                                    <!-- Submit button -->
                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary btn-block mb-4" @click="login_event" style="width: 10em">Accedi</button> 
                                        <p class="small fw-bold mt-2 pt-1 mb-0">Non hai un account? <router-link to="/register" style="color: #5E17EB">Registrati</router-link></p>
                                    </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `
};