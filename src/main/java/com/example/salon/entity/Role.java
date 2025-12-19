package com.example.salon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    // IMPORTANT for Set<Role>
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}



//package com.example.salon.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//@Entity
//@Table(name = "role")
//@Getter
//@Setter
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
//    @SequenceGenerator(
//            name = "role_seq",
//            sequenceName = "role_seq",
//            allocationSize = 1
//    )
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String name;
//}
