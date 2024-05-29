package hong.dailywod.domain.wod.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WodCreateDto {

    private String title;

    @NotBlank private String content;

    @NotNull private WodType type;

    @NotNull private LocalDate wodDate;

    public Wod toEntity() {
        return new Wod(title, content, type, wodDate);
    }
}
