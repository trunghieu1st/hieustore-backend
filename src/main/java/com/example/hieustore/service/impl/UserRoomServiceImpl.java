package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.response.UserRoomDto;
import com.example.hieustore.domain.entity.Room;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.domain.entity.UserRoom;
import com.example.hieustore.domain.entity.UserRoomId;
import com.example.hieustore.domain.mapper.UserRoomMapper;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.RoomRepository;
import com.example.hieustore.repository.UserRepository;
import com.example.hieustore.repository.UserRoomRepository;
import com.example.hieustore.service.UserRoomService;
import com.example.hieustore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoomServiceImpl implements UserRoomService {
    private final UserRoomRepository userRoomRepository;
    private final UserRoomMapper userRoomMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public UserRoomDto getById(UserRoomId userRoomId) {
        UserRoom userRoom = userRoomRepository.findById(userRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.UserRoom.ERR_NOT_FOUND_ID, new String[]{userRoomId.toString()}));
        return userRoomMapper.mapUserRoomToUserRoomDto(userRoom);
    }

    @Override
    public PaginationResponseDto<UserRoomDto> getAll(String userId, PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.USER_ROOM);
        Page<UserRoom> userRoomPage = userRoomRepository.getAll(userId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.MESSAGE, userRoomPage);
        List<UserRoomDto> userRoomDtos = userRoomMapper.mapUserRoomToUserRoomDto(userRoomPage.getContent());
        return new PaginationResponseDto<>(meta, userRoomDtos);
    }

    @Override
    public UserRoomDto create(String roomId, String userId) {
        UserRoomId userRoomId = new UserRoomId(roomId, userId);
        if (userRoomRepository.existsById(userRoomId)) {
            throw new NotFoundException(ErrorMessage.UserRoom.ERR_ALREADY_EXIST, new String[]{userRoomId.getRoomId()});
        }
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{roomId}));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        UserRoom userRoom = new UserRoom();
        userRoom.setId(userRoomId);
        userRoom.setUser(user);
        userRoom.setRoom(room);
        return userRoomMapper.mapUserRoomToUserRoomDto(userRoomRepository.save(userRoom));
    }

}
