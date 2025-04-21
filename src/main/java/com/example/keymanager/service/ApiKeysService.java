package com.example.keymanager.service;

import com.example.keymanager.dto.ApiKeysDTO;

import java.util.List;

public interface ApiKeysService {
    List<ApiKeysDTO> saveApiKeys(List<ApiKeysDTO> apiKeysAddRequest);

    void deleteApiKeys(String apiName);

    List<ApiKeysDTO> updateApiKeys(List<ApiKeysDTO> apiKeysUpdateRequest);

    List<ApiKeysDTO> getAllApiKeys();

    ApiKeysDTO getApiKeyByName(String apiName);
}
