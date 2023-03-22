export var adminHomeView = {
    data: function() {
        return {
            nomeAdmin: localStorage.getItem("name")
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center">Welcome back, {{nomeAdmin}}. &#128075;</h1>
            <hr>
            <div class="row text-center" style="margin-top: 2em">
                <div class="col"></div>
                <div class="col">
                    <router-link to="/admin_available_slots">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Available Slots</button>
                    </router-link>
                    <router-link to="/admin_courses">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Course List</button>
                    </router-link>
                    <router-link to="/admin_bookings">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Booking List</button>
                    </router-link>
                    <router-link to="/add_tutor">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 12em">Add Tutor</button>
                    </router-link>
                </div>
                <div class="col"></div>
            </div>
        </div>
    `,
    mounted() {
        var self = this;
        document.title = "Admin | Booksy";
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "amministratore") {
            self.$router.push("/");
        }
    }
}