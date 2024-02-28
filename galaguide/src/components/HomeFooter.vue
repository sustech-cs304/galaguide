<script setup>
import { ref, onMounted } from 'vue'

const maxHeight = ref(0)

onMounted(() => {
    setTimeout(() => {
        maxHeight.value = document.getElementById('home-er-holder').clientHeight +
            document.getElementById('home-announcements').clientHeight +
            document.getElementById('top-bar').clientHeight + 100
        // console.log(ele.clientHeight,ele.style.border,ele.clientTop)
        console.log('maxHeight:', maxHeight.value)
        document.getElementById('footer').style.top = `calc( 4% + ${maxHeight.value}px )`
    }, 500)
})

</script>

<template>
    <div class="footer" id="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>About Us</h3>
                <p>Learn more about our mission and values.</p>
            </div>
            <div class="footer-section">
                <h3>Product</h3>
                <ul>
                    <li>Features</li>
                    <li>Pricing</li>
                    <li>FAQs</li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Contact Us</h3>
                <p>Get in touch with our team.</p>
            </div>
        </div>
        <div class="footer-bottom">
            <img src="../assets/logo.png" alt="Company Logo" class="footer-logo">
            <p>&copy; 2024 GalaGuide. All rights reserved.</p>
        </div>
    </div>
</template>

<style scoped>
.footer {
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
