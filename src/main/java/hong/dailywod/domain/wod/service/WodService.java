package hong.dailywod.domain.wod.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import hong.dailywod.domain.wod.dto.DailyWodResponseDto;
import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.global.request.Pagination;

public interface WodService {

    DailyWodResponseDto getDailyWod();

    WodResponseDto getWodById(Long id);

    List<WodResponseDto> getWodsByDateBetween(LocalDate startDate, LocalDate endDate);

    WodResponseDto createWod(WodCreateDto dto);

    Page<WodResponseDto> getWodsByPagination(Pagination pagination);
}
