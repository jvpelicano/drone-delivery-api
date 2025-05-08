package com.pelicano.drone_delivery_api.dto;

/*
 * @created 07/05/2025 - 10:54 PM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private String statusCode;

    private String statusMsg;
}
