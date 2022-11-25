export var appointmentCard = {
    props: {
        course: String,
        tutor: String,
        day: String,
        timeSlot: String,
        active: Boolean,
    },
    methods: {
        change_status: function() {
            this.active = !this.active;

            /* Chiamata HTTP che modificherà il dato (stato della prenotazione) anche sul DB */
        }
    },
    template: `
     <div class="card" style="width: 18rem">
        <div class="card-body">
            <h5 class="card-title">{{course}}</h5>
            <h6 class="card-subtitle">{{day}} - {{timeSlot}}</h6>
            <div class="row justify-content-end">
                <div class="badge badge-success" v-if="active">Attivo</div>
                <div class="badge badge-danger" v-else>Inattivo</div>
            </div>
        </div>
     </div>
    `
}