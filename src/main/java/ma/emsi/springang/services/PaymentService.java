package ma.emsi.springang.services;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import ma.emsi.springang.entities.Payment;
import ma.emsi.springang.entities.PaymentStatus;
import ma.emsi.springang.entities.PaymentType;
import ma.emsi.springang.entities.Student;
import ma.emsi.springang.repository.PaymentRepository;
import ma.emsi.springang.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
        this.studentRepository=studentRepository;
    }
    public Payment savePayment(MultipartFile file, LocalDate date, double amount, PaymentType type,
                               String studentCode) throws IOException {
        Path forlderPath= Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if(!Files.exists(forlderPath)){
            Files.createDirectories(forlderPath);
        }
        String fileName= UUID.randomUUID().toString();
        Path filePath= Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student=studentRepository.findByCode(studentCode);
        Payment payment= Payment.builder()
                .date(date).type(type).student(student)
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }
    public Payment updatePaymentStatus( PaymentStatus status, Long id){
        Payment payment=paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    public byte[] getPaymentFile( Long paymentId)throws IOException{
        Payment payment=paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(payment.getFile()));
    }
}
