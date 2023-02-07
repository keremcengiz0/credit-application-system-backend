package com.keremcengiz0.creditapplicationsystem.mappers;

import com.keremcengiz0.creditapplicationsystem.dtos.ApplicationDTO;
import com.keremcengiz0.creditapplicationsystem.entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Named("fromApplicationDtoToApplication")
    Application fromApplicationDtoToApplication(ApplicationDTO applicationDTO);

    @Named("fromApplicationToApplicationDto")
    ApplicationDTO fromApplicationToApplicationDto(Application application);

    @Named("fromApplicationListToApplicationDtoList")
    List<ApplicationDTO> fromApplicationListToApplicationDtoList(List<Application> applications);

}
