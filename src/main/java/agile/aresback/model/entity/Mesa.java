@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(name = "capacidad", nullable = false)
private Integer capacidad;

@Column(name = "numero_mesa", nullable = false)
private Integer numeroMesa;

@Enumerated(EnumType.STRING)
private StateTable estado;

@ManyToOne
@JoinColumn(name = "zone_id")
private Zone zone;

@OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
private List<Reservation> reservations;