package com.volunteer_platform.volunteer_platform.entity.register;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VolOrgan {

    @Id @GeneratedValue
    @Column(name = "vol_organ_id")
    private Long id;

    @OneToMany(mappedBy = "volOrgan")
    private List<VolActivity> volActivities = new ArrayList<>();

    private String manager;
    private String organPhoneNumber;

    @Embedded
    private Address address;
}
