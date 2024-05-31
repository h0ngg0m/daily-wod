package hong.dailywod.domain.wodhistory.service;

import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;

public interface WodHistoryService {
    WodHistoryResponseDto createWodHistory(WodHistoryCreateDto dto);
}
