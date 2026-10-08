package com.employee.backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.backend.dto.LoginRequest;
import com.employee.backend.entity.Employee;
import com.employee.backend.repository.EmployeeRepository;
import com.employee.backend.service.JwtService;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final EmployeeRepository employeeRepository;

    private final JwtService jwtService;

    public LoginController(
            EmployeeRepository employeeRepository,
            JwtService jwtService
    ) {
        this.employeeRepository =
                employeeRepository;

        this.jwtService =
                jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        // Kiểm tra username và password có được gửi lên không
        if (
                request.getUsername() == null ||
                request.getUsername().isBlank() ||
                request.getPassword() == null ||
                request.getPassword().isBlank()
        ) {

            Map<String, String> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Username và password là bắt buộc"
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        // Tìm nhân viên theo username
        Optional<Employee> employeeOptional =
                employeeRepository
                        .findByUsername(
                                request.getUsername()
                        );

        // Không tìm thấy username
        if (employeeOptional.isEmpty()) {

            Map<String, String> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Username hoặc password không đúng"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        Employee employee =
                employeeOptional.get();

        /*
         * Database hiện tại đang sử dụng
         * password dạng cũ giống Node.js.
         *
         * Tạm thời so sánh trực tiếp.
         *
         * Sau khi hệ thống chạy ổn,
         * chúng ta sẽ có thể nâng cấp
         * sang BCrypt.
         */
        if (
                !request.getPassword()
                        .equals(employee.getPassword())
        ) {

            Map<String, String> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Username hoặc password không đúng"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        // Tạo JWT
        String token =
                jwtService.generateToken(
                        employee.getId(),
                        employee.getUsername(),
                        employee.getRole()
                );

        // Tạo response trả về frontend
        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Login thành công"
        );

        response.put(
                "token",
                token
        );

        response.put(
                "employee",
                employee
        );

        return ResponseEntity.ok(response);
    }
}