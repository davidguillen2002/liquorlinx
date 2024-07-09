package com.tuempresa.liquorlinx.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members = "id, nombre, direccion, telefono, email; pedidos { pedidos }")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 100)
    private String direccion;

    @Column(length = 15)
    private String telefono;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("id, fecha, total")
    private List<Pedido> pedidos;

    // Custom Methods
    public void registrarCliente(String nombre, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public void editarCliente(String nombre, String direccion, String telefono, String email) {
        if (nombre != null) this.nombre = nombre;
        if (direccion != null) this.direccion = direccion;
        if (telefono != null) this.telefono = telefono;
        if (email != null) this.email = email;
    }

    public void eliminarCliente(EntityManager entityManager) {
        for (Pedido pedido : pedidos) {
            pedido.eliminarPedido(entityManager);
        }
        entityManager.remove(this);
    }

    public List<Pedido> verHistorialCompras() {
        return pedidos;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}