package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VolOrgan extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "vol_organ_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "volOrgan")
    private List<VolActivity> volActivities = new ArrayList<>();

    private String manager;

    private String contact;

    @Embedded
    private Address address;

    private Long memberId;
}
