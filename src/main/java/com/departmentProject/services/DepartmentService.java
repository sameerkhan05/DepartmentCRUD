package com.departmentProject.services;

import com.departmentProject.dto.DepartmentDTO;
import com.departmentProject.entities.DepartmentEntity;
import com.departmentProject.exceptions.ResourceNotFoundException;
import com.departmentProject.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;

	public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
		this.departmentRepository = departmentRepository;
		this.modelMapper = modelMapper;
	}

	public Optional<DepartmentDTO> getDepartmentByID(long id) {
		return departmentRepository.findById(id).map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class));
	}

	public List<DepartmentDTO> getAllDepartments() {
		List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
		return departmentEntities
				.stream()
				.map(departmentEntity1 -> modelMapper.map(departmentEntity1, DepartmentDTO.class))
				.collect(Collectors.toList());
	}

	public DepartmentDTO createNewDepartment(@RequestBody DepartmentDTO inputDepartment) {
		DepartmentEntity toSaveEntity = modelMapper.map(inputDepartment, DepartmentEntity.class);
		DepartmentEntity savedDepartment = departmentRepository.save(toSaveEntity);
		return modelMapper.map(savedDepartment, DepartmentDTO.class);
	}

	public DepartmentDTO updateDepartmentById(long departmentId, DepartmentDTO departmentDTO) {
		isExistByDepartmentById(departmentId);
		DepartmentEntity departmentEntity = modelMapper.map(departmentId, DepartmentEntity.class);
		departmentEntity.setId(departmentId);
		DepartmentEntity saveDepartmentEntity = departmentRepository.save(departmentEntity);
		return modelMapper.map(saveDepartmentEntity, DepartmentDTO.class);
	}

	public boolean deleteDepartmentById(long departmentId) {
		isExistByDepartmentById(departmentId);
		departmentRepository.deleteById(departmentId);
		return true;
	}

	private void isExistByDepartmentById(long departmentId) {
		boolean exist = departmentRepository.existsById(departmentId);
		if (!exist) throw new ResourceNotFoundException("Department not found with id: " + departmentId);
	}

	public DepartmentDTO updatePartialDepartmentById(long departmentId, Map<String, Object> updates) {
		isExistByDepartmentById(departmentId);
		DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
		updates.forEach((field, value) -> {
			Field fieldToBeUpdated = ReflectionUtils.findRequiredField(DepartmentEntity.class, field);
			ReflectionUtils.setField(fieldToBeUpdated, departmentEntity, value);
		});
		return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
	}
}
