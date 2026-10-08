<template>
    <div>
        <h1>sample use computed</h1>
        <p>Original Message: {{ originalMessage }}</p>
        <p>Reversed Message: {{ reversedMessage }}</p>
        <input type="text" v-model="originalMessage" placeholder="Type something">

        <p>Counter: {{counter}}</p>
        <button @click="incrementCounter">increment</button>
    </div>
</template>
<script>
import{ ref,computed} from 'vue';
export default {
    setup(){
        const originalMessage = ref('');
        const counter = ref(0);

        const reversedMessage = computed(() => {
            console.log('Message duoc cap nhat');
            return originalMessage.value.split('').reverse().join('');
        });

        const incrementCounter = () =>{
            counter.value++;
        }
        return{
            originalMessage,
            reversedMessage,
            counter,
            incrementCounter,
        }
    }

}
</script>