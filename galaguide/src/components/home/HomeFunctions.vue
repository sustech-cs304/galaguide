<script setup>

import { defineProps, ref } from 'vue'

const props = defineProps({
    role: Number
})

const createEvent = () => {
    console.log('Create an event');
};

const makeReservation = () => {
    console.log('Make a reservation');
};

const candidates = [
    '🍔 Cheeseburger',
    '🍕 Pizza',
    '🍣 Sushi',
    '🍜 Ramen',
    '🍝 Spaghetti',
    '🍲 Hotpot',
    '🍛 Curry Rice',
    '🍙 Onigiri',
    '🍚 Rice',
    '🍞 Toast',
    '🥞 Pancake',
    '🥪 Sandwich',
    '🥗 Salad',
    '🍦 Ice Cream',
    '🍧 Shaved Ice',
    '🍨 Sundae',
    '🍩 Donut',
    '🍪 Cookie',
    '🎂 Cake',
    '🍰 Cheesecake',
    '🧁 Cupcake',
    '🥧 Pie',
    '🍫 Chocolate',
    '🍬 Candy',
    '🍭 Lollipop',
    '🍮 Pudding',
    '🍯 Honey',
    '🍼 Milk',
    '🥛 Milkshake',
    '☕ Coffee',
    '🍵 Tea',
    '🍶 Sake',
    '🍺 Beer',
    '🍸 Cocktail',
    '🍹 Tropical Drink',
    '🍾 Champagne',
    '🍷 Wine',
    '🍔 Big Mac',
    '🍟 French Fries',
    '🍕 Pepperoni Pizza',
    '🌭 Hot Dog',
    '🥪 Club Sandwich',
    '🌮 Taco',
    '🌯 Burrito',
    '🥙 Falafel',
    '🍳 Fried Egg',
    '🥞 Pancakes',
    '🥓 Bacon',
    '🥩 T-Bone Steak',
    '🍗 Chicken Drumstick',
    '🍖 Meat on Bone',
    '🌽 Ear of Corn',
    '🥦 Broccoli',
    '🍅 Tomato',
    '🥒 Cucumber',
    '🥬 Leafy Green',
    '🥕 Carrot',
    '🌶️ Hot Pepper',
    '🥔 Potato',
    '🍠 Sweet Potato',
    '🥐 Croissant',
    '🥖 Baguette',
    '🥨 Pretzel',
    '🥯 Bagel',
    '🥚 Omelette',
    '🧀 Cheese Wedge',
    '🍝 Macaroni',
    '🍜 Hunan Noodles',
    '🍲 Borshch',
    '🍛 Pilaf',
    '🍞 Bread',
    '🥞 Quiche',
    '🍲 Boeuf Bourguignon',
    '🍛 Paella',
    '🍜 Pho',
    '🍝 Lasagna',
    '🍕 Margherita Pizza',
    '🦞 Lobster Thermidor',
    '🍰 Gruyere Souffle',
    '🍮 Creme Brulee',
    '🍧 Tiramisu',
    '🍨 Panna Cotta',
    '🍦 Affogato',
    '🍩 Beignet',
    '🍪 Macaron',
    '🍫 Truffle',
    '🍬 Marshmallow',
    '🍭 Rock Candy',
    '🍯 Baklava',
    '🍼 Bubble Tea',
    '☕ Matcha Latte',
    '🍵 Chai',
    '🍶 Horchata',
    '🍺 IPA',
    '🍸 Martini',
    '🍹 Mojito',
    '🍾 Prosecco',
    '🍷 Merlot',
    '🍔 Whopper',
    '🍟 Curly Fries',
    '🍕 Hawaiian Pizza',
    '🌭 Corn Dog',
    '🥪 BLT Sandwich',
    '🌮 Quesadilla',
    '🌯 Gyro',
    '🥙 Shawarma',
    '🍳 Scrambled Eggs',
    '🥞 Waffles',
    '🥓 Sausage',
    '🥩 Filet Mignon',
    '🍗 Chicken Wing',
    '🍖 Ribs',
    '🌽 Popcorn',
    '🥦 Cauliflower',
    '🍅 Eggplant',
    '🥒 Pickles',
    '🥬 Cabbage',
    '🥕 Radish',
    '🌶️ Bell Pepper',
    '🥔 Sweet Potato',
    '🍠 Yam',
    '🥐 Danish',
    '🥖 Sourdough',
    '🥨 Brioche',
    '🥯 English Muffin',
    '🥚 Frittata',
    '🧀 Fondue',
    '🍝 Ravioli',
    '🍜 Udon',
    '🍛 Korma',
    '🥞 Crepe',
    '🍲 Goulash',
    '🥗 Tuna Nicoise',
    '🥘 Spanakopita Egg Muffins',
    '🍞 Garlic Bread',
    '🍜 Pad Thai'
];

const chosenFood = ref('');
const chosenPremium = ref('');
const chooseCount = ref(0);

const whatToEat = () => {
    chosenFood.value = '';
    const whatToEat = document.getElementById('lottery-what-to-eat');
    whatToEat.style.display = 'block';
};

const closeWhatToEat = () => {
    const whatToEat = document.getElementById('lottery-what-to-eat');
    whatToEat.style.display = 'none';
};

