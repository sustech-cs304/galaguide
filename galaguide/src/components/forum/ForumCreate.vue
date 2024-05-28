<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();
const discussId = ref(null);

onMounted(() => {
    console.log("Forum Create Page");
    const token = localStorage.getItem("token");
    if (token) {
        axios.defaults.headers.common["Bearer"] = token;
    }
    else {
        router.push("/login");
    }
    try {
        discussId.value = route.params.id;
        // If such a parameter exists, then we are editing a post
        // Get the post data and fill the form
        if (discussId.value) {
            axios.get(`/api/discuss/${discussId.value}`, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                },
            }).then((res) => {
                console.log(res.data);
                document.querySelector("#post-form input").value = res.data.data[0].title;
                document.querySelector("#post-form textarea").value = res.data.data[0].content;
            }).catch((err) => {
                console.log(err);
            });
        }
    } catch (err) {
        console.log(err);
        // Otherwise, we are creating a new post
    }
});

const createPost = () => {
    console.log("Creating a post");
    const title = document.querySelector("#post-form input").value;
    const content = document.querySelector("#post-form textarea").value;
    if (!title || !content) {
        alert("Please fill in all fields");
        return;
    }
    if (discussId.value) {
        axios.put(`/api/discuss/${discussId.value}`, {
            title: title,
            content: content,
        }, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
        }).then((res) => {
            console.log(res.data);
            router.push("/forum");
        }).catch((err) => {
            console.log(err);
        });
        return;
    }
    axios.post("/api/discuss/create-discuss", {
        title: title,
        content: content,
    }, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
    }).then((res) => {
        console.log(res.data);
        router.push("/forum");
    }).catch((err) => {
        console.log(err);
    });
};
</script>

<template>
  <div id="forum-create">
    <h1>Create a Post</h1>
    <div id="new-post-holder">
        <div id="post-form">
            <input type="text" placeholder="Title" />
            <textarea placeholder="Content"></textarea>
            <button @click="createPost">
                Post
            </button>
        </div>
    </div>
  </div>
</template>

<style scoped>
#forum-create {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
    position: absolute;
    top: 10%;
    left: 55%;
    transform: translate(-50%, 0);
    width: 90%;
}

#new-post-holder {
    background-color: #f0f0f0;
    padding: 20px;
    border-radius: 10px;
    width: 80%;
}

#post-form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

#post-form input {
    width: 50%;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

#post-form textarea {
    width: 50%;
    height: 200px;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

#post-form button {
    padding: 10px;
    width: 50%;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

#post-form button:hover {
    background-color: #0056b3;
}



</style>
