export var loginForm = {
    data: function() {
        return {
            username: "",
            usernameMissing: false,
            password: "",
            passwordMissing: false
        }
    },
    methods: {
        login_event: function() {
            var self = this;
            if(self.valid_input()) {
                this.$emit("login-event", {
                    username: self.username,
                    password: self.password
                });
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
        <form>
            <!-- Username input -->
            <div class="form-outline mb-4" v-if="!usernameMissing">
                <div class="form-floating mb-3">
                    <input type="text" v-model="username" class="form-control shadow" id="username" name="username" placeholder="Username" />
                    <label for="username">Username</label>
                </div>
            </div>
            <div class="form-outline mb-4" v-else>
                <div class="form-floating mb-3">
                    <input type="text" v-model="username" class="form-control is-invalid shadow" id="usernameWarning" name="username" placeholder="Username" />
                    <label for="usernameWarning">Username</label>
                    <div id="invalidUsernameFeedback" class="invalid-feedback">Il campo username non può essere vuoto.</div>
                </div>
            </div>

            <!-- Password input -->
            <div class="form-outline mb-4" v-if="!passwordMissing">
                <div class="form-floating mb-3">
                    <input type="password" v-model="password" class="form-control shadow" id="password" name="password" placeholder="Password" />
                    <label for="password">Password</label>
                </div>
            </div>
            <div class="form-outline mb-4" v-else>
                <div class="form-floating mb-3">
                    <input type="password" v-model="password" class="form-control is-invalid shadow" id="passwordWarning" name="password" placeholder="Password" />
                    <label for="passwordWarning">Password</label>
                    <div id="invalidPasswordFeedback" class="invalid-feedback">Il campo password non può essere vuoto.</div>
                </div>
            </div>

            <!-- Submit button -->
            <div class="text-center">
                <button type="button" class="btn btn-primary btn-lg shadow" @click="login_event" style="padding-left: 2.5rem; padding-right: 2.5rem;">Accedi</button>
                <p class="small fw-bold mt-2 pt-1 mb-0">Non hai un account? <a href="#">Registrati</a></p>
            </div>
        </form>
`
};