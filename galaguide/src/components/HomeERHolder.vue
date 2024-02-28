<script setup>
import { defineProps } from 'vue'

const props = defineProps({
    role: Number
})

const ers = [
    {
        title: 'Tennis Club Meeting',
        date: '2024-02-21',
        link: '#',
        st: '15:00',
        ed: '17:00',
        location: 'Room 101, Teaching Building A'
    },
    {
        title: 'Reservation at the Library for Group Study',
        date: '2024-01-01',
        link: '#',
        st: '08:00',
        ed: '12:00',
        location: 'Library, 3rd Floor, Group Study Area'
    },
    { 
        title: 'English Corner for International Students', 
        date: '2024-01-02', 
        link: '#',
        st: '09:00',
        ed: '11:00',
        location: 'Room 301, Teaching Building C'
    },
    {
        title: 'Tennis Club Meeting',
        date: '2024-02-21',
        link: '#',
        st: '15:00',
        ed: '17:00',
        location: 'Room 101, Teaching Building A'
    },
    {
        title: 'Reservation at the Library for Group Study',
        date: '2024-01-01',
        link: '#',
        st: '08:00',
        ed: '12:00',
        location: 'Library, 3rd Floor, Group Study Area'
    },
    { 
        title: 'English Corner for International Students', 
        date: '2024-01-02', 
        link: '#',
        st: '09:00',
        ed: '11:00',
        location: 'Room 301, Teaching Building C'
    }
]

const recs = [
    {
        title: 'Invitation to the English Corner on Feburary 24th (Saturday)',
        date: '2024-02-21',
        link: '#',
        st: '15:00',
        ed: '17:00',
        location: 'Room 101, Teaching Building A'
    },
    {
        title: 'GRE General Test - Please tell us about your experience',
        date: '2024-01-01',
        link: '#',
        st: '08:00',
        ed: '12:00',
        location: 'Library, 3rd Floor, Group Study Area'
    },
    { 
        title: 'Algorithm Design and Analysis SE2 Spring 2024: CS208 Course Info', 
        date: '2024-01-02', 
        link: '#',
        st: '09:00',
        ed: '11:00',
        location: 'Room 301, Teaching Building C'
    }
]
</script>

<template>
    <div class="home-er-holder" id="home-er-holder">
        <h1 style="text-align: center; margin-bottom: 20px;">Events & Reservations</h1>
        <div v-if="props.role === 0">
            <p style="text-align: center; margin-top: 20px;">Please log in to view events and make reservations.</p>
        </div>
        <div v-else>
            <div class="my-ers">
                <h2 style="text-align: center; margin-bottom: 20px;">My Events & Reservations</h2>
                <div class="er" v-for="(er, index) in ers" :key="index">
                    <span class="title">
                        <a :href="er.link">{{ er.title }}</a>
                    </span>
                    <span class="date">{{ er.date }}</span>
                    <span class="time">{{ er.st }} - {{ er.ed }}</span>
                    <span class="location">{{ er.location }}</span>
                    <span class="cancel">
                        Cancel
                    </span>
                </div>
            </div>
            <div class="recommended-events">
                <h2 style="text-align: center; margin-bottom: 20px;">Recommended For You</h2>
                <div class="er" v-for="(er, index) in recs" :key="index">
                    <span class="title">
                        <a :href="er.link">{{ er.title }}</a>
                    </span>
                    <span class="date">{{ er.date }}</span>
                    <span class="time">{{ er.st }} - {{ er.ed }}</span>
                    <span class="location">{{ er.location }}</span>
                    <span class="accept">
                        Reserve
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.home-er-holder {
    position: absolute;
    top: calc(290px + 7%);
    left: 12%;
    /* width: 83.5%;*/
    right: 2%;
    height: auto;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    /* justify-content: center; */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    padding: 20px;
}

.my-ers {
    position: relative;
    top: 7%;
    left: 1%;
    width: 98%;
    height: auto;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.er {
    margin: 10px;
    border-bottom: 1px solid #f0f0f0;
    padding: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;

}

.title {
    position: relative;
    top: 50%;
    left: 0;
    width: 30%;
    text-align: center;
}

.date {
    position: relative;
    top: 50%;
    left: 0;
    width: 10%;
    text-align: center;
}

.time {
    position: relative;
    top: 50%;
    left: 0;
    width: 10%;
    text-align: center;
}

.location {
    position: relative;
    top: 50%;
    left: 0;
    width: 30%;
    text-align: center;
}

.cancel {
    position: relative;
    top: 50%;
    left: 0;
    width: 10%;
    text-align: center;
    color: white;
    background-color: #ff4d4d;
    border-radius: 5px;
    padding: 5px;
    cursor: pointer;
}

.cancel:hover {
    background-color: #ff3333;
}

.recommended-events {
    position: relative;
    top: 15%;
    left: 1%;
    width: 98%;
    height: auto;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    margin-top: 20px;
}

.accept {
    position: relative;
    top: 50%;
    left: 0;
    width: 10%;
    text-align: center;
    color: white;
    background-color: #4d79ff;
    border-radius: 5px;
    padding: 5px;
    cursor: pointer;
}

.accept:hover {
    background-color: #3366ff;
}

</style>