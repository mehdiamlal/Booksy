export var navbar = {
    props: {
        logged: Boolean
    },
    data: function() {
        var self = this;
        return {
            loggedData: self.logged
        }
    },
    template: `
        <nav class="navbar navbar-expand-lg sticky-top" style="background-color: #fff;">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="./img/logo.png" alt="Logo" width="166" height="32.3">
                </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse" id="navbarNavDropdown">
                <ul class="navbar-nav ms-auto">
                <li class="nav-item" v-if="loggedData">
                    <div class="nav-link">
                        <router-link to="/">Home</router-link>
                    </div>
                </li>
                <li class="nav-item" v-if="loggedData">
                    <div class="nav-link">
                        <router-link to="/corsi">Corsi</router-link>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="nav-link" v-if="loggedData">
                        <router-link to="/prenotazioni">Prenotazioni</router-link>
                    </div>
                </li>
                <li>
                    <div class="nav-link" v-if="!loggedData">
                        <router-link to="/login">Login</router-link>
                    </div>
                </li>
                <li>
                    <div class="nav-link" v-if="!loggedData">
                        <router-link to="/register">Registrati</router-link>
                    </div>
                </li>
                </ul>
            </div>
            </div>
        </nav>
    `
};