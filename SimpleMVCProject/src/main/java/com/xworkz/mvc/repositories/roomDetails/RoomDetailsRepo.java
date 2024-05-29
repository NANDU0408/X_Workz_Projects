package com.xworkz.mvc.repositories.roomDetails;

import com.xworkz.mvc.dto.roomDetails.RoomDetailsDTO;

import java.util.List;

public interface RoomDetailsRepo {
    public void saveAllDept(List<RoomDetailsDTO> roomDetailsDTOS);
}
