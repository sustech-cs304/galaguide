<script setup>
import Eventposter from "./Eventposter.vue";
import SearchBox from "./SearchBox.vue";
import EventRecommend from "./EventRecommend.vue";

</script>

<template>
  <div class="event-center">
    <div class="side-bar">
      <h2 style="color: #e5e1db">This is sidebar</h2>
    </div>

    <div class="main-content">
      <div class="hot-events">
        <Eventposter></Eventposter>

        <h2>This is hot-events</h2>
      </div>

      <!-- TODO: following sections need links to jump to relating search result pages -->
      <RouterLink to="{path='/search', query:{category:'sport'}}">
        <div class="top-by-sections">
          <h2>This is sport sections</h2>
        </div>
      </RouterLink>

      <RouterLink to="{path='/search', query:{category:'literature and art'}}">
        <div class="top-by-sections">
          <h2>This is literature and art sections</h2>
        </div>
      </RouterLink>
      <RouterLink to="{path='/search', query:{category:'education'}}">
        <div class="top-by-sections">
          <h2>This is academic lecture section</h2>
        </div>
      </RouterLink>
      <RouterLink to="{path='/search', query:{category:'ceremony'}}">
        <div class="top-by-sections">
          <h2>This is ceremony sections</h2>
        </div>
      </RouterLink>

    </div>

    <div class="aid-functions">
      <div class="search-box">
        <SearchBox></SearchBox>
      </div>
      <div class="recommend-column">
        <EventRecommend></EventRecommend>
        <h2>This is recommend-column</h2>
      </div>
    </div>
  </div>
</template>


<style scoped>
.event-center {
  height: 100%;
  display: flex;
  flex-direction: row;
  max-width: 100%;
  margin: auto;
}

.sidebar {
  width: 15%;
  flex: 1;
  background-color: #f2f2f2;
  /* Example color */
  padding: 20px;
  box-sizing: border-box;
}

.main-content {
  width: 55%;
  height: 100%;
  flex: 4;
  padding: 20px;
  box-sizing: border-box;
}

.aid-functions {
  width: 30%;
}

.hot-events {
  background-color: #8797c2;
  /* Example color */
  margin-bottom: 20px;
  padding: 10px;
  box-sizing: border-box;
}

.top-by-sections {
  background-color: #7590cc;
  /* Example color */
  margin-bottom: 20px;
  padding: 10px;
  box-sizing: border-box;
}

.search-box {
  display: flex;
  justify-content: space-between;
  width: 95%;
  height: 100px;
  align-items: center;
  padding: 10px;
  margin-bottom: 20px;
  background-color: whitesmoke;
  background: transparent;
  /* Example color */
  box-sizing: border-box;
}

.recommend-column {
  flex: 1;
  background-color: #b3d4df;
  /* Example color */
  padding: 20px;
  box-sizing: border-box;
}

.top-by-sections {
  background-color: #7aa5a5;
  border-radius: 6px;
  /* Example color */
}
</style>
