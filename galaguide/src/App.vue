<template>
  <!--<img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld msg="Welcome to Your Vue.js App" />-->
  <HomeAnnouncements />
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import HomeAnnouncements from './components/HomeAnnouncements.vue'

export default {
  name: 'App',
  components: {
    // HelloWorld,
    HomeAnnouncements
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');
#app {
  /*font-family: Avenir, Helvetica, Arial, sans-serif;*/
  font-family: 'Montserrat', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
a {
  color: rgb(43, 54, 58); 
  text-decoration: none; 
}
a:hover {
  color: rgb(115, 201, 230);
  /*text-decoration: underline;*/
}
</style>
