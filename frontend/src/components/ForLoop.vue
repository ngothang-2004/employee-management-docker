
<template>
    <div>
        <h2>use for loop and list</h2>

        <p v-for="(item, index) in course" :key="index">
            <button @click="showCourse(item)">{{ item }}</button>
        </p>
    </div>
</template>

<script>
export default {
    data() {
        return {
            course: ['PHP', 'Java', 'Nodejs']
        }
    },
    methods: {
        showCourse(item) {
            alert(item);
        }
    }
}
</script>
