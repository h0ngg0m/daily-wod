package hong.dailywod.domain.wod.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private WodType type;

    private LocalDate wodDate;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public Wod(String title, String content, WodType type, LocalDate wodDate) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.wodDate = wodDate;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
}
