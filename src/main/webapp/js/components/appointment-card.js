export var appointmentCard = {
    props: {
        tutor: String,
        course: String,
        day: String,
        timeSlot: String,
        active: Boolean,
        completed: Boolean
    },
    data: function() {
            var self = this;
            return {
                tutorData: self.tutor,
                courseData: self.course,
                dayData: self.day,
                timeSlotData: self.timeSlot,
                activeData: self.active,
                completedData: self.completed
            }
    },
    methods: {
        deleteBooking: function() {
            var self = this;
            /* Chiamata HTTP che imposta prenotazione come attiva = 0 sul DB */
            $.post("http://localhost:8080/progetto_TWeb_war_exploded/prenotazioni", {
                action: "rimuoviPrenotazione",
                username: self.userData,
                emailDocente: self.tutorData,
                idCorso: self.courseData,
                data: self.dayData,
                fasciaOraria: self.timeSlotData
            }, function(data) {
                if(data === "no_session") {
                    localStorage.clear();
                    self.$router.push("/login");
                }
            });
            self.activeData = !self.activeData;
            console.log("Cancellata prenotazione del: " + self.dayData);
        },
        completeBooking: function() {
            var self = this;
            /* Chiamata HTTP che imposta prenotazione come completata = 1 sul DB */
            $.post("http://localhost:8080/progetto_TWeb_war_exploded/prenotazioni", {
               action: "impostaPrenotazioneEffettuata",
               emailDocente: self.tutorData,
               data: self.dayData,
               fasciaOraria: self.timeSlotData
            }, function(data) {
                if(data === "no_session") {
                    localStorage.clear();
                    self.$router.push("/login");
                }
            });
            self.completedData = !this.completedData;
            console.log("Completata prenotazione del: " + self.dayData);
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <div class="d-flex justify-content-between mb-4">
                    <div class="user-info">
                        <div class="user-info__basic">
                            <h5 class="mb-0">{{tutorData}}</h5>
                        </div>
                    </div>
                    <div class="dropdown open" v-if="activeData && !completedData">
                        <a href="#!" class="px-2" id="triggerId1" data-toggle="dropdown" aria-haspopup="true"
                           aria-expanded="false">
                            <i class="fa fa-ellipsis-v" style="color: #5E17EB !important;"></i>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="triggerId1">
                            <a class="dropdown-item text-danger" @click="deleteBooking"><i
                                class="fa fa-trash mr-1"></i> Cancella</a>
                            <a class="dropdown-item text-success" @click="completeBooking"><i class="fas fa-check-circle"></i> Effettua</a>
                        </div>
                    </div>
                </div>
                <h4 class="mb-0">{{courseData}}</h4>
                <div class="d-flex justify-content-between mt-4">
                    <div>
                        <h5 class="mb-0">{{timeSlotData}}
                            <small class="ml-1 text-muted">{{dayData}}</small>
                        </h5>
                    </div>
                    <span class="text-primary fw-bold" v-if="!completedData && activeData">Attiva</span>
                    <span class="text-success fw-bold" v-else-if="completedData">Effettuata</span>
                    <span class="text-danger fw-bold" v-else>Cancellata</span> 
                </div>
            </div>
        </div>
    `
}

