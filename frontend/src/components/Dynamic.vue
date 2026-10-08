<template>
    <div>
        <h1>use dynamic component</h1>
        <button @click="currentComponent = 'ComponentA'">Show Component A</button>
        <button @click="currentComponent = 'ComponentB'">Show Component B</button>

        <component :is="currentComponent"></component>
    </div>
</template>
<script>
import ComponentA from './ComponentA.vue';
import ComponentB from './ComponentB.vue';
export default{
    components: {
        ComponentA,
        ComponentB
    },
    data() {
        return {
            currentComponent: 'ComponentA'
        }
    }

}
</script>