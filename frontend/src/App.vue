<template>
  <!-- ========================================== -->
  <!-- LOGIN -->
  <!-- ========================================== -->
  <Login
    v-if="!isLoggedIn"
    @login-success="handleLoginSuccess"
  />

  <!-- ========================================== -->
  <!-- MAIN APP -->
  <!-- ========================================== -->
  <div
    v-else
    class="wrapper"
    :class="{ 'sidebar-collapsed': sidebarCollapsed }"
  >

    <!-- ========================================== -->
    <!-- SIDEBAR -->
    <!-- ========================================== -->
    <aside class="main-sidebar">

      <div class="brand-link">

        <i class="bi bi-people-fill me-2"></i>

        <span v-if="!sidebarCollapsed">
          Employee Management
        </span>

      </div>

      <div class="sidebar">

        <nav class="mt-3">

          <ul class="nav flex-column">

            <!-- DASHBOARD -->
            <li class="nav-item">

              <a
                href="#"
                class="nav-link"
                :class="{
                  active: currentPageView === 'dashboard'
                }"
                @click.prevent="
                  currentPageView = 'dashboard'
                "
              >

                <i class="bi bi-speedometer2"></i>

                <span v-if="!sidebarCollapsed">
                  Dashboard
                </span>

              </a>

            </li>

            <!-- EMPLOYEE MANAGEMENT -->
            <li class="nav-item">

              <a
                href="#"
                class="nav-link"
                :class="{
                  active: currentPageView === 'employees'
                }"
                @click.prevent="
                  currentPageView = 'employees'
                "
              >

                <i class="bi bi-people"></i>

                <span v-if="!sidebarCollapsed">
                  Employee Management
                </span>

              </a>

            </li>

            <!-- UNIT MANAGEMENT -->
            <li class="nav-item">

              <a
                href="#"
                class="nav-link"
                :class="{
                  active: currentPageView === 'units'
                }"
                @click.prevent="
                  currentPageView = 'units'
                "
              >

                <i class="bi bi-building"></i>

                <span v-if="!sidebarCollapsed">
                  Unit Management
                </span>

              </a>

            </li>

          </ul>

        </nav>

      </div>

    </aside>

    <!-- ========================================== -->
    <!-- MAIN CONTENT -->
    <!-- ========================================== -->
    <div class="main-content">

      <!-- ========================================== -->
      <!-- NAVBAR -->
      <!-- ========================================== -->
      <nav class="main-navbar">

        <div class="navbar-left">

          <button
            type="button"
            class="btn btn-light"
            @click="
              sidebarCollapsed =
                !sidebarCollapsed
            "
          >

            <i class="bi bi-list"></i>

          </button>

          <span class="page-title">
            {{ pageTitle }}
          </span>

        </div>

        <div class="navbar-right">

          <div class="current-user">

            <i class="bi bi-person-circle"></i>

            <span>
              {{ currentEmployee?.username }}
            </span>

            <span
              class="badge ms-2"
              :class="
                currentEmployee?.role === 'admin'
                  ? 'bg-danger'
                  : 'bg-secondary'
              "
            >

              {{ currentEmployee?.role }}

            </span>

          </div>

          <button
            type="button"
            class="btn btn-outline-danger btn-sm ms-3"
            @click="logout"
          >

            <i
              class="bi bi-box-arrow-right me-1"
            ></i>

            Logout

          </button>

        </div>

      </nav>

      <!-- ========================================== -->
      <!-- CONTENT -->
      <!-- ========================================== -->
      <main class="content-wrapper">

        <!-- ========================================== -->
        <!-- DASHBOARD -->
        <!-- ========================================== -->
        <div
          v-if="
            currentPageView === 'dashboard'
          "
          class="container-fluid"
        >

          <div class="mb-4">

            <h3 class="mb-1">
              Dashboard
            </h3>

            <p class="text-muted mb-0">
              Tổng quan hệ thống quản lý nhân viên
            </p>

          </div>

          <div class="row g-4">

            <!-- TOTAL EMPLOYEES -->
            <div class="col-md-4">

              <div
                class="card dashboard-card shadow-sm"
              >

                <div class="card-body">

                  <div
                    class="dashboard-icon bg-primary"
                  >

                    <i
                      class="bi bi-people-fill"
                    ></i>

                  </div>

                  <div>

                    <h6 class="text-muted">
                      Tổng nhân viên
                    </h6>

                    <h2 class="mb-0">
                      {{ totalElements }}
                    </h2>

                  </div>

                </div>

              </div>

            </div>

            <!-- ADMIN -->
            <div class="col-md-4">

              <div
                class="card dashboard-card shadow-sm"
              >

                <div class="card-body">

                  <div
                    class="dashboard-icon bg-danger"
                  >

                    <i
                      class="bi bi-shield-lock-fill"
                    ></i>

                  </div>

                  <div>

                    <h6 class="text-muted">
                      Admin
                    </h6>

                    <h2 class="mb-0">
                      {{ adminCount }}
                    </h2>

                  </div>

                </div>

              </div>

            </div>

            <!-- UNITS -->
            <div class="col-md-4">

              <div
                class="card dashboard-card shadow-sm"
              >

                <div class="card-body">

                  <div
                    class="dashboard-icon bg-success"
                  >

                    <i
                      class="bi bi-building"
                    ></i>

                  </div>

                  <div>

                    <h6 class="text-muted">
                      Đơn vị
                    </h6>

                    <h2 class="mb-0">
                      {{ units.length }}
                    </h2>

                  </div>

                </div>

              </div>

            </div>

          </div>

        </div>

        <!-- ========================================== -->
        <!-- EMPLOYEE MANAGEMENT -->
        <!-- ========================================== -->
        <div
          v-else-if="
            currentPageView === 'employees'
          "
          class="container-fluid"
        >

          <!-- PAGE HEADER -->
          <div
            class="
              d-flex
              justify-content-between
              align-items-center
              mb-4
            "
          >

            <div>

              <h3 class="mb-1">
                Employee Management
              </h3>

              <p class="text-muted mb-0">
                Quản lý thông tin nhân viên
              </p>

            </div>

            <!-- ADD EMPLOYEE - ADMIN ONLY -->
            <button
              v-if="isAdmin"
              type="button"
              class="btn btn-primary"
              @click="clickAdd"
            >

              <i
                class="bi bi-plus-lg me-1"
              ></i>

              Add Employee

            </button>

          </div>

          <!-- EMPLOYEE CARD -->
          <div
            class="
              card
              shadow-sm
              employee-card
            "
          >

            <!-- CARD HEADER -->
            <div class="card-header">

              <div
                class="
                  row
                  g-3
                  align-items-center
                "
              >

                <!-- SEARCH -->
                <div class="col-md-6">

                  <div class="input-group">

                    <span
                      class="input-group-text"
                    >

                      <i
                        class="bi bi-search"
                      ></i>

                    </span>

                    <input
                      v-model="searchKeyword"
                      type="text"
                      class="form-control"
                      placeholder="Search employee..."
                      @input="handleSearch"
                    />

                  </div>

                </div>

                <!-- ROLE FILTER -->
                <div class="col-md-3">

                  <select
                    v-model="roleFilter"
                    class="form-select"
                    @change="handleRoleFilter"
                  >

                    <option value="">
                      All roles
                    </option>

                    <option value="admin">
                      Admin
                    </option>

                    <option value="user">
                      User
                    </option>

                  </select>

                </div>

                <!-- TOTAL -->
                <div
                  class="
                    col-md-3
                    text-md-end
                  "
                >

                  <span class="text-muted">

                    Total:

                    <strong>
                      {{ totalElements }}
                    </strong>

                  </span>

                </div>

              </div>

            </div>

            <!-- ====================================== -->
            <!-- TABLE -->
            <!-- ====================================== -->
            <div class="card-body p-0">

              <div
                class="employee-table-wrapper"
              >

                <table
                  class="
                    table
                    table-hover
                    employee-table
                  "
                >

                  <thead
                    class="table-light"
                  >

                    <tr>

                      <th
                        class="text-center"
                        style="width: 70px"
                      >
                        #
                      </th>

                      <th>
                        Name
                      </th>

                      <th>
                        Address
                      </th>

                      <th>
                        Phone
                      </th>

                      <th>
                        Email
                      </th>

                      <th>
                        Username
                      </th>

                      <th>
                        Unit
                      </th>

                      <th
                        class="text-center"
                        style="width: 110px"
                      >
                        Role
                      </th>

                      <!-- ACTION - ADMIN ONLY -->
                      <th
                        v-if="isAdmin"
                        class="text-center"
                        style="width: 180px"
                      >
                        Action
                      </th>

                    </tr>

                  </thead>

                  <tbody>

                    <!-- LOADING -->
                    <tr
                      v-if="loadingEmployees"
                    >

                      <td
                        :colspan="
                          isAdmin ? 9 : 8
                        "
                        class="
                          text-center
                          py-5
                          text-muted
                        "
                      >

                        <div
                          class="spinner-border text-primary mb-2"
                          role="status"
                        ></div>

                        <div>
                          Đang tải dữ liệu...
                        </div>

                      </td>

                    </tr>

                    <!-- NO DATA -->
                    <tr
                      v-else-if="
                        employees.length === 0
                      "
                    >

                      <td
                        :colspan="
                          isAdmin ? 9 : 8
                        "
                        class="
                          text-center
                          py-5
                          text-muted
                        "
                      >

                        <i
                          class="
                            bi
                            bi-inbox
                            fs-2
                            d-block
                            mb-2
                          "
                        ></i>

                        Không tìm thấy nhân viên

                      </td>

                    </tr>

                    <!-- EMPLOYEE DATA -->
                    <tr
                      v-else
                      v-for="
                        (item, index)
                        in employees
                      "
                      :key="item.id"
                    >

                      <!-- # -->
                      <td
                        class="text-center"
                      >

                        {{ startItem + index }}

                      </td>

                      <!-- NAME -->
                      <td>

                        <strong>
                          {{ item.name }}
                        </strong>

                      </td>

                      <!-- ADDRESS -->
                      <td>

                        {{ item.address }}

                      </td>

                      <!-- PHONE -->
                      <td>

                        {{ item.phone }}

                      </td>

                      <!-- EMAIL -->
                      <td>

                        {{ item.email || '-' }}

                      </td>

                      <!-- USERNAME -->
                      <td>

                        <i
                          class="
                            bi
                            bi-person
                            me-1
                          "
                        ></i>

                        {{ item.username }}

                      </td>

                      <!-- UNIT -->
                      <td>

                        <span
                          v-if="
                            getUnitName(
                              item.unitId
                            )
                          "
                        >

                          {{
                            getUnitName(
                              item.unitId
                            )
                          }}

                        </span>

                        <span
                          v-else
                          class="text-muted"
                        >

                          -

                        </span>

                      </td>

                      <!-- ROLE -->
                      <td
                        class="text-center"
                      >

                        <span
                          class="badge"
                          :class="
                            item.role === 'admin'
                              ? 'bg-danger'
                              : 'bg-secondary'
                          "
                        >

                          <i
                            class="bi"
                            :class="
                              item.role === 'admin'
                                ? 'bi-shield-lock'
                                : 'bi-person'
                            "
                          ></i>

                          {{ item.role }}

                        </span>

                      </td>

                      <!-- ACTION - ADMIN ONLY -->
                      <td
                        v-if="isAdmin"
                        class="text-center"
                      >

                        <!-- EDIT -->
                        <button
                          type="button"
                          class="
                            btn
                            btn-sm
                            btn-warning
                            me-1
                          "
                          @click="
                            clickEdit(item)
                          "
                        >

                          <i
                            class="
                              bi
                              bi-pencil
                            "
                          ></i>

                          Edit

                        </button>

                        <!-- DELETE -->
                        <button
                          type="button"
                          class="
                            btn
                            btn-sm
                            btn-danger
                          "
                          @click="
                            clickDelete(item)
                          "
                        >

                          <i
                            class="
                              bi
                              bi-trash
                            "
                          ></i>

                          Delete

                        </button>

                      </td>

                    </tr>

                  </tbody>

                </table>

              </div>

            </div>

            <!-- ====================================== -->
            <!-- PAGINATION -->
            <!-- ====================================== -->
            <div class="card-footer">

              <div
                class="
                  d-flex
                  justify-content-between
                  align-items-center
                "
              >

                <div class="text-muted">

                  Showing

                  <strong>
                    {{ startItem }}
                  </strong>

                  -

                  <strong>
                    {{ endItem }}
                  </strong>

                  of

                  <strong>
                    {{ totalElements }}
                  </strong>

                </div>

                <div
                  class="d-flex gap-1"
                >

                  <!-- PREVIOUS -->
                  <button
                    type="button"
                    class="
                      btn
                      btn-sm
                      btn-outline-secondary
                    "
                    :disabled="
                      currentPage === 1 ||
                      loadingEmployees
                    "
                    @click="
                      goToPage(currentPage - 1)
                    "
                  >
                    Previous
                  </button>

                  <!-- FIRST PAGE -->
                  <button
                    v-if="
                      totalPagesFromServer > 0
                    "
                    type="button"
                    class="btn btn-sm"
                    :class="
                      currentPage === 1
                        ? 'btn-primary'
                        : 'btn-outline-primary'
                    "
                    @click="
                      goToPage(1)
                    "
                  >
                    1
                  </button>

                  <!-- LEFT DOTS -->
                  <span
                    v-if="
                      pageNumbers[0] > 2
                    "
                    class="
                      btn
                      btn-sm
                      btn-outline-secondary
                    "
                  >
                    ...
                  </span>

                  <!-- PAGE NUMBERS -->
                  <button
                    v-for="
                      page in pageNumbers
                    "
                    :key="page"
                    type="button"
                    class="btn btn-sm"
                    :class="
                      page === currentPage
                        ? 'btn-primary'
                        : 'btn-outline-primary'
                    "
                    @click="
                      goToPage(page)
                    "
                  >

                    {{ page }}

                  </button>

                  <!-- RIGHT DOTS -->
                  <span
                    v-if="
                      pageNumbers.length > 0 &&
                      pageNumbers[pageNumbers.length - 1]
                        < totalPagesFromServer - 1
                    "
                    class="
                      btn
                      btn-sm
                      btn-outline-secondary
                    "
                  >
                    ...
                  </span>

                  <!-- LAST PAGE -->
                  <button
                    v-if="
                      totalPagesFromServer > 1
                    "
                    type="button"
                    class="btn btn-sm"
                    :class="
                      currentPage === totalPagesFromServer
                        ? 'btn-primary'
                        : 'btn-outline-primary'
                    "
                    @click="
                      goToPage(totalPagesFromServer)
                    "
                  >

                    {{ totalPagesFromServer }}

                  </button>

                  <!-- NEXT -->
                  <button
                    type="button"
                    class="
                      btn
                      btn-sm
                      btn-outline-secondary
                    "
                    :disabled="
                      currentPage === totalPagesFromServer ||
                      totalPagesFromServer === 0 ||
                      loadingEmployees
                    "
                    @click="
                      goToPage(currentPage + 1)
                    "
                  >
                    Next
                  </button>

                </div>

              </div>

            </div>

          </div>

        </div>

        <!-- ========================================== -->
        <!-- UNIT MANAGEMENT -->
        <!-- ========================================== -->
        <div
          v-else-if="
            currentPageView === 'units'
          "
          class="container-fluid"
        >

          <UnitManagement
            :is-admin="isAdmin"
          />

        </div>

      </main>

      <!-- ========================================== -->
      <!-- FOOTER -->
      <!-- ========================================== -->
      <footer class="main-footer">

        <strong>
          Employee Management
        </strong>

        <span
          class="
            float-end
            d-none
            d-sm-inline
          "
        >
          Vue.js + Spring Boot + MySQL
        </span>

      </footer>

    </div>

    <!-- ========================================== -->
    <!-- EMPLOYEE MODAL -->
    <!-- ========================================== -->
    <div
      v-if="employee"
      class="modal-backdrop-custom"
      @click.self="closeModal"
    >

      <div class="modal-dialog-custom">

        <div class="modal-content">

          <!-- MODAL HEADER -->
          <div class="modal-header">

            <h5 class="modal-title">

              {{
                employee.id
                  ? 'Edit Employee'
                  : 'Add Employee'
              }}

            </h5>

            <button
              type="button"
              class="btn-close"
              aria-label="Close"
              @click="closeModal"
            ></button>

          </div>

          <!-- MODAL BODY -->
          <div class="modal-body">

            <EditEmployee
              :employee="employee"
              :units="units"
              @save="clickSave"
              @cancel="closeModal"
            />

          </div>

        </div>

      </div>

    </div>

  </div>
