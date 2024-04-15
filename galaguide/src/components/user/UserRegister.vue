<template>
  <div class="container">
    <div id="main-reg" class="row">
      <div class="col-md-6">
        <h1>Register to enroll in campus galas!</h1>
        <form @submit="validateAndSubmit">
          <div class="form-group">
            <label>Your Name: </label>
            <input v-model="user.name" class="form-control" type="text"/>
          </div>
          <div class="form-group">
            <label>Your Email: </label>
            <input v-model="user.email" class="form-control" type="text"/>
          </div>
          <div class="form-group">
            <label>Your Password: </label>
            <input
                id="ps"
                v-model="user.password"
                class="form-control"
                type="password"
            />
            <button
                class="btn btn-primary"
                style="left: 65%"
                type="button"
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
          <button class="btn btn-primary" type="submit">Submit</button>
          <router-link class="btn btn-link" to="/login"
          >Already have an account? Login.
          </router-link
          >
        </form>
      </div>
    </div>
    <div id="verify-email" style="display: none">
      <h1>Verify your email</h1>
      <p>Check your email for a 6-digit verification code.</p>
      <form @submit="verifyEmail">
        <div class="form-group">
          <label>Verification Code: </label>
          <input
              v-model="user.verificationCode"
              class="form-control"
              type="text"
          />
        </div>
        <div class="form-group">
          <label
              id="errors-email"
              style="color: red; position: relative; left: 0"
          ></label>
        </div>
        <button class="btn btn-primary" type="submit">Submit</button>
      </form>
    </div>
  </div>
  <div class="words"></div>
</template>

<script setup>
import axios from "axios";
// import UserService from "../../service/UserService";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const setCookie = (cname, cvalue, exdays) => {
  const d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  const expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
};

const user = ref({
  name: "",
  email: "",
  password: "",
  verificationCode: "",
});
const errors = ref([]);

const togglePassword = () => {
  const password = document.querySelector("input[type='password']");
  if (password !== null) {
    password.type = "text";
  } else {
    document.querySelector("#ps").type = "password";
  }
};

const validateAndSubmit = (e) => {
  e.preventDefault();
  errors.value = [];
  if (!user.value.name || !user.value.email || !user.value.password) {
    errors.value.push("Enter valid values");
  } else if (user.value.password.length < 8) {
    errors.value.push("Password must be at least 8 characters long");
  } else {
    axios
        .post("/api/user/register", {
          name: user.value.name,
          email: user.value.email,
          password: user.value.password,
        })
        .then((response) => {
          if (response.data.success) {
            // setCookie('userRole', response.data.role, 1)
            // window.location.href = '/home'
            document.querySelector("#main-reg").style.display = "none";
            document.querySelector("#verify-email").style.display = "block";
          } else {
            errors.value.push(response.data.message);
            document.getElementById("errors").innerHTML =
                errors.value.join("<br>");
          }
        })
        .catch((error) => {
          errors.value.push(error.message);
          document.getElementById("errors").innerHTML = errors.value.join("<br>");
        });
  }
  document.getElementById("errors").innerHTML = errors.value.join("<br>");
};

const verifyEmail = (e) => {
  e.preventDefault();
  errors.value = [];
  if (!user.value.verificationCode) {
    errors.value.push("Enter a valid verification code");
  } else {
    axios
        .post("/api/user/verify-email", {
          email: user.value.email,
          verificationCode: user.value.verificationCode,
        })
        .then((response) => {
          if (response.data.success) {
            setCookie("userRole", response.data.role, 7);
            // window.location.href = '/home'
            router.push("/login");
          } else {
            errors.value.push(response.data.message);
            document.getElementById("errors-email").innerHTML =
                errors.value.join("<br>");
          }
        })
        .catch((error) => {
          errors.value.push(error.message);
          document.getElementById("errors-email").innerHTML =
              errors.value.join("<br>");
        });
  }
  document.getElementById("errors-email").innerHTML = errors.value.join("<br>");
};
/*
* AI-generated-content
* tool: Copilot
* version: latest
* usage: Placeholder data
* weight: whole
*/
const sentences = [
  "Welcome to GalaGuide! 🎉",
  "Enroll in campus galas! 🎈",
  "Create your own events! 🎊",
  "Make reservations! 🎁",
  "Join the fun! 🎇",
  "Meet new people! 🎆",
  "Enjoy the campus life! 🎀",
  "Explore the campus! 🎐",
  "Discover new places! 🎑",
  "Have fun! 🎄",
  "Get involved! 🎍",
];

onMounted(() => {
  const ele = document.querySelector(".words");
  setInterval(() => {
    for (let i = 0; i < 1; i++) {
      const span = document.createElement("span");
      let tmp = Math.floor(Math.random() * 10);
      span.textContent = "🍀 " + sentences[tmp];
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
  }, 1000);
});
</script>

<style scoped>
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
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
  left: -18%;
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

.words {
  border: 1px solid black;
  white-space: nowrap;
  overflow: hidden;
  display: inline-block;
}
</style>
