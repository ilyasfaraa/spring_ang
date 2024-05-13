package ma.emsi.springang.web;

import ma.emsi.springang.entities.Payment;
import ma.emsi.springang.entities.PaymentStatus;
import ma.emsi.springang.entities.PaymentType;
import ma.emsi.springang.entities.Student;
import ma.emsi.springang.repository.PaymentRepository;
import ma.emsi.springang.repository.StudentRepository;
import ma.emsi.springang.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;
    public PaymentRestController(PaymentRepository paymentRepository,StudentRepository studentRepository){
        this.paymentRepository=paymentRepository;
        this.studentRepository= studentRepository;
    }
    @GetMapping(path="/payments")
    public List<Payment> allPaymets(){
        return paymentRepository.findAll();
    }
    @GetMapping(path="/Students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path="/paymentsByStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path="/paymentsByType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }



    @GetMapping(path = "/payments/id")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }
    @GetMapping(path ="/students")
    public List<Student> allStundets(){
        return studentRepository.findAll();
    }
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/studentsByProgramId")
    public List<Student> getstdentByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }
    @PutMapping("/payment/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status,@PathVariable Long id){
       return paymentService.updatePaymentStatus(status,id);
    }
    @PostMapping(path="/payments",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,double amount,PaymentType type,
                               String studentCode) throws IOException {
        return this.paymentService.savePayment(file,date,amount,type,studentCode);
    }

    @GetMapping(path="paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId)throws IOException{
        return paymentService.getPaymentFile(paymentId);
    }
}
