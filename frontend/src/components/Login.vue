<template>
  <div class="login-page">

    <div class="login-box">

      <!-- Logo -->
      <div class="login-logo">
        <strong>Employee</strong> Management
      </div>

      <!-- Login Card -->
      <div class="card card-outline card-primary">

        <!-- Card Header -->
        <div class="card-header text-center">
          <span class="h4">
            <strong>Đăng nhập</strong>
          </span>
        </div>

        <!-- Card Body -->
        <div class="card-body">

          <p class="login-box-msg">
            Đăng nhập để bắt đầu phiên làm việc
          </p>

          <form @submit.prevent="clickLogin">

            <!-- Username -->
            <div class="input-group mb-3">

              <input
                type="text"
                class="form-control"
                v-model="username"
                placeholder="Username"
              >

              <span class="input-group-text">
                <i class="bi bi-person"></i>
              </span>

            </div>


            <!-- Password -->
            <div class="input-group mb-3">

              <input
                type="password"
                class="form-control"
                v-model="password"
                placeholder="Password"
              >

              <span class="input-group-text">
                <i class="bi bi-lock"></i>
              </span>

            </div>


            <!-- Error -->
            <div
              v-if="errorMessage"
              class="alert alert-danger"
            >
              <i class="bi bi-exclamation-triangle me-2"></i>

              {{ errorMessage }}
            </div>


            <!-- Login Button -->
            <div class="row">

              <div class="col-12">

                <button
                  type="submit"
                  class="btn btn-primary w-100"
                >
                  <i class="bi bi-box-arrow-in-right me-2"></i>

                  Đăng nhập
                </button>

              </div>

            </div>

          </form>

        </div>

      </div>

    </div>

  </div>
</template>


<script>

import api from '../api'

export default {

  name: 'Login',

  data() {

    return {

      username: '',

      password: '',

      errorMessage: ''

    }

  },


  methods: {

    async clickLogin() {

      // ==========================================
      // XÓA THÔNG BÁO LỖI CŨ
      // ==========================================

      this.errorMessage = ''


      // ==========================================
      // KIỂM TRA USERNAME
      // ==========================================

      if (!this.username) {

        this.errorMessage =
          'Vui lòng nhập Username!'

        return

      }


      // ==========================================
      // KIỂM TRA PASSWORD
      // ==========================================

      if (!this.password) {

        this.errorMessage =
          'Vui lòng nhập Password!'

        return

      }


      try {

        // ==========================================
        // GỌI SPRING BOOT BACKEND
        // POST http://localhost:8080/api/login
        // ==========================================

        const data = await api.post(
          '/login',
          {
            username: this.username,
            password: this.password
          }
        )


        // ==========================================
        // LƯU JWT TOKEN
        // ==========================================

        localStorage.setItem(
          'token',
          data.token
        )


        // ==========================================
        // LƯU THÔNG TIN NHÂN VIÊN
        // ==========================================

        localStorage.setItem(
          'employee',
          JSON.stringify(data.employee)
        )


        // ==========================================
        // THÔNG BÁO CHO APP.VUE
        // LOGIN THÀNH CÔNG
        // ==========================================

        this.$emit(
          'login-success',
          data.employee
        )

      }


      catch (error) {

        // ==========================================
        // IN LỖI RA CONSOLE
        // ==========================================

        console.error(
          'Login error:',
          error
        )


        // ==========================================
        // HIỂN THỊ LỖI
        // ==========================================

        this.errorMessage =
          error.message ||
          'Không thể kết nối đến Backend!'

      }

    }

  }

}

</script>


<style scoped>

/* ==============================
   LOGIN PAGE
================================ */

.login-page {

  min-height: 100vh;

  display: flex;

  justify-content: center;

  align-items: center;

  background-color: #f4f6f9;

}


/* ==============================
   LOGIN BOX
================================ */

.login-box {

  width: 400px;

  max-width: 90%;

}


/* ==============================
   LOGO
================================ */

.login-logo {

  text-align: center;

  font-size: 32px;

  margin-bottom: 20px;

  color: #343a40;

}


/* ==============================
   CARD
================================ */

.card {

  margin-bottom: 0;

  box-shadow:
    0 0 1px rgba(0, 0, 0, .125),
    0 1px 3px rgba(0, 0, 0, .2);

}


/* ==============================
   CARD HEADER
================================ */

.card-header {

  padding: 15px;

}


/* ==============================
   LOGIN MESSAGE
================================ */

.login-box-msg {

  text-align: center;

  margin-bottom: 20px;

  color: #6c757d;

}


/* ==============================
   INPUT
================================ */

.form-control {

  height: 45px;

}


/* ==============================
   ICON
================================ */

.input-group-text {

  width: 45px;

  display: flex;

  justify-content: center;

  align-items: center;

}


/* ==============================
   BUTTON
================================ */

.btn-primary {

  height: 45px;

}

</style>