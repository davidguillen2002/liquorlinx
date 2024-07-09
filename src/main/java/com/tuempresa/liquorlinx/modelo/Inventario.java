package com.tuempresa.liquorlinx.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members = "id; productos { productos }")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    @JoinTable(
        name = "Inventario_Producto",
        joinColumns = @JoinColumn(name = "inventario_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @ListProperties("id, nombre, cantidad")
    private List<Producto> productos = new ArrayList<>();

    // Método para añadir un producto al inventario
    public void añadirProducto(Producto producto, EntityManager entityManager) {
        if (producto != null && producto.getId() != 0 && producto.getNombre() != null) {
            productos.add(producto);
            entityManager.merge(this);
        } else {
            throw new IllegalArgumentException("Producto inválido");
        }
    }

    // Actualiza la cantidad de productos en el inventario
    public void actualizarInventario(List<Producto> nuevosProductos, EntityManager entityManager) {
        for (Producto nuevoProducto : nuevosProductos) {
            boolean productoEncontrado = false;
            for (Producto producto : productos) {
                if (producto.getId() == nuevoProducto.getId()) {
                    producto.setCantidad(nuevoProducto.getCantidad());
                    productoEncontrado = true;
                    break;
                }
            }
            if (!productoEncontrado) {
                productos.add(nuevoProducto);
            }
        }
        entityManager.merge(this);
    }

    // Genera alertas para productos con bajo stock
    public List<String> generarAlertasBajoStock(int limite) {
        List<String> alertas = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCantidad() < limite) {
                alertas.add("Producto " + producto.getNombre() + " tiene bajo stock: " + producto.getCantidad());
            }
        }
        return alertas;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}