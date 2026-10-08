<template>
    <div>
        <h1>Data from component child: {{ parentData }}</h1>
        <ChildComponent @updateDataParent="handleUpdataData"></ChildComponent>
    </div>
</template>
<script>
import ChildComponent from './ChildComponent.vue';

export default {
    data() {
        return{
            parentData: ''
        }
    },
    components: {
        ChildComponent
    },
    methods: {
        handleUpdataData(newData) {
            this.parentData = newData;
            console.log(newData);
        }
    }
}
</script>