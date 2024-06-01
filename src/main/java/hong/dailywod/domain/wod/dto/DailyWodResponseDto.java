package hong.dailywod.domain.wod.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyWodResponseDto {

    private WodResponseDto metcon;
    private WodResponseDto cardio;

    public DailyWodResponseDto(WodResponseDto metcon, WodResponseDto cardio) {
        this.metcon = metcon;
        this.cardio = cardio;
    }

}
