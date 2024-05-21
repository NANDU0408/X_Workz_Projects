package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.RoomDetailsDTO;

import java.util.List;

public interface RoomDetailsRepo {
    public void saveAllDept(List<RoomDetailsDTO> roomDetailsDTOS);
}
