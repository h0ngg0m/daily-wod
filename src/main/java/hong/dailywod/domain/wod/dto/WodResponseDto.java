package hong.dailywod.domain.wod.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import lombok.Getter;

@Getter
public class WodResponseDto {

    private Long id;
    private String title;
    private String content;
    private WodType type;
    private LocalDate wodDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public WodResponseDto(Wod wod) {
        this.id = wod.getId();
        this.title = wod.getTitle();
        this.content = wod.getContent();
        this.type = wod.getType();
        this.wodDate = wod.getWodDate();
        this.createdDate = wod.getCreatedDate();
        this.updatedDate = wod.getUpdatedDate();
    }
}
