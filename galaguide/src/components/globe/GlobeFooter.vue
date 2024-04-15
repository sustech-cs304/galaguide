<script setup>
import {onMounted, ref} from 'vue'

const maxHeight = ref(0)

onMounted(() => {
  setTimeout(() => {
    try {
      maxHeight.value = document.getElementById('home-er-holder').clientHeight +
          document.getElementById('home-announcements').clientHeight +
          document.getElementById('top-bar').clientHeight + 100
    } catch (e) {
      console.log(e)
      return
    }
    // maxHeight.value = document.getElementById('home-er-holder').clientHeight +
    //     document.getElementById('home-announcements').clientHeight +
    //     document.getElementById('top-bar').clientHeight + 100
    // console.log(document.getElementById('home-er-holder').clientHeight)
    // console.log(document.getElementById('home-announcements').clientHeight)
    // console.log(document.getElementById('top-bar').clientHeight)
    // console.log(ele.clientHeight,ele.style.border,ele.clientTop)
    console.log('maxHeight:', maxHeight.value)
    document.getElementById('footer').style.display = 'block'
    document.getElementById('footer').style.top = `${maxHeight.value}px`
  }, 500)
})

</script>

<template>
  <div id="footer" class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>Contributing</h3>
        <p>
          <a href="../../../../CONTRIBUTING.md" style="text-decoration: underline;" target="_blank">Contributing
            Guidelines</a>
        </p>
      </div>
      <!-- <div class="footer-section">
          <h3>Product</h3>
          <ul>
              <li>Features</li>
              <li>Pricing</li>
              <li>FAQs</li>
          </ul>
      </div> -->
      <div class="footer-section">
        <h3>Contact</h3>

        <p>
          <svg class="bi bi-github" fill="currentColor" height="30" viewBox="-4 -4 20 20"
               width="30" xmlns="http://www.w3.org/2000/svg">
            <path
                d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.012 8.012 0 0 0 16 8c0-4.42-3.58-8-8-8z"/>
          </svg>&nbsp;
          <a href="https://github.com/sustech-cs304/team-project-24spring-23333/tree/main" style="text-decoration: underline;"
             target="_blank">GitHub Repository</a>
        </p>
      </div>
    </div>
    <div class="footer-bottom">
      <img alt="Company Logo" class="footer-logo" src="../../assets/logo.png">
      <p>&copy; 2024 GalaGuide. All rights reserved.</p>
    </div>
  </div>
</template>

<style scoped>
.footer {
  display: none;
  position: absolute;
  top: 0;
  left: 10%;
  width: 90%;
  height: auto;
  background-color: #f0f0f0;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.footer-section {
  flex: 1;
  margin-right: 20px;
}

.footer-section h3 {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section ul li {
  margin-bottom: 5px;
}

.footer-bottom {
  margin-top: 20px;
  padding: 20px 0;
  margin-bottom: 20px;
  text-align: center;
}

.footer-bottom img.footer-logo {
  width: 100px;
  height: auto;
  margin-bottom: 10px;
}

.footer-bottom p {
  margin: 0;
  font-size: 0.9rem;
}
</style>
