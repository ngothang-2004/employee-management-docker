<template>

  <div class="unit-management">

    <!-- ================================= -->
    <!-- HEADER -->
    <!-- ================================= -->

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
          Quản lý đơn vị
        </h3>

        <p class="text-muted mb-0">
          Quản lý danh sách các đơn vị trong hệ thống
        </p>

      </div>


      <!-- THÊM ĐƠN VỊ -->
      <button
        v-if="isAdmin"
        type="button"
        class="btn btn-primary"
        @click="clickAdd"
      >

        <i class="bi bi-plus-lg me-1"></i>

        Thêm đơn vị

      </button>

    </div>


    <!-- ================================= -->
    <!-- TABLE -->
    <!-- ================================= -->

    <div class="card shadow-sm">

      <div class="card-header">

        <h5 class="mb-0">

          <i class="bi bi-building me-2"></i>

          Danh sách đơn vị

        </h5>

      </div>


      <div class="card-body p-0">

        <div class="table-responsive">

          <table class="table table-hover mb-0">

            <thead class="table-light">

              <tr>

                <th
                  class="text-center"
                  style="width: 80px"
                >
                  ID
                </th>

                <th>
                  Tên đơn vị
                </th>

                <th>
                  Mã đơn vị
                </th>

                <th>
                  Địa chỉ
                </th>

                <th
                  v-if="isAdmin"
                  class="text-center"
                  style="width: 200px"
                >
                  Thao tác
                </th>

              </tr>

            </thead>


            <tbody>

              <!-- KHÔNG CÓ DỮ LIỆU -->
              <tr
                v-if="units.length === 0"
              >

                <td
                  :colspan="isAdmin ? 5 : 4"
                  class="text-center py-4"
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

                  Chưa có đơn vị nào

                </td>

              </tr>


              <!-- DANH SÁCH -->
              <tr
                v-for="unit in units"
                :key="unit.id"
              >

                <td class="text-center">

                  {{ unit.id }}

                </td>


                <td>

                  <strong>
                    {{ unit.name }}
                  </strong>

                </td>


                <td>

                  <span
                    class="badge bg-secondary"
                  >

                    {{ unit.code }}

                  </span>

                </td>


                <td>

                  {{ unit.address || '-' }}

                </td>


                <!-- THAO TÁC CHỈ ADMIN -->
                <td
                  v-if="isAdmin"
                  class="text-center"
                >

                  <!-- SỬA -->
                  <button
                    v-if="isAdmin"
                    type="button"
                    class="
                      btn
                      btn-sm
                      btn-warning
                      me-2
                    "
                    @click="clickEdit(unit)"
                  >

                    <i
                      class="bi bi-pencil"
                    ></i>

                    Sửa

                  </button>


                  <!-- XÓA -->
                  <button
                    v-if="isAdmin"
                    type="button"
                    class="
                      btn
                      btn-sm
                      btn-danger
                    "
                    @click="clickDelete(unit)"
                  >

                    <i
                      class="bi bi-trash"
                    ></i>

                    Xóa

                  </button>

                </td>

              </tr>

            </tbody>

          </table>

        </div>

      </div>

    </div>


    <!-- ================================= -->
    <!-- MODAL THÊM / SỬA -->
    <!-- ================================= -->

    <div
      v-if="showModal"
      class="modal-backdrop-custom"
      @click.self="closeModal"
    >

      <div class="modal-dialog-custom">

        <div class="modal-content">


          <!-- HEADER -->
          <div class="modal-header">

            <h5 class="modal-title">

              {{
                form.id
                  ? 'Sửa đơn vị'
                  : 'Thêm đơn vị'
              }}

            </h5>


            <button
              type="button"
              class="btn-close"
              @click="closeModal"
            ></button>

          </div>


          <!-- BODY -->
          <div class="modal-body">

            <form
              @submit.prevent="clickSave"
            >


              <!-- TÊN ĐƠN VỊ -->
              <div class="mb-3">

                <label
                  for="unitName"
                  class="form-label"
                >
                  Tên đơn vị
                </label>


                <div class="input-group">

                  <span
                    class="input-group-text"
                  >

                    <i
                      class="bi bi-building"
                    ></i>

                  </span>


                  <input
                    id="unitName"
                    v-model="form.name"
                    type="text"
                    class="form-control"
                    placeholder="Nhập tên đơn vị"
                  />

                </div>

              </div>


              <!-- MÃ ĐƠN VỊ -->
              <div class="mb-3">

                <label
                  for="unitCode"
                  class="form-label"
                >
                  Mã đơn vị
                </label>


                <div class="input-group">

                  <span
                    class="input-group-text"
                  >

                    <i
                      class="bi bi-upc-scan"
                    ></i>

                  </span>


                  <input
                    id="unitCode"
                    v-model="form.code"
                    type="text"
                    class="form-control"
                    placeholder="Nhập mã đơn vị"
                  />

                </div>

              </div>


              <!-- ĐỊA CHỈ -->
              <div class="mb-3">

                <label
                  for="unitAddress"
                  class="form-label"
                >
                  Địa chỉ
                </label>


                <div class="input-group">

                  <span
                    class="input-group-text"
                  >

                    <i
                      class="bi bi-geo-alt"
                    ></i>

                  </span>


                  <input
                    id="unitAddress"
                    v-model="form.address"
                    type="text"
                    class="form-control"
                    placeholder="Nhập địa chỉ"
                  />

                </div>

              </div>


              <!-- BUTTON -->
              <div
                class="
                  d-flex
                  justify-content-end
                  gap-2
                  mt-4
                "
              >

                <button
                  type="button"
                  class="btn btn-secondary"
                  @click="closeModal"
                >

                  <i
                    class="bi bi-x-lg me-1"
                  ></i>

                  Hủy

                </button>


                <button
                  type="submit"
                  class="btn btn-primary"
                >

                  <i
                    class="bi bi-check-lg me-1"
                  ></i>

                  Lưu

                </button>

              </div>

            </form>

          </div>

        </div>

      </div>

    </div>

  </div>

