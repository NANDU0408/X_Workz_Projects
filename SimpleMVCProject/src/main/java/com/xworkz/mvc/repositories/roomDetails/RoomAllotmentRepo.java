package com.xworkz.mvc.repositories.roomDetails;

import com.xworkz.mvc.dto.roomDetails.RoomAllotmentDTO;

import java.util.List;

public interface RoomAllotmentRepo {
    public void saveAllDept(List<RoomAllotmentDTO> roomAllotmentDTOS);
}
