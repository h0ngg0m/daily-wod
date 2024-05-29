package hong.dailywod.domain.wod;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.service.WodService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wods")
public class WodController {

    private final WodService wodService;

    @GetMapping("/today")
    public WodResponseDto getWod() {
        return wodService.getWod();
    }

    // TODO: 관리자만 사용?
    @GetMapping("/{id}")
    public WodResponseDto getWodById(@PathVariable Long id) {
        return wodService.getWodById(id);
    }

    // TODO: 관리자만 사용 가능하도록 권한 설정
    @GetMapping("")
    public List<WodResponseDto> getWodsByDateBetween(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate endDate) {
        return wodService.getWodsByDateBetween(startDate, endDate);
    }

    // TODO: 관리자만 사용 가능하도록 권한 설정
    @PostMapping("")
    public WodResponseDto createWod(@Valid @RequestBody WodCreateDto wodCreateDto) {
        return wodService.createWod(wodCreateDto);
    }

    // 사용자용 와드 기록 조회 api (날짜로)
}
