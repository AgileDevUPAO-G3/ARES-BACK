package agile.aresback.config;

import agile.aresback.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final ReservationService reservationService;

    /**
     * Limpia reservas pendientes con más de 10 minutos de antigüedad.
     * Se ejecuta automáticamente cada minuto.
     */
    @Scheduled(fixedRate = 60000) // Cada 1 minuto
    public void limpiarReservasExpiradas() {
        log.info("Iniciando limpieza automática de reservas expiradas...");
        reservationService.deleteExpiredPendingReservations();
    }
    /**
     * Marca automáticamente como NO ASISTIÓ a los que no llegaron después de 2 minutos.
     */
    @Scheduled(fixedRate = 60000) // cada 1 minuto
    public void marcarAsistenciasNoConfirmadas() {
        log.info("⏱️ Verificando reservas EN_ESPERA que no llegaron...");
        reservationService.markNoShowReservations();
    }


}