<template>
    <div>
        <h2>sample condition if else</h2>
        <button @click="checkLogin">{{ isLogin ?'Logout':'Login' }}</button>
        <p v-if="isLogin">Welcome to user!</p>
        <p v-else>Please login!</p>
    </div>
</template>
<script>
export default{
    data(){
        return{
            isLogin: false
        }
    },
    methods:{
        checkLogin(){
            this.isLogin = !this.isLogin;
        }
    }
}
</script>