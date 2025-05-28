package agile.aresback.service;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.model.entity.Reservation;

public interface AppPaymentService {
    AppPaymentDTO createPreference(AppPaymentDTO dto, Reservation reservation);
}
