package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.response.RoomDto;
import com.example.hieustore.domain.entity.Room;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.domain.mapper.RoomMapper;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.RoomRepository;
import com.example.hieustore.repository.UserRepository;
import com.example.hieustore.repository.UserRoomRepository;
import com.example.hieustore.service.RoomService;
import com.example.hieustore.service.UserRoomService;
import com.example.hieustore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomMapper roomMapper;
    private final UserRoomService userRoomService;
    private final UserRoomRepository userRoomRepository;

    @Override
    public RoomDto getById(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Room.ERR_NOT_FOUND_ID, new String[]{id}));
        return roomMapper.mapRoomToRoomDto(room);
    }

    @Override
    public PaginationResponseDto<RoomDto> getAll(PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.ROOM);
        Page<Room> roomPage = roomRepository.getAll(pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.MESSAGE, roomPage);

        List<RoomDto> rooms = roomMapper.mapRoomToRoomDto(roomPage.getContent());
        return new PaginationResponseDto<>(meta, rooms);
    }

    @Override
    public RoomDto create(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        Room room = new Room();
        roomRepository.save(room);

        List<User> userRoom = new ArrayList<>();
        List<User> users = userRepository.findByUserRole(3);
        users.add(user);
        for (User user1 : users) {
            userRoomService.create(room.getId(), user1.getId());
        }
        return roomMapper.mapRoomToRoomDto(room);
    }
}
