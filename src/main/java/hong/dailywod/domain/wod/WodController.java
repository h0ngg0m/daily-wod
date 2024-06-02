package hong.dailywod.domain.wod;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hong.dailywod.domain.wod.dto.DailyWodResponseDto;
import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.service.WodService;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wods")
public class WodController {

    private final WodService wodService;

    @GetMapping("/daily")
    public ResponseEntity<ApiResult<DailyWodResponseDto>> getDailyWod() {
        return ResponseFactory.ok(wodService.getDailyWod());
    }

    // TODO: 관리자만 사용?
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<WodResponseDto>> getWodById(@PathVariable("id") Long id) {
        return ResponseFactory.ok(wodService.getWodById(id));
    }

    // TODO: 관리자만 사용 가능하도록 권한 설정
    @GetMapping("")
    public ResponseEntity<ApiResult<List<WodResponseDto>>> getWodsByDateBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate endDate) {
        return ResponseFactory.ok(wodService.getWodsByDateBetween(startDate, endDate));
    }

    // TODO: 관리자만 사용 가능하도록 권한 설정
    @PostMapping("")
    public ResponseEntity<ApiResult<WodResponseDto>> createWod(
            @Valid @RequestBody WodCreateDto dto) {
        return ResponseFactory.ok(wodService.createWod(dto));
    }

    // 사용자용 와드 기록 조회 api (날짜로)
}
