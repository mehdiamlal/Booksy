export var adminHomeView = {
    data: function() {
        return {
            nomeAdmin: localStorage.getItem("name")
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center">Benvenuto, {{nomeAdmin}}. &#128075;</h1>
            <hr>
            <div class="row text-center" style="margin-top: 2em">
                <div class="col"></div>
                <div class="col">
                    <router-link to="/admin_courses">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Lista corsi</button>
                    </router-link>
                    <router-link to="/admin_bookings">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Lista prenotazioni</button>
                    </router-link>
                    <router-link to="/add_tutor">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Aggiungi docente</button>
                    </router-link>
                </div>
                <div class="col"></div>
            </div>
        </div>
    `,
    mounted() {
        var self = this;
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "amministratore") {
            self.$router.push("/");
        }
    }
}