</template>


<script>

import Login
  from './components/Login.vue'

import EditEmployee
  from './components/EditEmployee.vue'

import UnitManagement
  from './components/UnitManagement.vue'

import api
  from './api.js'


export default {

  name: 'App',

  components: {

    Login,

    EditEmployee,

    UnitManagement

  },

  data() {

    return {

      /* ====================================== */
      /* LOGIN */
      /* ====================================== */

      isLoggedIn: false,

      currentEmployee: null,


      /* ====================================== */
      /* SIDEBAR */
      /* ====================================== */

      sidebarCollapsed: false,

      currentPageView: 'employees',


      /* ====================================== */
      /* EMPLOYEES */
      /* ====================================== */

      employees: [],

      employee: null,


      /* ====================================== */
      /* UNITS */
      /* ====================================== */

      units: [],


      /* ====================================== */
      /* SEARCH */
      /* ====================================== */

      searchKeyword: '',

      roleFilter: '',


      /* ====================================== */
      /* PAGINATION */
      /* ====================================== */

      currentPage: 1,

      pageSize: 5,

      totalElements: 0,

      totalPagesFromServer: 0,


      /* ====================================== */
      /* DASHBOARD */
      /* ====================================== */

      adminCount: 0,


      /* ====================================== */
      /* LOADING */
      /* ====================================== */

      loadingEmployees: false,

      searchTimer: null

    }

  },


  computed: {

    /* ====================================== */
    /* CHECK ADMIN */
    /* ====================================== */

    isAdmin() {

      return (
        this.currentEmployee?.role ===
        'admin'
      )

    },


    /* ====================================== */
    /* PAGE TITLE */
    /* ====================================== */

    pageTitle() {

      if (
        this.currentPageView ===
        'dashboard'
      ) {

        return 'Dashboard'

      }


      if (
        this.currentPageView ===
        'units'
      ) {

        return 'Unit Management'

      }


      return 'Employee Management'

    },


    /* ====================================== */
    /* START ITEM */
    /* ====================================== */

    startItem() {

      if (
        this.totalElements ===
        0
      ) {

        return 0

      }


      return (
        (
          this.currentPage - 1
        ) *
        this.pageSize
      ) + 1

    },


    /* ====================================== */
    /* END ITEM */
    /* ====================================== */

    endItem() {

      return Math.min(

        this.currentPage *
          this.pageSize,

        this.totalElements

      )

    },


    /* ====================================== */
    /* PAGE NUMBERS */
    /* ====================================== */

    pageNumbers() {

      const total =
        this.totalPagesFromServer


      const current =
        this.currentPage


      if (
        total <= 1
      ) {

        return []

      }


      const pages = []


      /*
       * Hiển thị tối đa các trang
       * xung quanh trang hiện tại.
       *
       * Không tạo 800 nút khi có
       * hàng nghìn nhân viên.
       */

      let start =
        Math.max(
          2,
          current - 2
        )


      let end =
        Math.min(
          total - 1,
          current + 2
        )


      if (
        current <= 3
      ) {

        start = 2

        end =
          Math.min(
            total - 1,
            5
          )

      }


      if (
        current >= total - 2
      ) {

        start =
          Math.max(
            2,
            total - 4
          )

        end =
          total - 1

      }


      for (
        let page = start;
        page <= end;
        page++
      ) {

        pages.push(page)

      }


      return pages

    }

  },


  /* ====================================== */
  /* MOUNTED */
  /* ====================================== */

  mounted() {

    const token =
      localStorage.getItem(
        'token'
      )


    const employee =
      localStorage.getItem(
        'employee'
      )


    if (
      token &&
      employee
    ) {

      try {

        this.currentEmployee =
          JSON.parse(
            employee
          )


        this.isLoggedIn =
          true


        this.getEmployees()

        this.getUnits()

        this.getAdminCount()

      }
      catch (error) {

        console.error(
          'Lỗi đọc thông tin đăng nhập:',
          error
        )


        this.logout()

      }

    }

  },


  methods: {

    /* ====================================== */
    /* LOGIN SUCCESS */
    /* ====================================== */

    async handleLoginSuccess(
      employee
    ) {

      this.currentEmployee =
        employee


      this.isLoggedIn =
        true


      this.currentPageView =
        'employees'


      this.currentPage =
        1


      await this.getEmployees()

      await this.getUnits()

      await this.getAdminCount()

    },


    /* ====================================== */
    /* GET EMPLOYEES */
    /* ====================================== */

    async getEmployees() {

      this.loadingEmployees =
        true


      try {

        const page =
          this.currentPage - 1


        const params =
          new URLSearchParams()


        params.set(
          'page',
          page
        )


        params.set(
          'size',
          this.pageSize
        )


        params.set(
          'search',
          this.searchKeyword.trim()
        )


        params.set(
          'role',
          this.roleFilter
        )


        const data =
          await api.get(
            `/employees?${params.toString()}`
          )


        /*
         * Spring Boot Page<Employee>
         * trả về:
         *
         * content
         * totalElements
         * totalPages
         */

        this.employees =
          Array.isArray(
            data?.content
          )
            ? data.content
            : []


        this.totalElements =
          Number(
            data?.totalElements || 0
          )


        this.totalPagesFromServer =
          Number(
            data?.totalPages || 0
          )


        /*
         * Trường hợp xóa hết dữ liệu
         * ở trang cuối.
         */

        if (
          this.totalPagesFromServer > 0 &&
          this.currentPage >
            this.totalPagesFromServer
        ) {

          this.currentPage =
            this.totalPagesFromServer


          await this.getEmployees()

          return

        }

      }
      catch (error) {

        console.error(
          'Lỗi khi lấy danh sách nhân viên:',
          error
        )


        if (
          error.status === 401 ||
          error.status === 403
        ) {

          alert(
            'Phiên đăng nhập đã hết hạn hoặc không có quyền truy cập!'
          )


          this.logout()

          return

        }


        alert(
          error.message ||
          'Không thể lấy danh sách nhân viên'
        )

      }
      finally {

        this.loadingEmployees =
          false

      }

    },


    /* ====================================== */
    /* GET ADMIN COUNT */
    /* ====================================== */

    async getAdminCount() {

      try {

        const params =
          new URLSearchParams()


        params.set(
          'page',
          '0'
        )


        params.set(
          'size',
          '1'
        )


        params.set(
          'search',
          ''
        )


        params.set(
          'role',
          'admin'
        )


        const data =
          await api.get(
            `/employees?${params.toString()}`
          )


        this.adminCount =
          Number(
            data?.totalElements || 0
          )

      }
      catch (error) {

        console.error(
          'Lỗi lấy số lượng admin:',
          error
        )

        this.adminCount = 0

      }

    },


    /* ====================================== */
    /* GET UNITS */
    /* ====================================== */

    async getUnits() {

      try {

        const data =
          await api.get(
            '/units'
          )


        this.units =
          Array.isArray(data)
            ? data
            : []

      }
      catch (error) {

        console.error(
          'Lỗi khi lấy danh sách đơn vị:',
          error
        )


        if (
          error.status === 401 ||
          error.status === 403
        ) {

          alert(
            'Phiên đăng nhập đã hết hạn hoặc không có quyền truy cập!'
          )


          this.logout()

          return

        }


        alert(
          error.message ||
          'Không thể lấy danh sách đơn vị'
        )

      }

    },


    /* ====================================== */
    /* GET UNIT NAME */
    /* ====================================== */

    getUnitName(
      unitId
    ) {

      if (!unitId) {

        return ''

      }


      const unit =
        this.units.find(
          item =>
            Number(item.id) ===
            Number(unitId)
        )


      return unit
        ? `${unit.name} (${unit.code})`
        : ''

    },


    /* ====================================== */
    /* SEARCH */
    /* ====================================== */

    handleSearch() {

      /*
       * Tránh gọi API liên tục
       * khi người dùng đang gõ.
       */

      clearTimeout(
        this.searchTimer
      )


      this.searchTimer =
        setTimeout(
          async () => {

            this.currentPage =
              1


            await this.getEmployees()

          },
          350
        )

    },


    /* ====================================== */
    /* ROLE FILTER */
    /* ====================================== */

    async handleRoleFilter() {

      this.currentPage =
        1


      await this.getEmployees()

    },


    /* ====================================== */
    /* GO TO PAGE */
    /* ====================================== */

    async goToPage(
      page
    ) {

      if (
        page < 1
      ) {

        return

      }


      if (
        page >
        this.totalPagesFromServer
      ) {

        return

      }


      if (
        page ===
        this.currentPage
      ) {

        return

      }


      this.currentPage =
        page


      await this.getEmployees()

    },


    /* ====================================== */
    /* ADD EMPLOYEE */
    /* ====================================== */

    clickAdd() {

      /*
       * BẢO VỆ THÊM NHÂN VIÊN
       * CHỈ ADMIN ĐƯỢC THỰC HIỆN
       */

      if (
        !this.isAdmin
      ) {

        alert(
          'Bạn không có quyền thêm nhân viên!'
        )

        return

      }


      this.currentPage =
        1


      this.employee = {

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

    },


    /* ====================================== */
    /* EDIT EMPLOYEE */
    /* ====================================== */

    clickEdit(
      employee
    ) {

      /*
       * BẢO VỆ SỬA NHÂN VIÊN
       * CHỈ ADMIN ĐƯỢC THỰC HIỆN
       */

      if (
        !this.isAdmin
      ) {

        alert(
          'Bạn không có quyền sửa nhân viên!'
        )

        return

      }


      this.employee =
        JSON.parse(
          JSON.stringify(
            employee
          )
        )

    },


    /* ====================================== */
    /* CLOSE MODAL */
    /* ====================================== */

    closeModal() {

      this.employee =
        null

    },


    /* ====================================== */
    /* SAVE EMPLOYEE */
    /* ====================================== */

    async clickSave(
      formData
    ) {

      /*
       * BẢO VỆ SAVE
       * CHỈ ADMIN ĐƯỢC THỰC HIỆN
       */

      if (
        !this.isAdmin
      ) {

        alert(
          'Bạn không có quyền thực hiện thao tác này!'
        )

        return

      }


      try {

        /* ================================== */
        /* ADD */
        /* ================================== */

        if (
          !formData.id
        ) {

          await api.post(
            '/employees',
            {

              name:
                formData.name,

              address:
                formData.address,

              phone:
                formData.phone,

              email:
                formData.email,

              username:
                formData.username,

              password:
                formData.password,

              role:
                formData.role ||
                'user',

              unitId:
                formData.unitId

            }
          )

        }


        /* ================================== */
        /* EDIT */
        /* ================================== */

        else {

          await api.put(
            `/employees/${formData.id}`,
            {

              name:
                formData.name,

              address:
                formData.address,

              phone:
                formData.phone,

              email:
                formData.email,

              username:
                formData.username,

              password:
                formData.password,

              role:
                formData.role ||
                'user',

              unitId:
                formData.unitId

            }
          )

        }


        /* ================================== */
        /* SUCCESS */
        /* ================================== */

        alert(
          formData.id
            ? 'Cập nhật nhân viên thành công!'
            : 'Thêm nhân viên thành công!'
        )


        this.employee =
          null


        await this.getEmployees()

        await this.getAdminCount()

      }
      catch (error) {

        console.error(
          error
        )


        if (
          error.status === 401 ||
          error.status === 403
        ) {

          alert(
            error.message ||
            'Phiên đăng nhập đã hết hạn hoặc bạn không có quyền thực hiện thao tác này'
          )

          if (
            error.status === 401
          ) {

            this.logout()

          }

          return

        }


        alert(
          error.message ||
          'Không thể lưu nhân viên'
        )

      }

    },


    /* ====================================== */
    /* DELETE EMPLOYEE */
    /* ====================================== */

    async clickDelete(
      employee
    ) {

      /*
       * BẢO VỆ DELETE
       * CHỈ ADMIN ĐƯỢC THỰC HIỆN
       */

      if (
        !this.isAdmin
      ) {

        alert(
          'Bạn không có quyền xóa nhân viên!'
        )

        return

      }


      const confirmed =
        confirm(
          `Bạn có chắc muốn xóa nhân viên "${employee.name}" không?`
        )


      if (!confirmed) {

        return

      }


      try {

        await api.delete(
          `/employees/${employee.id}`
        )


        alert(
          'Xóa nhân viên thành công!'
        )


        /*
         * Nếu xóa người cuối cùng
         * của trang cuối,
         * getEmployees() tự điều chỉnh.
         */

        await this.getEmployees()

        await this.getAdminCount()

      }
      catch (error) {

        console.error(
          error
        )


        if (
          error.status === 401 ||
          error.status === 403
        ) {

          alert(
            error.message ||
            'Phiên đăng nhập đã hết hạn hoặc bạn không có quyền xóa nhân viên'
          )

          if (
            error.status === 401
          ) {

            this.logout()

          }

          return

        }


        alert(
          error.message ||
          'Không thể xóa nhân viên'
        )

      }

    },


    /* ====================================== */
    /* LOGOUT */
    /* ====================================== */

    logout() {

      clearTimeout(
        this.searchTimer
      )


      localStorage.removeItem(
        'token'
      )


      localStorage.removeItem(
        'employee'
      )


      this.isLoggedIn =
        false


      this.currentEmployee =
        null


      this.employees =
        []


      this.units =
        []


      this.employee =
        null


      this.currentPage =
        1


      this.totalElements =
        0


      this.totalPagesFromServer =
        0


      this.adminCount =
        0


      this.currentPageView =
        'employees'

    }

  }

}

</script>


<style scoped>

/* ========================================== */
/* GLOBAL */
/* ========================================== */

* {
  box-sizing: border-box;
}


/* ========================================== */
/* SIDEBAR */
/* ========================================== */

.wrapper {

  min-height: 100vh;

  display: flex;

  background: #f4f6f9;

}


.main-sidebar {

  width: 250px;

  min-height: 100vh;

  background: #343a40;

  color: white;

  transition:
    width 0.2s ease;

  flex-shrink: 0;

}


.sidebar-collapsed
.main-sidebar {

  width: 70px;

}


.brand-link {

  height: 57px;

  display: flex;

  align-items: center;

  padding: 0 20px;

  background: #007bff;

  font-size: 18px;

  font-weight: 600;

  white-space: nowrap;

  overflow: hidden;

}


.sidebar {

  padding: 10px;

}


.nav-link {

  color: #c2c7d0;

  padding: 12px 15px;

  border-radius: 5px;

  margin-bottom: 5px;

  display: flex;

  align-items: center;

  gap: 10px;

  text-decoration: none;

}


.nav-link:hover {

  background:
    rgba(255,255,255,0.1);

  color: white;

}


.nav-link.active {

  background: #007bff;

  color: white;

}


.nav-link i {

  font-size: 18px;

  min-width: 20px;

}


/* ========================================== */
/* MAIN CONTENT */
/* ========================================== */

.main-content {

  flex: 1;

  min-width: 0;

  display: flex;

  flex-direction: column;

  min-height: 100vh;

}


/* ========================================== */
/* NAVBAR */
/* ========================================== */

.main-navbar {

  height: 57px;

  background: white;

  border-bottom:
    1px solid #dee2e6;

  display: flex;

  justify-content:
    space-between;

  align-items: center;

  padding: 0 20px;

}


.navbar-left,
.navbar-right {

  display: flex;

  align-items: center;

}


.page-title {

  margin-left: 15px;

  font-weight: 600;

  font-size: 18px;

}


.current-user {

  display: flex;

  align-items: center;

  gap: 8px;

}


.current-user i {

  font-size: 24px;

}


/* ========================================== */
/* CONTENT */
/* ========================================== */

.content-wrapper {

  flex: 1;

  padding: 25px 15px;

}


.content-wrapper
.container-fluid {

  width: 100%;

  max-width: none;

  margin-left: 0;

  margin-right: 0;

}


/* ========================================== */
/* EMPLOYEE CARD */
/* ========================================== */

.employee-card {

  width: 100% !important;

  max-width: none !important;

  margin-left: 0 !important;

  margin-right: 0 !important;

}


/* ========================================== */
/* TABLE WRAPPER */
/* ========================================== */

.employee-table-wrapper {

  width: 100% !important;

  max-width: none;

  overflow-x: auto;

}


/* ========================================== */
/* TABLE */
/* ========================================== */

.employee-table {

  width: 100% !important;

  min-width: 1200px;

  margin-bottom: 0;

  table-layout: auto;

  border-collapse: collapse;

}


/* ========================================== */
/* TABLE CELL */
/* ========================================== */

.employee-table th,
.employee-table td {

  vertical-align: middle;

  white-space: nowrap;

  border: 1px solid #dee2e6;

}


/* ========================================== */
/* TABLE HEADER */
/* ========================================== */

.employee-table thead th {

  background-color: #f8f9fa;

  border: 1px solid #dee2e6;

  font-weight: 600;

}


/* ========================================== */
/* TABLE BODY */
/* ========================================== */

.employee-table tbody td {

  border: 1px solid #dee2e6;

}


/* ========================================== */
/* TABLE ROW */
/* ========================================== */

.employee-table tbody tr {

  border-bottom:
    1px solid #dee2e6;

}


/* ========================================== */
/* HOVER */
/* ========================================== */

.employee-table tbody tr:hover {

  background-color:
    rgba(0, 123, 255, 0.04);

}


/* ========================================== */
/* BUTTON */
/* ========================================== */

.btn {

  border-radius: 5px;

}


/* ========================================== */
/* DASHBOARD */
/* ========================================== */

.dashboard-card {

  border: 0;

}


.dashboard-card
.card-body {

  display: flex;

  align-items: center;

  gap: 20px;

}


.dashboard-icon {

  width: 55px;

  height: 55px;

  border-radius: 10px;

  display: flex;

  align-items: center;

  justify-content: center;

  color: white;

  font-size: 24px;

}


.bg-primary {

  background-color:
    #007bff !important;

}


.bg-danger {

  background-color:
    #dc3545 !important;

}


.bg-success {

  background-color:
    #198754 !important;

}


/* ========================================== */
/* MODAL */
/* ========================================== */

.modal-backdrop-custom {

  position: fixed;

  inset: 0;

  background:
    rgba(0,0,0,0.5);

  z-index: 1050;

  display: flex;

  align-items: center;

  justify-content: center;

  padding: 20px;

}


.modal-dialog-custom {

  width: 100%;

  max-width: 650px;

}


.modal-content {

  background: white;

  border-radius: 8px;

  box-shadow:
    0 10px 40px
    rgba(0,0,0,0.2);

  overflow: hidden;

}


.modal-header {

  padding: 16px 20px;

  border-bottom:
    1px solid #dee2e6;

  display: flex;

  justify-content:
    space-between;

  align-items: center;

}


.modal-body {

  padding: 20px;

}


/* ========================================== */
/* FOOTER */
/* ========================================== */

.main-footer {

  background: white;

  border-top:
    1px solid #dee2e6;

  padding: 15px 20px;

  color: #6c757d;

}


/* ========================================== */
/* RESPONSIVE */
/* ========================================== */

@media (max-width: 768px) {

  .main-sidebar {

    width: 70px;

  }


  .brand-link {

    justify-content: center;

    padding: 0;

  }


  .content-wrapper {

    padding: 15px 10px;

  }


  .main-navbar {

    padding: 0 10px;

  }


  .current-user span {

    display: none;

  }

}

</style>