const startChoose = () => {
    document.getElementById('start-choose').disabled = true;
    let index1 = 0;
    let index2 = 0;
    let index3 = 0;
    let delay = 10;

    const chooseFood = () => {
        index1 = Math.floor(Math.random() * candidates.length);
        index2 = Math.floor(Math.random() * candidates.length);
        index3 = Math.floor(Math.random() * candidates.length);
        chosenFood.value = candidates[index1] + ' ' + candidates[index2] + ' ' + candidates[index3];
        
        delay += 8; 
        
        if (delay <= 100) {
            setTimeout(chooseFood, delay);
        } else {
            chooseCount.value++;
            document.getElementById('start-choose').disabled = false;
            if (chooseCount.value >= 10) {
                chooseCount.value = 0;
                chosenFood.value = '😡 You are so picky! You got nothing!';
                const popUpSurprise = document.getElementById('pop-up-surprise')
                popUpSurprise.style.display = 'block';
                popUpSurprise.innerHTML = '😡 You are so picky! You got nothing!';
                popUpSurprise.style.fontSize = '50px';
                popUpSurprise.style.color = 'red';
                setTimeout(() => {
                    popUpSurprise.style.display = 'none';
                }, 3000);
            }
            if (candidates[index1] === '🥗 Tuna Nicoise' || candidates[index2] === '🥗 Tuna Nicoise' || candidates[index3] === '🥗 Tuna Nicoise') {
                const popUpSurprise = document.getElementById('pop-up-surprise');
                popUpImgs.value = "https://www.themediterraneandish.com/wp-content/uploads/2023/06/TMD-Nicoise-WEB-19.jpg";
                popUpSurprise.style.display = 'block';
                chosenPremium.value = '🥗 Tuna Nicoise';
                setTimeout(() => {
                    popUpSurprise.style.display = 'none';
                }, 3000);
            }
            else if (candidates[index1] === '🥘 Spanakopita Egg Muffins' || candidates[index2] === '🥘 Spanakopita Egg Muffins' || candidates[index3] === '🥘 Spanakopita Egg Muffins') {
                const popUpSurprise = document.getElementById('pop-up-surprise');
                popUpImgs.value = "https://www.themediterraneandish.com/wp-content/uploads/2023/12/Spanakopita-Egg-Muffins-Leads-03-Vertical.jpg";
                popUpSurprise.style.display = 'block';
                chosenPremium.value = '🥘 Spanakopita Egg Muffins';
                setTimeout(() => {
                    popUpSurprise.style.display = 'none';
                }, 3000);
            }
        }
    };

    chooseFood();
};

const popUpImgs = ref();

</script>

<template>
    <div class="home-functions" v-if="props.role > 0">
        <h1 style="text-align: center; margin-bottom: 20px;">Quick Functions</h1>
        <input class="search" type="text" placeholder="Search in GalaGuide..." />
        <button class="create-event" @click="createEvent">
            <a href="/create-event" style="color: aliceblue;">Create an event</a>
        </button>
        <button class="make-res" @click="makeReservation">
            <a href="/make-res" style="color: aliceblue;">Make a reservation</a>
        </button>
        <button class="what-to-eat" @click="whatToEat">
            What to eat
        </button>
    </div>
    <div class="home-functions" v-if="props.role === 0">
        <p style="text-align: center; margin-top: 20px;position: absolute;top:50%;">Please log in to use quick functions</p>
    </div>
    <div id="lottery-what-to-eat" style="display: none;">
        <span class="close" @click="closeWhatToEat">&times; </span>
        <h1 style="text-align: center; margin-bottom: 20px;">What to eat</h1>
        <p style="text-align: center; margin-bottom: 20px;">Let's decide what to eat today!</p>
        <button class="make-res" @click="startChoose" id="start-choose">
            Decide for me
        </button>
        <h3 style="text-align: center; margin-top: 20px;" >
            {{ chosenFood }}
        </h3>
    </div>
    <div id="pop-up-surprise" style="display: none;">
        <h1 style="text-align: center; margin-bottom: 20px;">Surprise!</h1>
        <p style="text-align: center; margin-bottom: 20px;">You got a {{ chosenPremium }}</p>
        <img :src="popUpImgs" alt="Surprise!" style="width: 50%; height: 50%;"/>
    </div>
</template>

<style scoped>
.home-functions {
    position: absolute;
    top: 7%;
    right: 2%;
    width: 35%;
    height: 270px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    /* justify-content: center; */
    align-items: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.search {
    width: 80%;
    height: 30px;
    margin-bottom: 20px;
    border-radius: 5px;
    border: 1px solid #ccc;
    padding: 0 10px;
}

.create-event, .make-res {
    width: 80%;
    height: 30px;
    margin-bottom: 10px;
    border-radius: 5px;
    border: none;
    color: white;
    cursor: pointer;
}

.create-event {
    background-color: #23b375;
}

.create-event:hover {
    background-color: #1f9e64;
}

.make-res {
    background-color: #2a78c7;
}

.make-res:hover {
    background-color: #2467c4;
}

.what-to-eat {
    width: 80%;
    height: 30px;
    margin-bottom: 10px;
    border-radius: 5px;
    border: none;
    color: white;
    cursor: pointer;
    background-color: #f7b500;
}

.what-to-eat:hover {
    background-color: #f5a800;
}

#lottery-what-to-eat {
    position: absolute;
    top: 7%;
    right: 2%;
    width: 35%;
    height: 270px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    /* justify-content: center; */
    align-items: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#pop-up-surprise {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    transition-duration: 0.3s;
    animation: pop-up 1s;
    z-index: 100;
}

@keyframes pop-up {
    0% {
        transform: scale(0) translate(-50%, -50%);
    }
    100% {
        transform: scale(1) translate(-50%, -50%);
    }
}
</style>
  