package agile.aresback.service;

import agile.aresback.model.dto.BoletaDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class BoletaPdfService {

    public ByteArrayInputStream generarPdf(BoletaDTO boleta) {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Boleta de Venta", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(" "));

            doc.add(new Paragraph("Cliente: " + boleta.getCliente()));
            doc.add(new Paragraph("Detalles: " + boleta.getDetalles()));
            doc.add(new Paragraph("Total: S/ " + boleta.getTotal()));

            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}