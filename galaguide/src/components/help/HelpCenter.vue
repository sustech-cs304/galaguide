<template>
  <div>
    <h1>Help Center</h1>
    <p>GalaGuide is an MIT-licensed open-source cross-platform software application</p>
    <p>as your personal manager for campus events & reservations based on Electron, Vue, and Ktor.</p>
    <p>For help, please contact us at <a href="mailto:12211634@mail.sustech.edu.cn">this email</a>.</p>
    <div id="fireworks-container" class="fireworks-container"></div>
  </div>
</template>

<script setup>
import {onMounted} from 'vue';

onMounted(() => {
  console.log('Fireworks!');
  createFireworks();
});

function createFireworks() {
  const container = document.getElementById('fireworks-container');
  console.log(container);

  const colors = [];
  const saturation = 60;
  for (let hue = 0; hue < 360; hue += 10) {
    colors.push(`hsl(${hue}, ${saturation}%, 50%)`);
  }

  setInterval(() => {
    const firework = document.createElement('div');
    firework.classList.add('firework');
    firework.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
    firework.style.left = `${Math.random() * 100}%`;
    firework.style.top = `${Math.random() * 100}%`;
    firework.style.animationDuration = `${1 + Math.random()}s`;
    container.appendChild(firework);

    setTimeout(() => {
      container.removeChild(firework);
    }, 2000);
  }, 100);
}
</script>

<style>
.fireworks-container {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.firework {
  position: absolute;
  width: 4px;
  height: 4px;
  background-color: hsl(200, 100%, 50%);
  border-radius: 50%;
  animation: explode 1.5s linear;
}

@keyframes explode {
  0% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 1;
    transform: scale(20);
  }
  100% {
    opacity: 0;
    transform: scale(0);
  }
}
</style>
