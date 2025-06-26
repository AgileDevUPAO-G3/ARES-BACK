package agile.aresback.service;

import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import agile.aresback.dto.ItemDTO;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoletaService {

    @Autowired
    private JavaMailSender mailSender;


    public File generarBoletaPdf(String cliente, LocalDate fecha, List<ItemDTO> items, BigDecimal total)
            throws IOException, DocumentException {
        String htmlPath = "src/main/resources/templates/boleta.html";
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlPath)));

        String itemsHtml = construirTablaHtml(items);

        htmlContent = htmlContent.replace("{{cliente}}", cliente)
                .replace("{{fecha}}", fecha.toString())
                .replace("{{items}}", itemsHtml)
                .replace("{{total}}", total.toString());

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        File outputFile = new File("boleta_" + cliente.replaceAll(" ", "_") + ".pdf");
        OutputStream os = new FileOutputStream(outputFile);
        renderer.createPDF(os);
        os.close();

        return outputFile;
    }

    public void enviarBoletaPorCorreo(File boletaPdf, String correoDestino)
            throws MessagingException, IOException {
        byte[] pdfBytes = Files.readAllBytes(boletaPdf.toPath());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(correoDestino);
        helper.setSubject("Tu boleta de compra");
        helper.setText("Gracias por tu compra. Adjuntamos tu boleta.");

        helper.addAttachment("boleta.pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(message);
    }

    private String construirTablaHtml(List<ItemDTO> items) {
        StringBuilder sb = new StringBuilder();
        for (ItemDTO item : items) {
            sb.append("<tr>")
                    .append("<td>").append(item.getNombre()).append("</td>")
                    .append("<td>").append(item.getCantidad()).append("</td>")
                    .append("<td>").append(item.getPrecio()).append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }
}
