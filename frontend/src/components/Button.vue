<template>
    <div class="buttonCutom">
        <p>{{ message }}</p>

    </div>
</template>
<script>
export default {
    props: {
        message: String
    }
}
</script>
<style scoped>
.buttonCutom {
    padding: 10px;
    border: 1px solid #ccc;
    margin-bottom: 10px;
    background-color: #f9f9f9;
    width: 100px;
    text-align: center;

}
</style>