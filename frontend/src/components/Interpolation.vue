<template>
    <div>
        <h1>Interpolation and data</h1>
        <h2>{{ message }}</h2>
        <h2>{{ number + 5 }}</h2>
        <h2>{{ sayHello() }}</h2>
    </div>
</template>
<script>
export default {
    data(){
        return{
            message: 'xin chao vuejs3',
            number: 10
        }
    },
    methods:{
        sayHello(){
            return 'xin chao, day la 1 phuong thuc'
        }
    }
}
</script>