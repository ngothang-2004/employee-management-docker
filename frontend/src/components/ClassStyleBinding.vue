<template>
    <div>
        <h1 :class="{active : isActive}">Hello vuejs 3</h1>
        <h2 :style="styleObject">Hello,i am peter</h2>
    </div>
</template>
<script>
export default {
    data() {
        return {
            isActive: false,
            styleObject: {
                color: 'yellow',
                fontSize: '18px',
            }
        }
    }
}
</script>
<style>
.active{
    color: red;
}
</style>