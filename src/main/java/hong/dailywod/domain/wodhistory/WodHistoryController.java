package hong.dailywod.domain.wodhistory;

import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;
import hong.dailywod.domain.wodhistory.service.WodHistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wod-histories")
public class WodHistoryController {

    private final WodHistoryService wodHistoryService;

    @PostMapping("")
    public ResponseEntity<ApiResult<WodHistoryResponseDto>> createWodHistory(@Valid @RequestBody WodHistoryCreateDto dto) {
        return ResponseFactory.ok(wodHistoryService.createWodHistory(dto));
    }
}
