<template>
    <div id="map-center">
        <div ref="mapContainer" class="map-container"></div>
        <div id="prev-event" class="toggler" @click="prevEvent">
            <span>Previous Event</span>
        </div>
        <div class="toggler" @click="toggleMode">
            <span>{{ mode ? "Showing All Events" : "Showing My Events" }}</span>
        </div>
        <div id="next-event" class="toggler" @click="nextEvent">
            <span>Next Event</span>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";
import { useRouter } from "vue-router";
import 'leaflet-routing-machine';
const mapContainer = ref(null);
const mode = ref(true);
const router = useRouter();

let pos = 0;

const allevents = ref([
    { 
        event_id: 1,
        lat: -31.57,
        lng: 115.5,
        name: "Symposium on Degenarative Diseases",
        st: "2022-10-10 08:00",
        ed: "2022-10-12 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on degenerative diseases.",
        tags: ["symposium", "degenerative diseases"],
        img: "https://via.placeholder.com/150",
    },
    { 
        event_id: 2,
        lat: -31.572,
        lng: 115.53,
        name: "Overall Health and Wellness",
        st: "2022-10-12 09:00",
        ed: "2022-10-12 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on overall health and wellness.",
        tags: ["health"],
        img: "https://via.placeholder.com/150",
    },
    { 
        event_id: 3,
        lat: -31.573,
        lng: 115.54,
        name: "Symposium on Mental Health",
        st: "2022-10-14 09:00",
        ed: "2022-10-14 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on mental health.",
        tags: ["symposium", "mental health"],
        img: "https://via.placeholder.com/150",
    },
    { 
        event_id: 4,
        lat: -31.574,
        lng: 115.55,
        name: "Symposium on Infectious Diseases",
        st: "2022-10-16 09:00",
        ed: "2022-10-16 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on infectious diseases.",
        tags: ["symposium", "infectious diseases"],
        img: "https://via.placeholder.com/150",
    },
    { 
        event_id: 5,
        lat: -31.575,
        lng: 115.56,
        name: "Symposium on Cardiovascular Diseases",
        st: "2022-10-18 09:00",
        ed: "2022-10-18 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on cardiovascular diseases.",
        tags: ["symposium", "cardiovascular diseases"],
        img: "https://via.placeholder.com/150",
    },
    { 
        event_id: 6,
        lat: -31.576,
        lng: 115.57,
        name: "Symposium on Cancer",
        st: "2022-10-20 09:00",
        ed: "2022-10-20 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        desc: "A symposium on cancer.",
        tags: ["symposium", "cancer"],
        img: "https://via.placeholder.com/150",
    }
]);

const myevents = ref([
    { 
        event_id: 2,
        lat: 55.505,
        lng: 1.09,
        name: "Overall Health and Wellness",
        st: "2022-10-12 09:00",
        ed: "2022-10-12 17:00",
        venue: "102, Lecture Hall 1",
        host: "John Hopkins University",
        tags: ["health"],
        img: "https://via.placeholder.com/150",
    }
]);

let map = null;
const routingControl = ref(null);

const eventShow = (someevent) => {
    return `<b style="font-size: 150%;">${someevent.name}</b><br>By ${someevent.host}<br>From <b>${someevent.st}</b> to <b>${someevent.ed}</b><br>At <b>${someevent.venue}</b><br><img src="${someevent.img}" style="width: 150px; height: auto;"><br>${someevent.desc}<br>More details or reservation: <a href="/events/${someevent.event_id}">Click here</a>`;
};

const getRoute = () => {
    axios.get("http://ip-api.com/json/")
        .then((res) => {
            const { lat, lon } = res.data;
            L.marker([lat, lon])
                .addTo(map)
                .bindPopup("You are here")
                .openPopup();
            map.setView([lat, lon], 13);

            map.eachLayer((layer) => {
                if (layer instanceof L.Routing.control) {
                    map.removeControl(layer);
                }
            });

            L.Routing.control({
                waypoints: [
                    L.latLng(lat, lon),
                    L.latLng(mode.value ? allevents.value[pos].lat : myevents.value[pos].lat, mode.value ? allevents.value[pos].lng : myevents.value[pos].lng),
                ],
            }).addTo(map);
            const marker = L.marker([mode.value ? allevents.value[pos].lat : myevents.value[pos].lat, mode.value ? allevents.value[pos].lng : myevents.value[pos].lng]).addTo(map);
            marker.bindPopup(eventShow(mode.value ? allevents.value[pos] : myevents.value[pos])).openPopup();
        }).catch((error) => {
            console.log(error);
        });
};

