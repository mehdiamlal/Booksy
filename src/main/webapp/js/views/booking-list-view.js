export var bookingListView = {
    data: function () {
        return {
            searchDate: "", 
            listaPrenotazioni: [],
            nessunaPrenotazione: false
        }
    },
    methods: {
        filterBookings() {
            var self = this;
            var formattedDate = 
            self.searchDate.substring(8, 10) + "/" +
            self.searchDate.substring(5, 7) + "/" +
            self.searchDate.substring(0, 4);
            var contatorePrenotazioni = self.listaPrenotazioni.length;

            self.listaPrenotazioni.forEach((prenotazione) => {
                if(prenotazione.day.includes(formattedDate)) {
                    prenotazione.show = true;
                } else {
                    prenotazione.show = false;
                    contatorePrenotazioni--;
                }
            });

            if(contatorePrenotazioni === 0) {
                self.nessunaPrenotazione = true;
            }
        },
        clearFilter() {
            var self = this;
            self.searchDate = "";
            self.listaPrenotazioni.forEach((prenotazione) => {
                prenotazione.show = true;
            });
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Le mie prenotazioni</h1>
            <div class="form-group row">
                <div class="col"></div>
                <div class="col-6 text-center">
                    <label for="dateFilter" class="form-label text-muted">Filtra per data:</label>
                    <div class="input-group">
                        <input type="date" class="form-control" v-model="searchDate" id="dateFilter" @change="filterBookings">
                        <button class="btn btn-primary" type="button" @click="clearFilter"><i class="far fa-times"></i></button>
                    </div>
                </div>
                <div class="col"></div>
            </div>
            <hr>
            <div class="row">
                <appointment-card v-for="prenotazione in listaPrenotazioni" 
                :tutor="prenotazione.tutor"
                :email="prenotazione.email"
                :course="prenotazione.course"
                :day="prenotazione.day"
                :timeSlot="prenotazione.timeSlot"
                active v-show="prenotazione.show"></appointment-card>
            </div>
            <h5 class="text-center text-muted" v-if="nessunaPrenotazione" style="margin-top: 2em">Nessuna prenotazione trovata.</h5>
        </div>
    `,
    created: function() {
        var self = this;

        self.listaPrenotazioni = [{
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "23/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        }, {
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "24/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        }, {
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "25/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "26/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        }]
    }
}