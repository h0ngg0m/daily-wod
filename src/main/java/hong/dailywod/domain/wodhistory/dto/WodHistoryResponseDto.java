package hong.dailywod.domain.wodhistory.dto;

import hong.dailywod.domain.user.dto.UserResponseDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wodhistory.model.WodHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WodHistoryResponseDto {

    private Long id;
    private String record;
    private WodResponseDto wod;
    private UserResponseDto user;

    public WodHistoryResponseDto(WodHistory wodHistory) {
        this.id = wodHistory.getId();
        this.record = wodHistory.getRecord();
        this.wod = new WodResponseDto(wodHistory.getWod());
        this.user = new UserResponseDto(wodHistory.getUser());
    }
}
