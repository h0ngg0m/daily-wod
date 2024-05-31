package hong.dailywod.domain.wod.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.repository.WodRepository;
import hong.dailywod.global.exception.ClientBadRequestException;
import hong.dailywod.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WodServiceImpl implements WodService {

    private final WodRepository wodRepository;

    @Override
    @Transactional(readOnly = true)
    public WodResponseDto getWod() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public WodResponseDto getWodById(Long id) {
        return wodRepository
                .findById(id)
                .map(WodResponseDto::new)
                .orElseThrow(
                        () ->
                                new ClientBadRequestException(
                                        ExceptionCode.FAIL_NOT_FOUND_DATA,
                                        "존재하지 않는 WOD입니다. / ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WodResponseDto> getWodsByDateBetween(LocalDate startDate, LocalDate endDate) {
        return wodRepository.findAllByWodDateBetween(startDate, endDate).stream()
                .map(WodResponseDto::new)
                .toList();
    }

    @Override
    public WodResponseDto createWod(WodCreateDto dto) {
        wodRepository
                .findByWodDateAndType(dto.getWodDate(), dto.getType())
                .ifPresent(
                        wod -> {
                            throw new ClientBadRequestException(
                                    ExceptionCode.FAIL_DUPLICATED_DATA,
                                    "이미 존재하는 WOD입니다. / WOD 날짜: "
                                            + dto.getWodDate()
                                            + ", WOD 타입: "
                                            + dto.getType());
                        });
        return new WodResponseDto(wodRepository.persist(dto.toEntity()));
    }
}
