package hong.dailywod.domain.wodhistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wodhistory.model.WodHistory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WodHistoryCreateDto {

    @NotBlank private String record;

    private Long wodId;

    @NotNull private Long userId;

    public WodHistory toEntity(User user, Wod wod) {
        return new WodHistory(record, wod, user);
    }
}
