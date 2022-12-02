export var navbar = {
    props: {
        logged: Boolean,
        admin: Boolean
    },
    template: `
        <nav class="navbar navbar-expand-lg sticky-top" style="background-color: #fff;">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="./img/logo.png" alt="Logo" width="166" height="32.3">
                    <span v-if=admin> admin</admin>
                </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse" id="navbarNavDropdown">
                <ul class="navbar-nav ms-auto">
                <li class="nav-item" v-if="logged">
                    <div class="nav-link">
                        <router-link to="/">Home</router-link>
                    </div>
                </li>
                <li class="nav-item" v-if="logged">
                    <div class="nav-link">
                        <router-link to="/corsi">Corsi</router-link>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="nav-link" v-if="logged">
                        <router-link to="/prenotazioni">Prenotazioni</router-link>
                    </div>
                </li>
                <li>
                    <div class="nav-link" v-if="!logged">
                        <router-link to="/login">Login</router-link>
                    </div>
                </li>
                <li>
                    <div class="nav-link" v-if="!logged">
                        <router-link to="/register">Registrati</router-link>
                    </div>
                </li>
                </ul>
            </div>
            </div>
        </nav>
    `
};