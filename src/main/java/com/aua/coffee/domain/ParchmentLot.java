package com.aua.coffee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "parchment_lot")
public class ParchmentLot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String grade;

    private String remarks;

    @Column(nullable = false)
    private int weightKg;

    private LocalDate recordedDate;

    @ManyToOne
    @JoinColumn(name = "cherry_delivery_id")
    private CherryDelivery cherryDelivery;

    public ParchmentLot() {
    }

    public ParchmentLot(String grade, String remarks, int weightKg, LocalDate recordedDate, CherryDelivery cherryDelivery) {
        this.grade = grade;
        this.remarks = remarks;
        this.weightKg = weightKg;
        this.recordedDate = recordedDate;
        this.cherryDelivery = cherryDelivery;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public LocalDate getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(LocalDate recordedDate) {
        this.recordedDate = recordedDate;
    }

    public CherryDelivery getCherryDelivery() {
        return cherryDelivery;
    }

    public void setCherryDelivery(CherryDelivery cherryDelivery) {
        this.cherryDelivery = cherryDelivery;
    }
}
