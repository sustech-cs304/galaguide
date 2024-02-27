<template>
    <div class="home-announcements">
        <h1 style="text-align: center; margin-bottom: 20px;">Announcements</h1>
        <Vue3SeamlessScroll :list="announcements" :step="0.2" :hover="true" :wheel="true" class="scroll">
            <div class="announcement" v-for="(announcement, index) in announcements" :key="index">
                <span class="title">
                    <a :href="announcement.link">{{ announcement.title }}</a>
                </span>
                <span class="date">{{ announcement.date }}</span>
            </div>
        </Vue3SeamlessScroll>
    </div>
</template>
    
<script>
import axios from 'axios';
import { ref } from 'vue';
import { Vue3SeamlessScroll } from 'vue3-seamless-scroll';

export default {
    components: { Vue3SeamlessScroll },
    data() {
        return {
            announcements: ref([
                { title: 'Invitation to the English Corner on Feburary 24th (Saturday)', date: '2024-02-21', link: '#' },
                { title: 'GRE General Test - Please tell us about your experience', date: '2024-01-01', link: '#' },
                { title: 'Algorithm Design and Analysis SE2 Spring 2024: CS208 Course Info', date: '2024-01-02', link: '#' },
                { title: '[Info Session] HEC Paris will give an info session on Thursday, Feb. 22, 2024', date: '2024-02-03', link: '#' },
                { title: 'Upper Elementary French Spring 2024: What is the class like and what should I do?', date: '2024-01-04', link: '#' },
                { title: 'Reminder for the Start of Classes in Spring 2024', date: '2024-01-05', link: '#' },
                { title: 'Notice on Issuing and Implementing the Management of Study Abroad Scholarships for Undergraduates of SUSTech', date: '2024-01-06', link: '#' }
            ])
        };
    },
    mounted() {
        this.fetchAnnouncements();
    },
    methods: {
        fetchAnnouncements() {
            axios.get('/api/announcements')
                .then(response => {
                    this.announcements = response.data;
                })
                .catch(error => {
                    console.error('Error fetching announcements:', error);
                });
        }
    }
};
</script>
    
<style>
.home-announcements {
    position: absolute;
    top: 20px;
    left: 12%;
    width: 49%;
    height: 270px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.scroll {
    height: calc(100% - 50px);
    overflow: hidden;
}

.scroll .announcement {
    display: flex;
    justify-content: space-between;
    padding: 8px;
    background-color: #f9f9f9;
    width: 90%;
    margin-left: 5%;
    border-radius: 8px;
    margin-bottom: 10px;
}

.scroll .announcement:hover {
    background-color: #ebebeb;
}

.title {
    flex: 1;
    margin-right: 20px;
}

.date {
    font-size: 0.9em;
    color: #888;
}
</style>
  