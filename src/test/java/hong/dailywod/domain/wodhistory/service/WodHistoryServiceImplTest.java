package hong.dailywod.domain.wodhistory.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.role.model.Role;
import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.user.repository.UserRepository;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import hong.dailywod.domain.wod.repository.WodRepository;
import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;
import hong.dailywod.domain.wodhistory.model.WodHistory;
import hong.dailywod.domain.wodhistory.repository.WodHistoryRepository;

@SpringBootTest
@Transactional
class WodHistoryServiceImplTest {

    @Autowired WodHistoryService wodHistoryService;

    @Autowired WodHistoryRepository wodHistoryRepository;

    @Autowired WodRepository wodRepository;

    @Autowired UserRepository userRepository;

    @Test
    void 와드_기록을_작성할_수_있다() {
        // given
        User savedUser = userRepository.persist(new User("hong", Role.USER));
        Wod savedWod =
                wodRepository.persist(
                        new Wod(
                                "WOD Title Example",
                                "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                                WodType.METCON,
                                LocalDate.now()));

        WodHistoryCreateDto createDto =
                new WodHistoryCreateDto("기록", savedWod.getId(), savedUser.getId());

        // when
        WodHistoryResponseDto wodHistoryResponseDto = wodHistoryService.createWodHistory(createDto);

        // then
        WodHistory wodHistory = wodHistoryRepository.findById(wodHistoryResponseDto.getId()).get();

        assertThat(wodHistoryResponseDto.getId()).isNotNull();
        assertThat(wodHistoryResponseDto.getRecord()).isEqualTo("기록");
        assertThat(wodHistoryResponseDto.getWod().getId()).isEqualTo(savedWod.getId());
        assertThat(wodHistoryResponseDto.getUser().getId()).isEqualTo(savedUser.getId());

        assertThat(wodHistory.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(wodHistory.getWod().getId()).isEqualTo(savedWod.getId());
    }

    @Test
    void 와드_기록들을_조회할_수_있다() {
        // given
        User savedUser = userRepository.persist(new User("hong", Role.USER));
        Wod savedWod1 =
                wodRepository.persist(
                        new Wod("Metcon", "Metcon WOD", WodType.METCON, LocalDate.now()));

        User otherUser = userRepository.persist(new User("other", Role.USER));
        Wod savedWod2 =
                wodRepository.persist(
                        new Wod("Cardio", "Cardio WOD", WodType.CARDIO, LocalDate.now()));

        wodHistoryRepository.persist(new WodHistory("기록1", savedWod1, savedUser));
        wodHistoryRepository.persist(new WodHistory("기록2", savedWod2, otherUser));

        // when
        List<WodHistoryResponseDto> responseDtoList =
                wodHistoryService.getWodHistoriesByDateBetweenAndUserId(
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(1),
                        savedUser.getId());

        // then
        assertThat(responseDtoList.size()).isEqualTo(1);
        assertThat(responseDtoList.get(0).getRecord()).isEqualTo("기록1");
        assertThat(responseDtoList.get(0).getWod().getId()).isEqualTo(savedWod1.getId());
        assertThat(responseDtoList.get(0).getUser().getId()).isEqualTo(savedUser.getId());
    }
}
