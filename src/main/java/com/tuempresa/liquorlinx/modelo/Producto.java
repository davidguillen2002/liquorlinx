package com.tuempresa.liquorlinx.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members = "id, nombre, descripcion, precio, cantidad, categoria, proveedor; pedidoProductos { pedidoProductos }")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    private double precio;
    private int cantidad;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("id, pedido.id, cantidad")
    private List<PedidoProducto> pedidoProductos;

    // Custom Methods
    public void crearProducto(String nombre, String descripcion, double precio, int cantidad, String categoria, Proveedor proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public void editarProducto(String nombre, String descripcion, double precio, int cantidad, String categoria, Proveedor proveedor) {
        if (nombre != null) this.nombre = nombre;
        if (descripcion != null) this.descripcion = descripcion;
        if (precio > 0) this.precio = precio;
        if (cantidad >= 0) this.cantidad = cantidad;
        if (categoria != null) this.categoria = categoria;
        if (proveedor != null) this.proveedor = proveedor;
    }

    public void eliminarProducto(EntityManager entityManager) {
        for (PedidoProducto pedidoProducto : pedidoProductos) {
            entityManager.remove(pedidoProducto);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<PedidoProducto> getPedidoProductos() {
        return pedidoProductos;
    }

    public void setPedidoProductos(List<PedidoProducto> pedidoProductos) {
        this.pedidoProductos = pedidoProductos;
    }
}