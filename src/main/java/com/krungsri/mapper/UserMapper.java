package com.krungsri.mapper;

import com.krungsri.model.RegistrationResponse;
import com.krungsri.repository.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface UserMapper {
    List<RegistrationResponse> toUserListResponse(List<UserEntity> userEntities);
    RegistrationResponse toUserResponse(UserEntity userEntity);
}
