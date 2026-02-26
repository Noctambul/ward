package fr.wardcare.api.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patients")
public class PatientEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "birth_date", nullable = false)
    public LocalDate birthDate;

    @Column(name = "sex")
    public String sex;

    @Column(name = "phone")
    public String phone;

    @Column(name = "email")
    public String email;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;
}
