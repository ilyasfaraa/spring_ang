package ma.emsi.springang;

import ma.emsi.springang.entities.Payment;
import ma.emsi.springang.entities.PaymentStatus;
import ma.emsi.springang.entities.PaymentType;
import ma.emsi.springang.entities.Student;
import ma.emsi.springang.repository.PaymentRepository;
import ma.emsi.springang.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringAngApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngApplication.class, args);
    }

    @Bean
    CommandLineRunner commandeLineRunner(StudentRepository studentRepository,
                                         PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                            .firstname("Mohamed").code("112233").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstname("Imane").code("112244").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstname("yasmine").code("112255").programId("GLSID")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstname("najat").code("112266").programId("BDCC")
                    .build());

            PaymentType[] paymentTypes=PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st->{
                for (int i=0; i<10 ;i++){
                    int index= random.nextInt(paymentTypes.length);
                    Payment payment=Payment.builder()
                            .amount(1000+(int)(Math.random()+20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });

        };
    }
}
