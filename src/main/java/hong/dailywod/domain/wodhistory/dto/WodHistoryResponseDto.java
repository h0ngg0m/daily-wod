package hong.dailywod.domain.wodhistory.dto;

import hong.dailywod.domain.user.dto.UserResponseDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WodHistoryResponseDto {

    private Long id;
    private Long record;
    private WodResponseDto wod;
    private UserResponseDto user;
}
