<template>
    <div class="announcements">
        <div class="album">
            <div class="announcement" v-for="(announcement, index) in announcements" :key="index">
                {{ announcement.title }}
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';

export default {
    data() {
        return {
            announcements: []
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
.album {
    display: flex;
    flex-wrap: nowrap;
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
}

.announcement {
    flex: 0 0 auto;
    width: 200px;
    height: 150px;
    margin-right: 10px;
    background-color: #f0f0f0;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
  