export var appointmentCard = {
    props: {
        tutor: String,
        email: String,
        course: String,
        day: String,
        timeSlot: String,
        active: Boolean,
    },
    data: function() {
            var self = this;
            return {
                tutorData: self.tutor,
                emailData: self.email,
                courseData: self.course,
                dayData: self.day,
                timeSlotData: self.timeSlot,
                activeData: self.active
            }
    },
    methods: {
        change_status: function() {
            var self = this;
            /* Chiamata HTTP che modificherà il dato (stato della prenotazione) sul DB */
            self.activeData = !self.activeData;
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <div class="d-flex justify-content-between mb-4">
                    <div class="user-info">
                        <div class="user-info__basic">
                            <h5 class="mb-0">{{tutorData}}</h5>
                            <p class="text-muted mb-0">{{emailData}}</p>
                        </div>
                    </div>
                    <div class="dropdown open" v-if="activeData">
                        <a href="#!" class="px-2" id="triggerId1" data-toggle="dropdown" aria-haspopup="true"
                           aria-expanded="false">
                            <i class="fa fa-ellipsis-v" style="color: #5E17EB !important;"></i>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="triggerId1">
                            <a class="dropdown-item text-danger" @click="change_status"><i
                                class="fa fa-trash mr-1"></i> Cancella</a>
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
                    <span class="text-success fw-bold" v-if="activeData">Attiva</span>
                    <span class="text-danger fw-bold" v-else>Cancellata</span> 
                </div>
            </div>
        </div>
    `
}