const toggleMode = () => {
    mode.value = !mode.value;
    map.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);
        }
    });

    if (mode.value) {
        allevents.value.forEach((event) => {
            const marker = L.marker([event.lat, event.lng]).addTo(map);
            marker.bindPopup(eventShow(event)).openPopup();
        });
    } else {
        myevents.value.forEach((event) => {
            const marker = L.marker([event.lat, event.lng]).addTo(map);
            marker.bindPopup(eventShow(event)).openPopup();
        });
    }
    getRoute();
};

const prevEvent = () => {
    if (mode.value) {
        pos = pos === 0 ? allevents.value.length - 1 : pos - 1;
        map.setView(allevents.value[pos], 13);
    } else {
        pos = pos === 0 ? myevents.value.length - 1 : pos - 1;
        map.setView(myevents.value[pos], 13);
    }
    getRoute();
};

const nextEvent = () => {
    if (mode.value) {
        pos = pos === allevents.value.length - 1 ? 0 : pos + 1;
        map.setView(allevents.value[pos], 13);

    } else {
        pos = pos === myevents.value.length - 1 ? 0 : pos + 1;
        map.setView(myevents.value[pos], 13);
    }
    getRoute();
};

onMounted(() => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            router.push("/login");
        }
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        axios.get("/api/event-center/list/all").then((res) => {
            console.log(res.data);
            allevents.value = res.data.data;
        }).catch((err) => {
            console.log(err);
        });
        axios.get("/api/event-center/list/my").then((res) => {
            console.log(res.data);
            myevents.value = res.data.data;
        }).catch((err) => {
            console.log(err);
        });
        delete L.Icon.Default.prototype._getIconUrl;
        L.Icon.Default.mergeOptions({
            iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
            iconUrl: require("leaflet/dist/images/marker-icon.png"),
            shadowUrl: require("leaflet/dist/images/marker-shadow.png")
        });
        map = L.map(mapContainer.value).setView(allevents.value[pos], 13);

        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            attribution:
                'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
        }).addTo(map);

        allevents.value.forEach((event) => {
            const marker = L.marker([event.lat, event.lng]).addTo(map);
            marker.bindPopup(eventShow(event)).openPopup();
        });

        axios.get("http://ip-api.com/json/")
            .then((res) => {
                const { lat, lon } = res.data;
                L.marker([lat, lon])
                    .addTo(map)
                    .bindPopup("You are here")
                    .openPopup();
                map.setView([lat, lon], 13);

                routingControl.value = L.Routing.control({
                    waypoints: [
                        L.latLng(lat, lon),
                        L.latLng(allevents.value[pos].lat, allevents.value[pos].lng),
                    ],
                }).addTo(map);
            }).catch((error) => {
                console.log(error);
            });
    }catch (error) {
        console.log(error);
    }
});
</script>

<style scoped>
#map-center {
    position: absolute;
    left: 10%;
    height: 100%;
    width: 90%;
    z-index: 1;
}
.map-container {
    position: absolute;
    left: 0%;
    height: 80%;
    width: 100%;
    overflow: scroll;
}

.toggler {
    position: absolute;
    left: 25%;
    top: 80%;
    height: 10%;
    width: 50%;
    background-color: #34495e;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    margin-top: 10px;
    border-radius: 5px;
}

.toggler:hover {
    background-color: #2c3e50;
}

.toggler span {
    font-size: 150%;
}

#prev-event {
    position: absolute;
    left: 6%;
    width: 18%;
}

#prev-event:hover {
    background-color: #34495e;
}

#next-event {
    position: absolute;
    left: 76%;
    width: 18%;
}

#prev-event:hover {
    background-color: #34495e;
}

</style>
  