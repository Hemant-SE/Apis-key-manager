package com.example.keymanager.mapper;

import com.example.keymanager.dto.ApiKeysDTO;
import com.example.keymanager.entity.ApiKeys;
import org.springframework.stereotype.Component;

@Component
public class ApiKeysMapper {

    public ApiKeysDTO toDto(ApiKeys apiKeys) {
        if (apiKeys == null) {
            return null;
        }
        ApiKeysDTO dto = new ApiKeysDTO();
        dto.setApiName(apiKeys.getApiName());
        dto.setApiKey(apiKeys.getApikey());
        return dto;
    }

    public ApiKeys toEntity(ApiKeysDTO dto) {
        if (dto == null) {
            return null;
        }
        ApiKeys apiKeys = new ApiKeys();
        apiKeys.setApiName(dto.getApiName());
        apiKeys.setApikey(dto.getApiKey());
        return apiKeys;
    }
}