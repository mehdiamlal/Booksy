export var navbar = {
    props: {
        logged: Boolean,
        admin: Boolean
    },
    template: `
        <nav class="navbar navbar-expand-lg sticky-top" style="background-color: #fff;">
            <div class="container">
                <router-link class="navbar-brand" to="/admin" v-if="logged && admin">
                    <img src="./img/logo.png" alt="Logo" width="166" height="32.3">
                    <span v-if=admin> admin</span>
                </router-link>
                <router-link class="navbar-brand" to="/home" v-if="logged && !admin">
                    <img src="./img/logo.png" alt="Logo" width="166" height="32.3">
                </router-link>
                <router-link class="navbar-brand" to="/" v-if="!logged">
                    <img src="./img/logo.png" alt="Logo" width="166" height="32.3">
                </router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse" id="navbarNavDropdown">
                <ul class="navbar-nav ms-auto" v-if="logged && !admin">
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/home" class="nav-link">Home</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/book" class="nav-link">Prenota</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/courses" class="nav-link">Lista Corsi</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/bookings" class="nav-link">Lista Prenotazioni</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/" class="nav-link">Logout</router-link>
                        </div>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto" v-if="logged && admin">
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/admin" class="nav-link">Home</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/admin_courses" class="nav-link">Corsi</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/admin_bookings" class="nav-link">Prenotazioni</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/add_tutor" class="nav-link">Aggiungi Docente</router-link>
                        </div>
                    </li>
                    <li class="nav-item">
                        <div class="nav-link">
                            <router-link to="/" class="nav-link">Logout</router-link>
                        </div>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto" v-if="!logged">
                    <li>
                        <div class="nav-link">
                            <router-link to="/login" class="nav-link">Login</router-link>
                        </div>
                    </li>
                    <li>
                        <div class="nav-link">
                            <router-link to="/register" class="nav-link">Registrati</router-link>
                        </div>
                    </li>
                </ul>
            </div>
            </div>
        </nav>
    `
};