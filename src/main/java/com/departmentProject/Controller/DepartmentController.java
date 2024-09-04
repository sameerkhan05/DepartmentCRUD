package com.departmentProject.Controller;

import com.departmentProject.dto.DepartmentDTO;
import com.departmentProject.exceptions.ResourceNotFoundException;
import com.departmentProject.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("/{departmentID")
	public ResponseEntity<DepartmentDTO> getDepartmentByID(@PathVariable(name = "departmentID") @Valid long id) {
		Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentByID(id);
		return departmentDTO
				.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
				.orElseThrow(() -> new ResourceNotFoundException("Department not found by iD : " + id));
	}
	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments(@RequestParam(required = false)@Valid String title){
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}
}
