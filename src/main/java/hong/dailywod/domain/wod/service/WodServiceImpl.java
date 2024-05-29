package hong.dailywod.domain.wod.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.wod.dto.WodCreateDto;
import hong.dailywod.domain.wod.dto.WodResponseDto;
import hong.dailywod.domain.wod.repository.WodRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 와드 입니다"));
    }

    @Override
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
                            // TODO: 예외 처리 다듬기
                            throw new IllegalArgumentException("이미 해당 날짜에 해당 타입의 WOD가 존재합니다.");
                        });
        return new WodResponseDto(wodRepository.persist(dto.toEntity()));
    }
}
