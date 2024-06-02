package hong.dailywod.domain.wod.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.wod.dto.DailyWodResponseDto;
import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;
import hong.dailywod.domain.wod.repository.WodRepository;
import hong.dailywod.global.exception.ClientBadRequestException;
import hong.dailywod.global.exception.SystemException;

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
                .isInstanceOf(ClientBadRequestException.class)
                .hasMessage("이미 존재하는 WOD입니다. / WOD 날짜: 2021-08-12, WOD 타입: METCON");
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

    @Test
    void 데일리_와드를_조회할_수_있다() {
        // given
        wodRepository.persist(
                new Wod(
                        "METCON WOD",
                        "5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats",
                        WodType.METCON,
                        LocalDate.now()));
        wodRepository.persist(new Wod("CARDIO WOD", "Run 5km", WodType.CARDIO, LocalDate.now()));

        // when
        DailyWodResponseDto dailyWod = wodService.getDailyWod();
        WodResponseDto metcon = dailyWod.getMetcon();
        WodResponseDto cardio = dailyWod.getCardio();

        // then
        assertThat(metcon.getTitle()).isEqualTo("METCON WOD");
        assertThat(metcon.getContent())
                .isEqualTo("5 Rounds for time: 10 Pull-ups, 20 Push-ups, 30 Air Squats");
        assertThat(metcon.getType()).isEqualTo(WodType.METCON);
        assertThat(metcon.getWodDate()).isEqualTo(LocalDate.now());
        assertThat(metcon.getCreatedDate()).isNotNull();
        assertThat(metcon.getUpdatedDate()).isNotNull();

        assertThat(cardio.getTitle()).isEqualTo("CARDIO WOD");
        assertThat(cardio.getContent()).isEqualTo("Run 5km");
        assertThat(cardio.getType()).isEqualTo(WodType.CARDIO);
        assertThat(cardio.getWodDate()).isEqualTo(LocalDate.now());
        assertThat(cardio.getCreatedDate()).isNotNull();
        assertThat(cardio.getUpdatedDate()).isNotNull();
    }

    @Test
    void 데일리_와드가_존재하지_않으면_시스템_에러를_발생시킨다() {
        // when, then
        assertThatThrownBy(() -> wodService.getDailyWod())
                .isInstanceOf(SystemException.class)
                .hasMessage("오늘 날짜의 METCON WOD가 존재하지 않습니다. / WOD 날짜: " + LocalDate.now());
    }
}
