package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application fromApplicationDtoToApplication(ApplicationDTO applicationDTO);

    ApplicationDTO fromApplicationToApplicationDto(Application application);

    List<ApplicationDTO> fromApplicationListToApplicationDtoList(List<Application> applications);

}
