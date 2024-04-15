<template>
  <div id="home-announcements" class="home-announcements">
    <h1 style="text-align: center; margin-bottom: 20px;">Recent</h1>
    <Vue3Marquee :duration="10" :pauseOnHover="true" :vertical="true" class="scroll" style="width: 100%;">
      <div v-for="(announcement, index) in announcements" :key="index" class="announcement">
                <span class="title">
                    <a :href="announcement.link">{{ announcement.title }}</a>
                </span>
        <span class="date">{{ announcement.date }}</span>
      </div>
    </Vue3Marquee>
  </div>
</template>

<script>
import axios from 'axios';
import {ref} from 'vue';
import {Vue3Marquee} from 'vue3-marquee';

export default {
  components: {Vue3Marquee,},
  data() {
    return {
      announcements: ref([])
    };
  },
  mounted() {
    this.fetchAnnouncements();
  },
  methods: {
    fetchAnnouncements() {
      axios.get('/api/showcase')
          .then(response => {
            console.log(response.data)
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
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
.home-announcements {
  position: absolute;
  top: 7%;
  left: 12%;
  width: 49%;
  height: 270px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.scroll {
  position: absolute;
  width: 100%;
  height: calc(100% - 50px);
  overflow: hidden;
}

.scroll .announcement {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  background-color: #f9f9f9;
  width: 80%;
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
  