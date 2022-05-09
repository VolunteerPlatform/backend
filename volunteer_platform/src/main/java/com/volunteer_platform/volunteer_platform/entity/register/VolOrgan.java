package com.volunteer_platform.volunteer_platform.entity.register;

import com.volunteer_platform.volunteer_platform.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VolOrgan extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "vol_organ_id")
    private Long id;

    @OneToMany(mappedBy = "volOrgan", cascade = CascadeType.ALL)
    private List<VolActivity> volActivities = new ArrayList<>();

    private String manager;
    private String organPhoneNumber;

    @Embedded
    private Address address;
}
