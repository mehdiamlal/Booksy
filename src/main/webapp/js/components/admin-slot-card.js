export var adminSlotCard = {
    props: {
        tutor: String,
        day: String,
        timeSlot: String,
        course: String
    },
    data: function() {
        var self = this;
        return {
            tutorData: self.tutor,
            dayData: self.day,
            timeSlotData: self.timeSlot
        }
    },
    template: `
        <div class="col-sm-6 col-md-6 col-lg-4">
            <div class="card bg-white p-3 mb-4 shadow">
                <div class="d-flex justify-content-between mb-4">
                    <div class="user-info">
                        <div class="user-info__basic">
                            <h5 class="mb-0">Docente: <br><span class="text-muted">{{tutorData}}</span></h5>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-between mt-4">
                    <div>
                        <h5 class="mb-0">{{timeSlotData}} |
                            <small class="ml-1 text-muted">{{dayData}}</small>
                        </h5>
                    </div>
                </div>
            </div>
        </div>
    `
}