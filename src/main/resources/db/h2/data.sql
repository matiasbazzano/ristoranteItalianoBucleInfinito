INSERT INTO tipos_mesa (nombre) VALUES ('INTERIOR');
INSERT INTO tipos_mesa (nombre) VALUES ('EXTERIOR');
INSERT INTO tipos_mesa (nombre) VALUES ('VIP');
INSERT INTO tipos_mesa (nombre) VALUES ('PRIVADO');

INSERT INTO comidas_especiales (nombre) VALUES ('ESTÁNDAR');
INSERT INTO comidas_especiales (nombre) VALUES ('VEGETARIANA');
INSERT INTO comidas_especiales (nombre) VALUES ('SIN GLUTEN');

INSERT INTO mesas (numero, capacidad, activa) VALUES (1, 2, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (2, 4, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (3, 4, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (4, 6, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (5, 8, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (6, 2, FALSE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (7, 4, TRUE);
INSERT INTO mesas (numero, capacidad, activa) VALUES (8, 10, TRUE);

INSERT INTO clientes (nombre, apellido, telefono, direccion, ciudad, vip)
    VALUES ('Sonia', 'Qube', '612345678', 'Calle Gran Vía 1', 'San Sebastian', TRUE);
INSERT INTO clientes (nombre, apellido, telefono, direccion, ciudad, vip)
    VALUES ('Elena', 'Nito', '623456789', 'Br. Artigas 1415', 'Montevideo', FALSE);
INSERT INTO clientes (nombre, apellido, telefono, direccion, ciudad, vip)
    VALUES ('Roll', 'Back', '634567890', 'A. Tolosa 9', 'San Sebastian', FALSE);
INSERT INTO clientes (nombre, apellido, telefono, direccion, ciudad, vip)
    VALUES ('Tim', 'Eout', '645678901', 'Av. José Pedro Varela 3312', 'Montevideo', TRUE);

INSERT INTO reservas (dia, hora, cantidad_personas, estado, cliente_id, mesa_id, tipo_mesa_id, comida_especial_id)
    VALUES ('2026-05-20', '20:30', 2, 'CONFIRMADA', 1, 1, 1, 1);
INSERT INTO reservas (dia, hora, cantidad_personas, estado, cliente_id, mesa_id, tipo_mesa_id, comida_especial_id)
    VALUES ('2026-05-21', '13:00', 4, 'PENDIENTE', 2, 2, 2, 2);
INSERT INTO reservas (dia, hora, cantidad_personas, estado, cliente_id, mesa_id, tipo_mesa_id, comida_especial_id)
    VALUES ('2026-05-22', '21:00', 6, 'CONFIRMADA', 3, 4, 3, 3);
INSERT INTO reservas (dia, hora, cantidad_personas, estado, cliente_id, mesa_id, tipo_mesa_id, comida_especial_id)
    VALUES ('2026-04-10', '14:00', 2, 'CANCELADA', 4, NULL, 1, 1);

INSERT INTO visitas (fecha_visita, notas, reserva_id)
    VALUES ('2026-05-20', 'Celebración de aniversario. Todo perfecto.', 1);
INSERT INTO visitas (fecha_visita, notas, reserva_id)
    VALUES ('2026-05-22', 'Grupo de empresa. Solicitaron mesa extra.', 3);
