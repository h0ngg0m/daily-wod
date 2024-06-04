package hong.dailywod.domain.wodhistory;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;
import hong.dailywod.domain.wodhistory.service.WodHistoryService;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wod-histories")
public class WodHistoryController {

    private final WodHistoryService wodHistoryService;

    @PostMapping("")
    public ResponseEntity<ApiResult<WodHistoryResponseDto>> createWodHistory(
            @Valid @RequestBody WodHistoryCreateDto dto) {
        return ResponseFactory.ok(wodHistoryService.createWodHistory(dto));
    }

    @GetMapping("")
    public ResponseEntity<ApiResult<List<WodHistoryResponseDto>>>
            getWodHistoriesByDateBetweenAndUserId(
                    @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            LocalDate startDate,
                    @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            LocalDate endDate,
                    @RequestParam("userId") Long userId) {
        return ResponseFactory.ok(
                wodHistoryService.getWodHistoriesByDateBetweenAndUserId(startDate, endDate, userId));
    }
}
