package com.khamzin.inventoryservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class InventoryErrorResponse {
    private HttpStatus httpStatus;
    private String name;
    private String date;
}
