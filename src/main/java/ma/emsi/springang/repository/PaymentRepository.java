package ma.emsi.springang.repository;

import ma.emsi.springang.entities.Payment;
import ma.emsi.springang.entities.PaymentStatus;
import ma.emsi.springang.entities.PaymentType;
import ma.emsi.springang.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List <Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
