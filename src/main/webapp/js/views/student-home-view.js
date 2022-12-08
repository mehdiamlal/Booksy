export var studentHomeView = {
    data: function() {
        return {
            prenotImminenti: [{
                tutor: "Mehdi Amlal",
                email: "mehdi@gmail.com",
                course: "Informatica",
                day: "23/01/2023",
                timeSlot: "16:00 - 17:00",
            },{
                tutor: "Mehdi Amlal",
                email: "mehdi@gmail.com",
                course: "Informatica",
                day: "24/01/2023",
                timeSlot: "16:00 - 17:00",
            },{
                tutor: "Mehdi Amlal",
                email: "mehdi@gmail.com",
                course: "Informatica",
                day: "25/01/2023",
                timeSlot: "16:00 - 17:00",
            }]
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center">Benvenuto, Mehdi. &#128075;</h1>
            <hr>
            <h3 class="text-center" style="margin: 1em">Le tue prenotazioni più imminenti</h3>
            <div class="row">
                <appointment-card v-for="prenotazione in prenotImminenti" 
                    :tutor="prenotazione.tutor"
                    :email="prenotazione.email"
                    :course="prenotazione.course"
                    :day="prenotazione.day"
                    :timeSlot="prenotazione.timeSlot"
                    active v-if="prenotImminenti.length > 0"></appointment-card>
                <h5 class="text-center text-muted" v-else >Nessuna prenotazione imminente.</h5>
            </div>
            <hr>
            <div class="row text-center" style="margin-top: 2em">
                <div class="col"></div>
                <div class="col">
                    <router-link to="/book">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Prenota una ripetizione</button>
                    </router-link>
                    <router-link to="/bookings">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Le tue prenotazioni</button>
                    </router-link>
                    <router-link to="/courses">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Lista corsi</button>
                    </router-link>
                    <router-link to="/home">
                        <button class="btn btn-primary shadow home-btn" style="margin: 1em; width: 15em">Controlla disponibilità</button>
                    </router-link>
                </div>
                <div class="col"></div>
            </div>
        </div>
    `
}