package com.zpd.incident.entity;

import com.zpd.incident.exception.RetiredOfficerAssignmentException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import com.zpd.incident.entity.enums.OfficerSize;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerStatus;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "zpd_officer")
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfficerSize size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfficerStatus status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Officer() {
    }

    // 다른 진행 중 사건이 없는 경찰관을 대기 상태로 복귀시킨다.
    public void returnToStandby() {
        if (status != OfficerStatus.RETIRED) {
            this.status = OfficerStatus.STANDBY;
        }
    }

    // 사건 배정 시 경찰관을 수사 중 상태로 변경하며, 퇴직 경찰관의 배정은 차단한다.
    public void startWorking() {
        if (status == OfficerStatus.RETIRED) {
            throw new RetiredOfficerAssignmentException(id);
        }

        this.status = OfficerStatus.WORKING;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public OfficerSize getSize() {
        return size;
    }

    public District getDistrict() {
        return district;
    }

    public OfficerStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeSpecies(String species) {
        this.species = species;
    }

    public void changeSize(OfficerSize size) {
        this.size = size;
    }

    public void changeDistrict(District district) {
        this.district = district;
    }

    public void retire() {
        this.status = OfficerStatus.RETIRED;
    }
}
