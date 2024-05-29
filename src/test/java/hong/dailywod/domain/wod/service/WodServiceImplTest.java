package hong.dailywod.domain.wod.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import hong.dailywod.domain.wod.repository.WodRepository;

@SpringBootTest
@Transactional
class WodServiceImplTest {

    @Autowired WodService wodService;

    @Autowired WodRepository wodRepository;

    @Test
    void 와드를_생성할_수_있다() {
        // given
        WodCreateDto dto =
                new WodCreateDto(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 12));

        // when
        WodResponseDto responseDto = wodService.createWod(dto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo("WOD Title Example");
        assertThat(responseDto.getContent())
                .isEqualTo("5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats");
        assertThat(responseDto.getType()).isEqualTo(WodType.METCON);
        assertThat(responseDto.getWodDate()).isEqualTo(LocalDate.of(2021, 8, 12));
        assertThat(responseDto.getCreatedDate()).isNotNull();
        assertThat(responseDto.getUpdatedDate()).isNotNull();
    }

    @Test
    void 같은_날짜와_같은_타입의_와드가_이미_존재하면_생성할_수_없다() {
        // given
        wodRepository.persist(
                new Wod(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 12)));

        // when, then
        WodCreateDto sameTypeAndSameDateWod =
                new WodCreateDto(
                        "Another Title...", "WOD...", WodType.METCON, LocalDate.of(2021, 8, 12));
        assertThatThrownBy(() -> wodService.createWod(sameTypeAndSameDateWod))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 해당 날짜에 해당 타입의 WOD가 존재합니다.");
    }

    @Test
    void 와드들을_날짜를_기준으로_조회할_수_있다() {
        // given
        wodRepository.persist(
                new Wod(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 12)));
        wodRepository.persist(
                new Wod(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 13)));
        wodRepository.persist(
                new Wod(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 14)));
        // 조회되면 안 되는 데이터
        wodRepository.persist(
                new Wod(
                        "WOD Title Example",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.of(2021, 8, 15)));

        // when
        LocalDate startDate = LocalDate.of(2021, 8, 12);
        LocalDate endDate = LocalDate.of(2021, 8, 14);
        List<WodResponseDto> wods = wodService.getWodsByDateBetween(startDate, endDate);

        // then
        assertThat(wods).hasSize(3);
    }
}
