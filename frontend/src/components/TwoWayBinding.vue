<template>
    <div>
        <h2>User Name: {{ userName }}</h2>
        <h2>Course: {{ course }}</h2>
        <h2>time: {{ time }}</h2>
        <h2>gender: {{ gender }}</h2>
        <input type="text" placeholder="Enter username" v-model="userName">
        <br/><br/>

        <!-- use select -->
        <select v-model="course">
            <option>PHP</option>
            <option>Java</option>
        </select>
        
        <br/><br/>
        <!-- user checkbox  -->
         <label for="Monday">Moday </label>
         <input type="checkbox" value="Monday" id="Monday" v-model="time">
         <label for="Friday">Friday </label>
         <input type="checkbox" value="Friday" id="Monday" v-model="time">

         <br/><br/>
        <!-- user radio  -->
         <label for="Male">Male </label>
         <input type="checkbox" value="Male" id="Male" v-model="gender">
         <label for="FeMale">FeMale </label>
         <input type="checkbox" value="FeMale" id="FeMale" v-model="gender">
    </div>
</template>
<script>
export default{
    data(){
        return{
            userName: '',
            course: '',
            time: [],
            gender:[],
        }
    }
}</script>