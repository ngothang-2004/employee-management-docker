<template>
    <div>
        <h1>sample submit from</h1>
        <form>
            <label for="username">username</label>
            <input type="text" id="username" v-model="form.username">
            <br/>
            <br/>
            <label for="password">password</label>
            <input type="text" id="password" v-model="form.password">

            <button type="button" @click="submitForm">Submit</button>
        </form>
    </div>
</template>
<script>
export default {
    data() {
        return {
            form: {
                username: '',
                password: '',
            },
            errors: []
        }
    },
    methods: {
        submitForm(){
            this.errors = [];
            // console.log('data form: ', this.form);
            for (const item in this.form) {
                if (this.form[item] === '' || this.form[item].length == 0) {
                    this.errors.push(item);

                }
            }
            if (this.errors.length === 0){
                console.log("Data submitteed", this.form);
            }else{
                for (const index in this.errors) {
                    console.log(this.errors[index]+ " is required!");
                    
                    
                }
            }

        }

    }
}
</script>
<style>
input[type=text],
select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 100%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #45a049;
}

div {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
</style>