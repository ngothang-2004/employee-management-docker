package com.employee.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.backend.entity.Unit;
import com.employee.backend.repository.EmployeeRepository;
import com.employee.backend.repository.UnitRepository;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitRepository unitRepository;
    private final EmployeeRepository employeeRepository;

    public UnitController(
            UnitRepository unitRepository,
            EmployeeRepository employeeRepository
    ) {
        this.unitRepository = unitRepository;
        this.employeeRepository = employeeRepository;
    }

    // =========================
    // GET ALL
    // =========================
    @GetMapping
    public List<Unit> getUnits() {
        return unitRepository.findAll();
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUnit(@PathVariable Long id) {

        Unit unit = unitRepository.findById(id).orElse(null);

        if (unit == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            "Không tìm thấy đơn vị"
                    ));
        }

        return ResponseEntity.ok(unit);
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<?> createUnit(
            @RequestBody Unit unit
    ) {

        // Kiểm tra tên đơn vị
        if (
                unit.getName() == null ||
                unit.getName().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "Tên đơn vị là bắt buộc"
                    ));
        }

        // Kiểm tra mã đơn vị
        if (
                unit.getCode() == null ||
                unit.getCode().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "Mã đơn vị là bắt buộc"
                    ));
        }

        // Chuẩn hóa mã đơn vị
        String code = unit.getCode().trim();

        // Kiểm tra mã đơn vị đã tồn tại
        if (unitRepository.existsByCode(code)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            "Mã đơn vị " + code + " đã tồn tại"
                    ));
        }

        unit.setCode(code);

        Unit savedUnit = unitRepository.save(unit);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUnit);
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUnit(
            @PathVariable Long id,
            @RequestBody Unit unit
    ) {

        Unit existingUnit =
                unitRepository.findById(id).orElse(null);

        // Không tìm thấy Unit
        if (existingUnit == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            "Không tìm thấy đơn vị"
                    ));
        }

        // Kiểm tra tên
        if (
                unit.getName() == null ||
                unit.getName().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "Tên đơn vị là bắt buộc"
                    ));
        }

        // Kiểm tra mã
        if (
                unit.getCode() == null ||
                unit.getCode().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "Mã đơn vị là bắt buộc"
                    ));
        }

        // Chuẩn hóa mã
        String code = unit.getCode().trim();

        // Kiểm tra mã đã thuộc Unit khác
        if (unitRepository.existsByCodeAndIdNot(code, id)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            "Mã đơn vị " + code + " đã tồn tại"
                    ));
        }

        // Cập nhật dữ liệu
        existingUnit.setName(unit.getName().trim());
        existingUnit.setCode(code);
        existingUnit.setAddress(unit.getAddress());

        Unit updatedUnit =
                unitRepository.save(existingUnit);

        return ResponseEntity.ok(updatedUnit);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnit(
            @PathVariable Long id
    ) {

        Unit unit =
                unitRepository.findById(id).orElse(null);

        // Không tìm thấy Unit
        if (unit == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            "Không tìm thấy đơn vị"
                    ));
        }

        // Kiểm tra Unit đang được Employee sử dụng
        if (employeeRepository.existsByUnitId(id)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            "Không thể xóa đơn vị vì đang có nhân viên sử dụng"
                    ));
        }

        // Cho phép xóa nếu không có Employee sử dụng
        unitRepository.delete(unit);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Xóa đơn vị thành công"
                )
        );
    }
}