</template>


<script>

import api from '../api'


export default {

  name: 'UnitManagement',

  /*
   * NHẬN QUYỀN ADMIN TỪ APP.VUE
   *
   * Nếu đăng nhập bằng admin:
   * isAdmin = true
   *
   * Nếu đăng nhập bằng user:
   * isAdmin = false
   */
  props: {
    isAdmin: {
      type: Boolean,
      default: false
    }
  },


  data() {

    return {

      /* ============================= */
      /* DANH SÁCH ĐƠN VỊ */
      /* ============================= */

      units: [],


      /* ============================= */
      /* MODAL */
      /* ============================= */

      showModal: false,


      /* ============================= */
      /* FORM */
      /* ============================= */

      form: {

        id: null,

        name: '',

        code: '',

        address: ''

      }

    }

  },


  mounted() {

    this.getUnits()

  },


  methods: {


    /* ============================= */
    /* GET UNITS */
    /* ============================= */

    async getUnits() {

      try {

        const data =
          await api.get('/units')


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


        alert(
          error.message ||
          'Không thể lấy danh sách đơn vị'
        )

      }

    },


    /* ============================= */
    /* ADD */
    /* ============================= */

    clickAdd() {

      this.form = {

        id: null,

        name: '',

        code: '',

        address: ''

      }


      this.showModal = true

    },


    /* ============================= */
    /* EDIT */
    /* ============================= */

    clickEdit(unit) {

      this.form = {

        id: unit.id,

        name: unit.name || '',

        code: unit.code || '',

        address: unit.address || ''

      }


      this.showModal = true

    },


    /* ============================= */
    /* CLOSE MODAL */
    /* ============================= */

    closeModal() {

      this.showModal = false


      this.form = {

        id: null,

        name: '',

        code: '',

        address: ''

      }

    },


    /* ============================= */
    /* SAVE */
    /* ============================= */

    async clickSave() {


      /* KIỂM TRA TÊN */

      if (!this.form.name.trim()) {

        alert(
          'Vui lòng nhập tên đơn vị!'
        )

        return

      }


      /* KIỂM TRA MÃ */

      if (!this.form.code.trim()) {

        alert(
          'Vui lòng nhập mã đơn vị!'
        )

        return

      }


      try {


        /* ========================= */
        /* THÊM */
        /* ========================= */

        if (!this.form.id) {

          await api.post(
            '/units',
            {

              name:
                this.form.name,

              code:
                this.form.code,

              address:
                this.form.address

            }
          )


          alert(
            'Thêm đơn vị thành công!'
          )

        }


        /* ========================= */
        /* SỬA */
        /* ========================= */

        else {

          await api.put(
            `/units/${this.form.id}`,
            {

              name:
                this.form.name,

              code:
                this.form.code,

              address:
                this.form.address

            }
          )


          alert(
            'Cập nhật đơn vị thành công!'
          )

        }


        /* ========================= */
        /* ĐÓNG */
        /* ========================= */

        this.closeModal()


        /* ========================= */
        /* LOAD LẠI */
        /* ========================= */

        await this.getUnits()

      }
      catch (error) {

        console.error(
          'Lỗi khi lưu đơn vị:',
          error
        )


        if (
          error.status === 401
        ) {

          alert(
            'Phiên đăng nhập đã hết hạn!'
          )

          return

        }


        if (
          error.status === 403
        ) {

          alert(
            'Bạn không có quyền thực hiện thao tác này!'
          )

          return

        }


        alert(
          error.message ||
          'Không thể lưu đơn vị'
        )

      }

    },


    /* ============================= */
    /* DELETE */
    /* ============================= */

    async clickDelete(unit) {

      const confirmed =
        confirm(
          `Bạn có chắc muốn xóa đơn vị "${unit.name}" không?`
        )


      if (!confirmed) {

        return

      }


      try {

        await api.delete(
          `/units/${unit.id}`
        )


        alert(
          'Xóa đơn vị thành công!'
        )


        await this.getUnits()

      }
      catch (error) {

        console.error(
          'Lỗi khi xóa đơn vị:',
          error
        )


        if (
          error.status === 401
        ) {

          alert(
            'Phiên đăng nhập đã hết hạn!'
          )

          return

        }


        if (
          error.status === 403
        ) {

          alert(
            'Bạn không có quyền xóa đơn vị!'
          )

          return

        }


        alert(
          error.message ||
          'Không thể xóa đơn vị'
        )

      }

    }

  }

}

</script>


<style scoped>

.unit-management {

  width: 100%;

}


.card {

  width: 100%;

}


.table {

  margin-bottom: 0;

}


.table th,
.table td {

  vertical-align: middle;

  border: 1px solid #dee2e6;

}


.table {

  border-collapse: collapse;

}


.table thead th {

  background-color: #f8f9fa;

}


.form-label {

  font-weight: 600;

}


.form-control {

  min-height: 42px;

}


.input-group-text {

  width: 45px;

  justify-content: center;

}


.btn {

  border-radius: 5px;

}


/* ================================= */
/* MODAL */
/* ================================= */

.modal-backdrop-custom {

  position: fixed;

  inset: 0;

  background:
    rgba(0, 0, 0, 0.5);

  z-index: 1050;

  display: flex;

  align-items: center;

  justify-content: center;

  padding: 20px;

}


.modal-dialog-custom {

  width: 100%;

  max-width: 600px;

}


.modal-content {

  background: white;

  border-radius: 8px;

  box-shadow:
    0 10px 40px
    rgba(0, 0, 0, 0.2);

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

</style>