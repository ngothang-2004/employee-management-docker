<template>
    <div>
        <input type="text" placeholder="Enter some text" v-model="childData">
        <button @click="sendDatatoParent"> Send data to Parent</button>
    </div>
</template>
<script>
export default {
    data() {
        return {
            childData:''
        }
    },
    methods: {
        sendDatatoParent() {
            this.$emit('updateDataParent', this.childData)
        }
    }
}</script>