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
                $.post("http://localhost:8080/progetto_TWeb_war_exploded/autentica", {
                    action: "autenticaUtente",
                    username: self.username,
                    password: self.password
                }, function(data) {
                    if(data === null) {
                        self.userNotFound = true;
                    } else {
                        self.userNotFound = false;
                        localStorage.setItem("username", self.username);
                        localStorage.setItem("name", data["nome"]);
                        localStorage.setItem("role", data["ruolo"]);
                        if(localStorage.getItem("role") === "amministratore") {
                            self.$router.push("/admin");
                        } else if(localStorage.getItem("role") === "studente") {
                            self.$router.push("/home");
                        }
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
                document.getElementById("flexCheckDefault").checked = false;
            }

            if(self.username == "") {
                self.usernameMissing = true;
                res = false;
            }

            if(self.password == "") {
                self.passwordMissing = true;
                document.getElementById("flexCheckDefault").checked = false;
                res = false;
            }

            return res;
        },
        showPassword: function() {
            var x = document.getElementById("password");
            if (x.type === "password") {
                x.type = "text";
            } else {
                x.type = "password";
            }
        }
    },
    template: `
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-6 offset-md-3 border p-4 shadow bg-light">
                    <form>
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
                                        <div id="invalidUsernameFeedback" class="invalid-feedback">The username field cannot be empty.</div>
                                    </div>
                                
                                    <!-- Password input -->
                                    <div class="form-outline mb-4" v-if="!passwordMissing">
                                        <label class="text-secondary" for="password">Password</label>
                                        <input v-model="password" type="password" id="password" name="password" class="form-control"/>
                                    </div>
                                    <div class="form-outline mb-4" v-else>
                                        <label class="text-secondary" for="password">Password</label>
                                        <input v-model="password" type="password" id="password" name="password" class="form-control is-invalid"/>
                                        <div id="invalidUsernameFeedback" class="invalid-feedback">The password field cannot be empty.</div>
                                    </div>
                                    <div class="form-check" style="margin-bottom: 2em; margin-top: -1em">
                                        <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" @click="showPassword">
                                        <label class="form-check-label" for="flexCheckDefault">Show Password</label>
                                    </div>
                                    
                                    <div v-if="userNotFound" class="text-secondary text-danger text-center">
                                        <p></p>
                                        The username or password is incorrect. Please, try again.
                                        <p></p>
                                    </div>
                                
                                    <!-- Submit button -->
                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary btn-block mb-4" @click="login_event" style="width: 10em">Login</button> 
                                        <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <router-link to="/register" style="color: #5E17EB">Sign up</router-link></p>
                                    </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `
};