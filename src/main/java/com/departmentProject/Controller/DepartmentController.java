package com.departmentProject.Controller;

import com.departmentProject.dto.DepartmentDTO;
import com.departmentProject.exceptions.ResourceNotFoundException;
import com.departmentProject.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("/{departmentId}")
	public ResponseEntity<DepartmentDTO> getDepartmentByID(@PathVariable(name = "departmentId") @Valid long id) {
		Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentByID(id);
		return departmentDTO
				.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
				.orElseThrow(() -> new ResourceNotFoundException("Department not found by iD : " + id));
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments(@RequestParam(required = false) @Valid String title) {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@PostMapping
	public ResponseEntity<DepartmentDTO> createNewDepartment(@RequestBody @Valid DepartmentDTO inputDepartment) {
		DepartmentDTO savedDepartment = departmentService.createNewDepartment(inputDepartment);
		return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{departmentId}")
	public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestBody @Valid DepartmentDTO departmentDTO, @PathVariable long departmentId) {
		return ResponseEntity.ok(departmentService.updateDepartmentById(departmentId, departmentDTO));
	}

	@DeleteMapping(path = "/{departmentId}")
	public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable @Valid long departmentId) {
		boolean gotDeleted = departmentService.deleteDepartmentById(departmentId);
		if (gotDeleted) return ResponseEntity.ok(true);
		return ResponseEntity.notFound().build();
	}

	@PatchMapping(path = "/{departmentId}")
	public ResponseEntity<DepartmentDTO> updatePartialDepartmentById(@RequestBody @Valid Map<String, Object> updates, @PathVariable long departmentId) {
		DepartmentDTO departmentDTO = departmentService.updatePartialDepartmentById(departmentId, updates);
		if (departmentDTO == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(departmentDTO);
	}

}
