package com.example.keymanager.service.impl;

import com.example.keymanager.controller.model.ErrorCode;
import com.example.keymanager.dto.ApiKeysDTO;
import com.example.keymanager.entity.ApiKeys;
import com.example.keymanager.exceptions.AlreadyExistsException;
import com.example.keymanager.exceptions.BadRequestException;
import com.example.keymanager.exceptions.ResourceNotFoundException;
import com.example.keymanager.mapper.ApiKeysMapper;
import com.example.keymanager.repository.ApiKeysRepository;
import com.example.keymanager.service.ApiKeysService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class ApiKeysServiceImpl implements ApiKeysService {
    private ApiKeysRepository apiKeysRepository;
    private ApiKeysMapper apiKeysMapper;

    @Override
    public List<ApiKeysDTO> saveApiKeys(List<ApiKeysDTO> apiKeysAddRequest) {
        if (apiKeysAddRequest == null || apiKeysAddRequest.isEmpty()) {
            throw new BadRequestException(ErrorCode.ERR_400000, "API keys list cannot be null or empty");
        }

       apiKeysAddRequest.forEach(apiKeysDTO -> apiKeysDTO.setApiName(apiKeysDTO.getApiName().toUpperCase()));

        // Check for duplicate API names
        checkDuplicateApiNames(apiKeysAddRequest);

        // Map DTOs to entities
        List<ApiKeys> apiKeysEntities = apiKeysAddRequest.stream()
                .map(apiKeysMapper::toEntity)
                .toList();

        // Save all entities to the database
        List<ApiKeys> savedEntities = apiKeysRepository.saveAll(apiKeysEntities);

        return savedEntities.stream()
                .map(apiKeysMapper::toDto)
                .toList();
    }

    private void checkDuplicateApiNames(List<ApiKeysDTO> apiKeysAddRequest) {
        List<String> apiNames = apiKeysAddRequest.stream()
                .map(ApiKeysDTO::getApiName)
                .toList();
        List<ApiKeys> existingApiKeys = apiKeysRepository.findByApiNameIn(apiNames);

        if (!existingApiKeys.isEmpty()) {
            String existingNames = existingApiKeys.stream()
                    .map(ApiKeys::getApiName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            throw new AlreadyExistsException(ErrorCode.ERR_409000, "API keys with following names already exist: {0}", existingNames);
        }
    }

    @Override
    public void deleteApiKeys(String apiName) {
        ApiKeys apiKey = apiKeysRepository.findByApiName(apiName.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_400000, "API key not found for provided api name", apiName));
        apiKeysRepository.delete(apiKey);
    }

    @Override
    public List<ApiKeysDTO> updateApiKeys(List<ApiKeysDTO> apiKeysUpdateRequest) {
        if (apiKeysUpdateRequest == null || apiKeysUpdateRequest.isEmpty()) {
            throw new BadRequestException(ErrorCode.ERR_400000, "API keys update list cannot be null or empty");
        }
        apiKeysUpdateRequest.forEach(apiKeysDTO -> apiKeysDTO.setApiName(apiKeysDTO.getApiName().toUpperCase()));

        List<ApiKeys> existingApiKeys = getExistingApiKeys(apiKeysUpdateRequest);

        // Map the update request to the existing entities
        List<ApiKeys> updatedApiKeys = existingApiKeys.stream()
                .map(existingApiKey -> {
                    apiKeysUpdateRequest.stream()
                            .filter(dto -> dto.getApiName().equals(existingApiKey.getApiName()))
                            .findFirst().ifPresent(updateRequest -> existingApiKey.setApikey(updateRequest.getApiKey()));
                    return existingApiKey;
                })
                .toList();

        // Save the updated entities
        List<ApiKeys> savedApiKeys = apiKeysRepository.saveAll(updatedApiKeys);

        // Map the saved entities back to DTOs
        return savedApiKeys.stream()
                .map(apiKeysMapper::toDto)
                .toList();
    }

    private List<ApiKeys> getExistingApiKeys(List<ApiKeysDTO> apiKeysUpdateRequest) {
        // Extract API names from the request
        List<String> apiNames = apiKeysUpdateRequest.stream()
                .map(ApiKeysDTO::getApiName)
                .toList();

        // Fetch existing API keys from the database
        List<ApiKeys> existingApiKeys = apiKeysRepository.findByApiNameIn(apiNames);

        if (existingApiKeys.isEmpty()) {
            throw new ResourceNotFoundException(ErrorCode.ERR_404000, "No matching API keys found in the database for update");
        }
        return existingApiKeys;
    }

    @Override
    public List<ApiKeysDTO> getAllApiKeys() {
        List<ApiKeys> apiKeys = apiKeysRepository.findAll();
        return apiKeys.stream()
                .map(apiKey -> apiKeysMapper.toDto(apiKey))
                .toList();
    }

    @Override
    public ApiKeysDTO getApiKeyByName(String apiName) {
        ApiKeys apiKey = apiKeysRepository.findByApiName(apiName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("API key not found"));
        return apiKeysMapper.toDto(apiKey);
    }
}
