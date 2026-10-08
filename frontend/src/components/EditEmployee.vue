<template>
  <form @submit.prevent="clickSave">

    <!-- NAME -->
    <div class="mb-3">

      <label
        for="nameInput"
        class="form-label"
      >
        Name
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-person"></i>
        </span>

        <input
          id="nameInput"
          v-model="form.name"
          type="text"
          class="form-control"
          placeholder="Enter name"
        >

      </div>

    </div>


    <!-- ADDRESS -->
    <div class="mb-3">

      <label
        for="addressInput"
        class="form-label"
      >
        Address
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-geo-alt"></i>
        </span>

        <input
          id="addressInput"
          v-model="form.address"
          type="text"
          class="form-control"
          placeholder="Enter address"
        >

      </div>

    </div>


    <!-- PHONE -->
    <div class="mb-3">

      <label
        for="phoneInput"
        class="form-label"
      >
        Phone Number
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-telephone"></i>
        </span>

        <input
          id="phoneInput"
          v-model="form.phone"
          type="text"
          class="form-control"
          placeholder="Enter phone number"
        >

      </div>

    </div>


    <!-- EMAIL -->
    <div class="mb-3">

      <label
        for="emailInput"
        class="form-label"
      >
        Email
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-envelope"></i>
        </span>

        <input
          id="emailInput"
          v-model="form.email"
          type="email"
          class="form-control"
          placeholder="Enter email"
        >

      </div>

    </div>


    <!-- UNIT -->
    <div class="mb-3">

      <label
        for="unitInput"
        class="form-label"
      >
        Unit
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-building"></i>
        </span>

        <select
          id="unitInput"
          v-model="form.unitId"
          class="form-select"
        >

          <option :value="null">
            -- Select unit --
          </option>

          <option
            v-for="unit in units"
            :key="unit.id"
            :value="unit.id"
          >
            {{ unit.name }} ({{ unit.code }})
          </option>

        </select>

      </div>

    </div>


    <!-- USERNAME -->
    <div class="mb-3">

      <label
        for="usernameInput"
        class="form-label"
      >
        Username
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-person-badge"></i>
        </span>

        <input
          id="usernameInput"
          v-model="form.username"
          type="text"
          class="form-control"
          placeholder="Enter username"
        >

      </div>

    </div>


    <!-- PASSWORD -->
    <div class="mb-3">

      <label
        for="passwordInput"
        class="form-label"
      >
        Password
      </label>

      <div class="input-group">

        <span class="input-group-text">
          <i class="bi bi-lock"></i>
        </span>

        <input
          id="passwordInput"
          v-model="form.password"
          type="password"
          class="form-control"
          placeholder="Enter password"
        >

      </div>

    </div>


    <!-- BUTTONS -->
    <div class="d-flex justify-content-end gap-2 mt-4">

      <button
        type="button"
        class="btn btn-secondary"
        @click="clickCancel"
      >

        <i class="bi bi-x-lg me-1"></i>

        Cancel

      </button>


      <button
        type="submit"
        class="btn btn-primary"
      >

        <i class="bi bi-check-lg me-1"></i>

        Save

      </button>

    </div>

  </form>
</template>


<script>
export default {

  name: 'EditEmployee',


  props: {

    employee: {

      type: Object,

      default: null

    },

    units: {

      type: Array,

      default: () => []

    }

  },


  data() {

    return {

      form: {

        id: null,

        name: '',

        address: '',

        phone: '',

        email: '',

        username: '',

        password: '',

        role: 'user',

        unitId: null

      }

    }

  },


  watch: {

    employee: {

      immediate: true,

      handler(value) {

        if (value) {

          this.form = {

            id:
              value.id ?? null,

            name:
              value.name ?? '',

            address:
              value.address ?? '',

            phone:
              value.phone ?? '',

            email:
              value.email ?? '',

            username:
              value.username ?? '',

            password:
              value.password ?? '',

            role:
              value.role ?? 'user',

            unitId:
              value.unitId ?? null

          }

        }

      }

    }

  },


  methods: {

    clickSave() {

      if (!this.form.name.trim()) {

        alert(
          'Vui lòng nhập Name!'
        )

        return

      }


      if (!this.form.address.trim()) {

        alert(
          'Vui lòng nhập Address!'
        )

        return

      }


      if (!this.form.phone.trim()) {

        alert(
          'Vui lòng nhập Phone Number!'
        )

        return

      }


      if (!this.form.username.trim()) {

        alert(
          'Vui lòng nhập Username!'
        )

        return

      }


      if (!this.form.password.trim()) {

        alert(
          'Vui lòng nhập Password!'
        )

        return

      }


      this.$emit(
        'save',
        {
          ...this.form
        }
      )

    },


    clickCancel() {

      this.$emit('cancel')

    }

  }

}
</script>


<style scoped>

.form-label {
  font-weight: 600;
}


.form-control,
.form-select {
  min-height: 42px;
}


.input-group-text {

  width: 45px;

  justify-content: center;

}


.btn {
  min-width: 100px;
}

</style>