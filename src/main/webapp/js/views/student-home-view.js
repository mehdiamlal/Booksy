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
                    <button class="btn btn-primary shadow home-btn">Prenota una ripetizione</button>
                    <button class="btn btn-primary shadow home-btn">Le tue prenotazioni</button>
                    <button class="btn btn-primary shadow home-btn">Lista corsi</button>
                    <button class="btn btn-primary shadow home-btn">Controlla disponibilità</button>
                </div>
                <div class="col"></div>
            </div>
        </div>
    `
}