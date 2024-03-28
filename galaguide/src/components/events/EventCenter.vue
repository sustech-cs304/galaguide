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

      <div class="top-by-sections">
        <h2>This is top-by-selections</h2>
      </div>
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

<script>
import Eventposter from "./Eventposter.vue";
import SearchBox from "./SearchBox.vue";
import EventRecommend from "./EventRecommend.vue";
//import EventSearch from "./EventSearch.vue";
export default {
  name: "EventCenter",
  components: {
    Eventposter,
    SearchBox,
    EventRecommend,
    //EventSearch,
    // SidebarComponent,
    // HotEventsComponent,
    // SearchComponent,
    // RecommendColumnComponent,
    // TopBySectionsComponent
  },
};
</script>

<style scoped>
.event-center {
  height: 100%;
  display: flex;
  flex-direction: row;
  font-family: "Arial", sans-serif;
  max-width: 100%;
  margin: auto;
}

.sidebar {
  width: 10%;
  flex: 1;
  background-color: #f2f2f2;
  /* Example color */
  padding: 20px;
  box-sizing: border-box;
}

.main-content {
  width: 60%;
  height: 100%;
  flex: 4;
  padding: 20px;
  box-sizing: border-box;
}

.aid-functions {
  width: 30%;
}

.hot-events {
  background-color: #e6e6e6;
  /* Example color */
  margin-bottom: 20px;
  padding: 10px;
  box-sizing: border-box;
}

.top-by-sections {
  background-color: #e6e6e6;
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
  background-color: #d9d9d9;
  /* Example color */
  padding: 20px;
  box-sizing: border-box;
}

.top-by-sections {
  background-color: #d0d0d0;
  /* Example color */
}
</style>
