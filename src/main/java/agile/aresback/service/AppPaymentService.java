package agile.aresback.service;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.exception.PaymentException;
import com.mercadopago.resources.payment.Payment;

public interface AppPaymentService {

    AppPaymentDTO createPreference(AppPaymentDTO appPaymentDTO) throws PaymentException;

    AppPaymentDTO findByPreferenceId(String preferenceId) throws PaymentException;

    void linkReservationAfterPaymentApproved(String externalReference, Long paymentId) throws PaymentException;

    AppPaymentDTO actualizarPago(AppPaymentDTO dto) throws PaymentException;

    Payment consultarPagoEnMercadoPago(Long paymentId) throws PaymentException; // <- CORREGIDO Y EXPUESTO
}
