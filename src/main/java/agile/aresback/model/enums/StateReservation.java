package agile.aresback.model.enums;

public enum StateReservation {

    ANULADA,    //Si la reserva paso los 5 minutos se eliminá la reserva por este estado
    PENDIENTE,  //En proceso de registro de datos y esperando el pago
    RESERVADA   //La reserva fue creada con exito

}
