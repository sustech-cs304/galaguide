<template>
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <h1>Login with your account</h1>
        <form @submit="check">
          <div class="form-group">
            <label>Name: </label>
            <input type="text" class="form-control" v-model="user.name" />
          </div>
          <div class="form-group">
            <label>Password: </label>
            <input
              type="password"
              class="form-control"
              v-model="user.password"
              id="ps"
            />
            <button
              type="button"
              class="btn btn-primary"
              style="left: 65%"
              @click="togglePassword"
            >
              View
            </button>
          </div>
          <div class="form-group">
            <label
              id="errors"
              style="color: red; position: relative; left: 0"
            ></label>
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <router-link to="/register" class="btn btn-link"
          >Don't have an account? Register.</router-link
        >
      </div>
    </div>
  </div>
  <div class="words"></div>
</template>

<script setup>
// import UserService from "../../service/UserService";
import axios from "axios";
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const user = ref({
  name: "",
  email: "",
  password: "",
});

const errors = ref([]);

// const encSHA256 = async (str) => {
//     const encoder = new TextEncoder();
//     const data = encoder.encode(str);
//     const hash = await crypto.subtle.digest("SHA-256", data);
//     let result = "";
//     const view = new DataView(hash);
//     for (let i = 0; i < hash.byteLength; i += 4) {
//         result += ("00000000" + view.getUint32(i).toString(16)).slice(-8);
//     }
//     return result;
// };

const setCookie = (cname, cvalue, exdays) => {
  const d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  const expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
};

const togglePassword = () => {
  const password = document.querySelector("input[type='password']");
  if (password !== null) {
    password.type = "text";
  } else {
    document.querySelector("#ps").type = "password";
  }
};

const check = (e) => {
  e.preventDefault();
  errors.value = [];

  if (!user.value.name || !user.value.password) {
    errors.value.push("Enter valid values");
  } else {
    // use jwt scheme
    axios
      .post("/api/user/login", {
        nameOrEmail: user.value.name,
        password: user.value.password,
      })
      .then((res) => {
        if (res.data.status === 200) {
          console.log("success")
          localStorage.setItem("token", res.data.token);
          localStorage.setItem("user", JSON.stringify(res.data.user));
          setCookie("token", res.data.token, 1);
          setCookie("userRole", res.data.user_role, 1);
          router.push("/");
          location.replace("/");
          location.reload();
          location.replace("/");
        } else {
          console.log(res.data.message)
          errors.value.push(res.data.message);
          document.querySelector("#errors").textContent = errors.value.join("<br>");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }

  document.querySelector("#errors").textContent = errors.value.join("<br>");
};

onMounted(() => {
  console.log("UserLogin mounted");
  const ele = document.querySelector(".words");
  setInterval(() => {
    for (let i = 0; i < 1; i++) {
      const span = document.createElement("span");
      span.textContent = "🍀 Welcome to GalaGuide";
      span.style.fontSize = Math.random() * 20 + 10 + "px";
      span.style.color = `hsl(${Math.random() * 360}, 50%, 50%)`;
      span.style.position = "absolute";
      span.style.left = "100%";
      span.style.top = Math.random() * 100 + "%";
      span.style.zIndex = -1;
      ele.appendChild(span);
      let shit = 100;
      setInterval(() => {
        shit -= 0.1;
        span.style.left = shit + "%";
      }, 10);
      setTimeout(() => {
        span.remove();
      }, 20000);
    }
  }, 3000);
});

onUnmounted(() => {
  console.log("UserLogin unmounted");
});
</script>

<style scoped>
.container {
  margin-top: 50px;
  position: absolute;
  left: 10%;
  width: 90%;
}

label {
  position: relative;
  margin-bottom: 5px;
  font-size: 1.2rem;
  color: #333;
  left: -15%;
  width: 10%;
}

h1 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

input {
  position: absolute;
  width: 20%;
  height: 30px;
  left: 50%;
  margin-bottom: 20px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 0 10px;
  transform: translate(-50%);
}

.btn-primary {
  position: absolute;
  width: 5%;
  height: 30px;
  left: 50%;
  margin-bottom: 20px;
  border-radius: 5px;
  border: none;
  background-color: #23b375;
  color: white;
  cursor: pointer;
  transform: translate(-50%);
  font-family: "Montserrat", sans-serif;
}

.btn-primary:hover {
  background-color: #1f9e64;
}

.btn-link {
  position: absolute;
  width: 55%;
  top: 120%;
  height: 30px;
  left: 50%;
  margin-bottom: 20px;
  border-radius: 5px;
  border: none;
  color: black;
  cursor: pointer;
  transform: translate(-50%);
  font-family: "Montserrat", sans-serif;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.btn-link:hover {
  color: rgb(133, 207, 232);
  text-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

@keyframes fadeInOut {
  0%,
  100% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
}

.words {
  border: 1px solid black;
  white-space: nowrap;
  overflow: hidden;
  display: inline-block;
}
</style>
