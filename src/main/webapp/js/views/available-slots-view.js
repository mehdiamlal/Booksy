export var availableSlotsView = {
    data: function () {
        return {
            selectedCorso: "",
            selectedData: "",
            formattedData: "",
            selectedTutor: "",
            listaSlot: [],
            listaCorsi: [],
            listaDocenti: [],
            numeroSlot: 0
        }
    },
    methods: {
        fetchSlots() {
            var self = this;
            if(self.selectedCorso !== "" && self.selectedData !== "") {
                self.formattedData = self.selectedData.substring(8, 10) + "/" +
                    self.selectedData.substring(5, 7) + "/" +
                    self.selectedData.substring(0, 4);
                self.listaSlot = [];
                self.listaDocenti = [];
                self.numeroSlot = 0;
                $.get("http://localhost:8080/progetto_TWeb_war_exploded/slot-disponibili", {
                    action: "ottieniSlotDisponibiliCorso",
                    corso: self.selectedCorso,
                    dataInizio: self.formattedData,
                    dataFine: self.formattedData
                }, function(data) {
                    for(var docente in data) {
                        self.listaDocenti.push(docente);
                        for(var d in data[docente]) {
                            for(var slot in data[docente][d]) {
                                var obj = {
                                    "docente": docente,
                                    "data": d,
                                    "slot": data[docente][d][slot],
                                    "show": true
                                }
                                self.listaSlot.push(obj);
                                self.numeroSlot++;
                            }
                        }
                    }
                });
            }
        },
        filterByTutor() {
            var self = this;
            if(self.selectedCorso !== "" && self.selectedData !== "") {
                if(self.selectedTutor !== "") {
                    self.listaSlot.forEach((item) => {
                        if(item.docente !== self.selectedTutor) {
                            item["show"] = false;
                        } else {
                            item["show"] = true;
                        }
                    })
                }
            }
        }
    },
    template: `
        <navbar logged></navbar>
        <div class="container">
            <h1 class="text-center" style="margin-bottom: 1.5em">Available Slots</h1>
            <div class="input-group">
                <select id="courseFilter" class="form-select" name="corso" v-model="selectedCorso" @change="fetchSlots">
                    <option value="" disabled selected hidden>Select a course</option>
                    <option v-for="corso in listaCorsi" :value="corso">{{corso}}</option>
                </select>
                <input type="date" class="form-control" v-model="selectedData" id="dateFilter" @change="fetchSlots">
                <select id="tutorFilter" class="form-select" name="docente" v-model="selectedTutor" @change="filterByTutor">
                    <option value="" disabled selected hidden>Filter by tutor</option>
                    <option v-for="docente in listaDocenti" :value="docente">{{docente}}</option>
                </select>
            </div>
            <hr>
            <div class="row">
                <slot-card v-for="slot in listaSlot" :tutor="slot.docente" :day="slot.data" :timeSlot="slot.slot" :course="selectedCorso" v-show="slot.show"></slot-card>
            </div>         
            <h5 class="text-center text-muted" style="margin-top: 2em" v-if="numeroSlot === 0 && selectedCorso !== '' && selectedData !== ''">No slots available on {{formattedData}}.</h5>
        </div>
    `,
    created: function() {
        var self = this;
        self.listaCorsi = [];
        document.title = "Available Slots | Booksy";
        if(localStorage.getItem("role") === null || localStorage.getItem("role") !== "studente") {
            self.$router.push("/");
        }
        /*HTTP request per ottenere lista corsi
        i dati li aggiungiamo a self.listaCorsi*/
        $.get("http://localhost:8080/progetto_TWeb_war_exploded/corsi", {
            action: "ottieniCorsiAttivi"
        }, function(data) {
            if(data === "no_session") {
                localStorage.clear();
                self.$router.push("/login");
            } else {
                data.forEach(function (corso) {
                    self.listaCorsi.push(corso.nome);
                });
            }
        });
    }
}