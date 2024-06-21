package hong.dailywod.domain.wod.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WodResponseDto {

    private Long id;
    private String title;
    private String content;
    private WodType type;
    private LocalDate wodDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WodResponseDto(Wod wod) {
        this.id = wod.getId();
        this.title = wod.getTitle();
        this.content = wod.getContent();
        this.type = wod.getType();
        this.wodDate = wod.getWodDate();
        this.createdAt = wod.getCreatedAt();
        this.updatedAt = wod.getUpdatedAt();
    }
}
