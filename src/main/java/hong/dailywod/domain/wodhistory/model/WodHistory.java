package hong.dailywod.domain.wodhistory.model;

import jakarta.persistence.*;

import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.wod.model.Wod;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "custom_wod_id")
//    private CustomWod customwod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
