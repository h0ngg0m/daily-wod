package hong.dailywod.domain.wod;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hong.dailywod.domain.wod.dto.DailyWodResponseDto;
import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.service.WodService;
import hong.dailywod.global.request.Pagination;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WodController {

    private final WodService wodService;

    @GetMapping("/api/v1/wods/daily")
    public ResponseEntity<ApiResult<DailyWodResponseDto>> getDailyWod() {
        return ResponseFactory.ok(wodService.getDailyWod());
    }

    @GetMapping("/admin-api/v1/wods/{id}")
    public ResponseEntity<ApiResult<WodResponseDto>> getWodById(@PathVariable("id") Long id) {
        return ResponseFactory.ok(wodService.getWodById(id));
    }

    @GetMapping("/admin-api/v1/wods")
    public ResponseEntity<ApiResult<Page<WodResponseDto>>> getWodsByPagination(
            @RequestParam("page") int page,
            @RequestParam("sortDesc") boolean sortDesc,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("itemsPerPage") Integer itemsPerPage) {
        return ResponseFactory.ok(
                wodService.getWodsByPagination(
                        Pagination.of(page, sortDesc, sortBy, itemsPerPage)));
    }

    //    @GetMapping("/admin-api/v1/wods")
    //    public ResponseEntity<ApiResult<List<WodResponseDto>>> getWodsByDateBetween(
    //            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //                    LocalDate startDate,
    //            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //                    LocalDate endDate) {
    //        return ResponseFactory.ok(wodService.getWodsByDateBetween(startDate, endDate));
    //    }

    @PostMapping("/admin-api/v1/wods")
    public ResponseEntity<ApiResult<WodResponseDto>> createWod(
            @Valid @RequestBody WodCreateDto dto) {
        return ResponseFactory.ok(wodService.createWod(dto));
    }

    // 사용자용 와드 기록 조회 api (날짜로)
}
