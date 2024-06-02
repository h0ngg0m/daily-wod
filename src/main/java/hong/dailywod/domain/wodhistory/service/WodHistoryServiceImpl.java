package hong.dailywod.domain.wodhistory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.user.repository.UserRepository;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.repository.WodRepository;
import hong.dailywod.domain.wodhistory.dto.WodHistoryCreateDto;
import hong.dailywod.domain.wodhistory.dto.WodHistoryResponseDto;
import hong.dailywod.domain.wodhistory.repository.WodHistoryRepository;
import hong.dailywod.global.exception.ClientBadRequestException;
import hong.dailywod.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WodHistoryServiceImpl implements WodHistoryService {

    private final WodHistoryRepository wodHistoryRepository;
    private final WodRepository wodRepository;
    private final UserRepository userRepository;

    @Override
    public WodHistoryResponseDto createWodHistory(WodHistoryCreateDto dto) {
        User user =
                userRepository
                        .findById(dto.getUserId())
                        .orElseThrow(
                                () ->
                                        new ClientBadRequestException(
                                                ExceptionCode.FAIL_NOT_FOUND_DATA,
                                                "존재하지 않는 User입니다. / ID: " + dto.getUserId()));

        Wod wod =
                wodRepository
                        .findById(dto.getWodId())
                        .orElseThrow(
                                () ->
                                        new ClientBadRequestException(
                                                ExceptionCode.FAIL_NOT_FOUND_DATA,
                                                "존재하지 않는 WOD입니다. / ID: " + dto.getWodId()));

        return new WodHistoryResponseDto(wodHistoryRepository.persist(dto.toEntity(user, wod)));
    }
}
