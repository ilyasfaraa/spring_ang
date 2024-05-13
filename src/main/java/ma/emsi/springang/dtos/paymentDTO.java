package ma.emsi.springang.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ma.emsi.springang.entities.PaymentStatus;
import ma.emsi.springang.entities.PaymentType;
import ma.emsi.springang.entities.Student;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class paymentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;


}