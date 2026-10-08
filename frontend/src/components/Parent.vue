<template>
    <div>
        <Child :message="message"></Child>
    </div>
</template>
<script>
import Child from './Child.vue';
export default {
    components: {
        Child
    },
    data() {
        return {
            message: ' Hello from component parent'
        }
    }
}
</script>