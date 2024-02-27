<template>
    <div>
        <h1 style="text-align: center; margin-bottom: 20px;">Announcements</h1>
    </div>
    <Vue3SeamlessScroll 
        :list="announcements" 
        :step="1"
        :hover="true"
        :wheel="true"
        class="scroll">
        <div class="announcement" v-for="(announcement, index) in announcements" :key="index">
            <span class="title">
                <a :href="announcement.link">{{ announcement.title }}</a>
            </span>
            <span class="date">{{ announcement.date }}</span>
        </div>
    </Vue3SeamlessScroll>
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
                { title: 'Loading...', date: '0', link: '#' },
                { title: 'Baidu', date: '2021-01-01', link: 'https://www.baidu.com'},
                { title: 'Google', date: '2021-01-02', link: 'https://www.google.com'},
                { title: 'Bing', date: '2021-01-03', link: 'https://www.bing.com'},
                { title: 'Yahoo', date: '2021-01-04', link: 'https://www.yahoo.com'},
                { title: 'DuckDuckGo', date: '2021-01-05', link: 'https://www.duckduckgo.com'},
                { title: 'Yandex', date: '2021-01-06', link: 'https://www.yandex.com'}
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
.scroll {
  height: 270px;
  width: 500px;
  margin: 10px auto;
  overflow: hidden;
}

.scroll .announcement {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  background-color: #f9f9f9;
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
