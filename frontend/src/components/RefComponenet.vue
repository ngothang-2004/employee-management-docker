<template>
    <div>
        <h1>use ref with dom element</h1>
        <input type="text" ref="myInput" placeholder="Enter some data">
        <button @click="focusInput">Focus input</button>
    </div>
</template>
<script>
import {ref} from 'vue';
export default {
    setup(){
    const myInput = ref(null);

    const focusInput = () => {
        myInput.value.focus();
        // myInput.value.value="test test";
        // myInput.value.style.color="red";
        console.log(myInput.value.value);   
        }

        return {
            myInput,
            focusInput
        }
    }
}
</script>