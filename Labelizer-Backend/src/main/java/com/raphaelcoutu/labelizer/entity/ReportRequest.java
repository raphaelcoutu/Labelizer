package com.raphaelcoutu.labelizer.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReportRequest {
    Long datasetId;
    List<Label> labels;

    @Override
    public String toString() {
        return "ReportRequest{" +
                "datasetId=" + datasetId +
                ", labels=" + labels +
                '}';
    }
}
