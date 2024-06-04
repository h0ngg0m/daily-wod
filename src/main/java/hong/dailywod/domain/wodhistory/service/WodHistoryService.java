package hong.dailywod.domain.wodhistory.service;

import java.time.LocalDate;
import java.util.List;

import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;

public interface WodHistoryService {
    WodHistoryResponseDto createWodHistory(WodHistoryCreateDto dto);

    List<WodHistoryResponseDto> getWodHistoriesByDateBetweenAndUserId(
            LocalDate startDate, LocalDate endDate, Long userId);
}
