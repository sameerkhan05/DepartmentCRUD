package com.departmentProject.services;

import com.departmentProject.dto.DepartmentDTO;
import com.departmentProject.entities.DepartmentEntity;
import com.departmentProject.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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

	public Optional<DepartmentDTO> getDepartmentByID(long id){
		return departmentRepository.findById(id).map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class));
	}

	public List<DepartmentDTO> getAllDepartments() {
		List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
		return departmentEntities
				.stream()
				.map(departmentEntity1 -> modelMapper.map(departmentEntity1, DepartmentDTO.class))
				.collect(Collectors.toList());
	}
	public DepartmentDTO createNewDepartment(@RequestBody DepartmentDTO inputDepartment){
		DepartmentEntity toSaveEntity = modelMapper.map(inputDepartment, DepartmentEntity.class);
		DepartmentEntity savedDepartment = departmentRepository.save(toSaveEntity);
		return modelMapper.map(savedDepartment, DepartmentDTO.class);
	}
}
