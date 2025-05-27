package agile.aresback.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class BoletaService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void generarYEnviarBoleta(String nombre, String correo, double monto) throws Exception {

        // 1. Crear PDF temporal
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("BOLETA DE PAGO"));
        document.add(new Paragraph("Nombre: " + nombre));
        document.add(new Paragraph("Monto: S/ " + monto));
        document.add(new Paragraph("Fecha: " + LocalDate.now()));

        document.close();

        // 2. Preparar correo con PDF adjunto
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(correo);
        helper.setSubject("Boleta de pago");
        helper.setText("Estimado " + nombre + ",\nAdjunto su boleta de pago.");

        DataSource dataSource = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");
        helper.addAttachment("boleta_pago.pdf", dataSource);

        // 3. Enviar correo
        javaMailSender.send(message);
    }
}
