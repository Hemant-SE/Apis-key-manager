package com.example.keymanager.controller;

import com.example.keymanager.controller.model.ResponseDto;
import com.example.keymanager.dto.ApiKeysDTO;
import com.example.keymanager.service.ApiKeysService;
import com.example.keymanager.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/keys")
public class ApiKeysController {
    private ApiKeysService apiKeysService;

    @PostMapping
    public ResponseEntity<ResponseDto<ApiKeysDTO>> saveApiKey(@RequestBody List<ApiKeysDTO> apiKeysAddRequest) {
        List<ApiKeysDTO> savedApiKeys = apiKeysService.saveApiKeys(apiKeysAddRequest);
        return ResponseEntity.status(201)
                .body(ResponseDto.<ApiKeysDTO>builder()
                        .data(savedApiKeys)
                        .message(Constants.SUCCESS)
                        .build());
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<ApiKeysDTO>> updateApiKeys(@RequestBody List<ApiKeysDTO> apiKeysUpdateRequest) {
        List<ApiKeysDTO> updatedApiKey = apiKeysService.updateApiKeys(apiKeysUpdateRequest);
        return ResponseEntity.status(200)
                .body(ResponseDto.<ApiKeysDTO>builder()
                        .data(updatedApiKey)
                        .message(Constants.SUCCESS)
                        .build());
    }

    @GetMapping
    public ResponseDto<ApiKeysDTO> getAllApiKeys() {
        return ResponseDto.<ApiKeysDTO>builder()
                .data(apiKeysService.getAllApiKeys())
                .message(Constants.SUCCESS)
                .build();
    }

    @GetMapping("/name")
    public ResponseDto<ApiKeysDTO> getApiKeyByApiName(@RequestParam String apiName) {
        return ResponseDto.<ApiKeysDTO>builder()
                .data(List.of(apiKeysService.getApiKeyByName(apiName)))
                .message(Constants.SUCCESS)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteApiKey(@RequestParam String apiName) {
        apiKeysService.deleteApiKeys(apiName);
        return ResponseEntity.status(200)
                .body(ResponseDto.<String>builder()
                        .data(List.of("API key deleted successfully"))
                        .message(Constants.SUCCESS)
                        .build());
    }
}
