package com.aresback.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boleta_pdf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoletaPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroBoleta;

    private String cliente;

    private String direccion;

    private String dni;

    private LocalDateTime fechaEmision;

    private Double total;


    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    private List<DetalleBoleta> detalles;


}
