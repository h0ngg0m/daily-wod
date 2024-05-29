package hong.dailywod.domain.wodhistory.model;

import hong.dailywod.domain.wod.model.Wod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WodHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wod_id")
    private Wod wod;
}
