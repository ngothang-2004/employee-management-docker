<template>
    <div>
        <h1>sample use watcher</h1>
        <p>Current Message: {{ message }}</p>
        <input type="text" v-model="message" placeholder="Type something">
    </div>
</template>
<script>
import {
    ref,
    watch
}
from'vue';
export default {
    setup() {
        const message = ref('');

        watch(message, (newValue,oldValue)=>{
            console.log('Message changed from ${oldValue} to ${newValue}');
        })
        return{
            message,
        }
    }
}</script>