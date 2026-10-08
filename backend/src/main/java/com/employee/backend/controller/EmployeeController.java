package com.employee.backend.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.backend.entity.Employee;
import com.employee.backend.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(
            EmployeeRepository employeeRepository
    ) {
        this.employeeRepository = employeeRepository;
    }

    // ===============================
    // GET - Lấy danh sách nhân viên
    // Có phân trang
    // ===============================
        @GetMapping
        public Page<Employee> getEmployees(
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "") String role,
        Pageable pageable
) {
    return employeeRepository.searchEmployees(search, role, pageable);
}

    // ===============================
    // GET - Lấy nhân viên theo ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(
            @PathVariable Long id
    ) {

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    "Không tìm thấy nhân viên"
                            )
                    );
        }

        return ResponseEntity.ok(employee);
    }

    // ===============================
    // POST - Thêm nhân viên
    // ===============================
    @PostMapping
    public ResponseEntity<?> createEmployee(
            @RequestBody Employee employee
    ) {

        // Kiểm tra username
        if (
                employee.getUsername() == null ||
                employee.getUsername().isBlank()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    "Username là bắt buộc"
                            )
                    );
        }

        // Kiểm tra password
        if (
                employee.getPassword() == null ||
                employee.getPassword().isBlank()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    "Password là bắt buộc"
                            )
                    );
        }

        // Kiểm tra username đã tồn tại
        if (
                employeeRepository
                        .findByUsername(
                                employee.getUsername()
                        )
                        .isPresent()
        ) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            Map.of(
                                    "message",
                                    "Username đã tồn tại"
                            )
                    );
        }

        // Nếu không truyền role
        // thì mặc định là user
        if (
                employee.getRole() == null ||
                employee.getRole().isBlank()
        ) {

            employee.setRole("user");
        }

        // Lưu nhân viên
        Employee savedEmployee =
                employeeRepository.save(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEmployee);
    }

    // ===============================
    // PUT - Sửa nhân viên
    // ===============================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee
    ) {

        // Tìm nhân viên cần sửa
        Employee existingEmployee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        // Không tìm thấy
        if (existingEmployee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    "Không tìm thấy nhân viên"
                            )
                    );
        }

        // Kiểm tra username
        if (
                employee.getUsername() == null ||
                employee.getUsername().isBlank()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    "Username là bắt buộc"
                            )
                    );
        }

        // Kiểm tra username có bị trùng
        Employee employeeByUsername =
                employeeRepository
                        .findByUsername(
                                employee.getUsername()
                        )
                        .orElse(null);

        if (
                employeeByUsername != null &&
                !employeeByUsername
                        .getId()
                        .equals(id)
        ) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            Map.of(
                                    "message",
                                    "Username đã tồn tại"
                            )
                    );
        }

        // ===============================
        // Cập nhật thông tin
        // ===============================

        existingEmployee.setName(
                employee.getName()
        );

        existingEmployee.setAddress(
                employee.getAddress()
        );

        existingEmployee.setPhone(
                employee.getPhone()
        );

        existingEmployee.setEmail(
                employee.getEmail()
        );

        existingEmployee.setUsername(
                employee.getUsername()
        );

        existingEmployee.setUnitId(
                employee.getUnitId()
        );

        // Chỉ cập nhật password
        // nếu gửi password mới
        if (
                employee.getPassword() != null &&
                !employee.getPassword().isBlank()
        ) {

            existingEmployee.setPassword(
                    employee.getPassword()
            );
        }

        // Cập nhật role nếu có truyền
        if (
                employee.getRole() != null &&
                !employee.getRole().isBlank()
        ) {

            existingEmployee.setRole(
                    employee.getRole()
            );
        }

        // Lưu xuống database
        Employee updatedEmployee =
                employeeRepository.save(
                        existingEmployee
                );

        return ResponseEntity.ok(
                updatedEmployee
        );
    }

    // ===============================
    // DELETE - Xóa nhân viên
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Long id
    ) {

        // Tìm nhân viên
        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        // Không tìm thấy
        if (employee == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    "Không tìm thấy nhân viên"
                            )
                    );
        }

        // Xóa nhân viên
        employeeRepository.delete(employee);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Xóa nhân viên thành công"
                )
        );
    }
}