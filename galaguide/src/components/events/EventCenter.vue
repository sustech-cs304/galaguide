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
      <RouterLink :to="{ path: '/events/search', query: { category: 'sport' } }">
        <div class="top-by-sections">
          <div style="display: flex; flex-direction: row; align-items: center; ">
            <h2>This is sport sections</h2>
          </div>
          <div style="display:flex; flex-direction: row; align-content: center; ">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor"
              class="bi bi-person-arms-up" viewBox="0 0 16 16">
              <path d="M8 3a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3" />
              <path
                d="m5.93 6.704-.846 8.451a.768.768 0 0 0 1.523.203l.81-4.865a.59.59 0 0 1 1.165 0l.81 4.865a.768.768 0 0 0 1.523-.203l-.845-8.451A1.5 1.5 0 0 1 10.5 5.5L13 2.284a.796.796 0 0 0-1.239-.998L9.634 3.84a.7.7 0 0 1-.33.235c-.23.074-.665.176-1.304.176-.64 0-1.074-.102-1.305-.176a.7.7 0 0 1-.329-.235L4.239 1.286a.796.796 0 0 0-1.24.998l2.5 3.216c.317.316.475.758.43 1.204Z" />
            </svg>
          </div>


        </div>
      </RouterLink>

      <RouterLink :to="{ path: '/events/search', query: { category: 'literature and art' } }">
        <div class="top-by-sections">
          <div style="display: flex; flex-direction: row; align-items: center; ">
            <h2>This is literature and art sections</h2>
          </div>
          <div style="display:flex; flex-direction: row; align-content: center; ">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-palette"
              viewBox="0 0 16 16">
              <path
                d="M8 5a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3m4 3a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3M5.5 7a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m.5 6a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3" />
              <path
                d="M16 8c0 3.15-1.866 2.585-3.567 2.07C11.42 9.763 10.465 9.473 10 10c-.603.683-.475 1.819-.351 2.92C9.826 14.495 9.996 16 8 16a8 8 0 1 1 8-8m-8 7c.611 0 .654-.171.655-.176.078-.146.124-.464.07-1.119-.014-.168-.037-.37-.061-.591-.052-.464-.112-1.005-.118-1.462-.01-.707.083-1.61.704-2.314.369-.417.845-.578 1.272-.618.404-.038.812.026 1.16.104.343.077.702.186 1.025.284l.028.008c.346.105.658.199.953.266.653.148.904.083.991.024C14.717 9.38 15 9.161 15 8a7 7 0 1 0-7 7" />
            </svg>
          </div>
        </div>
      </RouterLink>
      <RouterLink :to="{ path: '/events/search', query: { category: 'education' } }">

        <div class="top-by-sections">
          <div style="display: flex; flex-direction: row; align-items: center; ">
            <h2>This is education sections</h2>
          </div>
          <div style="display:flex; flex-direction: row; align-content: center; ">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-book"
              viewBox="0 0 16 16">
              <path
                d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783" />
            </svg>
          </div>
        </div>
      </RouterLink>
      <RouterLink :to="{ name: 'SearchResult', query: { category: 'ceremony' } }">
        <div class="top-by-sections">
          <h2>This is ceremony sections</h2>
        </div>
      </RouterLink>

    </div>

    <div class="aid-functions">
      
      <div class="search-box">
        <SearchBox></SearchBox>
      </div>
      <div class="create-event">
        <RouterLink to="/event/create">
          <button class="create-event-button">Create Your Own Event !</button>
        </RouterLink>
      </div>
      <div class="recommend-column">
        <h2>Recommend-column</h2>
        <EventRecommend></EventRecommend>
        
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
  animation: fadeIn 1s ease-in-out;
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
  animation: popIn 1s ease-in-out;
}

.aid-functions {
  width: 30%;
}

.hot-events {
  background-color: #c4c8d4;
  /* Example color */
  margin-bottom: 20px;
  padding: 10px;
  box-sizing: border-box;
  animation: slideDown 1s ease-in-out;
  border: #f2f2f2;
  border-radius: 16px;
}

.top-by-sections {
  width: auto;
  background-color: #83b395;
  /* Example color */
  border-radius: 16px;
  margin-bottom: 20px;
  padding: 10px;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-content: center;
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
  animation: slideDown 1s ease-in-out;
}

.recommend-column {
  flex: 1;
  background-color: #b3d4df;
  /* Example color */
  padding: 20px;
  box-sizing: border-box;
  animation: fadeIn 1s ease-in-out;
  border: #f2f2f2;
  border-radius: 16px;
}
/* Add these styles */
.create-event {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  animation: fadeIn 1s ease-in-out;
}

.create-event-button {
  font-family: "Montserrat", sans-serif;
  font-weight: 600;
  background-color: #cbe091;
  color: #000;
  border: none;
  border-radius: 10px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

.create-event-button:hover {
  background-color: #a5adba;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}

@keyframes slideDown {
    from {
        transform: translateY(-20px);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
}

@keyframes popIn {
    from {
        transform: scale(0.9);
        opacity: 0;
    }
    to {
        transform: scale(1);
        opacity: 1;
    }
}
</style>
