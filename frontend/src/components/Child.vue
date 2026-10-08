<template>
    <div>
        <h2>component child</h2>
        <p>Message from parent:{{ message }}</p>
    </div>
</template>
<script>
export default{
    props: {
        message: String
    }
}
</script>