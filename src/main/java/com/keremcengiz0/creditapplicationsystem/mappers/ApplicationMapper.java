package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    Application fromApplicationDtoToApplication(ApplicationDTO applicationDTO);

    ApplicationDTO fromApplicationToApplicationDto(Application application);

    List<ApplicationDTO> fromApplicationListToApplicationDtoList(List<Application> applications);

}
