<template>
    <div>
        <h2>{{ message }}</h2>
        <button @click="showMessage">Click to show message</button>
        <button @click="showCourse('PHP')">Click to show course</button>
    </div>
</template>
<script>
export default{
    data(){
        return{
            message: 'hello vue js 3'
        }
    },
    methods: {
        showMessage(){
            alert(this.message);
        },
        showCourse(course){
            alert(course);
        }
    }
}</script>