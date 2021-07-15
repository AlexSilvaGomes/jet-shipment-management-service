package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShipmentFilter {
    private Integer pageNumber;
    private Integer pageSize;
    private String clientId;
    private DeliveryStatusEnum status;
    private LocalDateTime initDate;
    private LocalDateTime endDate;
    private String shipmentCode;
}
