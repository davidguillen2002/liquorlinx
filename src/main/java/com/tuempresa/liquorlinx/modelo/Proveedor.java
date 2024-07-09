package com.tuempresa.liquorlinx.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members = "id, nombre, contacto; productos { productos }")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String contacto;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("id, nombre, cantidad")
    private List<Producto> productos;

    // Custom Methods
    public void registrarProveedor(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public void editarProveedor(String nombre, String contacto) {
        if (nombre != null) this.nombre = nombre;
        if (contacto != null) this.contacto = contacto;
    }

    public void eliminarProveedor(EntityManager entityManager) {
        for (Producto producto : productos) {
            producto.eliminarProducto(entityManager);
        }
        entityManager.remove(this);
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}