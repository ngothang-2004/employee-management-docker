<template>
    <div>
        <h1 v-html="htmlContent"></h1>
        <h2 v-html="dynamicHtmlContent"></h2>
        <button @click="loadHtml">Load Html</button>
    </div>
</template>
<script>
export default {
    data() {
        return {
            htmlContent: '<strong>Hello vuejs 3</strong>',
            dynamicHtmlContent: '',
        }
    },
    methods: {
        loadHtml() {
            this.dynamicHtmlContent = '<strong>dynamic html content</strong>'
        } 
    }
}
</script>