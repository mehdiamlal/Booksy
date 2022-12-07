export var adminBookingListView = {
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

            self.nessunaPrenotazione = contatorePrenotazioni === 0;
        },
        clearFilter() {
            var self = this;
            self.searchDate = "";
            self.listaPrenotazioni.forEach((prenotazione) => {
                prenotazione.show = true;
            });
            self.nessunaPrenotazione = self.listaPrenotazioni.length === 0;
        }
    },
    template: `
        <navbar logged admin></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Lista prenotazioni</h1>
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
            <div class="row" style="margin-bottom: 7em">
                <admin-booking-card v-for="prenotazione in listaPrenotazioni"
                :user="prenotazione.user" 
                :tutor="prenotazione.tutor"
                :email="prenotazione.email"
                :course="prenotazione.course"
                :day="prenotazione.day"
                :timeSlot="prenotazione.timeSlot"
                active v-show="prenotazione.show"></admin-booking-card>
            </div>
            <h5 class="text-center text-muted" v-if="nessunaPrenotazione" style="margin-top: 2em">Nessuna prenotazione trovata.</h5>
        </div>
    `,
    created: function() {
        var self = this;

        self.listaPrenotazioni = [{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "23/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        }, {
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "24/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        }, {
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "25/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "26/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "27/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "27/01/2023",
            timeSlot: "15:00 - 16:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "28/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "29/01/2023",
            timeSlot: "16:00 - 17:00",
            show: true
        },{
            user: "heymehdi",
            tutor: "Mehdi Amlal",
            email: "mehdi@gmail.com",
            course: "Informatica",
            day: "26/01/2023",
            timeSlot: "18:00 - 19:00",
            show: true
        }]
    }
}