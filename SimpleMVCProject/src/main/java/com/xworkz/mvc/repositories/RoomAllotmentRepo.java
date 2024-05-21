package com.xworkz.mvc.repositories;

import com.xworkz.mvc.dto.RoomAllotmentDTO;

import java.util.List;

public interface RoomAllotmentRepo {
    public void saveAllDept(List<RoomAllotmentDTO> roomAllotmentDTOS);
}
